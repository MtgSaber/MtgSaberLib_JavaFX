package net.mtgsaber.lib.javafx.standardgui;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TextArea;
import net.mtgsaber.lib.threads.Tickable;

import java.io.OutputStream;
import java.io.PrintStream;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author MtgSaber (9/18/2019)
 */

public class Console extends OutputStream implements Tickable {
    public final TextArea TA_CONSOLE;
    private final StringBuilder STR_BUFFER;

    public Console(TextArea TA_CONSOLE) {
        super();
        this.TA_CONSOLE = TA_CONSOLE;
        STR_BUFFER = new StringBuilder();
    }

    @Override
    public synchronized void write(int b) {
        STR_BUFFER.append((char) b);
    }

    public synchronized void tick() {
        if (STR_BUFFER.length() > 0) {
            String buffer = STR_BUFFER.toString();
            STR_BUFFER.replace(0, STR_BUFFER.length(), "");

            Platform.runLater(() -> {
                while (TA_CONSOLE.getLength() > 8192)
                    TA_CONSOLE.deleteText(0, 4096);
                TA_CONSOLE.appendText(buffer);
            });
        }
    }

    public synchronized void run() {
        tick();
    }

    public static class ConsolePrinter extends PrintStream {
        public final Console CONSOLE;
        public final BooleanProperty PROP_DOTIMESTAMP;
        public static final DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        public static final ZoneId zoneId = ZoneId.systemDefault();
        PrintStream out, err;

        public ConsolePrinter(Console CONSOLE, Boolean doTimestamps) {
            super(CONSOLE);
            this.CONSOLE = CONSOLE;
            PROP_DOTIMESTAMP = new SimpleBooleanProperty(this, "PROP_DOTIMESTAMP", doTimestamps);
        }

        public synchronized void println(String str) {
            if (PROP_DOTIMESTAMP.get())
                super.println(getTimeStamp() + str);
            else
                super.println(str);
        }

        public synchronized PrintStream printf(String f, Object... args) {
            if (PROP_DOTIMESTAMP.get()) {
                String timeStamp = getTimeStamp();
                return super.printf("%" + timeStamp.length() + "s " + f, getTimeStamp(), args);
            }
            return super.printf(f, args);
        }

        public static String getTimeStamp() {
            ZonedDateTime nowTime = ZonedDateTime.ofInstant(Instant.now(), zoneId);
            return "[" + nowTime.format(format) + "]:\t";
        }

        public synchronized void setSystemPS(boolean out, boolean err) {
            if (out) {
                this.out = System.out;
                System.setOut(this);
            }
            if (err) {
                this.err = System.err;
                System.setErr(this);
            }
        }

        public synchronized void resetSystemPS(boolean out, boolean err) {
            if (out && System.out == this && this.out != null) {
                System.setOut(this.out);
            }
            if (err && System.err == this && this.err != null) {
                System.setOut(this.err);
            }
        }

        public Console getConsole() { return CONSOLE; }
    }
}

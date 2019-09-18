import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.mtgsaber.lib.javafx.StdApp;
import net.mtgsaber.lib.javafx.standardgui.Console;
import net.mtgsaber.lib.javafx.standardgui.StdPane;
import net.mtgsaber.lib.javafx.standardgui.StdPaneEvents;
import net.mtgsaber.lib.threads.Clock;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * @author MtgSaber (9/18/2019)
 */

public class TestConsole extends StdApp {
    private static final String[] MESSAGES = new String[] {
            "Hello World!",
            "I can't let you do that.",
            "Bazinga!",
            "Bazonga!",
            "Give me your toes.",
            "Your toes, hand 'em over!",
            "The cat is a ghost."
    };

    private final Clock CLK_MAIN = new Clock(10);
    private final Thread THRD_CLK_MAIN = new Thread(CLK_MAIN);
    private final MainPane PANE = new MainPane();
    private final MainPaneEvents EVENTS = new MainPaneEvents(PANE, this);

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Console Test 1 - MtgSaber");
        EVENTS.hookEvents();
        EVENTS.hookStage(primaryStage);
        CLK_MAIN.add(PANE.OUT.getConsole());
        CLK_MAIN.start();
        THRD_CLK_MAIN.start();
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        CLK_MAIN.stop();
        super.stop();
    }

    private static class MainPane extends StdPane {
        final TextArea TA_CONSOLE;
        final Text TXT_LABEL_TA_CONSOLE;
        final Button BT_GENERATE_MESSAGE;
        final Console.ConsolePrinter OUT;

        MainPane() {
            super("TestConsole Main Pane", true, true);

            TA_CONSOLE = new TextArea();
            TXT_LABEL_TA_CONSOLE = new Text("Messages:");
            BT_GENERATE_MESSAGE = new Button("Generate Message");
            OUT = new Console.ConsolePrinter(new Console(TA_CONSOLE), true);

            super.build(
                    Arrays.asList(BT_GENERATE_MESSAGE, TXT_LABEL_TA_CONSOLE, TA_CONSOLE),
                    Collections.singleton(BT_GENERATE_MESSAGE),
                    () -> TA_CONSOLE.setEditable(false)
            );
        }
    }

    private static class MainPaneEvents extends StdPaneEvents {
        private static final Random RNG = new Random();

        MainPaneEvents(MainPane pane, TestConsole app) { super(pane, app); }

        @Override
        public void hookEvents() {
            super.hookEvents();
            ((MainPane) PANE).BT_GENERATE_MESSAGE.setOnAction(event ->
                ((MainPane) PANE).OUT.println(MESSAGES[RNG.nextInt(MESSAGES.length)])
            );
        }

        @Override
        public void hookStage(Stage stage) {
            super.hookStage(stage);
        }
    }
}

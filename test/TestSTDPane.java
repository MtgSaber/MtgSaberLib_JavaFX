import javafx.stage.Stage;
import net.mtgsaber.lib.javafx.StdApp;
import net.mtgsaber.lib.javafx.standardgui.StdPane;
import net.mtgsaber.lib.javafx.standardgui.StdPaneEvents;

/**
 * @author MtgSaber (9/18/2019)
 */

public class TestSTDPane extends StdApp {
    public void start(Stage stage) {
        StdPane pane = new StdPane("TestPane", true, true);
        pane.build(null, null, null);
        StdPaneEvents events = new StdPaneEvents(pane, this);
        events.hookEvents();
        events.hookStage(stage);
        stage.show();
    }
}

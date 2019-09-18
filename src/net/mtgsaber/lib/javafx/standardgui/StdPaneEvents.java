package net.mtgsaber.lib.javafx.standardgui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import net.mtgsaber.lib.javafx.StdApp;

/**
 * @author MtgSaber (9/18/2019)
 */

public class StdPaneEvents {
    protected final StdPane PANE;

    public final StdApp APP;

    public StdPaneEvents (StdPane pane, StdApp app) {
        PANE = pane;
        APP = app;
    }

    public void hookEvents() {
        PANE.PROP_IS_HOOKED.set(true);
    }
    public void unhookEvents() {
        PANE.PROP_IS_HOOKED.set(false);
    }

    public void hookStage(Stage stage) {
        PANE.PROP_IS_ROOT.set(true);
        if (PANE.myScene == null)
            PANE.myScene = new Scene(PANE);
        stage.setScene(PANE.myScene);
    }
    public void unhookStage(Stage stage) {
        PANE.PROP_IS_ROOT.set(false);
    }
}

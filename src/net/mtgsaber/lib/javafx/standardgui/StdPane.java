package net.mtgsaber.lib.javafx.standardgui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author MtgSaber (9/18/2019)
 */

public class StdPane extends VBox {
    protected interface BuildInstr {
        void build();
    }

    public final BooleanProperty
            PROP_IS_ROOT,
            PROP_HAS_STATUS,
            PROP_IS_HOOKED;

    public final StringProperty
            PROP_NAME,
            PROP_STATUS;

    public final TextArea TA_STATUS;
    public final Text TXT_TA_STATUS;
    public final Separator SEP_STATUS_BLOCK;

    protected Scene myScene;

    private final HashSet<Control> CONTROLS;

    public StdPane(String name, Boolean isRoot, Boolean hasStatus) {
        super(8);
        super.setPadding(new Insets(8));

        PROP_IS_ROOT = new SimpleBooleanProperty(isRoot);
        PROP_HAS_STATUS = new SimpleBooleanProperty(hasStatus);
        PROP_IS_HOOKED = new SimpleBooleanProperty(false);

        PROP_NAME = new SimpleStringProperty(name);
        PROP_STATUS = new SimpleStringProperty("Idle");

        TA_STATUS = new TextArea();
        TA_STATUS.textProperty().bind(PROP_STATUS);
        TA_STATUS.setPrefWidth(192);
        TA_STATUS.setPrefHeight(64);

        TXT_TA_STATUS = new Text("Status:");

        SEP_STATUS_BLOCK = new Separator(Orientation.HORIZONTAL);
        SEP_STATUS_BLOCK.setValignment(VPos.CENTER);
        SEP_STATUS_BLOCK.setHalignment(HPos.CENTER);
        SEP_STATUS_BLOCK.prefWidthProperty().bind(TA_STATUS.widthProperty().subtract(8));

        CONTROLS = new HashSet<>();

        if (isRoot)
            myScene = new Scene(this);
    }

    public void build(Collection<Node> children, Collection<Control> controls, BuildInstr instructions) {
        if (instructions != null)
            instructions.build();

        if (controls != null)
            CONTROLS.addAll(controls);

        if (children != null)
            super.getChildren().addAll(children);

        if (PROP_HAS_STATUS.get())
            super.getChildren().addAll(SEP_STATUS_BLOCK, TXT_TA_STATUS, TA_STATUS);
    }

    public void lockControls() {
        for (Control control : CONTROLS)
            control.setDisable(true);
    }

    public void unlockControls() {
        for (Control control : CONTROLS)
            control.setDisable(false);
    }
}

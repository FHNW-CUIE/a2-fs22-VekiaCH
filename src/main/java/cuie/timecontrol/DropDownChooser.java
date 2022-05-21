package cuie.timecontrol;

import cuie.timecontrol_manufactory.MyTimeControl;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



public class DropDownChooser extends VBox {
    private static final String STYLE_CSS = "dropDownChooser.css";

    private final TimeControl timeControl;

    private Label text;
    private CheckBox box;

    public DropDownChooser(TimeControl timeControl) {
        this.timeControl = timeControl;
        initializeSelf();
        initializeParts();
        layoutParts();
        setupEventHandlers();
        setupBindings();
    }

    private void initializeSelf() {
        String stylesheet = getClass().getResource(STYLE_CSS).toExternalForm();
        getStylesheets().add(stylesheet);

        getStyleClass().add("dropdown-chooser");
    }

    private void initializeParts() {
        text = new Label("Colorblind Mode");
        text.getStyleClass().add("text");
        box = new CheckBox();
        box.selectedProperty().set(false);
    }

    private void layoutParts() {
        getChildren().addAll(new HBox(5, text, box));
    }

    private void setupEventHandlers(){
        box.setOnAction(event -> {
            timeControl.setColorblind(box.selectedProperty().get());
        });
    }

    private void setupBindings() {
    }
}

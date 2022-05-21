package cuie.timecontrol;

import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;


class TimeSkin extends SkinBase<TimeControl> {

    private Label placeHolder;

    TimeSkin(TimeControl control) {
        super(control);
        initializeSelf();
        initializeParts();
        layoutParts();
        setupValueChangeListeners();
        setupBindings();
    }

    private void initializeSelf() {
        // getSkinnable().loadFonts("/fonts/Lato/Lato-Reg.ttf", "/fonts/Lato/Lato-Lig.ttf");
        getSkinnable().addStylesheetFiles("style.css");
    }

    private void initializeParts() {

        placeHolder = new Label("To be replaced");

    }

    private void layoutParts() {
        getChildren().addAll(placeHolder);
    }

    private void setupValueChangeListeners() {
    }

    private void setupBindings() {
    }
}

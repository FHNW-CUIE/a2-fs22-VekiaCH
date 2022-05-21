package cuie.timecontrol;

import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.text.Font;

public class TimeControl extends Control {

    private final SkinType skinType;

    public TimeControl(SkinType skinType) {
        this.skinType = skinType;
        initializeSelf();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return skinType.getFactory().apply(this);
    }

    private void initializeSelf() {
        getStyleClass().add("time-control");
    }


    public void loadFonts(String... font){
        for(String f : font){
            Font.loadFont(getClass().getResourceAsStream(f), 0);
        }
    }

    public void addStylesheetFiles(String... stylesheetFile){
        for(String file : stylesheetFile){
            String stylesheet = getClass().getResource(file).toExternalForm();
            getStylesheets().add(stylesheet);
        }
    }
}

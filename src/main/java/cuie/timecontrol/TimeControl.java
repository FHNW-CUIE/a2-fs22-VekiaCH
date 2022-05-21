package cuie.timecontrol;

import javafx.beans.property.*;
import javafx.css.PseudoClass;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.util.converter.LocalTimeStringConverter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class TimeControl extends Control {

    private static final PseudoClass COLORBLIND_CLASS = PseudoClass.getPseudoClass("colorblind");

    private final SkinType skinType;

    private final ObjectProperty<LocalTime> gameTime = new SimpleObjectProperty<>();
    private final StringProperty gameTimeAsString = new SimpleStringProperty();
    private final BooleanProperty gameTimeValid = new SimpleBooleanProperty(true);

    private final ObjectProperty<LocalTime> redRedTime = new SimpleObjectProperty<>();
    private final StringProperty redRedTimeAsString = new SimpleStringProperty();
    private final BooleanProperty redRedAlive = new SimpleBooleanProperty();
    private final BooleanProperty redRedValid = new SimpleBooleanProperty(true);

    private final ObjectProperty<LocalTime> blueBlueTime = new SimpleObjectProperty<>();
    private final StringProperty blueBlueTimeAsString = new SimpleStringProperty();
    private final BooleanProperty blueBlueAlive = new SimpleBooleanProperty();
    private final BooleanProperty blueBlueValid = new SimpleBooleanProperty(true);

    private final BooleanProperty pause = new SimpleBooleanProperty();
    private final BooleanProperty editable = new SimpleBooleanProperty();

    private final BooleanProperty colorblind = new SimpleBooleanProperty(){
        @Override
        protected void invalidated() {
            pseudoClassStateChanged(COLORBLIND_CLASS, get());
        }
    };

    private Image image;
    private Image imageColorblind;

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



        gameTimeAsString.bindBidirectional(gameTime,
                new LocalTimeStringConverter(DateTimeFormatter.ofPattern("mm:ss"),
                        DateTimeFormatter.ofPattern("HH:mm:ss")){
            @Override
            public LocalTime fromString(String value) {
                try {
                    //"00:"+ because LocalTime needs to know the hour for some reason (Exception thrown even
                    // with a valid Format),
                    // seriously this took me 2 hours to fix
                    LocalTime time =  super.fromString("00:"+value);
                    setGameTimeValid(true);
                    return time;
                }
                catch (Exception e){
                    setGameTimeValid(false);
                    return getGameTime();
                }
            }
        });

        redRedTimeAsString.bindBidirectional(redRedTime,
                new LocalTimeStringConverter(DateTimeFormatter.ofPattern("mm:ss"),
                        DateTimeFormatter.ofPattern("HH:mm:ss")){

                    @Override
                    public LocalTime fromString(String value) {
                        try {
                            //"00:"+ because LocalTime needs to know the hour for some reason (Exception thrown even
                            // with a valid Format),
                            // seriously this took me 2 hours to fix
                            LocalTime time =  super.fromString("00:"+value);
                            setRedRedValid(true);
                            return time;
                        }
                        catch (Exception e){
                            setRedRedValid(false);
                            return getRedRedTime();
                        }
                    }
                });

        blueBlueTimeAsString.bindBidirectional(blueBlueTime,
                new LocalTimeStringConverter(DateTimeFormatter.ofPattern("mm:ss"),
                        DateTimeFormatter.ofPattern("HH:mm:ss")){

            @Override
            public LocalTime fromString(String value) {
                try {
                    //"00:"+ because LocalTime needs to know the hour for some reason (Exception thrown even
                    // with a valid Format),
                    // seriously this took me 2 hours to fix
                    LocalTime time =  super.fromString("00:"+value);
                    setBlueBlueValid(true);
                    return time;
                }
                catch (Exception e){
                    setBlueBlueValid(false);
                    return getBlueBlueTime();
                }
            }
        });
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

    public LocalTime getGameTime() {
        return gameTime.get();
    }

    public ObjectProperty<LocalTime> gameTimeProperty() {
        return gameTime;
    }

    public void setGameTime(int m, int s){
        gameTime.setValue(LocalTime.of(0,m,s));
    }

    public String getGameTimeAsString() {
        return gameTimeAsString.get();
    }

    public StringProperty gameTimeAsStringProperty() {
        return gameTimeAsString;
    }

    public boolean isGameTimeValid() {
        return gameTimeValid.get();
    }

    public BooleanProperty gameTimeValidProperty() {
        return gameTimeValid;
    }

    public void setGameTimeValid(boolean b){
        gameTimeValid.set(b);
    }

    public void resetGameTime() {
        gameTimeAsString.setValue(getGameTime().format(DateTimeFormatter.ofPattern("mm:ss")));
    }

    public boolean isRedRedAlive() {
        return redRedAlive.get();
    }

    public BooleanProperty redRedAliveProperty() {
        return redRedAlive;
    }

    public void setRedRedAlive(boolean b){
        redRedAlive.set(b);
    }

    public LocalTime getRedRedTime() {
        return redRedTime.get();
    }

    public ObjectProperty<LocalTime> redRedTimeProperty() {
        return redRedTime;
    }

    public void setRedRedTime(int m, int s){
        redRedTime.setValue(LocalTime.of(0,m,s));
    }

    public String getRedRedTimeAsString() {
        return redRedTimeAsString.get();
    }

    public StringProperty redRedTimeAsStringProperty() {
        return redRedTimeAsString;
    }

    public boolean isRedRedValid() {
        return redRedValid.get();
    }

    public BooleanProperty redRedValidProperty() {
        return redRedValid;
    }

    public void setRedRedValid(boolean b){
        redRedValid.set(b);
    }

    public void resetRedRedTime(){
        redRedTimeAsString.setValue(getRedRedTime().format(DateTimeFormatter.ofPattern("mm:ss")));
    }

    public boolean isBlueBlueAlive() {
        return blueBlueAlive.get();
    }

    public BooleanProperty blueBlueAliveProperty() {
        return blueBlueAlive;
    }

    public void setBlueBlueAlive(boolean b){
        blueBlueAlive.set(b);
    }

    public LocalTime getBlueBlueTime() {
        return blueBlueTime.get();
    }

    public ObjectProperty<LocalTime> blueBlueTimeProperty() {
        return blueBlueTime;
    }

    public void setBlueBlueTime(int m, int s){
        blueBlueTime.setValue(LocalTime.of(0,m,s));
    }

    public String getBlueBlueTimeAsString() {
        return blueBlueTimeAsString.get();
    }

    public StringProperty blueBlueTimeAsStringProperty() {
        return blueBlueTimeAsString;
    }

    public boolean isBlueBlueValid() {
        return blueBlueValid.get();
    }

    public BooleanProperty blueBlueValidProperty() {
        return blueBlueValid;
    }

    public void setBlueBlueValid(boolean b){
        blueBlueValid.set(b);
    }

    public void resetBlueBlueTime() {
        blueBlueTimeAsString.setValue(getBlueBlueTime().format(DateTimeFormatter.ofPattern("mm:ss")));
    }

    public boolean isPause() {
        return pause.get();
    }

    public BooleanProperty pauseProperty() {
        return pause;
    }

    public void setPause(boolean b) {
        pause.set(b);
    }

    public boolean isEditable() {
        return editable.get();
    }

    public BooleanProperty editableProperty() {
        return editable;
    }

    public void setEditable(boolean b){
        editable.set(b);
    }

    public boolean isColorblind() {
        return colorblind.get();
    }

    public BooleanProperty colorblindProperty() {
        return colorblind;
    }

    public void setColorblind(boolean b){
        this.colorblind.set(b);
    }

    public Image getImage() {
        if(colorblind.get()) {
            return new Image(Objects.requireNonNull(getClass().getResourceAsStream("Summoner's_Rift_Minimap_Colorblind.png")));
        } else return new Image(Objects.requireNonNull(getClass().getResourceAsStream("Summoner's_Rift_Minimap.png")));
    }
}

package cuie.timecontrol.demo;

import java.time.LocalTime;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class PresentationModel {
    private final ObjectProperty<LocalTime> gameTime = new SimpleObjectProperty<>(LocalTime.of(0,0,0));
    private final ObjectProperty<LocalTime> blueBlueTime = new SimpleObjectProperty<>(LocalTime.of(0,1,30));
    private final ObjectProperty<LocalTime> redRedTime = new SimpleObjectProperty<>(LocalTime.of(0,1,30));
    private final BooleanProperty           blueAlive = new SimpleBooleanProperty(false);
    private final BooleanProperty           redAlive = new SimpleBooleanProperty(false);
    private final StringProperty            label     = new SimpleStringProperty("Time (mm:ss)");
    private final BooleanProperty           pause = new SimpleBooleanProperty(true);
    private final BooleanProperty           editable  = new SimpleBooleanProperty(false);


    public LocalTime getGameTime() {
        return gameTime.get();
    }

    public ObjectProperty<LocalTime> gameTimeProperty() {
        return gameTime;
    }

    public void setGameTime(LocalTime startTime) {
        this.gameTime.set(startTime);
    }

    public String getLabel() {
        return label.get();
    }

    public StringProperty labelProperty() {
        return label;
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    public boolean isMandatory() {
        return pause.get();
    }

    public BooleanProperty pauseProperty() {
        return pause;
    }

    public void setMandatory(boolean pause) {
        this.pause.set(pause);
    }

    public boolean isEditable() {
        return editable.get();
    }

    public BooleanProperty editableProperty() {
        return editable;
    }

    public void setEditable(boolean readOnly) {
        this.editable.set(readOnly);
    }

    public LocalTime getBlueBlueTime() {
        return blueBlueTime.get();
    }

    public ObjectProperty<LocalTime> blueBlueTimeProperty() {
        return blueBlueTime;
    }

    public LocalTime getRedRedTime() {
        return redRedTime.get();
    }

    public ObjectProperty<LocalTime> redRedTimeProperty() {
        return redRedTime;
    }

    public boolean isBlueAlive() {
        return blueAlive.get();
    }

    public BooleanProperty blueAliveProperty() {
        return blueAlive;
    }

    public boolean isRedAlive() {
        return redAlive.get();
    }

    public BooleanProperty redAliveProperty() {
        return redAlive;
    }
}


package cuie.timecontrol.demo;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import cuie.timecontrol.SkinType;
import cuie.timecontrol.TimeControl;


public class DemoPane extends BorderPane {
    private final PresentationModel pm;

    private TimeControl buffControl;

    private Label gameTimeLabel;
    private Slider gameMinuteSlider;
    private Slider gameSecondSlider;

    private Label redTimeLabel;
    private Slider redMinuteSlider;
    private Slider redSecondSlider;

    private Label blueTimeLabel;
    private Slider blueMinuteSlider;
    private Slider blueSecondSlider;

    private CheckBox editableBox;
    private CheckBox pauseBox;
    private CheckBox  redAliveBox;
    private CheckBox  blueAliveBox;

    public DemoPane(PresentationModel pm) {
        this.pm = pm;
        initializeControls();
        layoutControls();
        setupValueChangeListeners();
        setupBindings();
    }

    private void initializeControls() {
        setPadding(new Insets(10));

        buffControl = new TimeControl(SkinType.DEFAULT_TYPE);

        gameTimeLabel = new Label();
        gameMinuteSlider   = new Slider(0, 59, 0);
        gameSecondSlider = new Slider(0, 59, 0);

        redTimeLabel = new Label();
        redMinuteSlider   = new Slider(0, 59, 0);
        redSecondSlider = new Slider(0, 59, 0);

        blueTimeLabel = new Label();
        blueMinuteSlider   = new Slider(0, 59, 0);
        blueSecondSlider = new Slider(0, 59, 0);

        editableBox = new CheckBox();
        pauseBox = new CheckBox();
        redAliveBox = new CheckBox();
        blueAliveBox = new CheckBox();
    }

    private void layoutControls() {
        VBox box = new VBox(10, new Label("Time Control Properties"),
                            new HBox(5, new Label("Game Time:"), gameTimeLabel),
                            gameMinuteSlider, gameSecondSlider,
                            new HBox(5, new Label("Red Team Red Buff Respawn Time:"), redTimeLabel),
                            redMinuteSlider, redSecondSlider,
                            new HBox(5, new Label("Blue Team Blue Buff Respawn Time:"), blueTimeLabel),
                            blueMinuteSlider, blueSecondSlider,
                            new HBox(10, new Label("Editable?"), editableBox),
                            new HBox(10, new Label("Paused?"), pauseBox),
                            new HBox(10, new Label("Red Alive?"), redAliveBox),
                            new HBox(10, new Label("Blue Alive?"), blueAliveBox));
        box.setPadding(new Insets(10));
        box.setSpacing(10);

        setCenter(buffControl);
        setRight(box);
    }

    private void setupValueChangeListeners() {
        ChangeListener<Number> gameSliderListener = (observable, oldValue, newValue) ->
                pm.gameTimeProperty().setValue( LocalTime.of(0, (int) gameMinuteSlider.getValue(), (int) gameSecondSlider.getValue()));

        ChangeListener<Number> redSliderListener = (observable, oldValue, newValue) ->
                pm.redRedTimeProperty().setValue( LocalTime.of(0, (int) redMinuteSlider.getValue(), (int) redSecondSlider.getValue()));

        ChangeListener<Number> blueSliderListener = (observable, oldValue, newValue) ->
                pm.blueBlueTimeProperty().setValue( LocalTime.of(0, (int) blueMinuteSlider.getValue(), (int) blueSecondSlider.getValue()));

        gameMinuteSlider.valueProperty().addListener(gameSliderListener);
        gameSecondSlider.valueProperty().addListener(gameSliderListener);

        redMinuteSlider.valueProperty().addListener(redSliderListener);
        redSecondSlider.valueProperty().addListener(redSliderListener);

        blueMinuteSlider.valueProperty().addListener(blueSliderListener);
        blueSecondSlider.valueProperty().addListener(blueSliderListener);

        pm.gameTimeProperty().addListener((observable, oldValue, newValue) -> updateSliders());
        pm.redRedTimeProperty().addListener((observable, oldValue, newValue) -> updateSliders());
        pm.blueBlueTimeProperty().addListener((observable, oldValue, newValue) -> updateSliders());

        updateSliders();
    }

    private void updateSliders() {
        LocalTime gameTime = pm.getGameTime();

        gameMinuteSlider.setValue(gameTime.getMinute());
        gameSecondSlider.setValue(gameTime.getSecond());

        LocalTime redRedTime = pm.getRedRedTime();

        redMinuteSlider.setValue(redRedTime.getMinute());
        redSecondSlider.setValue(redRedTime.getSecond());

        LocalTime blueBlueTime = pm.getBlueBlueTime();

        blueMinuteSlider.setValue(blueBlueTime.getMinute());
        blueSecondSlider.setValue(blueBlueTime.getSecond());
    }

    private void setupBindings() {
        gameTimeLabel.textProperty().bind(Bindings.createStringBinding(() -> DateTimeFormatter.ofPattern("mm:ss").format(pm.getGameTime()), pm.gameTimeProperty()));
        redTimeLabel.textProperty().bind(Bindings.createStringBinding(() -> DateTimeFormatter.ofPattern("mm:ss").format(pm.getRedRedTime()), pm.redRedTimeProperty()));
        blueTimeLabel.textProperty().bind(Bindings.createStringBinding(() -> DateTimeFormatter.ofPattern("mm:ss").format(pm.getBlueBlueTime()), pm.blueBlueTimeProperty()));
        editableBox.selectedProperty().bindBidirectional(pm.editableProperty());
        pauseBox.selectedProperty().bindBidirectional(pm.pauseProperty());
        redAliveBox.selectedProperty().bindBidirectional(pm.redAliveProperty());
        blueAliveBox.selectedProperty().bindBidirectional(pm.blueAliveProperty());

        buffControl.gameTimeProperty().bindBidirectional(pm.gameTimeProperty());

        buffControl.redRedAliveProperty().bindBidirectional(pm.redAliveProperty());
        buffControl.redRedTimeProperty().bindBidirectional(pm.redRedTimeProperty());

        buffControl.blueBlueAliveProperty().bindBidirectional(pm.blueAliveProperty());
        buffControl.blueBlueTimeProperty().bindBidirectional(pm.blueBlueTimeProperty());

        buffControl.editableProperty().bindBidirectional(pm.editableProperty());
        buffControl.pauseProperty().bindBidirectional(pm.pauseProperty());

    }

}

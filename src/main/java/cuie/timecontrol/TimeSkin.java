package cuie.timecontrol;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


class TimeSkin extends SkinBase<TimeControl> {

    private TextField gameTimeInput;
    private Label gameTimeReadOnly;
    private Label gameTimeLabel;

    private Circle redRedIcon;
    private TextField redRedSpawnTimeInput;
    private Label redRedSpawnTimeReadOnly;

    private Circle blueBlueIcon;
    private TextField blueBlueSpawnTimeInput;
    private Label blueBlueSpawnTimeReadOnly;

    private ImageView imageView;

    private Timeline timer;
    private Button play;
    private Button edit;
    private Button reset;

    private Popup popup;
    private Pane dropDownChooser;
    private Button chooserButton;

    TimeSkin(TimeControl control) {
        super(control);
        initializeSelf();
        initializeParts();
        layoutParts();
        setupValueChangeListeners();
        setupEventHandlers();
        setupBindings();
    }

    private void initializeSelf() {
        getSkinnable().loadFonts("/fonts/fontawesome-webfont.ttf", "/fonts/fontawesome-webfont.ttf");
        getSkinnable().addStylesheetFiles("style.css");
    }

    private void initializeParts() {
        //map initialization
        imageView = new ImageView();
        imageView.setImage(getSkinnable().getImage());

        //game time initialization
        gameTimeInput = new TextField();
        gameTimeInput.getStyleClass().add("game-time");

        gameTimeLabel = new Label("Game Time: ");
        gameTimeLabel.getStyleClass().add("game-time");

        gameTimeReadOnly = new Label();
        gameTimeReadOnly.getStyleClass().add("game-time");
        gameTimeReadOnly.setLayoutX(135);
        gameTimeInput.setLayoutX(gameTimeReadOnly.getLayoutX());
        gameTimeInput.setLayoutY(gameTimeReadOnly.getLayoutY());
        gameTimeInput.setMaxWidth(43);

        //Red side red buff initialization
        redRedIcon = new Circle(10);
        redRedIcon.getStyleClass().add("red-icon");

        redRedSpawnTimeInput = new TextField();
        redRedSpawnTimeInput.getStyleClass().add("text-field");

        redRedSpawnTimeReadOnly = new Label();
        redRedSpawnTimeReadOnly.getStyleClass().add("spawn-time");
        redRedSpawnTimeReadOnly.setLayoutX(227);
        redRedSpawnTimeReadOnly.setLayoutY(120);
        redRedSpawnTimeInput.relocate(redRedSpawnTimeReadOnly.getLayoutX(), redRedSpawnTimeReadOnly.getLayoutY()+1);
        redRedSpawnTimeInput.setMaxWidth(43);
        redRedIcon.setCenterX(redRedSpawnTimeReadOnly.getLayoutX()+20);
        redRedIcon.setCenterY(redRedSpawnTimeReadOnly.getLayoutY()+12.5);

        //Blue side blue buff initialization
        blueBlueIcon = new Circle(10);
        blueBlueIcon.getStyleClass().add("blue-icon");

        blueBlueSpawnTimeInput = new TextField();
        blueBlueSpawnTimeInput.getStyleClass().add("text-field");

        blueBlueSpawnTimeReadOnly = new Label();
        blueBlueSpawnTimeReadOnly.getStyleClass().add("spawn-time");
        blueBlueSpawnTimeReadOnly.setLayoutX(110);
        blueBlueSpawnTimeReadOnly.setLayoutY(224);
        blueBlueSpawnTimeInput.relocate(blueBlueSpawnTimeReadOnly.getLayoutX(), blueBlueSpawnTimeReadOnly.getLayoutY()+1);
        blueBlueSpawnTimeInput.setMaxWidth(43);
        blueBlueIcon.setCenterX(blueBlueSpawnTimeReadOnly.getLayoutX()+20);
        blueBlueIcon.setCenterY(blueBlueSpawnTimeReadOnly.getLayoutY()+12.5);

        //button initialization
        play = new Button("Play");
        play.setMinWidth(75);
        edit = new Button("Edit");
        edit.setMinWidth(75);
        reset = new Button("Reset");
        reset.setMinWidth(75);

        //timer initialization
        timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {

            getSkinnable().gameTimeProperty().setValue(
                    getSkinnable().getGameTime().plus(java.time.Duration.ofSeconds(1)));

            if(!getSkinnable().isRedRedAlive()){
                getSkinnable().redRedTimeProperty().setValue(
                        getSkinnable().getRedRedTime().minus(java.time.Duration.ofSeconds(1)));
            }

            if(!getSkinnable().isBlueBlueAlive()){
                getSkinnable().blueBlueTimeProperty().setValue(
                        getSkinnable().getBlueBlueTime().minus(java.time.Duration.ofSeconds(1)));
            }
        }));

        timer.setCycleCount(Timeline.INDEFINITE);

        //dropdownchooser initialization
        chooserButton = new Button("\uF107");
        chooserButton.getStyleClass().add("chooser-button");
        chooserButton.setPadding(new Insets(1,5,1,5));

        dropDownChooser = new DropDownChooser(getSkinnable());

        popup = new Popup();
        popup.getContent().addAll(dropDownChooser);
    }

    private void layoutParts() {
        AnchorPane map = new AnchorPane(imageView, redRedIcon, redRedSpawnTimeInput, redRedSpawnTimeReadOnly,
                                        blueBlueIcon, blueBlueSpawnTimeInput, blueBlueSpawnTimeReadOnly);
        StackPane gameTime = new StackPane(gameTimeInput, gameTimeReadOnly);
        gameTime.setAlignment(Pos.BASELINE_LEFT);
        HBox buttons = new HBox(10, play, edit, reset, gameTimeLabel, gameTime, chooserButton);
        buttons.setPadding(new Insets(5));
        buttons.setBackground(new Background(new BackgroundFill(Color.BLACK,CornerRadii.EMPTY, Insets.EMPTY)));
        getChildren().addAll(new BorderPane(map, buttons, null, null ,null));
    }

    private void setupValueChangeListeners() {
        getSkinnable().gameTimeValidProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue && !getSkinnable().isColorblind()){
                gameTimeInput.styleProperty().set("-fx-text-fill: red");
            } else if(!newValue) {
                gameTimeInput.styleProperty().set("-fx-text-fill: #28ffff");
            } else {
                gameTimeInput.styleProperty().set("-fx-text-fill: white");
            }
        });

        getSkinnable().redRedTimeProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals(LocalTime.of(0,0,0))){
                getSkinnable().setRedRedAlive(true);
                getSkinnable().setRedRedTime(5,0);
            }
        });

        getSkinnable().redRedValidProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue && !getSkinnable().isColorblind()){
                redRedSpawnTimeInput.styleProperty().set("-fx-text-fill: red");
            } else if(!newValue){
                redRedSpawnTimeInput.styleProperty().set("-fx-text-fill: #28ffff");
            } else {
                redRedSpawnTimeInput.styleProperty().set("-fx-text-fill: white");
            }
        });

        getSkinnable().blueBlueTimeProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals(LocalTime.of(0,0,0))){
                getSkinnable().setBlueBlueAlive(true);
                getSkinnable().setBlueBlueTime(5,0);
            }
        });

        getSkinnable().blueBlueValidProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue && !getSkinnable().isColorblind()){
                blueBlueSpawnTimeInput.styleProperty().set("-fx-text-fill: red");
            } else if(!newValue) {
                blueBlueSpawnTimeInput.styleProperty().set("-fx-text-fill: #28ffff");
            } else {
                blueBlueSpawnTimeInput.styleProperty().set("-fx-text-fill: white");
            }
        });

        getSkinnable().colorblindProperty().addListener((observable, oldValue, newValue) -> {
            imageView.setImage(getSkinnable().getImage());
        });
    }

    private void setupEventHandlers() {
        redRedIcon.setOnMouseClicked(event -> {
            getSkinnable().setRedRedAlive(!getSkinnable().isRedRedAlive());

        });

        blueBlueIcon.setOnMouseClicked(event -> {
            getSkinnable().setBlueBlueAlive(!getSkinnable().isBlueBlueAlive());
        });

        play.setOnAction(event -> {
            if(getSkinnable().isPause()) {
                timer.play();
                play.textProperty().set("Stop");
                edit.setDisable(true);
                reset.setDisable(true);
                getSkinnable().setEditable(false);
            } else {
                timer.stop();
                play.textProperty().set("Play");
                edit.setDisable(false);
                reset.setDisable(false);
            }

            getSkinnable().setPause(!getSkinnable().isPause());
        });

        reset.setOnAction(event -> {
            resetGame();
        });

        edit.setOnAction(event -> {
            getSkinnable().setEditable(!getSkinnable().isEditable());
        });

        gameTimeInput.addEventFilter(KeyEvent.KEY_PRESSED,
                event -> {
                    int caretPos = gameTimeInput.getCaretPosition();
                    switch (event.getCode()) {
                        case ESCAPE -> {
                            event.consume();
                            getSkinnable().resetGameTime();
                            getSkinnable().setEditable(false);
                        }
                        case UP -> {
                            if (getSkinnable().isGameTimeValid()) {
                                if (caretPos < 3) {
                                    getSkinnable().gameTimeProperty().setValue(getSkinnable().getGameTime().plusMinutes(1));
                                } else {
                                    getSkinnable().gameTimeProperty().setValue(getSkinnable().getGameTime().plusSeconds(1));
                                }
                            }
                            event.consume();
                            gameTimeInput.positionCaret(caretPos);
                        }
                        case DOWN -> {
                            if (getSkinnable().isGameTimeValid()) {
                                if (caretPos < 3) {
                                    getSkinnable().gameTimeProperty().setValue(getSkinnable().getGameTime().minusMinutes(1));
                                } else {
                                    getSkinnable().gameTimeProperty().setValue(getSkinnable().getGameTime().minusSeconds(1));
                                }
                            }
                            event.consume();
                            gameTimeInput.positionCaret(caretPos);
                        }
                    }
        });

        redRedSpawnTimeInput.addEventFilter(KeyEvent.KEY_PRESSED,
                event -> {
                    int caretPos = redRedSpawnTimeInput.getCaretPosition();
                    switch (event.getCode()) {
                        case ESCAPE -> {
                            event.consume();
                            getSkinnable().resetRedRedTime();
                            getSkinnable().setEditable(false);
                        }
                        case UP -> {
                            if (getSkinnable().isRedRedValid()) {
                                if (caretPos < 3) {
                                    getSkinnable().redRedTimeProperty().setValue(getSkinnable().getRedRedTime().plusMinutes(1));
                                } else {
                                    getSkinnable().redRedTimeProperty().setValue(getSkinnable().getRedRedTime().plusSeconds(1));
                                }
                            }
                            event.consume();
                            redRedSpawnTimeInput.positionCaret(caretPos);
                        }
                        case DOWN -> {
                            if (getSkinnable().isRedRedValid()) {
                                if (caretPos < 3) {
                                    getSkinnable().redRedTimeProperty().setValue(getSkinnable().getRedRedTime().minusMinutes(1));
                                } else {
                                    getSkinnable().redRedTimeProperty().setValue(getSkinnable().getRedRedTime().minusSeconds(1));
                                }
                            }
                            event.consume();
                            redRedSpawnTimeInput.positionCaret(caretPos);
                        }
                    }
        });

        blueBlueSpawnTimeInput.addEventFilter(KeyEvent.KEY_PRESSED,
                event -> {
                    int caretPos = blueBlueSpawnTimeInput.getCaretPosition();
                    switch (event.getCode()) {
                        case ESCAPE -> {
                            event.consume();
                            getSkinnable().resetBlueBlueTime();
                            getSkinnable().setEditable(false);
                        }
                        case UP -> {
                            if (getSkinnable().isBlueBlueValid()) {
                                if (caretPos < 3) {
                                    getSkinnable().blueBlueTimeProperty().setValue(getSkinnable().getBlueBlueTime().plusMinutes(1));
                                } else {
                                    getSkinnable().blueBlueTimeProperty().setValue(getSkinnable().getBlueBlueTime().plusSeconds(1));
                                }
                            }
                            event.consume();
                            blueBlueSpawnTimeInput.positionCaret(caretPos);
                        }
                        case DOWN -> {
                            if (getSkinnable().isBlueBlueValid()) {
                                if (caretPos < 3) {
                                    getSkinnable().blueBlueTimeProperty().setValue(getSkinnable().getBlueBlueTime().minusMinutes(1));
                                } else {
                                    getSkinnable().blueBlueTimeProperty().setValue(getSkinnable().getBlueBlueTime().minusSeconds(1));
                                }
                            }
                            event.consume();
                            blueBlueSpawnTimeInput.positionCaret(caretPos);
                        }
                    }
        });


        chooserButton.setOnAction(event -> {
            if (popup.isShowing()) {
                popup.hide();
            } else {
                popup.show(gameTimeInput.getScene().getWindow());
            }
        });

        chooserButton.setOnMouseEntered(event -> {
            chooserButton.setStyle("-fx-background-color: rgba(28, 86, 80, 0.8)");
        });

        chooserButton.setOnMouseExited(event -> {
            chooserButton.setStyle("-fx-background-color: transparent");
        });

        popup.setOnHidden(event -> {
            chooserButton.setText("\uF107");
        });

        popup.setOnShown(event -> {
            chooserButton.setText("\uF106");
            Point2D location = gameTimeInput.localToScreen(
                    gameTimeInput.getWidth() - dropDownChooser.getPrefWidth() + 71,
                    gameTimeInput.getHeight() - 3);
            popup.setX(location.getX());
            popup.setY(location.getY());
        });
    }

    private void setupBindings() {
        gameTimeInput.textProperty().bindBidirectional(getSkinnable().gameTimeAsStringProperty());
        gameTimeReadOnly.textProperty().bind(Bindings.createStringBinding(() ->
                DateTimeFormatter.ofPattern("mm:ss").format(getSkinnable().getGameTime()),
                getSkinnable().gameTimeProperty()));
        gameTimeInput.visibleProperty().bind(getSkinnable().editableProperty());
        gameTimeReadOnly.visibleProperty().bind(getSkinnable().editableProperty().not());

        redRedIcon.visibleProperty().bind(getSkinnable().redRedAliveProperty());
        redRedSpawnTimeInput.textProperty().bindBidirectional(getSkinnable().redRedTimeAsStringProperty());
        redRedSpawnTimeReadOnly.textProperty().bind(Bindings.createStringBinding(() ->
                        DateTimeFormatter.ofPattern("mm:ss").format(getSkinnable().getRedRedTime()),
                getSkinnable().redRedTimeProperty()));

        redRedSpawnTimeInput.visibleProperty().bind(getSkinnable().editableProperty());
        redRedSpawnTimeReadOnly.visibleProperty().bind(getSkinnable().editableProperty().not().and(getSkinnable().redRedAliveProperty().not()));

        blueBlueIcon.visibleProperty().bind(getSkinnable().blueBlueAliveProperty());
        blueBlueSpawnTimeInput.textProperty().bindBidirectional(getSkinnable().blueBlueTimeAsStringProperty());
        blueBlueSpawnTimeReadOnly.textProperty().bind(Bindings.createStringBinding(() ->
                DateTimeFormatter.ofPattern("mm:ss").format(getSkinnable().getBlueBlueTime()),
                getSkinnable().blueBlueTimeProperty()));

        blueBlueSpawnTimeInput.visibleProperty().bind(getSkinnable().editableProperty());
        blueBlueSpawnTimeReadOnly.visibleProperty().bind(getSkinnable().editableProperty().not().and(getSkinnable().blueBlueAliveProperty().not()));
    }

    private void resetGame(){
        getSkinnable().setGameTime(0,0);
        getSkinnable().setRedRedAlive(false);
        getSkinnable().setRedRedTime(1,30);
        getSkinnable().setBlueBlueAlive(false);
        getSkinnable().setBlueBlueTime(1,30);
        getSkinnable().setEditable(false);
    }
}

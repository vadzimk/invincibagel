package invincibagel;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class InvinciBagel extends Application {

    static final double WIDTH = 640, HEIGHT = 400; //screen size
    boolean up, down, left, right; // key codes false by default
    private HBox buttonContainer;
    private Scene scene;
    private StackPane root;

    static Bagel iBagel;

    private Image splashScreen, instructionLayer, legalLayer, scoresLayer;
    private Image iB0, iB1, iB2, iB3, iB4, iB5, iB6, iB7, iB8;
    private ImageView splashScreenBackPlate, splashScreenTextArea;
    private Button gameButton, helpButton, scoreButton, legalButton;
    private Insets buttonContainerPadding;


    private GamePlayLoop gamePlayLoop;
    CastingDirector castingDirector;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStge) {
        root = new StackPane();
        scene = new Scene(root, WIDTH, HEIGHT, Color.WHITE);


        primaryStge.setTitle("InvinciBagel");
        primaryStge.setScene(scene);
        primaryStge.show();

        createSplashScreenNodes();
        loadImageAssets();
        createSceneEventHandling();
        createGameActors(); // creates actors
        createCastingDirection(); //add actors to the cast
        addGameActorNodes(); // adds actors to the scene
        addNodesToStackPane(); // overlays solid splashscreen stackplate on top of the game field

        gameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                splashScreenBackPlate.setVisible(false);
                splashScreenTextArea.setVisible(false);
            }
        });

        helpButton.setOnAction(new EventHandler<ActionEvent>() { //EventHandler is an interface
            @Override
            public void handle(ActionEvent event) {
                splashScreenBackPlate.setVisible(true);
                splashScreenTextArea.setVisible(true);
                splashScreenTextArea.setImage(instructionLayer);
            }
        });

        scoreButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                splashScreenBackPlate.setVisible(true);
                splashScreenTextArea.setVisible(true);
                splashScreenTextArea.setImage(scoresLayer);
            }
        });

        legalButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                splashScreenTextArea.setImage(legalLayer);
            }
        });

        //here create a dynamic object that will be processing the pulse-related logic
        gamePlayLoop = new GamePlayLoop();
        gamePlayLoop.start();

    }

    /**
     * Scene Graph node creation
     */
    private void createSplashScreenNodes() {
        gameButton = new Button("Play Game");
        helpButton = new Button("Instructions");
        scoreButton = new Button("High scores");
        legalButton = new Button("Legal & credits");

        buttonContainer = new HBox(12);
        buttonContainer.setAlignment(Pos.BOTTOM_LEFT);
        buttonContainerPadding = new Insets(0, 0, 10, 16);
        buttonContainer.setPadding(buttonContainerPadding);
        buttonContainer.getChildren().addAll(gameButton, helpButton, scoreButton, legalButton);
    }

    /**
     * Adds the nodes to stackPane root node
     */
    private void addNodesToStackPane() {
        root.getChildren().addAll(
                splashScreenBackPlate,
                splashScreenTextArea,
                buttonContainer);
    }

    /**
     * Scene Event handling
     */
    private void createSceneEventHandling() {
        //add eventHandlers to the scene - not envolving any Scene Graph Nodes
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:
                    case UP:
                        up = true;
                        break;
                    case S:
                    case DOWN:
                        down = true;
                        break;
                    case A:
                    case LEFT:
                        left = true;
                        break;
                    case D:
                    case RIGHT:
                        right = true;
                        break;
                }
            }
        });
        scene.setOnKeyReleased((KeyEvent event) -> {
            switch (event.getCode()) {
                case W:
                case UP:
                    up = false;
                    break;
                case S:
                case DOWN:
                    down = false;
                    break;
                case A:
                case LEFT:
                    left = false;
                    break;
                case D:
                case RIGHT:
                    right = false;
                    break;
            }
        });
    }

    /**
     * Load images of sprites into memory
     */
    private void loadImageAssets() {
        splashScreen = new Image("/invincibagelsplash.png", 640, 400, true, false, true);
        instructionLayer = new Image("/invincibagelinstruct.png", 640, 400, true, false, true);
        legalLayer = new Image("/invincibagelcreds.png", 640, 400, true, false, true);
        scoresLayer = new Image("/invincibagelscores.png", 640, 400, true, false, true);
        iB0 = new Image("/sprite0.png", 81, 81, true, false, true);
        iB1 = new Image("/sprite1.png", 81, 81, true, false, true);
        iB2 = new Image("/sprite2.png", 81, 81, true, false, true);
        iB3 = new Image("/sprite3.png", 81, 81, true, false, true);
        iB4 = new Image("/sprite4.png", 81, 81, true, false, true);
        iB5 = new Image("/sprite5.png", 81, 81, true, false, true);
        iB6 = new Image("/sprite6.png", 81, 81, true, false, true);
        iB7 = new Image("/sprite7.png", 81, 81, true, false, true);
        iB8 = new Image("/sprite8.png", 81, 81, true, false, true);


        splashScreenBackPlate = new ImageView(splashScreen);
        splashScreenTextArea = new ImageView(instructionLayer);
    }

    /**
     * Create Game Actor objects
     */
    private void createGameActors() {
        iBagel = new Bagel("M150 0 L75 500 L 225 200 Z", 0, 0, iB0, iB1, iB2, iB3, iB4, iB5, iB6, iB7, iB8);
    }

    /**
     * Adds actor objects to the scene
     */
    private void addGameActorNodes() {
        root.getChildren().add(iBagel.spriteFrame);
    }

    //
    private void createCastingDirection(){
        castingDirector = new CastingDirector();
        castingDirector.addCurrentCast(iBagel);
    }

}

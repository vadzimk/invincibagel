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

import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;


public class InvinciBagel extends Application {

    static final double WIDTH = 640, HEIGHT = 400; //screen size
    private boolean up, down, left, right; // key codes false by default
    private boolean wKey, aKey, sKey, dKey; // key codes false by default
    private HBox buttonContainer;
    private Scene scene;
    private StackPane root;

    Bagel iBagel; // main sprite
    Prop iPR0, iPR1; // static sprite
    PropH iPH0; // static sprite horizontal flip
    PropV iPV0, iPV1; // static sprite vertical flip
    PropB iPB0; // static sprite vartical and horizontal flip

    private Image splashScreen, instructionLayer, legalLayer, scoresLayer;
    private Image iB0, iB1, iB2, iB3, iB4, iB5, iB6, iB7, iB8, iP0, iP1;
    private ImageView splashScreenBackPlate, splashScreenTextArea;
    private Button gameButton, helpButton, scoreButton, legalButton;
    private Insets buttonContainerPadding;

    private AudioClip iSound0, iSound1, iSound2, iSound3, iSound4, iSound5;
    private URL iAudioFile0, iAudioFile1, iAudioFile2, iAudioFile3, iAudioFile4, iAudioFile5;

    private GamePlayLoop gamePlayLoop;
    CastingDirector castingDirector;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStge) {
        root = new StackPane();
        //root.setAlignment(Pos.TOP_LEFT); with .setAlignment() I can position elements added to the stackPane the way other than default: POS_CENTER
        root.setBackground(Background.EMPTY);
        scene = new Scene(root, WIDTH, HEIGHT, Color.DARKGREY);

        primaryStge.setTitle("InvinciBagel");
        primaryStge.initStyle(StageStyle.TRANSPARENT); //makes the stage transparent
        primaryStge.setScene(scene);
        primaryStge.show();

        createSceneEventHandling();
        loadAudioAssets();
        loadImageAssets();
        createGameActors(); // creates actors
        addGameActorNodes(); // adds actors to the scene
        createCastingDirection(); //add actors to the cast
        createSplashScreenNodes();
        addNodesToStackPane(); // overlays solid splashscreen stackplate on top of the game field
        createStartGameLoop();

    }

    /**
     * Scene Graph node creation
     */
    private void createSplashScreenNodes() {
        buttonContainer = new HBox(12);
        buttonContainer.setAlignment(Pos.BOTTOM_LEFT);
        buttonContainerPadding = new Insets(0, 0, 10, 16);
        buttonContainer.setPadding(buttonContainerPadding);

        gameButton = new Button("Play Game");
        gameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                splashScreenBackPlate.setVisible(false);
                splashScreenTextArea.setVisible(false);
            }
        });

        helpButton = new Button("Instructions");
        helpButton.setOnAction(new EventHandler<ActionEvent>() { //EventHandler is an interface
            @Override
            public void handle(ActionEvent event) {
                splashScreenBackPlate.setVisible(true);
                splashScreenTextArea.setVisible(true);
                splashScreenTextArea.setImage(instructionLayer);
            }
        });

        scoreButton = new Button("High scores");
        scoreButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                splashScreenBackPlate.setVisible(true);
                splashScreenTextArea.setVisible(true);
                splashScreenTextArea.setImage(scoresLayer);
            }
        });

        legalButton = new Button("Legal & credits");
        legalButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                splashScreenTextArea.setImage(legalLayer);
            }
        });

        buttonContainer.getChildren().addAll(gameButton, helpButton, scoreButton, legalButton);
        splashScreenBackPlate = new ImageView(splashScreen);
        splashScreenTextArea = new ImageView(instructionLayer);
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

                    case UP:
                        up = true;
                        break;
                    case DOWN:
                        down = true;
                        break;
                    case LEFT:
                        left = true;
                        break;
                    case RIGHT:
                        right = true;
                        break;
                    case W:
                        wKey = true;
                        break;
                    case S:
                        sKey = true;
                        break;
                    case A:
                        aKey = true;
                        break;
                    case D:
                        dKey = true;
                        break;
                }
            }
        });
        scene.setOnKeyReleased((KeyEvent event) -> {
            switch (event.getCode()) {

                case UP:
                    up = false;
                    break;
                case DOWN:
                    down = false;
                    break;
                case LEFT:
                    left = false;
                    break;
                case RIGHT:
                    right = false;
                    break;
                case W:
                    wKey = false;
                    break;
                case S:
                    sKey = false;
                    break;
                case A:
                    aKey = false;
                    break;
                case D:
                    dKey = false;
                    break;
            }
        });
    }

    /**
     * Load audioClips into memory
     */
    private void loadAudioAssets() {
        iAudioFile0 = getClass().getResource("/leftmono.wav");

        iSound0 = new AudioClip(iAudioFile0.toString());

        iAudioFile1 = getClass().getResource("/rightmono.wav");
        iSound1 = new AudioClip(iAudioFile1.toString());

        iAudioFile2 = getClass().getResource("/upmono.wav");
        iSound2 = new AudioClip(iAudioFile2.toString());

        iAudioFile3 = getClass().getResource("/downmono.wav");
        iSound3 = new AudioClip(iAudioFile3.toString());

        iAudioFile4 = getClass().getResource("/wmono.wav");
        iSound4 = new AudioClip(iAudioFile4.toString());

        iAudioFile5 = getClass().getResource("/smono.wav");
        iSound5 = new AudioClip(iAudioFile5.toString());
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
        iP0 = new Image("/prop0.png", 82, 32, true, false, true);
        iP1 = new Image("/prop1.png", 496, 92, true, false, true);
    }

    /**
     * Create Game Actor objects
     */
    private void createGameActors() {
        iBagel = new Bagel(this, "M150 0 L75 500 L 225 200 Z", 0, 0, iB0, iB1, iB2, iB3, iB4, iB5, iB6, iB7, iB8); //the this references the current Invincibagel object that is the 1st parameter of the Bagel constructor

        iPR0 = new Prop("M150 0 L75 200 L225 200 Z", 0, 148, iP0); // static brick wall basic object
        iPR1 = new Prop("M150 0 L75 200 L225 200 Z", 0, -150, iP1);  // big background image object
        iPV1 = new PropV("M150 0 L75 200 L225 200 Z", 0, -58, iP1); // flipped big background image object
        iPH0 = new PropH("M150 0 L75 200 L225 200 Z", 72, 148, iP0); // static brick wall flipped H object
        iPV0 = new PropV("M150 0 L75 200 L225 200 Z", 0, 116, iP0); // static brick wall flipped V object
        iPB0 = new PropB("M150 0 L75 200 L225 200 Z", 72, 116, iP0); // static brick wall flipped H, V object
    }

    /**
     * Adds actor objects to the scene
     */
    private void addGameActorNodes() {

        root.getChildren().add(iPR0.spriteFrame);
        root.getChildren().add(iPR1.spriteFrame);
        root.getChildren().add(iPV1.spriteFrame);
        root.getChildren().add(iPH0.spriteFrame);
        root.getChildren().add(iPV0.spriteFrame);
        root.getChildren().add(iPB0.spriteFrame);

        root.getChildren().add(iBagel.spriteFrame);
    }

    /**
     * creates CastingDirecor and adds actors to the cast
     */
    private void createCastingDirection() {
        castingDirector = new CastingDirector();
        castingDirector.addCurrentCast(iBagel);
        castingDirector.addCurrentCast(iPR0);
        castingDirector.addCurrentCast(iPR1);
        castingDirector.addCurrentCast(iPV1);
        castingDirector.addCurrentCast(iPH0);
        castingDirector.addCurrentCast(iPV0);
        castingDirector.addCurrentCast(iPB0);
    }

    /**
     * Create and start the gameLoop
     */
    private void createStartGameLoop() {
        //here create a dynamic object that will be processing the pulse-related logic
        gamePlayLoop = new GamePlayLoop(this);
        gamePlayLoop.start();
    }

    // ---------- Getter for key codes ---
    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean iswKey() {
        return wKey;
    }

    public boolean isaKey() {
        return aKey;
    }

    public boolean issKey() {
        return sKey;
    }

    public boolean isdKey() {
        return dKey;
    }

    // ---------- Setter for key codes ----

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setwKey(boolean wKey) {
        this.wKey = wKey;
    }

    public void setaKey(boolean aKey) {
        this.aKey = aKey;
    }

    public void setsKey(boolean sKey) {
        this.sKey = sKey;
    }

    public void setdKey(boolean dKey) {
        this.dKey = dKey;
    }

    // ------------ Access to AudioClips ---------------

    public void playiSound0() {
        this.iSound0.play();
    }

    public void playiSound1() {
        this.iSound1.play();
    }

    public void playiSound2() {
        this.iSound2.play();
    }

    public void playiSound3() {
        this.iSound3.play();
    }

    public void playiSound4() {
        this.iSound4.play();
    }

    public void playiSound5() {
        this.iSound5.play();
    }
}

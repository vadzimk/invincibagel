package invincibagel;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.net.URL;


public class InvinciBagel extends Application {

    static final double WIDTH = 640, HEIGHT = 400; //screen size
    private boolean up, down, left, right; // key codes false by default
    private boolean wKey, aKey, sKey, dKey; // key codes false by default
    private HBox buttonContainer;
    private Scene scene;
    Group root;

    Bagel iBagel; // main sprite
    Enemy iEnemy;
    Projectile iBullet, iCheese;

    Prop iPR0, iPR1; // static sprite
    PropH iPH0; // static sprite horizontal flip
    PropV iPV0, iPV1; // static sprite vertical flip
    PropB iPB0; // static sprite vartical and horizontal flip

    Treasure iTR0, iTR1;

    private Image splashScreen, instructionLayer, legalLayer, scoresLayer, skyCloud;
    private Image iB0, iB1, iB2, iB3, iB4, iB5, iB6, iB7, iB8, iP0, iP1, iT0, iT1, iE0, iC0, iC1;
    private ImageView splashScreenBackPlate, splashScreenTextArea;
    private Button gameButton, helpButton, scoreButton, legalButton;
    private Insets buttonContainerPadding;

    private AudioClip iSound0, iSound1, iSound2, iSound3, iSound4, iSound5;
    private URL iAudioFile0, iAudioFile1, iAudioFile2, iAudioFile3, iAudioFile4, iAudioFile5;

    private GamePlayLoop gamePlayLoop;
    CastingDirector castingDirector;

    int gameScore = 0;
    Text scoreText, scoreLabel;
    private Font scoreFont;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStge) {
        root = new Group();
        //root.setAlignment(Pos.TOP_LEFT); with .setAlignment() I can position elements added to the stackPane the way other than default: POS_CENTER
        //root.setBackground(Background.EMPTY);
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

        scoreText = new Text(String.valueOf(gameScore));
        scoreText.setLayoutY(HEIGHT - 15);
        scoreText.setLayoutX(WIDTH - 115);
        scoreFont = new Font("Verdana", 20);
        scoreText.setFont(scoreFont);
        scoreText.setFill(Color.BLUE);

        scoreLabel = new Text("SCORE: ");
        scoreLabel.setLayoutY(385);
        scoreLabel.setLayoutX(445);
        scoreLabel.setFont(scoreFont);
        scoreLabel.setFill(Color.BLACK);

        buttonContainer = new HBox(12);
        buttonContainer.setLayoutY(HEIGHT - 35);
        buttonContainerPadding = new Insets(0, 0, 10, 16);
        buttonContainer.setPadding(buttonContainerPadding);

        gameButton = new Button("Play Game");
        gameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                splashScreenBackPlate.setImage(skyCloud);
                splashScreenBackPlate.setVisible(true);
                splashScreenBackPlate.toBack(); // sets background imageView z-index to the lowest
                splashScreenTextArea.setVisible(false);
            }
        });

        helpButton = new Button("Instructions");
        helpButton.setOnAction(new EventHandler<ActionEvent>() { //EventHandler is an interface
            @Override
            public void handle(ActionEvent event) {
                splashScreenBackPlate.setImage(splashScreen);
                splashScreenBackPlate.toFront();
                splashScreenBackPlate.setVisible(true);
                splashScreenTextArea.setVisible(true);
                splashScreenTextArea.setImage(instructionLayer);
                splashScreenTextArea.toFront();
                buttonContainer.toFront();
            }
        });

        scoreButton = new Button("High scores");
        scoreButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                splashScreenBackPlate.setImage(splashScreen);
                splashScreenBackPlate.toFront();
                splashScreenBackPlate.setVisible(true);
                splashScreenTextArea.setImage(scoresLayer);
                splashScreenTextArea.setVisible(true);
                splashScreenTextArea.toFront();
                buttonContainer.toFront();
            }
        });

        legalButton = new Button("Legal & credits");
        legalButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                splashScreenBackPlate.setImage(splashScreen);
                splashScreenBackPlate.toFront();
                splashScreenBackPlate.setVisible(true);
                splashScreenTextArea.setImage(legalLayer);
                splashScreenTextArea.setVisible(true);
                splashScreenTextArea.toFront();
                buttonContainer.toFront();
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
                buttonContainer,
                scoreLabel,
                scoreText
        );
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
        skyCloud = new Image("/skycloud.png", 640, 400, true,false,true);
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
//        iP1 = new Image("/prop1.png", 496, 92, true, false, true);

        iT0 = new Image("/treasure1.png", 64, 64, true, false, true);
        iT1 = new Image("/treasure2.png", 64, 64, true, false, true);
        iE0 = new Image("/enemy.png", 70, 115, true, false, true);
        iC0 = new Image("/bullet.png", 64, 24, true, false, true);
        iC1 = new Image("/cheese.png", 32, 29, true, false, true);
    }

    /**
     * Create Game Actor objects
     */
    private void createGameActors() {
        iBagel = new Bagel(this,
                "M57,10 L46,25 30,26 30,41 18,41 18,44 27,56 37,57 35,75 39,81 43,81 45,53 54,40 63,43 72,26 Z",
                WIDTH / 2, HEIGHT / 2, iB0, iB1, iB2, iB3, iB4, iB5, iB6, iB7, iB8); //the this references the current Invincibagel object that is the 1st parameter of the Bagel constructor

        iPR0 = new Prop("M0 0 L0 32 72 32 72 0 Z", 30, 50, iP0); // static brick wall basic object
        iPR1 = new Prop("M150 0 L75 200 L225 200 Z", 175, 250, iP1);  // big background image object
        iPV1 = new PropV("M150 0 L75 200 L225 200 Z", 390, 110, iP1); // flipped big background image object
        iPH0 = new PropH("M150 0 L75 200 L225 200 Z", 500, 300, iP0); // static brick wall flipped H object
        iPV0 = new PropV("M150 0 L75 200 L225 200 Z", 50, 110, iP0); // static brick wall flipped V object
        iPB0 = new PropB("M150 0 L75 200 L225 200 Z", 540, 210, iP0); // static brick wall flipped H, V object

        iTR0 = new Treasure("M0 0 L0 64 64 64 64 0 Z", 50, 170, iT0);
        iTR1 = new Treasure("M0 0 L0 64 64 64 64 0 Z", 533, 110, iT1);

        iEnemy = new Enemy("M0 6 L0 52 70 52 70 70 70 93 115 45 115 0 84 0 68 16 Z", 520, 160, iE0);
        iBullet = new Projectile("M0 4 L0 16 64 16 64 4 Z", 8, 8, iC0);
        iCheese = new Projectile("M0 0 L0 32 32 32 32 0 Z", 96, 8, iC1);
    }

    /**
     * Adds actor objects to the scene
     */
    private void addGameActorNodes() {

        root.getChildren().add(iPR0.spriteFrame);
//        root.getChildren().add(iPR1.spriteFrame); //large background image
//        root.getChildren().add(iPV1.spriteFrame); //large background image
        root.getChildren().add(iPH0.spriteFrame);
        root.getChildren().add(iPV0.spriteFrame);
        root.getChildren().add(iPB0.spriteFrame);
        root.getChildren().add(iTR0.spriteFrame);
        root.getChildren().add(iTR1.spriteFrame);
        root.getChildren().add(iEnemy.spriteFrame);
        root.getChildren().add(iBullet.spriteFrame);
        root.getChildren().add(iCheese.spriteFrame);


        root.getChildren().add(iBagel.spriteFrame);
    }

    /**
     * creates CastingDirecor and adds actors to the cast
     */
    private void createCastingDirection() {
        castingDirector = new CastingDirector();
        castingDirector.addCurrentCast(iPR0, iPH0, iPV0, iPB0, iTR0, iTR1, iEnemy, iBullet, iCheese);
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

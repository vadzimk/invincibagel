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
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class InvinciBagel extends Application {
    private Scene scene;
    private StackPane root;
    private Image splashScreen, instructionLayer, legalLayer, scoresLayer;
    private ImageView splashScreenBackPlate, splashScreenTextArea;
    private Button gameButton, helpButton, scoreButton, legalButton;
    private HBox buttonContainer;
    private Insets buttonContainerPadding;


    @Override
    public void start(Stage primaryStge) {
        createSplashScreenNodes();
        addNodesToStackPane();

        primaryStge.setTitle("InvinciBagel");
        primaryStge.setScene(scene);
        primaryStge.show();

//        gameButton.setOnAction();

        helpButton.setOnAction(new EventHandler<ActionEvent>() { //EventHandler is an interface
            @Override
            public void handle(ActionEvent event) {
                splashScreenTextArea.setImage(instructionLayer);
            }
        });

        scoreButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                splashScreenTextArea.setImage(scoresLayer);
            }
        });

        legalButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                splashScreenTextArea.setImage(legalLayer);
            }
        });

    }

    /**
     * Scene Graph node creation
     */
    private void createSplashScreenNodes() {
        root = new StackPane();
        scene = new Scene(root, 640, 400);

        gameButton = new Button("Play Game");
        helpButton = new Button("Instructions");
        scoreButton = new Button("High scores");
        legalButton = new Button("Legal & credits");

        buttonContainer = new HBox(12);
        buttonContainer.setAlignment(Pos.BOTTOM_LEFT);
        buttonContainerPadding = new Insets(0, 0, 10, 16);
        buttonContainer.setPadding(buttonContainerPadding);
        buttonContainer.getChildren().addAll(gameButton, helpButton, scoreButton, legalButton);

        splashScreen = new Image("/invincibagelsplash.png", 640, 400, true, false, true);
        splashScreenBackPlate = new ImageView(splashScreen);

        instructionLayer = new Image("/invincibagelinstruct.png", 640, 400, true, false, true);
        splashScreenTextArea = new ImageView(instructionLayer);

        legalLayer = new Image("/invincibagelcreds.png", 640, 400, true, false, true);

        scoresLayer = new Image("/invincibagelscores.png", 640, 400, true, false, true);


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

    public static void main(String[] args) {
        launch(args);
    }
}

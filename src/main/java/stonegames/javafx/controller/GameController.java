package stonegames.javafx.controller;

import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import org.tinylog.Logger;
import stonegames.model.*;
import stonegames.model.StoneGameModel;
import java.util.Date;

public class GameController {

    private static final int NUMBEROFBOXES = 16;
    private static final int NUMBEROFSTONES = 6;
    private static MyRectangle myRectangles[] = new MyRectangle[NUMBEROFBOXES];

    private static String playerName;

    static MyButton myButtons[] = new MyButton[NUMBEROFSTONES];

    static boolean canMove = false;
    static boolean dragging = false;
    private static MyButton movingStoneButton1;
    private static MyButton movingStoneButton2;
    private static int countMove = 0;
    static Player player = new Player();

    static StoneGameModel stoneGameModel = new StoneGameModel();
    private static boolean drugClick = false;
    static MyRectangle movingButton1Rectangle = null;
    static MyRectangle movingButton2Rectangle = null;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label playerNameMsg;

    @FXML
    private Label movedTimes;

    @FXML
    public void initialize() {
        playerNameMsg.setText(playerName);
        player = new Player();
        player.setPlayerName(playerName);
        player.setStartTime(new Date());
        init();
    }

    public void init() {
        int n = 0;
        //initialize boxes
        for (int i = 0; i < NUMBEROFBOXES; i++) {
            MyRectangle myRectangle = new MyRectangle(n + 110, 78, 60, 150);
            myRectangles[i] = myRectangle;
            n += 60;
        }
        for (int i = 0; i < NUMBEROFSTONES; i++) {
            MyButton mybutton = new MyButton(i);    //initialize the button with index
            myButtons[i] = mybutton;
            if (i == 0 || i == 2 || i == 4) {
                mybutton.setStyle("-fx-background-color: red;" +
                        "-fx-font: Arial;-fx-font-size:15;-fx-text-fill: yellow;");
                mybutton.setColor("red");
                mybutton.setText(String.valueOf(i));
                stoneGameModel.setStonesInBoxes(mybutton,myRectangles[i]);
                anchorPane.getChildren().add(mybutton);
            }
            if (i == 1 || i == 3 || i == 5) {
                mybutton.setStyle("-fx-background-color: black;" +
                        "-fx-font: Arial; -fx-font-size:15;-fx-text-fill: yellow;");
                mybutton.setColor("black");
                mybutton.setText(String.valueOf(i));
                stoneGameModel.setStonesInBoxes(mybutton,myRectangles[i]);
                anchorPane.getChildren().add(mybutton);
            }
            mybutton.setOnMouseDragged(this::handleMouseDragged);
            mybutton.setOnMouseClicked(e -> {handleMouseClicked(e,mybutton);});
            mybutton.setOnMouseReleased(this::handleMouseReleased);
        }
    }

    public static void setPlayerName(String p) {
        playerName = p;
    }

    //reset the buttons
    public static void resetButtons() {
        //boolean valid = validateDrag(movingButton1,movingButton2);

        if (movingStoneButton1 != null) {
            stoneGameModel.setStonesInBoxes(movingStoneButton1,movingButton1Rectangle);
            movingStoneButton1.setUncheck();
            canMove = false;
        }
        if (movingStoneButton2 != null) {
            stoneGameModel.setStonesInBoxes(movingStoneButton2,movingButton2Rectangle);
            MyRectangle button2_rectangle = movingButton2Rectangle;
            movingStoneButton2.setUncheck();
            canMove = false;
        }

        movingStoneButton1.buttonSwitch();
        movingStoneButton2.buttonSwitch();
        canMove = false;
    }

    public void handleMouseClicked(MouseEvent e,MyButton mybutton){
        if (drugClick) {
            drugClick = false;
            return;
        }
        movingStoneButton1 = null;
        movingStoneButton2 = null;
        canMove = stoneGameModel.validateCheck(mybutton,myButtons);

        for (MyButton button1 : myButtons) {
            if (button1.isButtonClicked()) {
                //memorize the position of the buttons
                Coordinate button1_centerPoint = new Coordinate((int) button1.getLayoutX() + 30, (int) button1.getLayoutY() + 75);

                if (movingStoneButton1 == null) {
                    movingStoneButton1 = button1;
                    for(MyRectangle rectangles : myRectangles){
                        if(rectangles.contains(button1_centerPoint)) movingButton1Rectangle = rectangles;
                    }
                } else if (movingStoneButton2 == null) {
                    movingStoneButton2 = button1;
                    for (MyRectangle rectangles : myRectangles) {
                        if (rectangles.contains(button1_centerPoint)) movingButton2Rectangle = rectangles;
                    }
                }
            }
        }
    }

    public void handleMouseDragged(MouseEvent e){
        if (canMove &&(movingStoneButton1.isButtonClicked()==true&& movingStoneButton2.isButtonClicked()==true)){
            dragging=true;
            MyButton checkedButton1=null;
            MyButton checkedButton2=null;
            for (MyButton button : myButtons) {
                if(button.isButtonClicked()==true){
                    //Logger.info("2hao"+checkedButton2.getIndex());
                    if (checkedButton1==null){
                        checkedButton1=button;
                        //Logger.info("1hao"+checkedButton1.getIndex());
                    }else checkedButton2 = button;
                }
            }
            if(checkedButton1==e.getSource()){
                checkedButton1.setLayoutX(e.getSceneX());
                checkedButton1.setLayoutY(e.getSceneY());
                checkedButton2.setLayoutX(e.getSceneX()+checkedButton2.getWidth());
                checkedButton2.setLayoutY(e.getSceneY());
                //Logger.info("drag ahead");
            }
            else{
                checkedButton2.setLayoutX(e.getSceneX());
                checkedButton2.setLayoutY(e.getSceneY());
                checkedButton1.setLayoutX(e.getSceneX()-checkedButton1.getWidth());
                checkedButton1.setLayoutY(e.getSceneY());
                //Logger.info("drag after");
            }
        }
    }

    public void handleMouseReleased(MouseEvent e){
        //at the end of the dragging
        if (canMove == true && dragging == true) {
            drugClick = true;
            //Logger.info("end of dragging");
            canMove = false;
            dragging = false;
            MyButton button1 = null;
            MyButton button2 = null;
            for (MyButton button : myButtons) {
                //loop through and find those buttons that was dragged
                if (button.isButtonClicked()) if (button1 == null) {
                    button1 = button;
                } else button2 = button;
            }
            //make sure the destenation have no buttons before arrived
            boolean valid = stoneGameModel.validateDrag(button1, button2,myButtons);
            if (!valid) {
                //return the button to the previous position
                Logger.info("Already have buttons!");
                resetButtons();
                return;
            }
            //make sure two buttons are in the boxes
            boolean button1IsInBox = stoneGameModel.checkStoneInBox(button1,myRectangles);
            boolean button2IsInBox = stoneGameModel.checkStoneInBox(button2,myRectangles);
            MyRectangle button1Rectangle = stoneGameModel.getStoneBox(button1,myRectangles);
            MyRectangle button2Rectangle = stoneGameModel.getStoneBox(button2,myRectangles);

            //finish check, if both okay, then put them into the new boxes
            if (button1IsInBox && button2IsInBox) {
                stoneGameModel.setStonesInBoxes(button1,button1Rectangle);
                stoneGameModel.setStonesInBoxes(button2,button2Rectangle);

                //Very important , reset the indexes of all the buttons.
                MyButton tempButtons1[] = new MyButton[NUMBEROFSTONES];
                for (MyButton button : myButtons) {
                    tempButtons1[button.getIndex()] = button;
                }
                stoneGameModel.resetIndexOfButtons(tempButtons1,myRectangles,myButtons);
            } else {
                Logger.info("invalid location");
                resetButtons();
            }
            MyButton tempButtons[] = new MyButton[NUMBEROFSTONES];
            for (MyButton button : myButtons) {
                tempButtons[button.getIndex()] = button;
            }

            Boolean success = true;
            success = stoneGameModel.SuccessCheck(tempButtons,myRectangles);
            Logger.info("is success :" + success);

            countMove++;
            movedTimes.setText(String.valueOf(countMove));

            if (success) {
                successProcess();
            }
        }
    }

    public void successProcess(){
        stoneGameModel.SavePlayerDataToJson(player,countMove,playerName);
        List<Player> top10players = null;
        stoneGameModel.setScoreBoard(top10players);

        Stage stage = new Stage();
        try {
            stage.setScene(new Scene((Parent) FXMLLoader.load(getClass().getResource("/fxml/result.fxml"))));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        stage.setTitle("Red and Black stone game");
        stage.show();
    }
}
package stonegames.javafx.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.tinylog.Logger;
import stonegames.model.GameModel;
import stonegames.model.Player;

import java.util.Date;


public class GameController {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label playerNameMsg;

    @FXML
    private Label movedTimes;

    private Player player;
    @FXML
    private GridPane board;
    private static String playerName;
    private Button buttons[] = new Button[6];
    private Rectangle rectangles [] = new Rectangle[16];
    private GameModel model =  new GameModel();
    private static boolean drugClick = false;
//    private static ArrayList<Integer> clickedButtonsLocations = new ArrayList<Integer>();
//    private static ArrayList<Button> clickedButtons = new ArrayList<Button>();
    private Button clickedButtons[] = new Button[2];

    private static Button movingBallButton1;
    private static Button movingBallButton2;
    static boolean canMove = false;
    static boolean dragging = false;
    private boolean moving = false;

    public static void setPlayerName(String p) {
        playerName = p;
    }
    @FXML
    public void initialize() {
        playerNameMsg.setText(playerName);
        player = new Player();
        player.setPlayerName(playerName);
        player.setStartTime(new Date());
        int n = 0;


        for (int i = 0; i < 16; i++) {
            Rectangle rectangle = new Rectangle(n + 0, 0, 50, 200);
            rectangle.setLayoutX(n+50);
            rectangle.setLayoutY(0);
            rectangle.setFill(Paint.valueOf("White"));
            //rectangle.setOnMouseClicked(this::handleMouseClicked);
            rectangles[i] = rectangle;
            n += 50;
            board.add(rectangle,i,0);
        }
        for (int i = 0; i < 6; i++) {
            Button mybutton = new Button();    //initialize the button with index
            buttons[i] = mybutton;
            if (i == 0 || i == 2 || i == 4) {
                mybutton.setStyle("-fx-background-color: red;" +
                        "-fx-font: Arial;-fx-font-size:15;-fx-text-fill: yellow;");
                mybutton.setText(String.valueOf(i));
                setBallsInBoxes(mybutton,rectangles[i]);
               //board.getChildren().add(mybutton);
               board.add(mybutton,i,0);
            }
            if (i == 1 || i == 3 || i == 5) {
                mybutton.setStyle("-fx-background-color: black;" +
                        "-fx-font: Arial; -fx-font-size:15;-fx-text-fill: yellow;");
                mybutton.setText(String.valueOf(i));
                setBallsInBoxes(mybutton,rectangles[i]);
                //board.getChildren().add(mybutton);
                board.add(mybutton,i,0);
            }

             mybutton.setOnMouseDragged(this::handleMouseDragged);
             mybutton.setOnMouseClicked(this::handleMouseClicked);
             //mybutton.setOnMouseReleased(this::handleMouseReleased);
        }
    }
        public void handleMouseClicked(MouseEvent e){
        if (drugClick) {
            drugClick = false;
            return;
        }
        Button button = (Button) e.getSource();
        //clickedButtonsLocations.add(Integer.valueOf(button.getText()));
        if (clickedButtons[0]==null)
            clickedButtons[0]= (Button) e.getSource();
        else clickedButtons[1]= (Button) e.getSource();
        if (clickedButtons[0] != null && clickedButtons[1] != null){
            if (model.isBallsNeighbour(GridPane.getColumnIndex(clickedButtons[0]),GridPane.getColumnIndex(clickedButtons[1]))){
                canMove = true;
                Logger.debug(canMove);
                moving = true;
                //clickedButtons = new Button[2];
            }
            //clickedButtons = new Button[2];
        }

        if(moving = true){
            //Rectangle rectangle = (Rectangle) e.getSource();
            //Logger.debug(GridPane.getColumnIndex(rectangle));
            MouseButton a = e.getButton();
            System.out.println(a);
        }
    }

    public void handleMouseDragged(MouseEvent e){
        if (canMove){
            dragging=true;
            Button checkedButton1=clickedButtons[0];
            Button checkedButton2=clickedButtons[1];
//            for (Button button : myButtons) {
//                if(button.isButtonClicked()==true){
//                    //Logger.info("2hao"+checkedButton2.getIndex());
//                    if (checkedButton1==null){
//                        checkedButton1=button;
//                        //Logger.info("1hao"+checkedButton1.getIndex());
//                    }else checkedButton2 = button;
//                }
//            }
            if(checkedButton1== e.getSource()){
                checkedButton1.setLayoutX(e.getSceneX());
                checkedButton1.setLayoutY(e.getSceneY());
                checkedButton2.setLayoutX(e.getSceneX()+checkedButton2.getWidth());
                checkedButton2.setLayoutY(e.getSceneY());
                Logger.info("drag ahead");
            }
            else{

                checkedButton2.setLayoutX(e.getSceneX());
                checkedButton2.setLayoutY(e.getSceneY());
                checkedButton1.setLayoutX(e.getSceneX()-checkedButton1.getWidth());
                checkedButton1.setLayoutY(e.getSceneY());
                Logger.info("drag after");
            }
        }
    }

    public void setBallsInBoxes(Button button,Rectangle buttonRectangle){
        button.setLayoutX(buttonRectangle.getLayoutX());
        button.setLayoutY(buttonRectangle.getLayoutY());
        button.setPrefWidth(buttonRectangle.getWidth());
        button.setPrefHeight(buttonRectangle.getHeight());
    }

    /*public void handleMouseReleased(MouseEvent e){
        //at the end of the dragging
        if (canMove == true && dragging == true) {
            drugClick = true;
            //Logger.info("end of dragging");
            canMove = false;
            dragging = false;
            Button button1 = clickedButtons.get(0);
            Button button2 = clickedButtons.get(1);
//            for (MyButton button : myButtons) {
//                //loop through and find those buttons that was dragged
//                if (button.isButtonClicked()) if (button1 == null) {
//                    button1 = button;
//                } else button2 = button;
//            }
            //make sure the destenation have no buttons before arrived
            boolean valid = model.locationsDontContainBalls();
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
    }*/
}

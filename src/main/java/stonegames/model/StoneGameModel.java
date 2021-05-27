package stonegames.model;

import org.tinylog.Logger;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import stonegames.javafx.controller.ResultController;
import stonegames.results.WriteToGson;

public class StoneGameModel {
    WriteToGson writeToGson = new WriteToGson();

    public void setStonesInBoxes(MyButton button,MyRectangle buttonRectangle){
        button.setLayoutX(buttonRectangle.getX1() - 7);
        button.setLayoutY(buttonRectangle.getY1() - 30);
        button.setPrefWidth(buttonRectangle.getWidth());
        button.setPrefHeight(buttonRectangle.getHight());
    }

    public boolean validateDrag(MyButton button1, MyButton button2,MyButton myButtons[]) {
        for (MyButton button : myButtons) {
            if (button != button1 && button != button2) {
                if ((button.getLayoutX() < button1.getLayoutX()+button1.getWidth()/2 && button.getLayoutX() + button.getWidth() > button1.getLayoutX()+button1.getWidth()/2 || (button.getLayoutX() < button2.getLayoutX()+button2.getWidth()/2 && button.getLayoutX() + button.getWidth() > button2.getLayoutX()+button2.getWidth()/2))) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validateCheck(MyButton mybutton,MyButton myButtons[]) {

        int numberOfCheck = 0;  //number of chosen buttons
        int checkIndex = 0;  //button place that the you chose

        for (int i = 0; i < myButtons.length; i++) { // loop through all the buttons to see which one (or more ) is checked
            if (myButtons[i].isButtonClicked() == true) {
                if (myButtons[i] == mybutton) {
                    mybutton.buttonSwitch();//Double select -> cancel selection
                }
                numberOfCheck++;
                checkIndex = myButtons[i].getIndex();
            }
        }
        if (numberOfCheck == 0) { //if no one is checked , we set the current button checked
            mybutton.buttonSwitch();
        } else if (numberOfCheck == 1) { //if one is checked , loop through and find which one is that
            MyButton checkedButton = null;
            for (MyButton button : myButtons) {
                if (button.isButtonClicked() == true) {
                    checkedButton = button;
                    break;
                }
            }
            if (mybutton.getIndex() == checkIndex + 1 || mybutton.getIndex() == checkIndex - 1) {
                if (Math.abs(mybutton.getLayoutX() - checkedButton.getLayoutX()) > (mybutton.getWidth())) {//must be getWidth instead of getx
                    Logger.info("They are not neighbor,can not move them");
                    return false;  //return inside a "if" always takes control out of calling method.
                }
                mybutton.buttonSwitch();
                return true;
            }
            return false;
        }
        return false;
    }

    public void resetIndexOfButtons(MyButton tempButtons1[],MyRectangle myRectangles[],MyButton myButtons[]){
        int index = 0;
        for (MyRectangle rectangle : myRectangles) {
            for (MyButton button : tempButtons1) {
                //double check if the buttons are in the boxes
                if (rectangle.contains(new Coordinate((int) button.getLayoutX() + 30, (int) button.getLayoutY() + 75))) {
                    button.setIndex(index);
                    myButtons[index] = button;
                    if (button.isButtonClicked()){
                        button.buttonSwitch();
                    }else {
                        button.buttonSwitch();
                        button.buttonSwitch();
                    }
                    index++;
                }
            }
        }
    }
    public void SavePlayerDataToJson(Player player,int countMove,String playerName) {
        player.setEndTime(new Date());
        player.setCount(countMove);
        player.setScore();
        try {
            writeToGson.saveData(player);
        } catch (IOException exc) {
            // TODO Auto-generated catch block
            exc.printStackTrace();
        }
        Logger.info("player" + player);
        ResultController.setMyScore(playerName + ": Congratulation!! You moved :" + countMove + " times" + " score:" + player.getScore());

    }

    public boolean SuccessCheck(MyButton tempButtons[],MyRectangle myRectangles[]) {

        for (int j = 0; j < tempButtons.length; j++) {
            if (!myRectangles[j].contains(new Coordinate((int) tempButtons[j].getLayoutX() + 30, (int) tempButtons[j].getLayoutY() + 75))) {
                return false;
            }
            if ((j < 3 && (!tempButtons[j].getColor().equals("red")))) {
                return false;
            }
        }
        return true;
    }

    public void setScoreBoard(List<Player> top10players){
        try {
            top10players = writeToGson.findTop10Player();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (top10players != null) {
            for (int k = 0; k < 10; k++) {
                if (k < top10players.size()) {
                    stringBuilder.append(" Player name :");
                    stringBuilder.append(top10players.get(k).getPlayerName());
                    stringBuilder.append(" Time : ");
                    stringBuilder.append(top10players.get(k).getSeconds());
                    stringBuilder.append(" Moved times :");
                    stringBuilder.append(top10players.get(k).getCount());
                    stringBuilder.append(" Score :");
                    stringBuilder.append(top10players.get(k).getScore());
                    stringBuilder.append("\n");
                }
            }
        }
        Logger.info("top 10:" + stringBuilder);
        ResultController.setTop10(stringBuilder.toString());
    }

    public boolean checkStoneInBox(MyButton button,MyRectangle myRectangles[]){
        Coordinate button_centerPoint = new Coordinate((int) button.getLayoutX() + 30, (int) button.getLayoutY() + 75);
        //check if they are in the boxes
        for (MyRectangle rectangle : myRectangles) {
            if (rectangle.contains(button_centerPoint)) {
                return true;
                //Logger.info("button1 is in box");
            }
        }
        return false;
    }

    public MyRectangle getStoneBox(MyButton button,MyRectangle myRectangles[]){
        Coordinate button_centerPoint = new Coordinate((int) button.getLayoutX() + 30, (int) button.getLayoutY() + 75);
        //check if they are in the boxes
        for (MyRectangle rectangle : myRectangles) {
            if (rectangle.contains(button_centerPoint)) {
                return rectangle;
            }
        }
       return null;
    }
}

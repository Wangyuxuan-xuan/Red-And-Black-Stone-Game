package stonegames.model;

import javafx.scene.control.Button;

/**
 *Customize a button, and inherit javafx.scene.control.Button.
 */
public class MyButton extends Button {

    /**
     * The index of button.
     */
    private int index = -1;
    /**
     * The button is checked or not.
     */
    private boolean checkedOrNot = false;
    /**
     * The color of this button.
     */
    private String color;


    /**
     * A constructor with arguments,
     * @param index The index of button.
     */
    public MyButton(int index){
        this.index=index;
    }

    /**
     * Get the index of this button,
     * @return index.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Set the index of this button,
     * @param index index.
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     *  The  button is check or not,
     * @return check or not.
     */
    public boolean isButtonClicked() {
        return checkedOrNot;
    }

    /**
     * count  button is check or not.
     */
    public void buttonSwitch() {

        if(checkedOrNot==true){
            setText(index+"");
        }
        else{
            setText(index+"selected");
        }
        this.checkedOrNot=!checkedOrNot;
    }

    /**
     * set the button is not checked.
     */
    public void setUncheck(){
        this.checkedOrNot = true;
    }


    /**
     * Get button is check or not,
     * @return is Checked Or Not.
     */
    public boolean isCheckedOrNot() {
        return checkedOrNot;
    }

    /**
     * set  button is check or not,
     * @param checkedOrNot true or false.
     */
    public void setCheckedOrNot(boolean checkedOrNot) {
        this.checkedOrNot = checkedOrNot;
    }

    /**
     *  Get the color of this button,
     * @return color.
     */
    public String getColor() {
        return color;
    }


    /**
     * Set the color of this button,
     * @param color color.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     *  return the message of this button,
     * @return the message of this button.
     */
    @Override
    public String toString() {
        return "MyButton{" +
                "index=" + index +
                ", checkedOrNot=" + checkedOrNot +
                ", color='" + color + '\'' +
                '}';
    }
}
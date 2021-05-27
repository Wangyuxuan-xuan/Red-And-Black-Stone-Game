package stonegames.model;


/**
 * This is a rectangular class.
 */
public class MyRectangle {
    private int x1;
    private int y1;
    private int width;
    private int hight;

    /**
     * Rectangular class constructor without arguments.
     */
    MyRectangle(){

    }

    /**
     * The rectangle class takes a constructor with arguments,
     * @param x1 layout x,
     * @param y1 layout y,
     * @param width Rectangular width,
     * @param height Rectangular height.
     */
    public MyRectangle(int x1, int y1, int width, int height) {
        this.x1 = x1;
        this.y1 = y1;
        this.width = width;
        this.hight = height;
    }

    /**
     * return the message of Rectangle,
     * @return string type of message of the rectangle.
     */
    @Override
    public String toString() {
        return "Rectangle [x1="+x1+" y1="+y1+" width="+width+" hight="+hight+"]";
    }


    /**
     * get layout x,
     * @return x1.
     */
    public int getX1() {
        return x1;
    }

    /**
     * set layout x,
     * @param x1 x1.
     */
    public void setX1(int x1) {
        this.x1 = x1;
    }

    /**
     * get layout y,
     * @return y.
     */
    public int getY1() {
        return y1;
    }

    /**
     * set layout y,
     * @param y1 y.
     */
    public void setY1(int y1) {
        this.y1 = y1;
    }

    /**
     * get the width of the rectangle,
     * @return width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * set the width of the rectangle,
     * @param width width.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * get the height of the rectangle,
     * @return height.
     */
    public int getHight() {
        return hight;
    }

    /**
     * set the height of the rectangle,
     * @param height height.
     */
    public void setHeight(int height) {
        this.hight = height;
    }

    /**
     * Determines whether a coordinate is within the rectangle,
     * @param point  A coordinate,
     * @return true or false.
     */
    public boolean contains(Coordinate point){
        if(point.x>=x1&&point.x<=x1+width){
            if(point.y>=y1&&point.y<=y1+hight){
                return true;
            }
        }
        return false;
    }
    //

}
package state;

import org.junit.jupiter.api.Test;
import stonegames.modelAbandoned.Coordinate;
import stonegames.modelAbandoned.MyRectangle;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyRectangleTest {

    @Test
    public void testRectangle(){
        MyRectangle myRectangle=new MyRectangle(10,10,60,150);
        Coordinate point=new Coordinate(35,45);
        Coordinate point2=new Coordinate(80,90);
        System.out.println(myRectangle);
        assertTrue(myRectangle.getX1()==10);
        assertTrue(myRectangle.getY1()==10);
        assertTrue(myRectangle.getWidth()==60);
        assertTrue(myRectangle.getHight()==150);
        assertTrue(myRectangle.contains(point));
        assertFalse(myRectangle.contains(point2));
    }
}

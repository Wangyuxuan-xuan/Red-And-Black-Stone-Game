package state;

import org.junit.jupiter.api.Test;
import stonegames.model.Coordinate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CoordinateTest {

    @Test
    public void testCoordinate(){
        Coordinate coordinate=new Coordinate(10,20);
        assertNotNull(coordinate);
        assertTrue(coordinate.x==10);
        assertTrue(coordinate.y==20);
    }
}

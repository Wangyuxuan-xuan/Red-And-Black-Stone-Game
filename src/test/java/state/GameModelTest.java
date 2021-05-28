package state;
import org.junit.jupiter.api.Test;
import stonegames.model.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameModelTest {

    @Test
    public void TestSetBallsLocation(){
        GameModel gameModel = new GameModel();
        gameModel.setBallsLocation(1,2,6,7);
        //assertTrue(gameModel.);
    }

}

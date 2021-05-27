package results;

import org.junit.jupiter.api.Test;
import stonegames.results.WriteToGson;
import stonegames.model.Player;

import java.io.IOException;
import java.util.Date;

public class WriteToGsonTest {

    @Test
    public void testReadFromFile() throws IOException {
        System.out.println(WriteToGson.findTop10Player());
    }

    @Test
    public void testsaveData() throws InterruptedException, IOException {
        Player player=new Player();
        Date date1=new Date();
        player.setStartTime(date1);
        player.setPlayerName("player1");
        player.setCount(100);
        Thread.sleep(5000);
        Date date2=new Date();
        player.setEndTime(date2);
        player.setScore();

        WriteToGson writeToGson=new WriteToGson();
        writeToGson.saveData(player);

    }
}

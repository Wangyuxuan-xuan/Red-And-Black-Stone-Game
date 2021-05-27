package stonegames.results;

import com.google.gson.Gson;
import org.tinylog.Logger;
import stonegames.model.Player;


import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * A Gson read and write utility class.
 */
public class WriteToGson implements Comparator<Player> {


    /**
     * save game data,
     * @param player player,
     * '@throws' IOException.
     */
    public void saveData(Player player) throws IOException {
        try {
            File file=new File("GameData.json");

            FileOutputStream fileOutputStream = new FileOutputStream(file, true);

            fileOutputStream.write(player.toString().getBytes());
            fileOutputStream.write("\r\n".getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }//write to the end


        /*FileWriter fileWriter = new FileWriter("GameData.json");

        gson.toJson(player,fileWriter);*/
    }

    /**
     * Returns the top ten data in the GSON data,
     * @return List<Player>,
     * '@throws' IOException.
     */
    public static List<Player> findTop10Player() throws IOException {

        List<Player> top10Players = new ArrayList<Player>();
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("GameData.json"));
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fileInputStream));
            String string = null;
            while((string = buffer.readLine() )!= null){ //use buffer.readline read one line at a time
                Player player = new Gson().fromJson(string, Player.class);
                top10Players.add(player);
            }
            fileInputStream.close();

            top10Players.sort((o1, o2) -> o2.getScore()-o1.getScore());
            Logger.info("top10Players:"+top10Players);
            return top10Players;
            //top10Players.sort(new Comparator<Player>(){

                    /*
                    @Override
                    public int compare(Player player1,Player player2){
                        return player1.getScore().compareTo.player2.getScore();
                    }
                });   */


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        //return top10Players;
        return null;
    }

    /**
     * Compare two players by their scores,
     * @param o1 player1,
     * @param o2 player2,
     * @return score.
     */


    @Override
    public int compare(Player o1, Player o2) {
        // TODO Auto-generated method stub
        return o1.getScore()-o2.getScore();
    }

    //@Override

}

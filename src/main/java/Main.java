import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        //SimpleUI ui=new SimpleUI();
        //ui.setVisible(true);

        String path = "C:/Users/Robin/Desktop/Skolarbete/Thesis/Data/mango/images.jpg";
        String test = RobinsVersion.classifyImage(path);
        //JSONObject jo = (JSONObject) RobinsVersion.classifyImage(path); 
        RobinsVersion.list(test);

        
    }

}

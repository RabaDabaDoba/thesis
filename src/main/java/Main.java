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
        //String urltest = RobinsVersion.classifyURL("https://d2lnr5mha7bycj.cloudfront.net/product-image/file/large_91e6ebd6-fb26-4320-bc37-2003de8b54ce.jpg");
        //String cameratest = RobinsVersion.classifyCamera();
        
        
        ArrayList<ClassifiedObject> result = RobinsVersion.stringify(test);
        //System.out.println(result.toString());
        
        for (ClassifiedObject co : result) {
            System.out.println(co.getName() + " -> " + co.getValue());
        }
        
        /*
        To do list:
        
        Take a picture
        Classify picture
        Show results on screen
        Wait for users to click
        Register users choice
        Add users choice as category for the taken picture to a buffer
        "print out" the ticket that they will use to scan
        
       ...
        ...
        
        End of the day:
        
        Need some sort of error check on the buffered pictures in case they are wrong
        Send all of those pictures to IBM to improve api. 
  
        
        
        */

        
    }

}

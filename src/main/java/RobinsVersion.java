
import com.github.sarxos.webcam.Webcam;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import javax.imageio.ImageIO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Robin
 */
public class RobinsVersion {
final static String apikey= "arK3RfX0XWFFUdA_ES6TZ8-Yb1bnC-sTeNxo4BEAm1Jy";
    static IamOptions options = new IamOptions.Builder()
            .apiKey(apikey)
            .build();

    static VisualRecognition service = new VisualRecognition("2018-03-19", options);

    public static void main(String[] args) throws FileNotFoundException {
        String path = "C:/Users/Robin/Desktop/Skolarbete/Thesis/Data/mango/61TbBBpszTL._SX425_.jpg";
        System.out.println(classifyImage(path));
    }

    public static String classifyImage(String path) throws FileNotFoundException {
        
        /*
        URL url = new URL("https://images-na.ssl-images-amazon.com/images/I/71gI-IUNUkL._SY355_.jpg");
        Image image = ImageIO.read(url);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write((RenderedImage) image,"jpg", os); 
        InputStream fis = new ByteArrayInputStream(os.toByteArray());
        */
        
        InputStream imagesStream = new FileInputStream(path);
        ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                .imagesFile(imagesStream)
                .imagesFilename("download.jpg")
                .threshold((float) 0)
                .classifierIds(Arrays.asList("DefaultCustomModel_1716876290"))
                .build();

        ClassifiedImages result = service.classify(classifyOptions).execute();
        //System.out.println(result.toString());
        System.out.println(result.getImages().get(0).getClassifiers().get(0).getClasses().get(1).getClassName()); //fml one can do this way
        return result.toString();
    }
    
    public static String classifyURL(String adress) throws FileNotFoundException, IOException {
        
        
        URL url = new URL(adress);
        Image image = ImageIO.read(url);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write((RenderedImage) image,"jpg", os); 
        InputStream imagesStream = new ByteArrayInputStream(os.toByteArray());
        
        
        
        ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                .imagesFile(imagesStream)
                .imagesFilename("URL")
                .threshold((float) 0)
                .classifierIds(Arrays.asList("DefaultCustomModel_1716876290"))
                .build();

        ClassifiedImages result = service.classify(classifyOptions).execute();
        //System.out.println(result.toString());
        return result.toString();
    }
    
    public static String classifyCamera() throws FileNotFoundException, IOException {
        
        
        Webcam webcam = Webcam.getDefault();
        webcam.open();
	Image image = webcam.getImage();
	//ImageIO.write(image, "PNG", new File("test.png")); //THis has to be reaned later on when it is going to be trained
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write((RenderedImage) image,"jpg", os); 
        InputStream imagesStream = new ByteArrayInputStream(os.toByteArray());
        
        ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                .imagesFile(imagesStream)
                .imagesFilename("URL")
                .threshold((float) 0)
                .classifierIds(Arrays.asList("DefaultCustomModel_1716876290"))
                .build();

        ClassifiedImages result = service.classify(classifyOptions).execute();
        //System.out.println(result.toString());
        
        return result.toString();
    }
    
    

    public static ArrayList<ClassifiedObject> stringify(String JSON_string) {
        JSONParser parser = new JSONParser();
        
        

        try {
            JSONObject IBM_response_string = (JSONObject) parser.parse(JSON_string);
            String images_path = (String) IBM_response_string.get("images").toString();
            JSONArray images = (JSONArray) parser.parse(images_path);
            JSONObject classifiers = (JSONObject) images.get(0);
            String classifiers_path = (String) classifiers.get("classifiers").toString();
            JSONArray list_at_classifiers = (JSONArray) parser.parse(classifiers_path);
            JSONObject classes = (JSONObject) list_at_classifiers.get(0);
            String classes_path = (String) classes.get("classes").toString();

            

            //THIS IS THE ARRAY WITH ALL RESULTS
            JSONArray results_list = (JSONArray) parser.parse(classes_path);

            Iterator<JSONObject> it = results_list.iterator();

            ArrayList<JSONObject> results = new ArrayList<>();

            //This line of code is just like the one above but very compact XD, not ideal.
            //Iterator<JSONObject> it = ((JSONArray) parser.parse((String) ((JSONObject) ((JSONArray) parser.parse((String) ((JSONObject) ((JSONArray) parser.parse((String) ((JSONObject) parser.parse(test)).get("images").toString())).get(0)).get("classifiers").toString())).get(0)).get("classes").toString())).iterator();
            //Creates a list with json pairs
            //ArrayList<JSONObject> results = new ArrayList<>();
            while (it.hasNext()) {
                results.add(it.next());
            }

            //Now get them to a array without 
            ArrayList<ClassifiedObject> result = new ArrayList<>();
            for (JSONObject jo : results) {
                ClassifiedObject temp = new ClassifiedObject(jo.get("class").toString(), Double.parseDouble(jo.get("score").toString().trim()));
                result.add(temp);
            }
            //Sorting the results based on value
            Collections.sort(result, new Sortbyvalue());
            //reverse order so that at pos 0 is the highest %
            Collections.reverse(result);


            //for (ClassifiedObject co : result) {
            //    System.out.println(co.getName() + " -> " + co.getValue());
            //}
            
            return result;

        } catch (ParseException ex) {
            System.out.println("error =/");
            return null;
        }

    }

}


import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
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

    static IamOptions options = new IamOptions.Builder()
            .apiKey("sP83C9a-mEePlb5-4banZN1EYLSbxavGnpYmOLYGl-v7")
            .build();

    static VisualRecognition service = new VisualRecognition("2018-03-19", options);

    public static void main(String[] args) throws FileNotFoundException {
        String path = "C:/Users/Robin/Desktop/Skolarbete/Thesis/Data/mango/61TbBBpszTL._SX425_.jpg";
        System.out.println(classifyImage(path));
    }

    public static String classifyImage(String path) throws FileNotFoundException {
        InputStream imagesStream = new FileInputStream(path);
        ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                .imagesFile(imagesStream)
                .imagesFilename("download.jpg")
                .threshold((float) 0)
                .classifierIds(Arrays.asList("DefaultCustomModel_1716876290"))
                .build();

        ClassifiedImages result = service.classify(classifyOptions).execute();

        return result.toString();
    }

    public static ArrayList<ClassifiedObject> list(String JSON_string) {
        JSONParser parser = new JSONParser();

        try {
            JSONObject IBM_response_string = (JSONObject) parser.parse(JSON_string);

            String images_path = (String) IBM_response_string.get("images").toString();

            JSONArray images = (JSONArray) parser.parse(images_path);
            //System.out.println(images.get(0));

            JSONObject classifiers = (JSONObject) images.get(0);

            String classifiers_path = (String) classifiers.get("classifiers").toString();
            //System.out.println("This is second: " + second);

            JSONArray list_at_classifiers = (JSONArray) parser.parse(classifiers_path);
            //System.out.println(asd.get(0));
            System.out.println(list_at_classifiers);
            JSONObject classes = (JSONObject) list_at_classifiers.get(0);
            String classes_path = (String) classes.get("classes").toString();
            //System.out.println("This is third: " + third);

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
            //System.out.println(result);

            for (ClassifiedObject co : result) {
                System.out.println(co.toString());
            }
            
            return result;

        } catch (ParseException ex) {
            System.out.println("error =/");
            return null;
        }

    }

}

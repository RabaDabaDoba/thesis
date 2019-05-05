
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;


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

}

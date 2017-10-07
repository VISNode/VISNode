package jonata;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author JonataBecker
 */
public class MetaDataCreate {

    private static final String PATH = "/home/jonatabecker/NetBeansProjects/VISNode/src/main/resources/visnode/pdi/process";
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        MetaDataCreate metaDataCreate = new MetaDataCreate();
        metaDataCreate.process("AverageBlurProcess");
    }

    public void process(String process) throws FileNotFoundException, IOException {

        System.out.println(getClass().getResource("/"));
        
        Metadata metadata = new Metadata();
        String name = process;
        name = name.replaceFirst("Process$", "");
        name = name.replaceAll("([a-z])([A-Z])", "$1 $2");
        metadata.name = name;
        metadata.description = "<No description specified á>";
        metadata.author = "VISNode team";
        metadata.helpUrl = String.format("https://raw.githubusercontent.com/Jouwee/VISNode/master/src/main/resources/visnode/pdi/process/%s.md", process);
        metadata.codeUrl = String.format("https://raw.githubusercontent.com/Jouwee/VISNode/master/src/main/java/visnode/pdi/process/%s.java", process);
        
        
        
        
        try (FileOutputStream out = new FileOutputStream(new File(PATH + "/" + process + ".json"))) {
            out.write(new Gson().toJson(metadata).getBytes());
        }
        try (FileOutputStream out = new FileOutputStream(new File(PATH + "/" + process + ".md"))) {
            out.write(("# "+name).getBytes());
        }
    }

    private static class Metadata {

        private String name;
        private String description;
        private String author;
        private String[] tags;
        private String helpUrl;
        private String codeUrl;

    }

}

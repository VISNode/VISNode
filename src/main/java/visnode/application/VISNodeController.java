package visnode.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import visnode.application.parser.NodeNetworkParser;

/**
 * Application controler
 */
public class VISNodeController {

    /** Model */
    private final VISNodeModel model;
    /** Node network parser */
    private final NodeNetworkParser parser;

    /**
     * VISNode model
     *
     * @param model
     */
    public VISNodeController(VISNodeModel model) {
        this.model = model;
        this.parser = new NodeNetworkParser();
    }

    /**
     * Creates a new project
     */
    public void createNew() {
        model.setNetwork(NodeNetworkFactory.createEmtpy());
    }
    
    /**
     * Save the current project to it's linked file
     */
    public void save() {
        try (PrintWriter writer = new PrintWriter(model.getLinkedFile(), "UTF-8");) {
            writer.print(parser.toJson(model.getNetwork()));
        } catch (IOException ex) {
            ExceptionHandler.get().handle(ex);
        }
    }
    
    /**
     * Save the current project as a file
     *
     * @param file
     */
    public void saveAs(File file) {
        try (PrintWriter writer = new PrintWriter(file, "UTF-8");) {
            writer.print(parser.toJson(model.getNetwork()));
            model.setLinkedFile(file);
        } catch (IOException ex) {
            ExceptionHandler.get().handle(ex);
        }
    }

    /**
     * Open a file as a project
     *
     * @param file
     */
    public void open(File file) {
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
            try (BufferedReader br = new BufferedReader(isr)) {
                StringBuilder sb = new StringBuilder();
                String s = br.readLine();
                while (s != null) {
                    sb.append(s);
                    s = br.readLine();
                }
                model.setNetwork(parser.fromJson(sb.toString()));
            }
            model.setLinkedFile(file);
        } catch (IOException ex) {
            throw new InvalidOpenFileException(ex);
        }
    }

}

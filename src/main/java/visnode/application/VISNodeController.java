package visnode.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import visnode.application.mvc.PropertyEvent;
import visnode.application.parser.NodeNetworkParser;
import visnode.commons.NodeCloner;
import visnode.commons.clipboard.Clipboard;
import visnode.executor.EditNodeDecorator;
import visnode.executor.ProcessNode;
import visnode.gui.Selection;

/**
 * Application controller
 */
public class VISNodeController {

    /** Model */
    private final VISNodeModel model;
    /** Node network parser */
    private final NodeNetworkParser parser;
    /** Recent project listeners */
    private final List<Consumer<List<File>>> recentProjectListeners;
    /** Recent input listeners */
    private final List<Consumer<List<File>>> recentInputListeners;
    /** Rendering options listeners */
    private final List<Runnable> renderingOptionsListeners;

    /**
     * VISNode model
     *
     * @param model
     */
    public VISNodeController(VISNodeModel model) {
        this.model = model;
        this.parser = new NodeNetworkParser();
        this.recentProjectListeners = new ArrayList<>();
        this.recentInputListeners = new ArrayList<>();
        this.renderingOptionsListeners = new ArrayList<>();
        model.addEventListener(PropertyEvent.class, (evt) -> {
            if (evt.getPropertyName().equals("userPreferences")) {
                fireRecentProjects();
                fireRecentInputs();
            }
        });
    }

    /**
     * Creates a new project
     */
    public void createNew() {
        model.setNetwork(NodeNetworkFactory.createEmtpy());
        model.setLinkedFile(null);
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
            model.getUserPreferences().addRecentProject(file);
            fireRecentProjects();
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
            model.getUserPreferences().addRecentProject(file);
            fireRecentProjects();
        } catch (IOException ex) {
            throw new InvalidOpenFileException(ex);
        }
    }

    /**
     * Adds a recent project listener
     * 
     * @param listener 
     */
    public void addRecentProjectListener(Consumer<List<File>> listener) {
        recentProjectListeners.add(listener);
        fireRecentProjects(listener);
    }

    /**
     * Fire the recent projects event
     */
    private void fireRecentProjects() {
        for (Consumer<List<File>> listener : recentProjectListeners) {
            fireRecentProjects(listener);
        }
    }
    
    /**
     * Fire the recent projects event
     * 
     * @param listener
     */
    private void fireRecentProjects(Consumer<List<File>> listener) {
        listener.accept(model.getUserPreferences().getRecentProjects());
    }
    
    /**
     * Adds a recent input listener
     * 
     * @param listener 
     */
    public void addRecentInputListener(Consumer<List<File>> listener) {
        recentInputListeners.add(listener);
        fireRecentInputs(listener);
    }

    /**
     * Fire the recent projects event
     */
    public void fireRecentInputs() {
        for (Consumer<List<File>> listener : recentInputListeners) {
            fireRecentInputs(listener);
        }
    }
    
    /**
     * Fire the recent inputs event
     * 
     * @param listener
     */
    public void fireRecentInputs(Consumer<List<File>> listener) {
        listener.accept(model.getUserPreferences().getRecentInput());
    }
    
    /**
     * Rendering options listener
     * 
     * @param listener 
     */
    public void addRenderingOptionsListener(Runnable listener) {
        renderingOptionsListeners.add(listener);
    }
    
    /**
     * Repaint the image previews
     */
    public void repaintImagePreviews() {
        for (Runnable consumer : renderingOptionsListeners) {
            consumer.run();
        }
    }
    
    /**
     * Copy the selected nodes
     */
    public void copyNodes() {
        Selection<NodeView> nodes = VISNode.get().getNetworkEditor().getSelection();
        Clipboard.get().put(nodes
                .stream()
                .filter(view -> view.getModel().getDecorated() instanceof ProcessNode)
                .map(view -> view.getModel())
                .map(node -> new NodeCloner(node).fullClone().createEditNode())
                .collect(Collectors.toList()));
   }
    
    /**
     * Paste de selected nodes
     */
    public void pasteNodes() {
        if (Clipboard.get().isSupported(List.class)) {
            List<EditNodeDecorator> list = Clipboard.get().get(List.class);
            for (EditNodeDecorator node : list) {
                EditNodeDecorator newNode = new NodeCloner(node)
                        .cloneProcess()
                        .cloneInputs()
                        .createEditNode();
                newNode.getPosition().x += 50;
                newNode.getPosition().y += 50;
                model.getNetwork().add(newNode);
            }
        }
        
    }

}

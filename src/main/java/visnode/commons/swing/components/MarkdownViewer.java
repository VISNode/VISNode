package visnode.commons.swing.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.URL;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javax.swing.JPanel;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import visnode.commons.http.Http;

/**
 * Markdown viewer
 */
public class MarkdownViewer extends JPanel {

    /** Web view user for rendering the markdown */
    private WebView webView;

    /**
     * Markdown viewer
     */
    public MarkdownViewer() {
        super();
        initGui();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        JFXPanel jfxPanel = new JFXPanel();
        jfxPanel.setPreferredSize(new Dimension(800, 600));
        Platform.runLater(() -> {
            webView = new WebView();
            Scene scene = new Scene(webView);
            jfxPanel.setScene(scene);
        });
        setLayout(new BorderLayout());
        add(jfxPanel);
    }
    
    /**
     * Loads a markdown from a URL
     * 
     * @param url 
     */
    public void loadUrl(String url) {
        new Http().get(url).thenAccept((result) -> {
            load(result.asString());
        });
    }
    
    /**
     * Loads a markdown from a URL
     * 
     * @param url 
     */
    public void loadUrl(URL url) {
        new Http().get(url).thenAccept((result) -> {
            load(result.asString());
        });
    }
    
    /**
     * Loads the markdown from a buffer
     * 
     * @param buffer 
     */
    public void load(String buffer) {
        String asHtml = markdownToHtml(buffer);
        Platform.runLater(() -> {
            webView.getEngine().loadContent(asHtml);
        });
    }

    /**
     * Converts a markdown buffer to a Html buffer
     * 
     * @param buffer
     * @return String
     */
    private String markdownToHtml(String buffer) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(buffer);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String asHtml = renderer.render(document);
        return asHtml;
    }

}

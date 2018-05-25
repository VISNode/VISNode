package visnode.gui;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import org.pushingpixels.substance.api.ComponentState;
import org.pushingpixels.substance.api.SubstanceColorScheme;
import org.pushingpixels.substance.api.SubstanceSkin;

/**
 * UI Helper Skin Graphite
 */
public class UIHelperGraphite implements UIHelperTheme {

    /**
     * Returns the color scheme used by the interface
     *
     * @param skin
     * @return {@code Map<String, Color>}
     */
    @Override
    public Map<String, Color> getColors(SubstanceSkin skin) {
        SubstanceColorScheme scheme = skin.getColorScheme(new JPanel(), ComponentState.DEFAULT);
        Map<String, Color> colors = new HashMap<>();
        colors.put("Node.separetor", new Color(0xAAAAAA));
        colors.put("Node.background", new Color(0x666666));
        colors.put("Node.border", scheme.getUltraDarkColor().darker());
        colors.put("Node.border:selected", scheme.getSelectionBackgroundColor());
        colors.put("Node.Header.background1", new Color(0x555555));
        colors.put("Node.Header.background2", new Color(0x333333));
        colors.put("Node.Header.title", scheme.getUltraLightColor().brighter());
        colors.put("ConnectorPoint.border", scheme.getUltraDarkColor().darker());
        colors.put("NodeConnection.border", scheme.getUltraDarkColor().darker().darker());
        colors.put("NodeConnection.color1", scheme.getBackgroundFillColor());
        colors.put("NodeConnection.color2", scheme.getBackgroundFillColor().brighter());
        colors.put("NodeConnection.point", scheme.getMidColor());
        colors.put("ProcessBrowser.odd", new Color(0xEDEDED));
        colors.put("ProcessBrowser.even", new Color(0x555555));
        colors.put("DynamicValue.foreground", new Color(0x999999));
        colors.put("DynamicValue.background", new Color(0x333333));
        colors.put("Icon", new Color(0xDEDEDE));
        colors.put("Node.container.c1", new Color(0x484848));
        colors.put("Node.container.c2", new Color(0x585858));
        return colors;
    }

    /**
     * Returns the markdown style
     *
     * @return String
     */
    @Override
    public String getMarkdown() {
        return "MarkdownViewer.css";
    }

    /**
     * Returns the code editor style
     * 
     * @return String
     */    
    @Override
    public String getCodeEditor() {
        return "CodeEditorTheme.xml";
    }
}

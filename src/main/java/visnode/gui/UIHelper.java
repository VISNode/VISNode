package visnode.gui;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import org.pushingpixels.substance.api.ComponentState;
import org.pushingpixels.substance.api.SubstanceColorScheme;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.SubstanceSkin;

/**
 * UI Helper class
 */
public class UIHelper {
    
    /**
     * Returns a color of the interface
     * 
     * @param key
     * @return 
     */
    public static Color getColor(String key) {
        return getColors(SubstanceLookAndFeel.getCurrentSkin()).get(key);
    }
    
    /**
     * Returns the color scheme used by the interface
     * 
     * @param skin
     * @return {@code Map<String, Color>}
     */
    private static Map<String, Color> getColors(SubstanceSkin skin) {
        SubstanceColorScheme scheme = skin.getColorScheme(new JPanel(), ComponentState.DEFAULT);
        Map<String, Color> colors = new HashMap<>();
        colors.put("Node.background", scheme.getBackgroundFillColor());
        colors.put("Node.border", scheme.getUltraDarkColor().darker());
        colors.put("Node.Header.background1", scheme.getBackgroundFillColor());
        colors.put("Node.Header.background2", scheme.getBackgroundFillColor().darker());
        colors.put("Node.Header.title", scheme.getUltraLightColor().brighter());
        colors.put("ConnectorPoint.border", scheme.getUltraDarkColor().darker());
        colors.put("NodeConnection.border", scheme.getUltraDarkColor().darker().darker());
        colors.put("NodeConnection.color1", scheme.getBackgroundFillColor());
        colors.put("NodeConnection.color2", scheme.getBackgroundFillColor().brighter());
        colors.put("NodeConnection.point", scheme.getMidColor());
        return colors;
    }
    
}

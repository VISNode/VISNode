package visnode.gui;

import java.awt.Color;
import java.util.Map;
import org.pushingpixels.substance.api.SubstanceSkin;

/**
 * UI Helper Skin
 */
public interface UIHelperTheme {
    
    /**
     * Returns the markdown style
     * 
     * @return String
     */
    public String getMarkdown();
    
    /**
     * Returns the code editor style
     * 
     * @return String
     */
    public String getCodeEditor();
    
    /**
     * Returns the color scheme used by the interface
     *
     * @param skin
     * @return {@code Map<String, Color>}
     */
    public Map<String, Color> getColors(SubstanceSkin skin);
}

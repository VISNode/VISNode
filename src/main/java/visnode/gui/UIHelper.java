package visnode.gui;

import java.awt.Color;
import java.util.Map;
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
     * Returns the markdown style
     *
     * @return String
     */
    public static String getMarkdown() {
        return getSkin(SubstanceLookAndFeel.getCurrentSkin()).
                getHelper().
                getMarkdown();
    }
    
    /**
     * Returns the code editor style
     *
     * @return String
     */
    public static String getCodeEditor() {
        return getSkin(SubstanceLookAndFeel.getCurrentSkin()).
                getHelper().
                getCodeEditor();
    }

    /**
     * Returns the color scheme used by the interface
     *
     * @param skin
     * @return {@code Map<String, Color>}
     */
    private static Map<String, Color> getColors(SubstanceSkin skin) {
        return getSkin(skin).getHelper().getColors(skin);
    }

    private static Skin getSkin(SubstanceSkin skin) {
        Skin skinObj = Skin.valueOf(skin.getClass());
        if (skinObj == null) {
            skinObj = Skin.GRAPHITE;
        }
        return skinObj;
    }

}

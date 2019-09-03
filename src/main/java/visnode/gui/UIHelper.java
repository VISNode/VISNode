package visnode.gui;

import java.awt.Color;
import java.util.Map;
import javax.swing.UIManager;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.SubstanceSkin;
import org.pushingpixels.substance.api.skin.GraphiteSkin;

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
        return getColors(UIHelper.getSkin()).get(key);
    }

    /**
     * Returns the markdown style
     *
     * @return String
     */
    public static String getMarkdown() {
        return getTheme(UIHelper.getSkin()).
                getHelper().
                getMarkdown();
    }

    /**
     * Returns the code editor style
     *
     * @return String
     */
    public static String getCodeEditor() {
        return getTheme(UIHelper.getSkin()).
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
        return getTheme(skin).getHelper().getColors(skin);
    }

    private static Theme getTheme(SubstanceSkin skin) {
        Theme skinObj = Theme.valueOf(skin.getClass());
        if (skinObj == null) {
            skinObj = Theme.GRAPHITE;
        }
        return skinObj;
    }
    
    private static SubstanceSkin getSkin() {
        try {
            SubstanceLookAndFeel lookAndFeel = (SubstanceLookAndFeel) UIManager.getLookAndFeel();
            Class skin = Theme.valueOfKey(lookAndFeel.getName()).getTheme();
            return (SubstanceSkin) skin.newInstance();
        } catch(Exception e) {
            return new GraphiteSkin();
        }
    }

}

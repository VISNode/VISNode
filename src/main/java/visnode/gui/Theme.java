package visnode.gui;

import org.pushingpixels.substance.api.SubstanceSkin;
import org.pushingpixels.substance.api.skin.BusinessBlackSteelSkin;
import org.pushingpixels.substance.api.skin.GraphiteSkin;

/**
 * Application
 */
public enum Theme {

    GRAPHITE("Graphite", GraphiteSkin.class, new UIHelperGraphite(), "Substance Graphite"),
    BLACK_STEEL("Black Steel", BusinessBlackSteelSkin.class, new UIHelperBlackSteel(), "Substance Business Black Steel");

    /** Name */
    private final String name;
    /** Theme */
    private final Class theme;
    /** Theme helper */
    private final UIHelperTheme helper;
    /** Key */
    private final String key;

    private Theme(String name, Class<? extends SubstanceSkin> them, UIHelperTheme helper, String key) {
        this.name = name;
        this.theme = them;
        this.helper = helper;
        this.key = key;
    }

    /**
     * Returns the skin name
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the theme class
     *
     * @return Class
     */
    public Class getTheme() {
        return theme;
    }

    /**
     * Returns the theme helper
     *
     * @return UiHelperSkin
     */
    public UIHelperTheme getHelper() {
        return helper;
    }
    
    /**
     * Returns the them key
     * 
     * @return String
     */
    public String getKey() {
        return key;
    }

    public static Theme valueOf(Class<? extends SubstanceSkin> theme) {
        for (Theme s : values()) {
            if (s.theme.equals(theme)) {
                return s;
            }
        }
        return null;
    }
    
    public static Theme valueOfKey(String key) {
        for (Theme s : values()) {
            if (s.key.equals(key)) {
                return s;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return name;
    }

}

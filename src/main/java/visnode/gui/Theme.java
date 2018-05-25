package visnode.gui;

import org.pushingpixels.substance.api.SubstanceSkin;
import org.pushingpixels.substance.api.skin.BusinessBlackSteelSkin;
import org.pushingpixels.substance.api.skin.GraphiteSkin;

/**
 * Application
 */
public enum Theme {

    GRAPHITE("Graphite", GraphiteSkin.class, new UIHelperGraphite()),
    BLACK_STEEL("Black Steel", BusinessBlackSteelSkin.class, new UIHelperBlackSteel());

    /** Name */
    private final String name;
    /** Theme */
    private final Class theme;
    /** Theme helper */
    private final UIHelperTheme helper;

    private Theme(String name, Class<? extends SubstanceSkin> them, UIHelperTheme helper) {
        this.name = name;
        this.theme = them;
        this.helper = helper;
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

    public static Theme valueOf(Class<? extends SubstanceSkin> theme) {
        for (Theme s : values()) {
            if (s.theme.equals(theme)) {
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

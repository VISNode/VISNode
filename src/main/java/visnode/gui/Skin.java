package visnode.gui;

import org.pushingpixels.substance.api.SubstanceSkin;
import org.pushingpixels.substance.api.skin.BusinessBlackSteelSkin;
import org.pushingpixels.substance.api.skin.GraphiteSkin;

/**
 * Application
 */
public enum Skin {

    GRAPHITE("Graphite", GraphiteSkin.class, new UIHelperGraphite()),
    BLACK_STEEL("Black Steel", BusinessBlackSteelSkin.class, new UIHelperBlackSteel());

    /** Name */
    private final String name;
    /** Skin */
    private final Class skin;
    /** Skin helper */
    private final UIHelperSkin helper;

    private Skin(String name, Class<? extends SubstanceSkin> skin, UIHelperSkin helper) {
        this.name = name;
        this.skin = skin;
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
     * Returns the skin class
     *
     * @return Class
     */
    public Class getSkin() {
        return skin;
    }

    /**
     * Returns the skin helper
     *
     * @return UiHelperSkin
     */
    public UIHelperSkin getHelper() {
        return helper;
    }

    public static Skin valueOf(Class<? extends SubstanceSkin> skin) {
        for (Skin s : values()) {
            if (s.skin.equals(skin)) {
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

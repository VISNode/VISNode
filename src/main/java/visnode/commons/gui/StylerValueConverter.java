package visnode.commons.gui;

import java.awt.Color;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.RGBColor;

/**
 * Conversion functions for stylers
 */
public class StylerValueConverter {

    /**
     * Converts the value to the specified type
     * 
     * @param <T>
     * @param value
     * @param type
     * @return T
     */
    public static <T> T convert(CSSValue value, Class<T> type) {
        if (type.equals(Color.class)) {
            return (T) color(value);
        }
        throw new UnsupportedOperationException("Unknown CSS conversion to " + type);
    }
    
    /**
     * Converts a value to a color
     * 
     * @param value
     * @return Color
     */
    public static Color color(CSSValue value) {
        RGBColor rgbColorValue = ((CSSPrimitiveValue)value).getRGBColorValue();
        int r = (int)rgbColorValue.getRed().getFloatValue(CSSPrimitiveValue.CSS_NUMBER);
        int g = (int)rgbColorValue.getGreen().getFloatValue(CSSPrimitiveValue.CSS_NUMBER);
        int b = (int)rgbColorValue.getBlue().getFloatValue(CSSPrimitiveValue.CSS_NUMBER);
        return new Color(r, g, b);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.commons.gui;

import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.parser.SACParserCSS3;
import io.reactivex.functions.Consumer;
import java.awt.Color;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.RGBColor;

/**
 * Abstract component styler
 * 
 * @param <T>
 */
public abstract class AbstractStyler<T extends Component> implements Styler<T> {

    /** Component to style */
    private final T component;

    /**
     * Creates a new default component styler
     * 
     * @param component 
     */
    public AbstractStyler(T component) {
        this.component = component;
    }
    
    /**
     * Sets up the component RuleMap
     * 
     * @param ruleMap 
     */
    public abstract void setup(RuleMap ruleMap);
    
    @Override
    public void apply(String style) {
        RuleMap rulemap = new RuleMap();
        setup(rulemap);
        try {
            InputSource source = new InputSource(new StringReader(style));
            CSSOMParser parser = new CSSOMParser(new SACParserCSS3());
            CSSStyleDeclaration decl = parser.parseStyleDeclaration(source);
            for (int i = 0; i < decl.getLength(); i++) {
                final String propName = decl.item(i);

                Rule rule = rulemap.get(propName);
                if (rule == null) {
                    System.out.println("Unknown CSS property: " + propName);
                    continue;
                }
                rule.consumer.accept(StylerValueConverter.convert(decl.getPropertyCSSValue(propName), rule.type));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public T getComponent() {
        return component;
    }
    
    /**
     * Rule map
     */
    protected class RuleMap {
        
        /** The rules */
        private final Map<String, Rule> rules;

        /**
         * Creates a new RuleMap
         */
        private RuleMap() {
            this.rules = new HashMap<>();
        }
        
        /**
         * Puts a rule in the map
         * 
         * @param <K>
         * @param property
         * @param type
         * @param consumer 
         */
        public <K> void put(String property, Class<K> type, Consumer<K> consumer) {
            rules.put(property, new Rule(type, consumer));
        }
        
        /**
         * Returns a rule
         * @param <K>
         * @param property
         * @return Rule
         */
        protected <K> Rule<K> get(String property) {
            return rules.get(property);
        }
        
    }
    
    /**
     * Rule
     */
    protected class Rule<V> {
    
        /** Type */
        private final Class<V> type;
        /** Consumer */
        private final Consumer<V> consumer;

        /**
         * Creates a new Rule
         * 
         * @param type
         * @param consumer 
         */
        public Rule(Class<V> type, Consumer<V> consumer) {
            this.type = type;
            this.consumer = consumer;
        }
        
    }
    
}

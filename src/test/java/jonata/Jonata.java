package jonata;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * @author JonataBecker
 */
public class Jonata {

    public static void main(String[] args) {
        
//        System.out.println(NumberUtils.isParsable("5.5d"));
        System.out.println(int.class.isAssignableFrom(Number.class));
        System.out.println(Number.class.isAssignableFrom(int.class));
        
    }
    
}

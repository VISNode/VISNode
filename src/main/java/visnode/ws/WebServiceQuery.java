package visnode.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Web Service Query
 */
public class WebServiceQuery {

    /** Command list */
    private final List<Command> commands;

    private WebServiceQuery() {
        this.commands = new ArrayList<>();
    }
    
    /**
     * Adds a new command
     * 
     * @param name
     * @param value
     * @return WebServiceQuery
     */
    public WebServiceQuery put(String name, Object value) {
        commands.add(new Command(name, value));
        return this;
    }

    @Override
    public String toString() {
        return commands.stream().
                map((it) -> it.toString()).
                collect(Collectors.joining(" and "));
    }
    
    /**
     * Creates a new query
     * 
     * @return WebServiceQuery
     */
    public static WebServiceQuery create() {
        return new WebServiceQuery();
    }
    
    /**
     * Query command
     */
    private class Command {

        /** Name */
        private final String name;
        /** Value */
        private final Object value;

        public Command(String name, Object value) {
            this.name = name;
            this.value = value;
        }
        
        @Override
        public String toString() {
            return name + " eq " + String.valueOf(value);
        }

    }

}

package visnode.pdi.process;

import visnode.pdi.Process;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import org.reflections.Reflections;

/**
 * Class responsible for load processes
 */
public class ProcessLoader {

    /** Object instance */
    private static ProcessLoader instance;
    /** Process */
    private final Set<Class<? extends Process>> processes;

    private ProcessLoader() {
        this.processes = loadProcesses();
    }

    /**
     * Loads the processes
     *
     * @return {@code Set<Class<? extends Process>>}
     */
    private Set<Class<? extends Process>> loadProcesses() {
        Reflections reflections = new Reflections("visnode.pdi.process");
        Set<Class<? extends Process>> classes = reflections.
                getSubTypesOf(Process.class).stream().
                collect(Collectors.toSet());
        classes.add(DynamicPixelProcess.class);
        classes.add(ObjectExtractionProcess.class);
        classes.add(ScriptProcess.class);
        classes.add(InputProcess.class);
        return Collections.unmodifiableSet(classes);
    }

    /**
     * Returns the processes class
     *
     * @return {@code Set<Class<? extends Process>>}
     */
    public Set<Class<? extends Process>> getProcesses() {
        return processes;
    }

    /**
     * Returns the object instance
     *
     * @return ProcessLoader
     */
    public static ProcessLoader get() {
        if (instance == null) {
            instance = new ProcessLoader();
        }
        return instance;
    }

}

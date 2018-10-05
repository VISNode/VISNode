package visnode.application;

import com.google.gson.GsonBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import visnode.pdi.Process;

/**
 * Process meta-data
 */
public class ProcessMetadata {

    /** Process */
    private Class process;
    /** Name */
    private String name;
    /** Name en-US */
    private String name_en_US;
    /** Name pt-BR */
    private String name_pt_BR;
    /** Description */
    private String description;
    /** Description en-US */
    private String description_en_US;
    /** Description pt-BR */
    private String description_pt_BR;
    /** Help URL */
    private String helpUrl;
    /** Code URL */
    private String codeUrl;
    /** Script URL */
    private String scriptUrl;
    /** Project URL */
    private String projectUrl;
    /** Defaults */
    private final Map<String, String> defaults;
    /** Snippet */
    private String snippet;

    public ProcessMetadata() {
        name = "";
        name_en_US = "";
        name_pt_BR = "";
        description = "";
        description_pt_BR = "";
        description_en_US = "";
        defaults = new HashMap<>();
    }

    /**
     * Returns the meta-data from the class
     *
     * @param process
     * @return ProcessMetadata
     */
    public static ProcessMetadata fromClass(Class<? extends Process> process) {
        return fromClass(process, new Locale("en", "US"));
    }

    /**
     * Returns the meta-data from the class
     *
     * @param process
     * @param locale
     * @return ProcessMetadata
     */
    public static ProcessMetadata fromClass(Class<? extends Process> process, Locale locale) {
        try {
            InputStream stream = ProcessMetadata.class.getResourceAsStream('/' + process.getName().replace('.', '/') + ".json");
            ProcessMetadata meta = new GsonBuilder().create().fromJson(new InputStreamReader(stream, Charset.forName("utf-8")), ProcessMetadata.class);
            meta.process = process;
            meta.name = meta.name_en_US;
            meta.description = meta.description_en_US;
            if (locale.getLanguage().equals("pt")) {
                meta.name = meta.name_pt_BR;
                meta.description = meta.description_pt_BR;
            }
            String mdLocale = "_" + locale.toLanguageTag().replace("-", "_") + ".md";
            meta.helpUrl = meta.helpUrl.replace(".md", mdLocale);
            return meta;
        } catch (Exception e) {
            ProcessMetadata metadata = new ProcessMetadata();
            metadata.process = process;
            String name = process.getSimpleName();
            name = name.replaceFirst("Process$", "");
            name = name.replaceAll("([a-z])([A-Z])", "$1 $2");
            metadata.name = name;
            return metadata;
        }
    }

    /**
     * Returns the process class
     *
     * @return Class
     */
    public Class getProcess() {
        return process;
    }

    /**
     * Returns the name
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Returns true if the name contains a filter
     *
     * @param filter
     * @return boolean
     */
    public boolean containsName(String filter) {
        String clearFilter = StringUtils.stripAccents(filter.toLowerCase());
        return StringUtils.stripAccents(name_pt_BR).toLowerCase().contains(clearFilter)
                || StringUtils.stripAccents(name_en_US).toLowerCase().contains(clearFilter);
    }

    /**
     * Returns the description
     *
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns true if the description contains a filter
     *
     * @param filter
     * @return boolean
     */
    public boolean containsDescription(String filter) {
        String clearFilter = StringUtils.stripAccents(filter.toLowerCase());
        return StringUtils.stripAccents(description_pt_BR).toLowerCase().contains(clearFilter)
                || StringUtils.stripAccents(description_pt_BR).toLowerCase().contains(clearFilter);
    }

    /**
     * Returns the help URL
     *
     * @return String
     */
    public String getHelpUrl() {
        return helpUrl;
    }

    /**
     * Returns the code URL
     *
     * @return String
     */
    public String getCodeUrl() {
        return codeUrl;
    }

    /**
     * Returns the script URL
     *
     * @return String
     */
    public String getScriptUrl() {
        return scriptUrl;
    }

    /**
     * Returns the project URL
     *
     * @return String
     */
    public String getProjectUrl() {
        return projectUrl;
    }

    /**
     * Returns the defaults parameters
     *
     * @param key
     * @return String
     */
    public String getDefault(String key) {
        return this.defaults.get(key);
    }

    /**
     * Returns the snippet
     *
     * @return String
     */
    public String getSnippet() {
        return snippet;
    }
}

package visnode.application;

import visnode.gui.Skin;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.paim.commons.RenderingOptions;

/**
 * User preferences
 */
public class UserPreferences {

    /** Valid locale */
    private static final List LOCALES = Arrays.asList("pt-BR", "en-US");
    /** Maximum recent files */
    private static final int MAX_RECENTS = 10;
    /** Recent projects */
    private final List<String> recentProjects;
    /** Recent input files */
    private final List<String> recentInputFiles;
    /** Rendering options */
    private final RenderingOptions renderingOptions;
    /** System locale subject */
    private transient BehaviorSubject<Locale> localeSubject;
    /** System locale */
    private Locale locale;
    /** System skin subject */
    private transient BehaviorSubject<Skin> skinSubject;
    /** Skin */
    private Skin skin;

    /**
     * Creates a new set of user preferences
     */
    public UserPreferences() {
        this.recentProjects = new ArrayList<>();
        this.recentInputFiles = new ArrayList<>();
        this.renderingOptions = new RenderingOptions();
        this.locale = getDefaultLocale();
        this.skin = Skin.GRAPHITE;
    }

    /**
     * Returns the default locale
     *
     * @return Locale
     */
    private Locale getDefaultLocale() {
        Locale def = new Locale("en", "US");
        if (LOCALES.stream().
                anyMatch((l) -> l.equals(Locale.getDefault().toLanguageTag()))) {
            def = Locale.getDefault();
        }
        return def;
    }

    /**
     * Adds a file as a recent project
     *
     * @param file
     */
    public void addRecentProject(File file) {
        String name = file.getAbsolutePath();
        if (recentProjects.contains(name)) {
            recentProjects.remove(name);
        }
        recentProjects.add(0, name);
        while (recentProjects.size() > MAX_RECENTS) {
            recentProjects.remove(MAX_RECENTS);
        }
    }

    /**
     * Get the recent projects
     *
     * @return List
     */
    public List<File> getRecentProjects() {
        return recentProjects.stream().map(name -> new File(name)).collect(Collectors.toList());
    }

    /**
     * Adds a file as a recent inputs
     *
     * @param file
     */
    public void addRecentInput(File file) {
        String name = file.getAbsolutePath();
        if (recentInputFiles.contains(name)) {
            recentInputFiles.remove(name);
        }
        recentInputFiles.add(0, name);
        while (recentInputFiles.size() > MAX_RECENTS) {
            recentInputFiles.remove(MAX_RECENTS);
        }
    }

    /**
     * Get the recent inputs
     *
     * @return List
     */
    public List<File> getRecentInput() {
        return recentInputFiles.stream().map(name -> new File(name)).collect(Collectors.toList());
    }

    /**
     * Rendering options
     *
     * @return RenderingOptions
     */
    public RenderingOptions getRenderingOptions() {
        return renderingOptions;
    }

    /**
     * Sets the system locale
     *
     * @param locale
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
        this.localeSubject.onNext(locale);
    }

    /**
     * Returns the system locale
     *
     * @return Locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Returns the system locale
     *
     * @return {@code Observable<Locale> }
     */
    public Observable<Locale> getLocaleSubject() {
        if (localeSubject == null) {
            localeSubject = BehaviorSubject.createDefault(getLocale());
        }
        return localeSubject;
    }

    /**
     * Returns the skin
     *
     * @return String
     */
    public Skin getSkin() {
        return skin;
    }

    /**
     * Returns the system Skin
     *
     * @return {@code Observable<Skin> }
     */
    public Observable<Skin> getSkinSubject() {
        if (skinSubject == null) {
            skinSubject = BehaviorSubject.createDefault(getSkin());
        }
        return skinSubject;
    }

    /**
     * Sets the skin
     *
     * @param skin
     */
    public void setSkin(Skin skin) {
        this.skin = skin;
        this.skinSubject.onNext(skin);
    }

}

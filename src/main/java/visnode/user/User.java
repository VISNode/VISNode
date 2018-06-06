package visnode.user;

/**
 * The user
 */
public class User {

    /** Email */
    private String email;
    /** Name */
    private String name;
    /** Password */
    private String password;
    /** Points */
    private int xp;
    /** Institution */
    private String institution;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    /**
     * Returns the email
     *
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
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
     * Sets the name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the password
     *
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the points
     *
     * @return int
     */
    public int getXp() {
        return xp;
    }

    /**
     * Sets the points
     *
     * @param xp
     */
    public void setXp(int xp) {
        this.xp = xp;
    }

    /**
     * Returns the institution
     *
     * @return String
     */
    public String getInstitution() {
        return institution;
    }

    /**
     * Sets the institution
     *
     * @param institution
     */
    public void setInstitution(String institution) {
        this.institution = institution;
    }

}

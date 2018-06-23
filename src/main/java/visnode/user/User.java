package visnode.user;

import java.awt.image.BufferedImage;

/**
 * The user
 */
public class User {

    /** User image */
    private final transient UserImage userImage;
    /** User id */
    private long id;
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
    /** Image */
    private String image;

    public User() {
        this(null);
    }

    public User(String name) {
        this.name = name;
        this.userImage = new UserImage();
    }

    /**
     * Returns the user id
     *
     * @return long
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the user id
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
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

    /**
     * Returns the image
     *
     * @return String
     */
    public String getImage() {
        return image;
    }

    /**
     * Returns the image buffered
     *
     * @return BufferedImage
     */
    public BufferedImage getImageBuffered() {
        return userImage.fromBase64(getImage());
    }

    /**
     * Sets the image
     *
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Sets the image buffered
     * 
     * @param image
     */
    public void setImageBuffered(BufferedImage image) {
        setImage(userImage.toBase64(image));
    }

}

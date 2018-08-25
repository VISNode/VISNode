package visnode.challenge;

import visnode.user.User;

/**
 * Group User
 */
public class GroupUser {

    /** Id */
    private long id;
    /** User */
    private User user;

    public GroupUser() {
    }

    public GroupUser(User user) {
        this.user = user;
    }
    
    /**
     * Returns the id
     *
     * @return long
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the user
     *
     * @return User
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

}

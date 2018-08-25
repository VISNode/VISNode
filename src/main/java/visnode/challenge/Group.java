package visnode.challenge;

import java.util.ArrayList;
import java.util.List;
import visnode.user.User;

/**
 * Group
 */
public class Group {

    /** Id */
    private long id;
    /** Name */
    private String name;
    /** Users */
    private List<GroupUser> users;

    public Group() {
        this(null);
    }
    
    public Group(String name) {
        this.name = name;
        this.users = new ArrayList<>();
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
     * Clear user list
     */
    public void clearUsers() {
        this.users.clear();
    }

    /**
     * Adds a new user
     *
     * @param user
     */
    public void addUser(User user) {
        this.users.add(new GroupUser(user));
    }

    /**
     * Returns the users
     *
     * @return {@code List<GroupUser>}
     */
    public List<GroupUser> getUsers() {
        return users;
    }

    /**
     * Sets the users
     *
     * @param users
     */
    public void setUsers(List<GroupUser> users) {
        this.users = users;
    }

    /**
     * Returns true if the group has users
     *
     * @return boolean
     */
    public boolean hasUsers() {
        return !this.users.isEmpty();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Group other = (Group) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    
}

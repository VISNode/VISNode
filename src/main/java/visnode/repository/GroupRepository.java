package visnode.repository;

import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.stream.Collectors;
import visnode.challenge.Group;
import visnode.ws.WebService;
import visnode.ws.WebServiceException;

/**
 * Group repository
 */
public class GroupRepository {

    /** Instance */
    private static GroupRepository instance;

    /**
     * Returns all groups
     *
     * @return {@code List<Group>}
     * @throws RepositoryException
     */
    public List<Group> getAll() throws RepositoryException {
        try {
            return WebService.get().
                    get("group").
                    get(new TypeToken<List<Group>>() {
                    }).stream().collect(Collectors.toList());
        } catch (WebServiceException ex) {
            throw new RepositoryException(ex);
        }
    }

    /**
     * Save a new group
     *
     * @param group
     * @throws RepositoryException
     */
    public void save(Group group) throws RepositoryException {
        try {
            WebService.get().post("group", group);
        } catch (WebServiceException ex) {
            throw new RepositoryException(ex);
        }
    }

    /**
     * Save a new group
     *
     * @param group
     * @throws RepositoryException
     */
    public void update(Group group) throws RepositoryException {
        try {
            WebService.get().post("group/" + group.getId(), group);
        } catch (WebServiceException ex) {
            throw new RepositoryException(ex);
        }
    }

    /**
     * Returns the group repository instance
     *
     * @return GroupUserRepository
     */
    public static GroupRepository get() {
        if (instance == null) {
            instance = new GroupRepository();
        }
        return instance;
    }

}

package data;

import java.util.List;

public interface UserDAO {
    List<User> getAll();
    User getEntityById(String UID);
    User update(User user);
    boolean delete(String UID);
    boolean create(User user);
}

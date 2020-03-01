package dao;

import domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    void addUser(User user);

    //根据Id查找用户
    public User getUserById(Long id) throws Exception;
}

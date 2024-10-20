package lk.ecommerce.app.service.user;

import lk.ecommerce.app.dto.UserDto;
import lk.ecommerce.app.entity.User;

import java.util.List;

public interface UserService {

    User postUser(UserDto userDto);

    User updateUser(Long id, UserDto userDto);

    User getUserById(Long id);

    List<User> getAllUsers();

    void deleteUser(Long id);
}

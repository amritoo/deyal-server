package app.deyal.deyal_server.manager;

import app.deyal.deyal_server.dao.UserRepository;
import app.deyal.deyal_server.model.ApiError;
import app.deyal.deyal_server.model.User;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthManager {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void insertUser(User user) {
        userRepository.insert(user);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public User retrieveUserByEmail(String email) throws ApiError {
        User probe = new User();
        probe.setEmail(email);
        Optional<User> entity = userRepository.findOne(Example.of(probe));
        if (!entity.isPresent()) {
            throw ApiError.EMAIL_NOT_FOUND;
        }

        return entity.get();
    }

    public User retrieveUserById(String userId) throws ApiError {
        Optional<User> entity = userRepository.findById(userId);
        if (!entity.isPresent()) {
            throw ApiError.NOT_FOUND;
        }

        return entity.get();
    }

    public void validateEmail(String email) throws ApiError {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw ApiError.INVALID_EMAIL;
        }

        User probe = new User();
        probe.setEmail(email);
        Optional<User> entity = userRepository.findOne(Example.of(probe));
        if (entity.isPresent()) {
            throw ApiError.EMAIL_EXISTS;
        }
    }

}

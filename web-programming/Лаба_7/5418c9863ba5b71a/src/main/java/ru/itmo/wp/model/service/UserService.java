package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** @noinspection UnstableApiUsage*/
public class UserService {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private static final String PASSWORD_SALT = "177d4b5f2e4f4edafa7404533973c04c513ac619";

    public void validateRegistration(User user, String password) throws ValidationException {
        if (Strings.isNullOrEmpty(user.getLogin())) {
            throw new ValidationException("Login is required");
        }
        if (!user.getLogin().matches("[a-z]+")) {
            throw new ValidationException("Login can contain only lowercase Latin letters");
        }
        if (user.getLogin().length() > 8) {
            throw new ValidationException("Login can't be longer than 8 letters");
        }
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new ValidationException("Login is already in use");
        }

        if (Strings.isNullOrEmpty(password)) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4 characters");
        }
        if (password.length() > 12) {
            throw new ValidationException("Password can't be longer than 12 characters");
        }
    }

    public void register(User user, String password) {
        userRepository.save(user, getPasswordSha(password));
    }

    private String getPasswordSha(String password) {
        return Hashing.sha256().hashBytes((PASSWORD_SALT + password).getBytes(StandardCharsets.UTF_8)).toString();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User find(long id) {
        return userRepository.find(id);
    }

    public User validateAndFindByLoginAndPassword(String login, String password) throws ValidationException {
        User user = userRepository.findByLoginAndPasswordSha(login, getPasswordSha(password));
        if (user == null) {
            throw new ValidationException("Invalid login or password");
        }
        return user;
    }
    public Map<Long, User> findUsersMapByIds(List<Long> ids) {
        Map<Long, User> result = new TreeMap<>();

        for (Long id : ids) {
            result.put(id, find(id));
        }

        return result;
    }

    public void validatePrivilegesChange(User currentUser, String stringId, String stringAdmin) throws ValidationException {
        if (Strings.isNullOrEmpty(stringId)) {
            throw new ValidationException("User id is required");
        }
        if (!isLong(stringId)) {
            throw new ValidationException("User id must be a number");
        }

        if (Strings.isNullOrEmpty(stringAdmin)) {
            throw new ValidationException("Parameter 'admin' is required");
        }
        if (!isBoolean(stringAdmin)) {
            throw new ValidationException("Admin must be a boolean");
        }

        User user = find(Long.parseLong(stringId));
        if (user == null) {
            throw new ValidationException("There's no such user");
        }
        if (!currentUser.getAdmin()) {
            throw new ValidationException("You have no rights to grant admin rights");
        }
        if (user.getAdmin() == Boolean.parseBoolean(stringAdmin)) {
            throw new ValidationException("This user already has the rights you trying to grant him");
        }
    }

    public boolean isLong(String stringLong) {
        try {
            Long.parseLong(stringLong);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public boolean isBoolean(String stringBool) {
        return "true".equalsIgnoreCase(stringBool) || "false".equalsIgnoreCase(stringBool);
    }

    public void changeAdminStatus(long userId, boolean admin) {
        userRepository.changeAdminStatus(userRepository.find(userId), admin);
    }
}

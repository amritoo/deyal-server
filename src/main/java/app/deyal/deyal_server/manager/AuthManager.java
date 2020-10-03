package app.deyal.deyal_server.manager;

import app.deyal.deyal_server.dao.UserRepository;
import app.deyal.deyal_server.model.*;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Optional<User> entity = userRepository.findByEmail(email);
        if (!entity.isPresent())
            throw ApiError.EMAIL_NOT_FOUND;
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
        Optional<User> entity = userRepository.findByEmail(email);
        if (entity.isPresent()) {
            throw ApiError.EMAIL_EXISTS;
        }
    }

    public String retrieveUsername(String userId) throws ApiError {
        return retrieveUserById(userId).getUserName();
    }

    public void addMissionToUser(String userId, String missionId, RequestType type) throws ApiError {
        User user = retrieveUserById(userId);
        if (user.getAllMissionInfoExceptOngoing().contains(missionId))   //does not add if it is already added
            return;
        switch (type) {
            case CREATE:
                user.getMissionInfo().getCreated().add(missionId);
                break;
            case COMPLETED:
                user.getMissionInfo().getOngoing().remove(missionId);
                user.getMissionInfo().getCompleted().add(missionId);
                break;
            case FAILED:
                user.getMissionInfo().getOngoing().remove(missionId);
                user.getMissionInfo().getFailed().add(missionId);
                break;
            case ONGOING:
                user.getMissionInfo().getOngoing().add(missionId);
                break;
        }
        updateUser(user);
    }

    public void changeRating(String userId, RequestType type, MissionDifficulty missionDifficulty) throws ApiError {
        User user = retrieveUserById(userId);
        switch (type) {
            case CLIENT_INCREASE:
                user.getMissionInfo().changeRatingAsClient(true, missionDifficulty);
                break;
            case CLIENT_DECREASE:
                user.getMissionInfo().changeRatingAsClient(false, missionDifficulty);
                break;
            case CLIENT_DECREASE_MORE:
                user.getMissionInfo().changeRatingAsClient(false, missionDifficulty);
                user.getMissionInfo().changeRatingAsClient(false, missionDifficulty);
                user.getMissionInfo().changeRatingAsClient(false, missionDifficulty);
                break;
            case CONTRACTOR_INCREASE:
                user.getMissionInfo().changeRatingAsContractor(true, missionDifficulty);
                break;
            case CONTRACTOR_DECREASE:
                user.getMissionInfo().changeRatingAsContractor(false, missionDifficulty);
                break;
        }
        user.calculateReputation();
        updateUser(user);
    }

    public void addNotificationToUser(String userId, String message, String missionId) throws ApiError {
        User user = retrieveUserById(userId);
        user.getNotifications().add(new Notification(message, missionId));
        updateUser(user);
    }

}

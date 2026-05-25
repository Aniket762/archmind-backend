package archmind.service;

import archmind.model.user.User;
import archmind.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    public User updateUser(String userId, User updateUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setUserName(updateUser.getUserName());
        existingUser.setEmail(updateUser.getEmail());
        return userRepository.save(existingUser);
    }
    public void deleteUser(String userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(existingUser);
    }
}
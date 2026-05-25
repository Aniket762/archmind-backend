package archmind.controller;

import archmind.model.user.User;
import archmind.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name= "User API", description = "Operations related to user")
@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all users")
    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @Operation(summary = "Get user by Id")
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable String userId){
        return userService.getUserById(userId);
    }
    @Operation(summary = "Update user")
    @PutMapping("/{userId}")
    public User updateUser(@PathVariable String userId,@RequestBody User updateUser){
        return userService.updateUser(userId,updateUser);
    }
    @Operation(summary = "Delete User")
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId){
         userService.deleteUser(userId);
    }
}

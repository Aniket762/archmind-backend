package archmind.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "UserProfiles")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfile {
    @Id
    private String userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;
    private String dob;
    private String work;
    private String education;
    private List<String> skills;
    private String githubLink;
    private String profilePicture;
}
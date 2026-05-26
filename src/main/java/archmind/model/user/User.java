package archmind.model.user;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Users")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @Id
    @JsonProperty("id")          // frontend reads "id"
    private String userId;
    @JsonProperty("name")        // frontend reads "name"
    private String userName;
    private String email;
    @JsonProperty("role")        // frontend reads "role"
    private UserRole userRole;
    private String password;
    private UserStatus userStatus;
    private boolean isEmailValid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;
}

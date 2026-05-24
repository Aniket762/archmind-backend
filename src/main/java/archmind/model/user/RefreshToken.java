package archmind.model.user;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "RefreshToken")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefreshToken {
    @Id
    private String userName;
    private String token;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
    private Boolean revoked;
}

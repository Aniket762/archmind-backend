package archmind.model.user;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.imageio.ImageTranscoder;
import java.time.LocalDateTime;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "UserStats")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStats {
    @Id
    private String userName;
    private Integer totalProblemsSolved;
    private Integer totalSubmissions;
    private Double averageScore;
    private Integer easySolved;
    private Integer mediumSolved;
    private Integer hardSolved;
    private Integer currentStreak;
    private Integer maxStreak;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

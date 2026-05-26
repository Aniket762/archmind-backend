package archmind.model.user;
import archmind.model.problem.Problem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "UserProgress")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProgress {
    @Id
    private String userName;
    private Problem problem;
    private ProgressStatus status;
    private Double bestScore;
    private Integer attemptCount;
    private LocalDateTime lastAttempted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

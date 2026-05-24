package archmind.model.solution;

import archmind.model.problem.Problem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Solutions")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Submission {
    @Id
    private String userId;
    private String questionId;
    private Problem problem;
    private String solution;
    private Language language;
    private LocalDateTime submittedAt;
    private Double score;
}

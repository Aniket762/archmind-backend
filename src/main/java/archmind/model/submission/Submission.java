package archmind.model.submission;

import archmind.model.problem.Problem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Solutions")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Submission {
    @Id
    private String submissionId;
    private String userId;
    private String problemId;
    private String solution;
    private Language language;
    private LocalDateTime submittedAt;
    private Double score;
}

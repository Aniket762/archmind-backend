package archmind.model.problem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Questions")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Problem {
    @Id
    private String questionId;
    private String slug;
    private String description;
    private List<TestCase> testCase;
    private List<String> hint;
    private List<Topic> topics;
    private Level level;
    private String createdBy;
    private String isPublished;
    private String createdAt;
    private String updatedAt;
}

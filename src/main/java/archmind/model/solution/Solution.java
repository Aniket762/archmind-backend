package archmind.model.solution;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Solutions")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Solution {
    @Id
    private String userId;
    private String questionId;
    private String solution;
    private Language language;
    private String timeStamp;
}

package archmind.repository;

import archmind.model.submission.Submission;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SubmissionRepository
        extends MongoRepository<Submission, String> {
    List<Submission> findByUserId(String userId);
    List<Submission> findByProblemId(String problemId);
    List<Submission> findByUserIdOrderBySubmittedAtDesc(String userId);
}
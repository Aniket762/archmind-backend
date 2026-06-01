package archmind.repository;

import archmind.model.problem.Level;
import archmind.model.problem.Problem;
import archmind.model.problem.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProblemRepository
        extends MongoRepository<Problem, String> {
    List<Problem> findByLevel(Level level);
    List<Problem> findByTopicsContaining(Topic topic);
    List<Problem> findByLevelAndTopicsContaining(Level level, Topic topic);
    List<Problem> findByTitleContainingIgnoreCase(String keyword);
    long countByLevel(String level);
}
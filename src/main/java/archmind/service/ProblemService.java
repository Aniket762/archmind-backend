package archmind.service;

import archmind.model.problem.Level;
import archmind.model.problem.Problem;
import archmind.model.problem.Topic;
import archmind.repository.ProblemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProblemService {

    private final ProblemRepository problemRepository;

    public ProblemService(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }

    public Problem getProblemById(String problemId) {
        return problemRepository.findById(problemId)
                .orElseThrow(() -> new RuntimeException("Problem not found"));
    }

    public Problem createProblem(Problem problem) {
        problem.setCreatedAt(LocalDateTime.now());
        problem.setUpdatedAt(LocalDateTime.now());

        return problemRepository.insert(problem);
    }

    public Problem updateProblem(
            String problemId,
            Problem updatedProblem
    ) {

        Problem existingProblem = getProblemById(problemId);

        existingProblem.setSlug(updatedProblem.getSlug());
        existingProblem.setTitle(updatedProblem.getTitle());
        existingProblem.setDescription(updatedProblem.getDescription());
        existingProblem.setTestCase(updatedProblem.getTestCase());
        existingProblem.setHint(updatedProblem.getHint());
        existingProblem.setTopics(updatedProblem.getTopics());
        existingProblem.setLevel(updatedProblem.getLevel());
        existingProblem.setCreatedBy(updatedProblem.getCreatedBy());
      //  existingProblem.setPublished(updatedProblem.isPublished());

        existingProblem.setUpdatedAt(LocalDateTime.now());

        return problemRepository.save(existingProblem);
    }

    public void deleteProblem(String problemId) {
        problemRepository.deleteById(problemId);
    }

    public List<Problem> getProblemsByLevel(Level level) {
        return problemRepository.findByLevel(level);
    }

    public List<Problem> getProblemsByTopic(Topic topic) {
        return problemRepository.findByTopicsContaining(topic);
    }

    public List<Problem> getProblemsByLevelAndTopic(
            Level level,
            Topic topic
    ) {
        return problemRepository.findByLevelAndTopicsContaining(level, topic);
    }

    public List<Problem> searchProblems(String keyword) {
        return problemRepository.findByTitleContainingIgnoreCase(keyword);
    }
    public List<Topic> getAllDistinctTopics() {
        return problemRepository.findAll()
                .stream()
                .flatMap(p -> p.getTopics().stream())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
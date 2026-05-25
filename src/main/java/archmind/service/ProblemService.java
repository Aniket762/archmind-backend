package archmind.service;

import archmind.model.problem.Level;
import archmind.model.problem.Problem;
import archmind.model.problem.Topic;
import archmind.repository.ProblemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemService {
    private final ProblemRepository problemRepository;
    public ProblemService(
            ProblemRepository problemRepository
    ) {
        this.problemRepository = problemRepository;
    }
    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }
    public Problem getProblemById(String problemId) {
        return problemRepository.findById(problemId)
                .orElseThrow(() ->
                        new RuntimeException("Problem not found"));
    }
    public Problem createProblem(Problem problem) {
        return problemRepository.save(problem);
    }
    public Problem updateProblem(
            String problemId,
            Problem updatedProblem
    ) {
        Problem existingProblem = problemRepository.findById(problemId)
                .orElseThrow(() ->
                        new RuntimeException("Problem not found"));
        existingProblem.setSlug(updatedProblem.getSlug());
        existingProblem.setTitle(updatedProblem.getTitle());
        existingProblem.setDescription(updatedProblem.getDescription());
        existingProblem.setTestCase(updatedProblem.getTestCase());
        existingProblem.setHint(updatedProblem.getHint());
        existingProblem.setTopics(updatedProblem.getTopics());
        existingProblem.setLevel(updatedProblem.getLevel());
        existingProblem.setIsPublished(updatedProblem.getIsPublished());
        existingProblem.setUpdatedAt(updatedProblem.getUpdatedAt());
        return problemRepository.save(existingProblem);
    }
    public void deleteProblem(String problemId) {
        Problem existingProblem = problemRepository.findById(problemId)
                .orElseThrow(() ->
                        new RuntimeException("Problem not found"));
        problemRepository.delete(existingProblem);
    }
    public List<Problem> getProblemsByLevel(Level level) {
        return problemRepository.findByLevel(level);
    }

    public List<Problem> getProblemsByTopic(Topic topic) {
        return problemRepository.findByTopicsContaining(topic);
    }
    public List<Problem> searchProblems(String keyword) {
        return problemRepository
                .findByTitleContainingIgnoreCase(keyword);
    }

}
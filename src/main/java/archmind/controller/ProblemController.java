package archmind.controller;

import archmind.model.problem.Level;
import archmind.model.problem.Problem;
import archmind.model.problem.Topic;
import archmind.service.ProblemService;
import archmind.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Problem Api", description = "Operations related to Problems")
@RestController
@RequestMapping("/api/problems")
public class ProblemController {

    private final ProblemService problemService;
    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @Operation(summary = "Get all problems with optional filters")
    @GetMapping("getAllProblems")
    public List<Problem> getAllProblems(
            @RequestParam(required = false) Level level,
            @RequestParam(required = false) Topic topic,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "12") int limit
    ) {
        if (keyword != null && !keyword.isBlank()) {
            return problemService.searchProblems(keyword);
        }
        if (level != null && topic != null) {
            return problemService.getProblemsByLevelAndTopic(level, topic);
        }
        if (level != null) {
            return problemService.getProblemsByLevel(level);
        }
        if (topic != null) {
            return problemService.getProblemsByTopic(topic);
        }
        return problemService.getAllProblems(level,
                topic,
                keyword,
                offset,
                limit
        );
    }
    @Operation(summary = "Get problems by Id")
    @GetMapping("/getProblemsById/{problemId}")
    public Problem getProblemsById(@PathVariable String problemId){
        return problemService.getProblemById(problemId);
    }
    @Operation(summary = "Create problem")
    @PostMapping("/createProblem")
    public Problem createProblem(
            @RequestBody Problem problem
    ) {
        return problemService.createProblem(problem);
    }
    @Operation(summary = "Update problem")
    @PutMapping("/updateProblem/{problemId}")
    public Problem updateProblem(
            @PathVariable String problemId,
            @RequestBody Problem updatedProblem
    ) {
        return problemService.updateProblem(problemId, updatedProblem);
    }
    @Operation(summary = "Delete problem")
    @DeleteMapping("/deleteProblem/{problemId}")
    public void deleteProblem(
            @PathVariable String problemId
    ) {
        problemService.deleteProblem(problemId);
    }
    @Operation(summary = "Search problems")
    @GetMapping("/search")
    public List<Problem> searchProblems(
            @RequestParam String keyword
    ) {
        return problemService.searchProblems(keyword);
    }
    @Operation(summary = "Get all distinct topics")
    @GetMapping("getAllTopics")
    public List<Topic> getAllTopics() {
        return problemService.getAllDistinctTopics();
    }
}

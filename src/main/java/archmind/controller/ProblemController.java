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

    @Operation(summary = "Get all problems")
    @GetMapping
    public List<Problem> getAllProblems(){
        return problemService.getAllProblems();
    }
    @Operation(summary = "Get problems by Id")
    @GetMapping("/{problemId}")
    public Problem getProblemsById(@PathVariable String problemId){
        return problemService.getProblemById(problemId);
    }
    @Operation(summary = "Create problem")
    @PostMapping
    public Problem createProblem(
            @RequestBody Problem problem
    ) {
        return problemService.createProblem(problem);
    }
    @Operation(summary = "Update problem")
    @PutMapping("/{problemId}")
    public Problem updateProblem(
            @PathVariable String problemId,
            @RequestBody Problem updatedProblem
    ) {
        return problemService.updateProblem(problemId, updatedProblem);
    }
    @Operation(summary = "Delete problem")
    @DeleteMapping("/{problemId}")
    public void deleteProblem(
            @PathVariable String problemId
    ) {
        problemService.deleteProblem(problemId);
    }
    @Operation(summary = "Get problems by level")
    @GetMapping("/level/{level}")
    public List<Problem> getProblemsByLevel(@PathVariable Level level){
        return problemService.getProblemsByLevel(level);
    }
    @Operation(summary = "Get problems by topic")
    @GetMapping("/topic/{topic}")
    public List<Problem> getProblemsByTopic(@PathVariable Topic topic) {
        return problemService.getProblemsByTopic(topic);
    }
    @Operation(summary = "Search problems")
    @GetMapping("/search")
    public List<Problem> searchProblems(
            @RequestParam String keyword
    ) {
        return problemService.searchProblems(keyword);
    }
}

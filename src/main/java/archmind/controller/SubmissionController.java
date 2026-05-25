package archmind.controller;

import archmind.model.submission.Submission;
import archmind.service.SubmissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Submission API", description = "Operations relate dto Siubmission")
@RestController
@RequestMapping("/api/submission")
public class SubmissionController {
    private final SubmissionService submissionService;
    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @Operation(summary = "Create submission")
    @PostMapping
    public Submission createSubmission(
            @RequestBody Submission submission
    ) {
        return submissionService.createSubmission(submission);
    }

    @Operation(summary = "Get submission by ID")
    @GetMapping("/{submissionId}")
    public Submission getSubmissionById(
            @PathVariable String submissionId
    ) {
        return submissionService.getSubmissionById(submissionId);
    }
    @Operation(summary = "Get submissions by user")
    @GetMapping("/user/{userId}")
    public List<Submission> getSubmissionByUser(@PathVariable String userId){
        return submissionService.getSubmissionByUserId(userId);
    }
    @Operation(summary = "Get submissions by problem")
    @GetMapping("/problem/{problemId}")
    public List<Submission> getSubmissionsByProblem(
            @PathVariable String problemId
    ) {
        return submissionService.getSubmissionsByProblem(problemId);
    }

    @Operation(summary = "Delete submission")
    @DeleteMapping("/{submissionId}")
    public void deleteSubmission(
            @PathVariable String submissionId
    ) {
        submissionService.deleteSubmission(submissionId);
    }
}

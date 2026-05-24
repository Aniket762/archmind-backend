package archmind.controller;

import archmind.model.solution.Submission;
import archmind.service.SubmissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

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

}

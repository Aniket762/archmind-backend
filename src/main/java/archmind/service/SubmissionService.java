package archmind.service;

import archmind.model.submission.Submission;
import archmind.repository.SubmissionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    public SubmissionService(
            SubmissionRepository submissionRepository
    ) {
        this.submissionRepository = submissionRepository;
    }
    public Submission createSubmission(Submission submission) {
        submission.setSubmittedAt(LocalDateTime.now());
        return submissionRepository.save(submission);
    }
    public Submission getSubmissionById(String submissionId) {
        return submissionRepository.findById(submissionId)
                .orElseThrow(() ->
                        new RuntimeException("Submission not found"));
    }
    public List<Submission> getSubmissionByUserId(String userId) {
        return submissionRepository.findByUserId(userId);
    }
    public List<Submission> getSubmissionsByProblem(String questionId) {
        return submissionRepository.findByQuestionId(questionId);
    }
    public void deleteSubmission(String submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() ->
                        new RuntimeException("Submission not found"));
        submissionRepository.delete(submission);
    }
}
package archmind.service;

import archmind.dto.DashboardStatsResponse;
import archmind.dto.DashboardStatsResponse.*;
import archmind.model.problem.Problem;
import archmind.model.submission.Submission;
import archmind.repository.ProblemRepository;
import archmind.repository.SubmissionRepository;
import archmind.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final SubmissionRepository submissionRepository;
    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;
    public DashboardService(
            SubmissionRepository submissionRepository,
            ProblemRepository problemRepository,
            UserRepository userRepository
    ) {
        this.submissionRepository = submissionRepository;
        this.problemRepository = problemRepository;
        this.userRepository = userRepository;
    }
    public DashboardStatsResponse getDashboardStats(String userId) {
        DashboardStatsResponse stats = new DashboardStatsResponse();
        List<Submission> allSubmissions = submissionRepository
                .findByUserId(userId);
        long totalSolved = allSubmissions.stream()
                .filter(s -> s.getScore() != null && s.getScore() > 0)
                .map(Submission::getProblemId)
                .distinct()
                .count();
        stats.setTotalSolved((int) totalSolved);
        stats.setTotalProblems((int) problemRepository.count());
        double totalScore = allSubmissions.stream()
                .filter(s -> s.getScore() != null)
                .mapToDouble(Submission::getScore)
                .sum();
        stats.setTotalScore(totalScore);
        stats.setGlobalRank(calculateRank(userId, totalScore));
        stats.setCurrentStreak(calculateStreak(allSubmissions));
        setDifficultyBreakdown(stats, allSubmissions);
        stats.setSubmissionTrend(buildTrend(allSubmissions));
        stats.setActivityData(buildActivity(allSubmissions));
        stats.setRecentSubmissions(buildRecentSubmissions(allSubmissions));
        stats.setSkillBreakdown(buildSkillBreakdown(allSubmissions));
        return stats;
    }
    private int calculateStreak(List<Submission> submissions) {
        if (submissions.isEmpty()) return 0;
        List<LocalDateTime> dates = submissions.stream()
                .filter(s -> s.getSubmittedAt() != null)
                .map(Submission::getSubmittedAt)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        if (dates.isEmpty()) return 0;
        int streak = 1;
        LocalDateTime prev = dates.get(0);
        for (int i = 1; i < dates.size(); i++) {
            LocalDateTime curr = dates.get(i);
            long daysBetween = java.time.temporal.ChronoUnit.DAYS
                    .between(curr.toLocalDate(), prev.toLocalDate());
            if (daysBetween == 1) {
                streak++;
                prev = curr;
            } else if (daysBetween > 1) {
                break;
            }
        }
        return streak;
    }
    private int calculateRank(String userId, double userScore) {
        List<archmind.model.user.User> allUsers = userRepository.findAll();
        int rank = 1;
        for (archmind.model.user.User user : allUsers) {
            if (user.getUserId().equals(userId)) continue;
            List<Submission> userSubs = submissionRepository
                    .findByUserId(user.getUserId());
            double score = userSubs.stream()
                    .filter(s -> s.getScore() != null)
                    .mapToDouble(Submission::getScore)
                    .sum();
            if (score > userScore) rank++;
        }
        return rank;
    }
    private void setDifficultyBreakdown(
            DashboardStatsResponse stats,
            List<Submission> submissions
    ) {
        Set<String> solvedProblemIds = submissions.stream()
                .filter(s -> s.getScore() != null && s.getScore() > 0)
                .map(Submission::getProblemId)
                .collect(Collectors.toSet());

        int easy = 0, medium = 0, hard = 0;
        for (String pid : solvedProblemIds) {
            problemRepository.findById(pid).ifPresent(p -> {});
            Optional<Problem> prob = problemRepository.findById(pid);
            if (prob.isPresent()) {
                String level = String.valueOf(prob.get().getLevel());
                if ("EASY".equals(level))   easy++;
                else if ("MEDIUM".equals(level)) medium++;
                else if ("HARD".equals(level))   hard++;
            }
        }
        stats.setEasySolved(easy);
        stats.setMediumSolved(medium);
        stats.setHardSolved(hard);
        stats.setEasyTotal((int) problemRepository
                .countByLevel("EASY"));
        stats.setMediumTotal((int) problemRepository
                .countByLevel("MEDIUM"));
        stats.setHardTotal((int) problemRepository
                .countByLevel("HARD"));
    }
    private List<MonthlyTrend> buildTrend(List<Submission> submissions) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM");
        LocalDateTime sixMonthsAgo = LocalDateTime.now().minusMonths(6);
        Map<String, List<Submission>> byMonth = submissions.stream()
                .filter(s -> s.getSubmittedAt() != null
                        && s.getSubmittedAt().isAfter(sixMonthsAgo))
                .collect(Collectors.groupingBy(
                        s -> s.getSubmittedAt().format(fmt)
                ));
        return byMonth.entrySet().stream()
                .map(e -> {
                    double avg = e.getValue().stream()
                            .filter(s -> s.getScore() != null)
                            .mapToDouble(Submission::getScore)
                            .average().orElse(0);
                    return new MonthlyTrend(e.getKey(), e.getValue().size(), avg);
                })
                .collect(Collectors.toList());
    }
    private List<DayActivity> buildActivity(List<Submission> submissions) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime sixMonthsAgo = LocalDateTime.now().minusMonths(6);
        Map<String, Long> byDay = submissions.stream()
                .filter(s -> s.getSubmittedAt() != null
                        && s.getSubmittedAt().isAfter(sixMonthsAgo))
                .collect(Collectors.groupingBy(
                        s -> s.getSubmittedAt().format(fmt),
                        Collectors.counting()
                ));

        return byDay.entrySet().stream()
                .map(e -> new DayActivity(e.getKey(), e.getValue().intValue()))
                .collect(Collectors.toList());
    }
    private List<RecentSubmission> buildRecentSubmissions(
            List<Submission> submissions
    ) {
        return submissions.stream()
                .filter(s -> s.getSubmittedAt() != null)
                .sorted(Comparator.comparing(
                        Submission::getSubmittedAt, Comparator.reverseOrder()
                ))
                .limit(5)
                .map(s -> {
                    String title = problemRepository.findById(s.getProblemId())
                            .map(Problem::getTitle)
                            .orElse("Unknown Problem");
                    String level = problemRepository.findById(s.getProblemId())
                            .map(Problem::getLevel)
                            .orElse("EASY");
                    return new RecentSubmission(
                            s.getSubmissionId(),
                            s.getProblemId(),
                            title,
                            level,
                            s.getScore(),
                            "EVALUATED",
                            s.getSubmittedAt() != null
                                    ? s.getSubmittedAt().toString() : null
                    );
                })
                .collect(Collectors.toList());
    }
    private List<SkillScore> buildSkillBreakdown(
            List<Submission> submissions
    ) {
        double avg = submissions.stream()
                .filter(s -> s.getScore() != null)
                .mapToDouble(Submission::getScore)
                .average().orElse(0);
        return List.of(
                new SkillScore("Scalability",   avg * 0.9, 100),
                new SkillScore("API Design",    avg * 0.85, 100),
                new SkillScore("Data Modeling", avg * 0.8, 100),
                new SkillScore("Caching",       avg * 0.95, 100),
                new SkillScore("Reliability",   avg * 0.75, 100),
                new SkillScore("Security",      avg * 0.7, 100)
        );
    }
}
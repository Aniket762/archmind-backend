package archmind.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsResponse {
    private int totalSolved;
    private int totalProblems;
    private int currentStreak;
    private int globalRank;
    private double totalScore;
    private int easySolved;
    private int mediumSolved;
    private int hardSolved;
    private int easyTotal;
    private int mediumTotal;
    private int hardTotal;
    private List<MonthlyTrend> submissionTrend;
    private List<SkillScore> skillBreakdown;
    private List<DayActivity> activityData;
    private List<RecentSubmission> recentSubmissions;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class MonthlyTrend {
        private String month;
        private int submissions;
        private double avgScore;
    }
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class SkillScore {
        private String skill;
        private double score;
        private int fullMark = 100;
    }
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class DayActivity {
        private String date;
        private int count;
    }
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class RecentSubmission {
        private String submissionId;
        private String problemId;
        private String problemTitle;
        private String level;
        private Double score;
        private String status;
        private String submittedAt;
    }
}
package archmind.controller;

import archmind.dto.DashboardStatsResponse;
import archmind.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Dashboard API", description = "Dashboard statistics")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }
    @Operation(summary = "Get dashboard stats for a user")
    @GetMapping("getDashboardStats/{userId}")
    public DashboardStatsResponse getDashboardStats(
            @PathVariable String userId
    ) {
        return dashboardService.getDashboardStats(userId);
    }
}
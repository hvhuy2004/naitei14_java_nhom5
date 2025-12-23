package vn.sun.public_service_manager.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.sun.public_service_manager.entity.ActivityLog;
import vn.sun.public_service_manager.repository.ActivityLogRepository;

@Controller
@RequestMapping("/admin/activity-logs")
@PreAuthorize("hasRole('ADMIN')")
public class ActivityLogController {

    private final ActivityLogRepository activityLogRepository;

    public ActivityLogController(ActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    @GetMapping
    public String listActivityLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String targetType,
            Model model) {
        
        // Sort by createdAt descending (newest first)
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<ActivityLog> activityLogsPage;
        
        if (targetType != null && !targetType.trim().isEmpty()) {
            activityLogsPage = activityLogRepository.findByTargetType(targetType, pageable);
        } else {
            activityLogsPage = activityLogRepository.findAll(pageable);
        }

        // Get distinct target types for filter
        List<String> targetTypes = activityLogRepository.findDistinctTargetTypes();

        model.addAttribute("activityLogs", activityLogsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", activityLogsPage.getTotalPages());
        model.addAttribute("totalItems", activityLogsPage.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("targetTypes", targetTypes);
        model.addAttribute("selectedTargetType", targetType);

        return "admin/activity_logs";
    }

    @PostMapping("/{id}/delete")
    public String deleteActivityLog(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        try {
            activityLogRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đã xóa log thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa log: " + e.getMessage());
        }
        return "redirect:/admin/activity-logs";
    }
}

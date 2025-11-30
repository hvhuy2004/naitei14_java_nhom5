package vn.sun.public_service_manager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/citizen/protected")
public class CitizenProtectedController {

    // Demo API được bảo vệ (thay đổi route để tránh trùng với production endpoint)
    @PreAuthorize("hasRole('CITIZEN')")
    @GetMapping("/hello")
    public ResponseEntity<String> getCitizenProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // auth.getName() là nationalId của Citizen đã đăng nhập
        return ResponseEntity.ok("(demo) Welcome, Citizen! Your National ID (from token) is: " + auth.getName());
    }
}
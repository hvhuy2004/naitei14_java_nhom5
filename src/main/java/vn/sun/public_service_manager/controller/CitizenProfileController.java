package vn.sun.public_service_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.sun.public_service_manager.dto.CitizenProfileDTO;
import vn.sun.public_service_manager.dto.CitizenUpdateDTO;
import vn.sun.public_service_manager.service.CitizenService;
import vn.sun.public_service_manager.utils.annotation.ApiMessage;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/citizen/profile")
public class CitizenProfileController {

    @Autowired
    private CitizenService citizenService;

    @GetMapping
    @ApiMessage("Lấy thông tin cá nhân thành công")
    public ResponseEntity<CitizenProfileDTO> getProfile(Principal principal) {
        String nationalId = principal.getName();
        CitizenProfileDTO dto = citizenService.getProfileByNationalId(nationalId);
        return ResponseEntity.ok(dto);
    }

    @PutMapping
    @ApiMessage("Cập nhật thông tin cá nhân thành công")
    public ResponseEntity<?> updateProfile(Principal principal, @RequestBody CitizenUpdateDTO updateDTO) {
        try {
            String nationalId = principal.getName();
            CitizenProfileDTO updated = citizenService.updateProfile(nationalId, updateDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

package vn.sun.public_service_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.sun.public_service_manager.entity.Gender;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitizenProfileDTO {
    private Long id;
    private String fullName;
    private Date dob;
    private Gender gender;
    private String nationalId;
    private String address;
    private String phone;
    private String email;
    private Date createdAt;
    private Date updatedAt;

    public static CitizenProfileDTO fromEntity(vn.sun.public_service_manager.entity.Citizen c) {
        CitizenProfileDTO dto = new CitizenProfileDTO();
        dto.setId(c.getId());
        dto.setFullName(c.getFullName());
        dto.setDob(c.getDob());
        dto.setGender(c.getGender());
        dto.setNationalId(c.getNationalId());
        dto.setAddress(c.getAddress());
        dto.setPhone(c.getPhone());
        dto.setEmail(c.getEmail());
        dto.setCreatedAt(c.getCreatedAt());
        dto.setUpdatedAt(c.getUpdatedAt());
        return dto;
    }
}

package vn.sun.public_service_manager.dto;

import lombok.Data;
import vn.sun.public_service_manager.entity.Gender;

import java.util.Date;

@Data
public class CitizenUpdateDTO {
    private String fullName;
    private Date dob;
    private Gender gender;
    private String address;
    private String phone;
    private String email;
    private String password; // raw password; will be encoded if provided
}

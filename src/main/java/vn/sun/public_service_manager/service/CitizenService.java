package vn.sun.public_service_manager.service;

import vn.sun.public_service_manager.dto.CitizenProfileDTO;
import vn.sun.public_service_manager.dto.CitizenUpdateDTO;

public interface CitizenService {

    CitizenProfileDTO getProfileByNationalId(String nationalId);

    CitizenProfileDTO updateProfile(String nationalId, CitizenUpdateDTO updateDTO);
}

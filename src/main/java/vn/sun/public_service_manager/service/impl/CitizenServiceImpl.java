package vn.sun.public_service_manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.sun.public_service_manager.dto.CitizenProfileDTO;
import vn.sun.public_service_manager.dto.CitizenUpdateDTO;
import vn.sun.public_service_manager.entity.Citizen;
import vn.sun.public_service_manager.repository.CitizenRepository;
import vn.sun.public_service_manager.service.CitizenService;

@Service
public class CitizenServiceImpl implements CitizenService {

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public CitizenProfileDTO getProfileByNationalId(String nationalId) {
        Citizen citizen = citizenRepository.findByNationalId(nationalId)
                .orElseThrow(() -> new RuntimeException("Citizen not found: " + nationalId));
        return CitizenProfileDTO.fromEntity(citizen);
    }

    @Override
    @Transactional
    public CitizenProfileDTO updateProfile(String nationalId, CitizenUpdateDTO updateDTO) {
        Citizen citizen = citizenRepository.findByNationalId(nationalId)
                .orElseThrow(() -> new RuntimeException("Citizen not found: " + nationalId));

        if (updateDTO.getFullName() != null) citizen.setFullName(updateDTO.getFullName());
        if (updateDTO.getDob() != null) citizen.setDob(updateDTO.getDob());
        if (updateDTO.getGender() != null) citizen.setGender(updateDTO.getGender());
        if (updateDTO.getAddress() != null) citizen.setAddress(updateDTO.getAddress());
        if (updateDTO.getPhone() != null) citizen.setPhone(updateDTO.getPhone());
        if (updateDTO.getEmail() != null) citizen.setEmail(updateDTO.getEmail());
        if (updateDTO.getPassword() != null && !updateDTO.getPassword().isEmpty()) {
            citizen.setPassword(passwordEncoder.encode(updateDTO.getPassword()));
        }

        citizen = citizenRepository.save(citizen);
        return CitizenProfileDTO.fromEntity(citizen);
    }
}

package vn.sun.public_service_manager.service;

import vn.sun.public_service_manager.entity.Service;
import vn.sun.public_service_manager.entity.ServiceType;
import vn.sun.public_service_manager.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceManagementService {

    private final ServiceRepository serviceRepository;
    private final ServiceTypeService serviceTypeService;

    @Transactional
    public Service createOrUpdateService(Service service) {
        return serviceRepository.save(service);
    }

    public Optional<Service> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
    public Page<Service> getServices(
            int page,
            int size,
            String sortBy,
            String sortDir,
            String keyword,
            Long serviceTypeId)
    {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        boolean hasKeyword = StringUtils.hasText(keyword);
        boolean hasTypeFilter = serviceTypeId != null && serviceTypeId > 0;

        if (hasKeyword && hasTypeFilter) {
            ServiceType type = serviceTypeService.getServiceTypeById(serviceTypeId)
                    .orElseThrow(() -> new RuntimeException("ServiceType not found"));

            return serviceRepository.findByNameContainingIgnoreCaseOrCodeContainingIgnoreCaseAndServiceType(
                    keyword, keyword, type, pageable);

        } else if (hasKeyword) {
            return serviceRepository.findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(
                    keyword, keyword, pageable);

        } else if (hasTypeFilter) {
            ServiceType type = serviceTypeService.getServiceTypeById(serviceTypeId)
                    .orElseThrow(() -> new RuntimeException("ServiceType not found"));
            return serviceRepository.findByServiceType(type, pageable);

        } else {
            return serviceRepository.findAll(pageable);
        }
    }
}
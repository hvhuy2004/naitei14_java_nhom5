package vn.sun.public_service_manager.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.sun.public_service_manager.entity.ActivityLog;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

    Page<ActivityLog> findByTargetType(String targetType, Pageable pageable);

    @Query("SELECT DISTINCT al.targetType FROM ActivityLog al WHERE al.targetType IS NOT NULL ORDER BY al.targetType")
    List<String> findDistinctTargetTypes();

}

package com.musalasoft.repository;

import com.musalasoft.entity.DroneBatteryAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneBatteryAuditRepository extends JpaRepository<DroneBatteryAudit, String> {
}

package com.agms.automation_and_controller_service.repository;

import com.agms.automation_and_controller_service.entity.AutomationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomationLogRepository extends JpaRepository<AutomationLog, Long> {}

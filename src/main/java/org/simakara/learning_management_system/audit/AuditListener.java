package org.simakara.learning_management_system.audit;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class AuditListener {

    @PrePersist
    public void setCreatedOn(CreatedAndUpdatedSetter entity) {
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
    }

    @PreUpdate
    public void setUpdatedOn(CreatedAndUpdatedSetter entity) {
        entity.setUpdatedAt(LocalDateTime.now());
    }
}

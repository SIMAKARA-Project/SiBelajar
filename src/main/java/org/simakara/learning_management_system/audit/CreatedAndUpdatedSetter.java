package org.simakara.learning_management_system.audit;

import java.time.LocalDateTime;

public interface CreatedAndUpdatedSetter {

    void setCreatedAt(LocalDateTime createdAt);

    void setUpdatedAt(LocalDateTime updatedAt);
}

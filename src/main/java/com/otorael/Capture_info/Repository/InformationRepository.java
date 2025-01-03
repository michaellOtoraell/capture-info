package com.otorael.Capture_info.Repository;

import com.otorael.Capture_info.Model.InformationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationRepository extends JpaRepository<InformationModel, Long> {
}

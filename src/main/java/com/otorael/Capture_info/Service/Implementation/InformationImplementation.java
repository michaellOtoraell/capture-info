package com.otorael.Capture_info.Service.Implementation;

import com.otorael.Capture_info.Model.InformationModel;
import com.otorael.Capture_info.Repository.InformationRepository;
import com.otorael.Capture_info.Service.Information;
import org.springframework.stereotype.Service;

@Service
public class InformationImplementation implements Information {

    private final InformationRepository informationRepository;

    public InformationImplementation(InformationRepository informationRepository) {
        this.informationRepository = informationRepository;
    }

    @Override
    public InformationModel getIpAddress() {
        informationRepository.findAll();
        return null;
    }

    @Override
    public InformationModel getProtocol() {
        informationRepository.findAll();
        return null;
    }
}

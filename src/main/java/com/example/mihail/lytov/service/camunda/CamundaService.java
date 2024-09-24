package com.example.mihail.lytov.service.camunda;

import com.example.mihail.lytov.controller.dto.CustomerEvaluationDTO;

public interface CamundaService {

    void verificationUser(CustomerEvaluationDTO dto);
}

package com.example.mihail.lytov.service.camunda;

import com.example.mihail.lytov.controller.dto.CustomerEvaluationDTO;

import java.util.Map;

public interface CamundaService {

    Map<String, Object> verificationUser(CustomerEvaluationDTO dto);
}

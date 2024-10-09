package com.example.mihail.lytov.service.camunda;

import com.example.mihail.lytov.controller.dto.CustomerEvaluationDTO;
import com.example.mihail.lytov.entity.ResultEntity;

public interface CamundaService {

    ResultEntity verificationUser(CustomerEvaluationDTO dto);
}

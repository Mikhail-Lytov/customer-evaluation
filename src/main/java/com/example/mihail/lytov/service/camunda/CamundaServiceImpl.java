package com.example.mihail.lytov.service.camunda;

import com.example.mihail.lytov.controller.dto.CustomerEvaluationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.dmn.engine.*;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.ProcessEngine;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CamundaServiceImpl implements CamundaService {

    private final DmnEngine dmnEngine;


    private final ProcessEngine processEngine;

    public Map<String, Object> verificationUser(CustomerEvaluationDTO dto) {
        try {
            Map<String, Object> inputData = new HashMap<>();
            inputData.put("region_input", dto.getRegion());
            inputData.put("capital_input", dto.getCapital());
            inputData.put("inn_input", !(dto.getInn().length() == 12));
            inputData.put("resident_input", !dto.getResident().startsWith("9909"));

            DecisionService decisionService = processEngine.getDecisionService();

            DmnDecisionTableResult decisionResult = decisionService.evaluateDecisionTableByKey("result_map", inputData);
            return decisionResult.getFirstResult().getEntryMap();

        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("DMN process error. Message: " + e.getMessage() );
        }
    }
}

package com.example.mihail.lytov.service.camunda;

import com.example.mihail.lytov.controller.dto.CustomerEvaluationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionRequirementsGraph;
import org.camunda.bpm.dmn.engine.DmnDecisionResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CamundaServiceImpl implements CamundaService {

    private final DmnEngine dmnEngine;


    public Map<String, Object> verificationUser(CustomerEvaluationDTO dto) {
        try {
            ClassPathResource resource = new ClassPathResource("/processes/customer_evaluation.dmn");
            File file = resource.getFile();
            DmnModelInstance modelInstance = Dmn.readModelFromFile(file);

            Map<String, Object> inputData = new HashMap<>();
            inputData.put("region_input", dto.getRegion());
            inputData.put("capital_input", dto.getCapital());
            inputData.put("inn_input", !(dto.getInn().length() == 12));
            inputData.put("resident_input", !dto.getResident().startsWith("9909"));

            DmnDecisionRequirementsGraph dmnDecisionGraph = dmnEngine.parseDecisionRequirementsGraph(modelInstance);
            DmnDecision dmnDecision = dmnDecisionGraph.getDecision("result_map");
            DmnDecisionResult decisionResult = dmnEngine.evaluateDecision(dmnDecision, inputData);
            return decisionResult.getFirstResult().getEntryMap();

        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("DMN process error. Message: " + e.getMessage() );
        }
    }
}

package com.example.mihail.lytov.service.camunda;

import camundajar.impl.com.google.gson.Gson;
import camundajar.impl.com.google.gson.GsonBuilder;
import com.example.mihail.lytov.controller.dto.CustomerEvaluationDTO;
import com.example.mihail.lytov.entity.ResultEntity;
import com.example.mihail.lytov.service.ResultEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.dmn.engine.*;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.ProcessEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CamundaServiceImpl implements CamundaService {

    private final ResultEntityService resultEntityService;

    private final ProcessEngine processEngine;

    @Transactional
    public ResultEntity verificationUser(CustomerEvaluationDTO dto) {
        try {
            Map<String, Object> inputData = new HashMap<>();
            inputData.put("region_input", dto.getRegion());
            inputData.put("capital_input", dto.getCapital());
            inputData.put("inn_input", dto.getInn());
            inputData.put("resident_input", dto.getResident());

            DecisionService decisionService = processEngine.getDecisionService();

            DmnDecisionTableResult decisionResult = decisionService.evaluateDecisionTableByKey("result", inputData);
            Map<String, Object> resultMap = decisionResult.getFirstResult().getEntryMap();

            Gson gson = new GsonBuilder().create();
            ResultEntity result = gson.fromJson(resultMap.get("result_finish").toString(), ResultEntity.class);
            return resultEntityService.save(result);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("DMN process error. Message: " + e.getMessage() );
        }
    }
}

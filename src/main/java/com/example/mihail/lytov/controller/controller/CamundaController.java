package com.example.mihail.lytov.controller.controller;

import com.example.mihail.lytov.controller.dto.CustomerEvaluationDTO;
import com.example.mihail.lytov.service.camunda.CamundaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/controller")
@RequiredArgsConstructor
@Tag(name = "CAMUNDA")
public class CamundaController {

    private final CamundaService camundaService;

    @PostMapping
    @Operation(summary = "CAMUNDA", security = @SecurityRequirement(name = "JWT"))
    public ResponseEntity<Map<String, Object>> verificationUser(@RequestBody CustomerEvaluationDTO dto){
        return ResponseEntity.ok(camundaService.verificationUser(dto));
    }
}

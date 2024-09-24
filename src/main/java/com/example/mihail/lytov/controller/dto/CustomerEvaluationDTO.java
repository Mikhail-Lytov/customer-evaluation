package com.example.mihail.lytov.controller.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerEvaluationDTO implements Serializable {

    private String inn;
    private Integer region;
    private Integer capital;
    private String resident;
}

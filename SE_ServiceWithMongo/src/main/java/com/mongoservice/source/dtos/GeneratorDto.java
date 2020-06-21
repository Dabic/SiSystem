package com.mongoservice.source.dtos;

import lombok.Data;

import java.util.List;

@Data
public class GeneratorDto {

    private String patternName;
    private String equality;
    private String range;
    private List<String> includedFields;
}

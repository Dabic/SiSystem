package com.transformator.source.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EntityModel{
    public String name;
    public List<AttributeModel> attributes;
}

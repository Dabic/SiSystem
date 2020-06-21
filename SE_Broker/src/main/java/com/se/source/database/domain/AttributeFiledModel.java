package com.se.source.database.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public class AttributeFiledModel extends AbstractModel {
    public String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void addChild(AbstractModel abstractModel) {

    }

    @JsonIgnore
    @Override
    public List<AbstractModel> getChildren() {
        return null;
    }
}

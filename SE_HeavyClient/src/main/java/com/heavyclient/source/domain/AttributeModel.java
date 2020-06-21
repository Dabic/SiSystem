package com.heavyclient.source.domain;

import java.util.ArrayList;
import java.util.List;

public class AttributeModel extends AbstractModel {
    public List<AbstractModel> attributeFields;

    public AttributeModel() {
        attributeFields = new ArrayList<>();
    }

    @Override
    public void addChild(AbstractModel abstractModel) {
        attributeFields.add(abstractModel);
    }

    @Override
    public List<AbstractModel> getChildren() {
        return attributeFields;
    }

    public void setPrimaryKey(String value) {
        for (AbstractModel model : attributeFields) {
            if (model.getName().equals("primaryKey")) {
                ((AttributeFiledModel) model).setValue(value);
            }
        }
    }


}

package com.se.source.database.domain;

import java.util.ArrayList;
import java.util.List;

public class EntityModel extends AbstractModel {
    public List<AbstractModel> attributes;
    public List<AbstractModel> relations;

    public EntityModel() {
        attributes = new ArrayList<>();
        relations = new ArrayList<>();
    }

    @Override
    public void addChild(AbstractModel abstractModel) {
        attributes.add(abstractModel);
    }

    @Override
    public List<AbstractModel> getChildren() {
        return attributes;
    }

    public void addRelation(AbstractModel relation) {
        relations.add(relation);
    }

    public List<AbstractModel> getRelations() {
        return relations;
    }

    public void setChildren(List<AbstractModel> children) {
        attributes = children;
    }
}

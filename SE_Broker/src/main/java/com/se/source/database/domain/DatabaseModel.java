package com.se.source.database.domain;

import java.util.ArrayList;
import java.util.List;

public class DatabaseModel extends AbstractModel {
    public List<AbstractModel> entities;

    public DatabaseModel() {
        entities = new ArrayList<>();
    }

    @Override
    public void addChild(AbstractModel abstractModel) {
        entities.add(abstractModel);
    }

    @Override
    public List<AbstractModel> getChildren() {
        return entities;
    }
}

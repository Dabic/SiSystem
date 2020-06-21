package com.se.source.database.domain;

import java.util.List;

public abstract class AbstractModel {
    public String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void addChild(AbstractModel abstractModel);

    public abstract List<AbstractModel> getChildren();
}

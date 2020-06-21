package com.se.source.database.domain;

public class AbstractModelFactory {
    public AbstractModel getModel(String... args) {
        if (args[0].equals("database")) {
            DatabaseModel databaseModel = new DatabaseModel();
            databaseModel.setName(args[1]);
            return databaseModel;
        }
        if (args[0].equals("entity")) {
            EntityModel entityModel = new EntityModel();
            entityModel.setName(args[1]);
            return entityModel;
        }
        if (args[0].equals("attribute")) {
            AttributeModel attributeModel = new AttributeModel();
            attributeModel.setName(args[1]);
            AttributeFiledModel filedModel1 = new AttributeFiledModel();
            filedModel1.setName("length");
            filedModel1.setValue(args[2]);

            AttributeFiledModel filedModel2 = new AttributeFiledModel();
            filedModel2.setName("type");
            filedModel2.setValue(args[3]);

            AttributeFiledModel filedModel3 = new AttributeFiledModel();
            filedModel3.setName("primaryKey");
            filedModel3.setValue(args[4]);

            attributeModel.addChild(filedModel1);
            attributeModel.addChild(filedModel2);
            attributeModel.addChild(filedModel3);
            return attributeModel;
        }
        return null;
    }
}

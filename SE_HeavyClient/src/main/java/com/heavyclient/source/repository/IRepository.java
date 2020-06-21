package com.heavyclient.source.repository;

import java.sql.ResultSet;
import java.util.List;

public interface IRepository {

    boolean create(String object);

    String read(String object);

    boolean update(String object);

    boolean delete(String object);

    List<String> queryData(String query);
}

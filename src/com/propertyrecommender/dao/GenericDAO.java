package com.propertyrecommender.dao;

import java.util.List;

public interface GenericDAO<T, ID> {
    T save(T entity);
    T findById(ID id);
    List<T> findAll();
    boolean delete(ID id);
}

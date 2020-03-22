package com.thoughtworks.repository;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class DataUtil<E> {
    private final Class<E> entityClass;

    public DataUtil(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @SneakyThrows
    public void setValues(PreparedStatement statement, E entity) {
        Method[] getters = getGetters();
        for (int i = 0, len = getters.length; i < len; i++) {
            Object data = getters[i].invoke(entity);
            Class<?> dataClass = data.getClass();
            int paramIndex = i + 1;
            if (dataClass == Integer.class) {
                statement.setInt(paramIndex, (Integer) data);
            } else if (dataClass == String.class) {
                statement.setString(paramIndex, (String) data);
            } else if (dataClass == Date.class) {
                statement.setDate(paramIndex, new java.sql.Date(((Date) data).getTime()));
            } else {
                statement.setString(paramIndex, data.toString());
            }
        }
    }

    @SneakyThrows
    public List<E> makeEntities(ResultSet resultSet) {
        List<E> resultList = new ArrayList<>();
        while (resultSet.next()) {
            resultList.add(makeEntity(resultSet));
        }
        return resultList;
    }

    @SneakyThrows
    private E makeEntity(ResultSet resultSet) {
        E entity = getEmptyEntity();
        Method[] setters = getSetters();
        for (int i = 0, len = setters.length; i < len; i++) {
            Class<?> filedType = setters[i].getParameterTypes()[0];
            int columnIndex = i + 1;
            if (filedType == Integer.class) {
                setters[i].invoke(entity, resultSet.getInt(columnIndex));
            } else if (filedType == String.class) {
                setters[i].invoke(entity, resultSet.getString(columnIndex));
            } else if (filedType == Date.class) {
                setters[i].invoke(entity, resultSet.getDate(columnIndex));
            }
        }
        return entity;
    }

    @SneakyThrows
    private E getEmptyEntity() {
        return entityClass.getConstructor().newInstance();
    }

    @SneakyThrows
    private Method[] getGetters() {
        Field[] fields = entityClass.getDeclaredFields();
        Method[] getters = new Method[fields.length];
        for (int i = 0; i < fields.length; i++) {
            String getterName = getAccessorName(fields[i].getName(), "get");
            getters[i] = entityClass.getMethod(getterName);
        }
        return getters;
    }

    @SneakyThrows
    private Method[] getSetters() {
        Field[] fields = entityClass.getDeclaredFields();
        Method[] setters = new Method[fields.length];
        for (int i = 0; i < fields.length; i++) {
            String setterName = getAccessorName(fields[i].getName(), "set");
            Class<?> setterParamType = fields[i].getType();
            setters[i] = entityClass.getMethod(setterName, setterParamType);
        }
        return setters;
    }

    private static String getAccessorName(String fieldName, String getOrSet) {
        return getOrSet + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}

package com.example.demo.Employee.Repository;


import java.sql.SQLException;
import java.util.List;

public interface CRUDRepository<T>{
    public void add(T t) throws SQLException;
    public void update(T t) throws SQLException;
    public void delete(int id) throws SQLException;
    public T getById(int t) throws SQLException;
    public List<T> getAll() throws SQLException;
}

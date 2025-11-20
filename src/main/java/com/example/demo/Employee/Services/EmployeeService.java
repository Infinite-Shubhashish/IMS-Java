package com.example.demo.Employee.Services;

import com.example.demo.Employee.Repository.EmployeeRepository;
import com.example.demo.Employee.exception.EmployeeExceptionManagement;
import com.example.demo.Employee.model.Employee;
import com.example.demo.Employee.validation.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    @Autowired
    private EmployeeValidator employeeValidator;

//    public List<Employee> getAll(){
//        return repo.GetAll();
//    }s

    public List<Employee> getAll() throws SQLException{
        return repo.getAll();
    }

    public Employee getEmployeeInfo(int id) throws EmployeeExceptionManagement, SQLException {
        employeeValidator.validateExist(id);
        return repo.getById(id);
    }

    public void addEmployee(Employee employee) throws EmployeeExceptionManagement, SQLException {
        employeeValidator.validateAdd(employee);
        repo.add(employee);
    }

    public void deleteEmployee(int id) throws EmployeeExceptionManagement, SQLException{
        employeeValidator.validateExist(id);
        repo.delete(id);
    }


}

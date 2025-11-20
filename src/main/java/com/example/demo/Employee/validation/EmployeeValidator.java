package com.example.demo.Employee.validation;

import com.example.demo.Employee.Repository.EmployeeDAO;
import com.example.demo.Employee.exception.EmployeeExceptionManagement;
import com.example.demo.Employee.model.Department;
import com.example.demo.Employee.model.Employee;
import com.example.demo.Employee.model.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class EmployeeValidator {

    @Autowired
    private EmployeeDAO employeeDAO;

    public void validateAdd(Employee employee) throws EmployeeExceptionManagement, SQLException {
        validateDuplicate(employee);
        validateSalary(employee);
    }

    public void validateUpdate(Employee employee) throws EmployeeExceptionManagement, SQLException {
        validateExist(employee.getId());
        validateSalary(employee);
    }

    //validate duplicate
    public void validateDuplicate(Employee employee) throws EmployeeExceptionManagement, SQLException {

        if (employeeDAO.getById(employee.getId()) != null) {
            throw new EmployeeExceptionManagement(
                    "Employee with id: " + employee.getId() + " already exists."
            );
        }
    }

    //salary is non negative
    public void validateSalary(Employee employee) throws EmployeeExceptionManagement{
        if(employee.getSalary() < 0){
            throw new EmployeeExceptionManagement(
                    "Salary cannot be negative! "
            );
        }

    }

    //validate if exist or not
    public void validateExist(int id) throws EmployeeExceptionManagement, SQLException {
        if(employeeDAO.getById(id) == null){
            throw new EmployeeExceptionManagement(
                    "Employee with Id  " + id +  " does not exist."
            );
        }
    }

    public Department validateDepartment(int choice) throws EmployeeExceptionManagement{
        if(choice < 1 || choice > Department.values().length){
            throw new EmployeeExceptionManagement(
                    "Invalid choice! "
            );
        }
        return Department.values()[choice-1];

    }
    public Gender validateGender(int choice) throws EmployeeExceptionManagement{
        if(choice < 1 || choice > Gender.values().length){
            throw new EmployeeExceptionManagement(
                    "Invalid choice! "
            );
        }
        return Gender.values()[choice-1];

    }


}

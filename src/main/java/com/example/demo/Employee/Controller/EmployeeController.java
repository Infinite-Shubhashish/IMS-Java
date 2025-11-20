package com.example.demo.Employee.Controller;

import com.example.demo.Employee.Services.EmployeeService;
import com.example.demo.Employee.exception.EmployeeExceptionManagement;
import com.example.demo.Employee.model.Employee;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;


@RestController
@RequestMapping(value ="/api/employee")
public class EmployeeController {


//    private Employee employee = new Employee(1,"Ram",2000, Department.valueOf("JAVA"),
//            Gender.valueOf("MALE"),true
//
//    );

    @Autowired
    private EmployeeService service;

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeController.class);

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> doGet() {
        try
        {
            logger.info("Retrieved info");
            return ResponseEntity.ok(service.getAll());
        }catch (SQLException e)
        {
            logger.error("Database error while fetching employees", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());

        }
    }


    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") int id) {
        try {
            Employee employee = service.getEmployeeInfo(id);
            if (employee != null) {
                return ResponseEntity.ok(employee);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (SQLException e) {
            logger.error("Error fetching employee with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }catch (EmployeeExceptionManagement e){
            logger.error("Error during validation: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        try {
            service.addEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (SQLException e) {
            logger.error("Error ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }catch (EmployeeExceptionManagement e){
            logger.error("Error during validation: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteEmployee(@PathVariable int id){
        try {
            service.deleteEmployee(id);
            return ResponseEntity.ok("Employee deleted");
        }catch (SQLException e) {
            logger.error("Error ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }catch (EmployeeExceptionManagement e){
            logger.error("Error during validation: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        }

}
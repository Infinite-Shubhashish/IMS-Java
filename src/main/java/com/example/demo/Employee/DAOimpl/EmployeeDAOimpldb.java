package com.example.demo.Employee.DAOimpl;

import com.example.demo.Employee.Repository.EmployeeDAO;
import com.example.demo.Employee.DataSource.DBConnection;
import com.example.demo.Employee.DataSource.JDBCtemplate;
import com.example.demo.Employee.DataSource.RowMapper;
import com.example.demo.Employee.model.Department;
import com.example.demo.Employee.model.Employee;
import com.example.demo.Employee.model.Gender;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeeDAOimpldb implements EmployeeDAO {
    private JDBCtemplate<Employee> jdbCtemplate = new JDBCtemplate<>();

    @Override
    public void add(Employee employee) throws SQLException {
        String sql = "INSERT INTO Employee (id, name, salary, department, gender, isPresent) VALUES (?, ?, ?, ?, ?, ?)";
        Object[] params = {
                employee.getId(),
                employee.getName(),
                employee.getSalary(),
                String.valueOf(employee.getDepartment()),
                String.valueOf(employee.getGender()),
                employee.isPresent()
        };

        jdbCtemplate.process(sql,params);


    }

    @Override
    public void update(Employee employee) throws SQLException {

        String sql = "UPDATE Employee SET name = ?, salary = ?, department = ?, gender = ?, isPresent = ? WHERE id = ?";

        Object [] params = {
                employee.getName(),
                employee.getSalary(),
                String.valueOf(employee.getDepartment()),
                String.valueOf(employee.getGender()),
                employee.isPresent(),
                employee.getId(),
        };
        jdbCtemplate.process(sql,params);

    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE from Employee where id = ?";
        jdbCtemplate.process(sql, new Object[]{id});

    }

    @Override
    public Employee getById(int t) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "Select * from Employee where id = ?";

        return jdbCtemplate.getOnebyQuery(sql,t,new RowMapper<Employee>(){
            @Override
            public Employee mapRow(ResultSet rs) throws SQLException {
                Employee employee =new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setDepartment(Department.valueOf(rs.getString("department")));
                employee.setGender(Gender.valueOf(rs.getString("gender")));
                employee.setPresent(rs.getBoolean("isPresent"));
                return employee;
            }
        });
    }

    @Override
    public List<Employee> getAll() throws SQLException {

        String sql = "Select * from Employee";
        return jdbCtemplate.getAllbyQuery(sql,new RowMapper<Employee>(){
            @Override
            public Employee mapRow(ResultSet rs) throws SQLException {
                Employee employee =new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setDepartment(Department.valueOf(rs.getString("department")));
                employee.setGender(Gender.valueOf(rs.getString("gender")));
                employee.setPresent(rs.getBoolean("isPresent"));

                return employee;
            }
        });



    }
}

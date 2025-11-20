package com.example.demo.Employee.DataSource;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JDBCtemplate<T> {
    public void process(String sql, Object[] params) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql);

        int i = 1;
        for (Object param : params) {
            pstmt.setObject(i, param);
            i++;
        }

        pstmt.executeUpdate();
        connection.close();
    }

    public List<T> getAllbyQuery(String sql, RowMapper<T> mapper) throws SQLException {
        List<T> rows = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        PreparedStatement ptst = connection.prepareStatement(sql);
        ResultSet rs = ptst.executeQuery();

        while(rs.next()){
            rows.add(mapper.mapRow(rs));
        }
        return rows;
    }

    public T getOnebyQuery(String sql, int param, RowMapper<T> mapper) throws SQLException{
        Connection connection = DBConnection.getConnection();
        PreparedStatement ptst = connection.prepareStatement(sql);
        ptst.setObject(1,param);

        ResultSet rs = ptst.executeQuery();
        if(rs.next()){
            return mapper.mapRow(rs);
        }
        return null;
    }
}

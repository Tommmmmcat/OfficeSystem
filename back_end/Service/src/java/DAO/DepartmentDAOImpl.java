/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Department;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author QAQ
 */
public class DepartmentDAOImpl implements DepartmentDAO {

    private Connection conn;
    DataBaseDAO dao = null;

    @Override
    public void addNewDepartment() throws Exception {

    }

    public DepartmentDAOImpl() {
    }

    public DepartmentDAOImpl(DataBaseDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Department> getAllDepartments() throws Exception {
        String query = "SELECT * FROM `department`";
        if (dao == null) {
            conn = DataBaseDAO.getInstance().connect();
        } else {
            conn = dao.connect();
        }

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        List<Department> deptList = new ArrayList();
        if (rs.next()) {
            Department dept = new Department();
            dept.setDepartmentId(rs.getInt("department_id"));
            dept.setDepartmentName(rs.getString("department_name"));
            deptList.add(dept);
        }
        return deptList;
    }

    @Override
    public Department getDepartmentById(int departmentId) throws Exception {
        String query = "SELECT * FROM `department` WHERE `department_id` = " + departmentId;
        conn = DataBaseDAO.getInstance().connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        Department dept = new Department();
        if (rs.next()) {
            dept.setDepartmentId(rs.getInt("department_id"));
            dept.setDepartmentName(rs.getString("department_name"));
        }
        return dept;
    }

}

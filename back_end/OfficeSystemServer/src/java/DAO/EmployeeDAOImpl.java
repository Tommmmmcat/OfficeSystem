/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Employee;
import java.sql.Statement;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author QAQ
 */
public class EmployeeDAOImpl implements EmployeeDAO {

    private Connection conn;

    @Override
    public int register(String employee_account, String employee_name, int department_id, int position_id, String password, int status, String date) throws Exception {
        employee_account = Util.Utils.replceSymbol(employee_account);
        password = BCrypt.hashpw(Util.Utils.replceSymbol(password), BCrypt.gensalt());
        int eid = 0;
        conn = DataBaseDAO.getInstance().connect();
        String query = "SELECT * FROM `employee` WHERE employee_account = \"" + employee_account + "\"";
        String sql = "INSERT INTO `employee`("
                + "`employee_account`,"
                + "`employee_name`, "
                + "`department_id`, "
                + "`position_id`, "
                + "`password`,"
                + "`status`,"
                + "`date`"
                + ") VALUES "
                + "(\"" + employee_account + "\","
                + "\"" + employee_name + "\","
                + "\"" + department_id + "\","
                + "\"" + position_id + "\","
                + "\"" + password + "\","
                + "\"" + status + "\","
                + "\"" + date + "\""
                + ");";
        String selectSql = "SELECT LAST_INSERT_ID();";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        if (!rs.next()) {
            if (stmt.executeUpdate(sql) == 1) {
                rs = stmt.executeQuery(selectSql);
                while (rs.next()) {
                    eid = rs.getInt("LAST_INSERT_ID()");
                }
            }
        } else {
            eid = -1;
            throw new Exception("User Already Exists");
        }
        DataBaseDAO.getInstance().close();

        return eid;
    }

    @Override
    public Employee login(String employee_account, String password) throws Exception {
        Employee emp = null;
        String psw = "";
        employee_account = Util.Utils.replceSymbol(employee_account);
        password = Util.Utils.replceSymbol(password);
        int eid = -1;

        conn = DataBaseDAO.getInstance().connect();
        String sql = "SELECT * FROM `employee` WHERE `employee_account` = \"" + employee_account + "\";";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            psw = rs.getString("password");
            eid = rs.getInt("employee_id");
        }
        if (BCrypt.checkpw(password, psw)) {
            emp = new Employee();
            emp.setEmployee_id(eid);
        } else {
            throw new Exception("Wrong Password!");
        }
        DataBaseDAO.getInstance().close();
        return emp;
    }

    @Override
    public List<Employee> getAllEMployees() throws Exception {
        List<Employee> empList = new ArrayList();
        conn = DataBaseDAO.getInstance().connect();
        String sql = "SELECT \n"
                + "`employee`.`employee_id`,\n"
                + "`employee`.`employee_account`,\n"
                + "`employee`.`employee_name`, \n"
                + "`employee`.`status`,\n"
                + "`employee`.`department_id`,\n"
                + "`employee`.`position_id`,\n"
                + "`department`.`department_name`,\n"
                + "`position`.`position_name`,\n"
                + "`employee_details`.`genre`,\n"
                + "`employee_details`.`birthday`,\n"
                + "`employee_details`.`email`,\n"
                + "`employee_details`.`address`\n"
                + "FROM `employee`\n"
                + "LEFT JOIN `department` ON `department`.`department_id` = `employee`.`department_id` \n"
                + "LEFT JOIN `position` ON `position`.`position_id` = `employee`.`position_id`\n"
                + "LEFT JOIN `employee_details` ON `employee_details`.`employee_id` = `employee`.`employee_id`";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Employee emp = new Employee();
            emp.setEmployee_id(rs.getInt("employee_id"));
            emp.setEmployee_account(rs.getString("employee_account"));
            emp.setEmployee_name(rs.getString("employee_name"));
            emp.setPosition_id(rs.getInt("position_id"));
            emp.setDepartment_id(rs.getInt("department_id"));
            emp.setDepartment_name(rs.getString("department_name"));
            emp.setPosition_name(rs.getString("position_name"));
            emp.setStatus(rs.getInt("status"));
            emp.setGenre(rs.getInt("genre"));
            emp.setBirthday(rs.getDate("birthday"));
            emp.setEmail(rs.getString("email"));
            emp.setAddress(rs.getString("address"));
            empList.add(emp);
        }
        return empList;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Position;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author QAQ
 */
public class PositionDAOImpl implements PositionDAO {

    private Connection conn;

    @Override
    public List<Position> getAllPositionByDeptId(int departmentId) throws Exception {
        String query = "SELECT * FROM `position` WHERE `department_id` = " + departmentId;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        List<Position> posList = new ArrayList();
        if (rs.next()) {
            Position pos = new Position();
            pos.setPositionId(rs.getInt("position_id"));
            pos.setPositionName(rs.getString("position_name"));
            posList.add(pos);
        }
        return posList;

    }

    @Override
    public Position getPositionById(int positionId) throws Exception {
        String query = "SELECT * FROM `position` WHERE `department_id` = " + positionId;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        Position pos = new Position();
        if (rs.next()) {
            pos.setPositionId(rs.getInt("position_id"));
            pos.setPositionName(rs.getString("position_name"));
        }
        return pos;
    }

}

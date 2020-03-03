/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import org.json.simple.JSONObject;

/**
 *
 * @author QAQ
 */
public class Department {

    private int department_id;
    private String department_name;

    public int getDepartmentId() {
        return department_id;
    }

    public void setDepartmentId(int department_id) {
        this.department_id = department_id;
    }

    public String getDepartmentName() {
        return department_name;
    }

    public void setDepartmentName(String department_name) {
        this.department_name = department_name;
    }

    public JSONObject getJSONObject() {
        JSONObject json = new JSONObject();
        json.put("department_id", department_id);
        json.put("department_name", department_name);
        return json;
    }

}

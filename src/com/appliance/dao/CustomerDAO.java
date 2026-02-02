package com.appliance.dao;

import com.appliance.bean.Customer;
import com.appliance.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public Customer findCustomer(String customerID) throws SQLException {
        String sql = "SELECT * FROM CUSTOMER_TBL WHERE CUSTOMER_ID=?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customerID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer c = new Customer();
                c.setCustomerID(rs.getString("CUSTOMER_ID"));
                c.setFullName(rs.getString("FULL_NAME"));
                c.setMobile(rs.getString("MOBILE"));
                c.setEmail(rs.getString("EMAIL"));
                c.setAddressLine1(rs.getString("ADDRESS_LINE1"));
                c.setAddressLine2(rs.getString("ADDRESS_LINE2"));
                c.setCity(rs.getString("CITY"));
                c.setStatus(rs.getString("STATUS"));
                return c;
            }
        }
        return null;
    }
    
    public List<Customer> viewAllCustomers() throws SQLException {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM CUSTOMER_TBL";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomerID(rs.getString("CUSTOMER_ID"));
                c.setFullName(rs.getString("FULL_NAME"));
                c.setMobile(rs.getString("MOBILE"));
                c.setEmail(rs.getString("EMAIL"));
                c.setAddressLine1(rs.getString("ADDRESS_LINE1"));
                c.setAddressLine2(rs.getString("ADDRESS_LINE2"));
                c.setCity(rs.getString("CITY"));
                c.setStatus(rs.getString("STATUS"));
                list.add(c);
            }
        }
        return list;
    }
    
    public boolean insertCustomer(Customer c) throws SQLException {
        String sql = "INSERT INTO CUSTOMER_TBL VALUES (?,?,?,?,?,?,?,?)";

        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getCustomerID());
            ps.setString(2, c.getFullName());
            ps.setString(3, c.getMobile());
            ps.setString(4, c.getEmail());
            ps.setString(5, c.getAddressLine1());
            ps.setString(6, c.getAddressLine2());
            ps.setString(7, c.getCity());
            ps.setString(8, c.getStatus());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteCustomer(String customerID) throws SQLException {
        String sql = "DELETE FROM CUSTOMER_TBL WHERE CUSTOMER_ID=?";

        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, customerID);
            return ps.executeUpdate() > 0;
        }
    }
}

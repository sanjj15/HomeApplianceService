package com.appliance.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.appliance.bean.ServiceRequest;
import com.appliance.util.DBUtil;

public class ServiceRequestDAO {
	public int generateRequestID(Connection con) throws SQLException {
        String sql = "SELECT REQ_SEQ.NEXTVAL FROM DUAL";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public boolean insertServiceRequest(Connection con, ServiceRequest r) throws SQLException {
        String sql = "INSERT INTO SERVICE_REQ_TBL " +
                "(REQUEST_ID,CUSTOMER_ID,APPLIANCE_TYPE,BRAND_MODEL,PROBLEM_DESC,REQUEST_DATE,PREFERRED_VISIT_DATE,STATUS) " +
                "VALUES (?,?,?,?,?,?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, r.getRequestID());
            ps.setString(2, r.getCustomerID());
            ps.setString(3, r.getApplianceType());
            ps.setString(4, r.getBrandModel());
            ps.setString(5, r.getProblemDescription());
            ps.setDate(6, r.getRequestDate());
            ps.setDate(7, r.getPreferredVisitDate());
            ps.setString(8, r.getStatus());
            return ps.executeUpdate() > 0;
        }
    }

    public ServiceRequest findServiceRequest(int requestID) throws SQLException {
        String sql = "SELECT * FROM SERVICE_REQ_TBL WHERE REQUEST_ID=?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, requestID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ServiceRequest r = new ServiceRequest();
                r.setRequestID(rs.getInt("REQUEST_ID"));
                r.setCustomerID(rs.getString("CUSTOMER_ID"));
                r.setApplianceType(rs.getString("APPLIANCE_TYPE"));
                r.setBrandModel(rs.getString("BRAND_MODEL"));
                r.setProblemDescription(rs.getString("PROBLEM_DESC"));
                r.setRequestDate(rs.getDate("REQUEST_DATE"));
                r.setPreferredVisitDate(rs.getDate("PREFERRED_VISIT_DATE"));
                r.setAssignedTechnician(rs.getString("ASSIGNED_TECHNICIAN"));
                r.setVisitDate(rs.getDate("VISIT_DATE"));
                r.setVisitSlot(rs.getString("VISIT_SLOT"));
                r.setServiceCharge(rs.getBigDecimal("SERVICE_CHARGE"));
                r.setResolutionRemarks(rs.getString("RESOLUTION_REMARKS"));
                r.setStatus(rs.getString("STATUS"));
                return r;
            }
        }
        return null;
    }

    public List<ServiceRequest> findRequestsByTechnicianAndSlot(String tech, Date date, String slot) throws SQLException {
        List<ServiceRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM SERVICE_REQ_TBL WHERE ASSIGNED_TECHNICIAN=? AND VISIT_DATE=? AND VISIT_SLOT=? AND STATUS='ASSIGNED'";

        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tech);
            ps.setDate(2, date);
            ps.setString(3, slot);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ServiceRequest r = new ServiceRequest();
                r.setRequestID(rs.getInt("REQUEST_ID"));
                r.setStatus(rs.getString("STATUS"));
                list.add(r);
            }
        }
        return list;
    }

    public boolean updateAssignment(Connection con, int id, String tech, Date date, String slot, String status) throws SQLException {
        String sql = "UPDATE SERVICE_REQ_TBL SET ASSIGNED_TECHNICIAN=?, VISIT_DATE=?, VISIT_SLOT=?, STATUS=? WHERE REQUEST_ID=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tech);
            ps.setDate(2, date);
            ps.setString(3, slot);
            ps.setString(4, status);
            ps.setInt(5, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean closeRequest(Connection con, int id, java.math.BigDecimal charge, String remarks, String status) throws SQLException {
        String sql = "UPDATE SERVICE_REQ_TBL SET SERVICE_CHARGE=?, RESOLUTION_REMARKS=?, STATUS=? WHERE REQUEST_ID=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBigDecimal(1, charge);
            ps.setString(2, remarks);
            ps.setString(3, status);
            ps.setInt(4, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<ServiceRequest> findActiveRequestsForCustomer(String custID) throws SQLException {
        List<ServiceRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM SERVICE_REQ_TBL WHERE CUSTOMER_ID=? AND STATUS IN ('LOGGED','ASSIGNED')";

        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, custID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ServiceRequest r = new ServiceRequest();
                r.setRequestID(rs.getInt("REQUEST_ID"));
                r.setStatus(rs.getString("STATUS"));
                list.add(r);
            }
        }
        return list;
    }
}

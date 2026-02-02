package com.appliance.service;

import com.appliance.bean.*;
import com.appliance.dao.*;
import com.appliance.util.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class ApplianceService {
    private CustomerDAO customerDAO = new CustomerDAO();
    private ServiceRequestDAO requestDAO = new ServiceRequestDAO();

    public boolean registerNewCustomer(Customer c) throws Exception {
        if (c.getCustomerID()==null || c.getCustomerID().isEmpty())
            throw new ValidationException("Customer ID required");
        if (c.getFullName()==null || c.getFullName().isEmpty())
            throw new ValidationException("Name required");
        if (c.getMobile()==null || !c.getMobile().matches("\\d+"))
            throw new ValidationException("Invalid mobile");

        if (customerDAO.findCustomer(c.getCustomerID()) != null)
            throw new ValidationException("Customer already exists");

        if (c.getStatus()==null) c.setStatus("ACTIVE");
        return customerDAO.insertCustomer(c);
    }

    public boolean logServiceRequest(String custID, String appliance, String brand,String problem, Date reqDate, Date prefDate) throws Exception {

        if (custID==null || custID.isEmpty())
            throw new ValidationException("Customer ID required");

        Customer c = customerDAO.findCustomer(custID);
        if (c == null || !"ACTIVE".equals(c.getStatus()))
            return false;

        try (Connection con = DBUtil.getDBConnection()) {
            con.setAutoCommit(false);

            int id = requestDAO.generateRequestID(con);
            ServiceRequest r = new ServiceRequest();
            r.setRequestID(id);
            r.setCustomerID(custID);
            r.setApplianceType(appliance);
            r.setBrandModel(brand);
            r.setProblemDescription(problem);
            r.setRequestDate(reqDate);
            r.setPreferredVisitDate(prefDate);
            r.setStatus("LOGGED");

            boolean ok = requestDAO.insertServiceRequest(con, r);
            con.commit();
            return ok;
        }
    }

    public boolean assignTechnician(int reqID, String tech, Date visitDate, String slot) throws Exception {
        if (!requestDAO.findRequestsByTechnicianAndSlot(tech, visitDate, slot).isEmpty())
            throw new TechnicianOverbookedException("Technician already booked!");

        try (Connection con = DBUtil.getDBConnection()) {
            con.setAutoCommit(false);
            boolean ok = requestDAO.updateAssignment(con, reqID, tech, visitDate, slot, "ASSIGNED");
            con.commit();
            return ok;
        }
    }

    public boolean closeServiceRequest(int reqID, BigDecimal charge, String remarks, boolean cancel) throws Exception {
        String status = cancel ? "CANCELLED" : "COMPLETED";
        try (Connection con = DBUtil.getDBConnection()) {
            con.setAutoCommit(false);
            boolean ok = requestDAO.closeRequest(con, reqID, charge, remarks, status);
            con.commit();
            return ok;
        }
    }

    public List<ServiceRequest> listCustomerRequests(String custID) throws Exception {
        return requestDAO.findActiveRequestsForCustomer(custID);
    }
    
    public boolean removeCustomer(String custID) throws Exception {
        if (!requestDAO.findActiveRequestsForCustomer(custID).isEmpty())
            throw new ActiveRequestsExistException("Customer has active requests");
        return customerDAO.deleteCustomer(custID);
    }
}

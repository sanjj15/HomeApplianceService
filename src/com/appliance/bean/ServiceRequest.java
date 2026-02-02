package com.appliance.bean;

import java.math.BigDecimal;
import java.sql.Date;

public class ServiceRequest {
	private int requestID;
    private String customerID;
    private String applianceType;
    private String brandModel;
    private String problemDescription;
    private Date requestDate;
    private Date preferredVisitDate;
    private String assignedTechnician;
    private Date visitDate;
    private String visitSlot;
    private BigDecimal serviceCharge;
    private String resolutionRemarks;
    private String status;

    public int getRequestID() { 
    	return requestID; 
    	}
    public void setRequestID(int requestID) { 
    	this.requestID = requestID; 
    	}
    public String getCustomerID() { 
    	return customerID; 
    	}
    public void setCustomerID(String customerID) {
    	this.customerID = customerID; 
    	}
    public String getApplianceType() { 
    	return applianceType; 
    	}
    public void setApplianceType(String applianceType) { 
    	this.applianceType = applianceType; 
    	}
    public String getBrandModel() { 
    	return brandModel; 
    	}
    public void setBrandModel(String brandModel) { 
    	this.brandModel = brandModel; 
    	}
    public String getProblemDescription() { 
    	return problemDescription; 
    	}
    public void setProblemDescription(String problemDescription) { 
    	this.problemDescription = problemDescription; 
    	}
    public Date getRequestDate() { 
    	return requestDate; 
    	}
    public void setRequestDate(Date requestDate) { 
    	this.requestDate = requestDate; 
    	}
    public Date getPreferredVisitDate() { 
    	return preferredVisitDate; 
    	}
    public void setPreferredVisitDate(Date preferredVisitDate) { 
    	this.preferredVisitDate = preferredVisitDate; 
    	}
    public String getAssignedTechnician() { 
    	return assignedTechnician; 
    	}
    public void setAssignedTechnician(String assignedTechnician) { 
    	this.assignedTechnician = assignedTechnician; 
    	}
    public Date getVisitDate() { 
    	return visitDate; 
    	}
    public void setVisitDate(Date visitDate) { 
    	this.visitDate = visitDate; 
    	}
    public String getVisitSlot() { 
    	return visitSlot; 
    	}
    public void setVisitSlot(String visitSlot) { 
    	this.visitSlot = visitSlot; 
    	}
    public BigDecimal getServiceCharge() { 
    	return serviceCharge; 
    	}
    public void setServiceCharge(BigDecimal serviceCharge) { 
    	this.serviceCharge = serviceCharge; 
    	}
    public String getResolutionRemarks() { 
    	return resolutionRemarks; 
    	}
    public void setResolutionRemarks(String resolutionRemarks) { 
    	this.resolutionRemarks = resolutionRemarks; 
    	}
    public String getStatus() { 
    	return status; 
    	}
    public void setStatus(String status) { 
    	this.status = status; 
    	}
}

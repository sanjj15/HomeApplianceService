package com.appliance.app;

import com.appliance.bean.Customer;
import com.appliance.service.ApplianceService;
import com.appliance.util.ValidationException;

public class ApplianceMain { 
	private static ApplianceService service = new ApplianceService(); 
	public static void main(String[] args) { 
		java.util.Scanner sc = new java.util.Scanner(System.in); 
		System.out.println("--- Home Appliance Service Center Console ---"); 
		try { 
			Customer c = new Customer(); 
			c.setCustomerID("001"); 
			c.setFullName("Meenakshi Rao"); 
			c.setMobile("9998887771"); 
			c.setEmail("meenakshi.rao@example.com"); 
			c.setAddressLine1("Flat 5C, Sunrise Apartments"); 
			c.setAddressLine2("Near Central Park"); 
			c.setCity("Chennai"); 
			c.setStatus("ACTIVE"); 
			boolean ok = service.registerNewCustomer(c); 
			System.out.println(ok ? "CUSTOMER REGISTERED" : "CUSTOMER REGISTRATION FAILED"); 
			} catch (ValidationException e) { 
				System.out.println("Validation Error: " + e.toString()); 
			} catch (Exception e) { 
				System.out.println("System Error: " + e.getMessage()); 
			}  
		try { 
			java.sql.Date today = new java.sql.Date(System.currentTimeMillis()); 
			java.sql.Date pref = new java.sql.Date(System.currentTimeMillis() + 24L*60*60*1000); 
			boolean ok = service.logServiceRequest( 
					"CU2001", 
					"AIR_CONDITIONER", 
					"LG 1.5T Dual Inverter", 
					"No cooling",today, pref 
					); 
			System.out.println(ok ? "REQUEST LOGGED" : "REQUEST LOGGING FAILED"); 
			} catch (ValidationException e) { 
				System.out.println("Validation Error: " + e.toString()); 
				} catch (Exception e) { 
					System.out.println("System Error: " + e.getMessage()); 
					} 
		sc.close(); 
	} 
}

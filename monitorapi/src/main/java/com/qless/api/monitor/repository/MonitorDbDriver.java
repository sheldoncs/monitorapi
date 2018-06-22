package com.qless.api.monitor.repository;


import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.qless.api.monitor.dto.HTTPResponse;
import com.qless.api.monitor.model.Monitor;


@Repository
public class MonitorDbDriver implements IDbDriver {
    
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Logger LOG = LoggerFactory.getLogger(MonitorDbDriver.class);
    private HTTPResponse response = new HTTPResponse();
    
    @Override
    public String createNewMonitor(Monitor mon) {
        
        try {
            if (!monitorExists(mon.getUserid())){
                 
            	 String sqlstmt = "INSERT INTO account (userid, password) values (?, ?)";
            	 jdbcTemplate.update(sqlstmt, mon.getUserid(),  mon.getPwd());
            	 
            	String sqlstmt1 = "INSERT INTO Employee (account_userid, lastname, merchant_id) values (?, ?, ?)"; 
                jdbcTemplate.update(sqlstmt1, mon.getUserid(),  "monitor", mon.getMerchantId());
                LOG.trace("Monitor Successfully Created.");
        
            } else {
                
            	return response.createOperationResponse("Monitor Account Already Exists.",409).toString();
            }
        	
        } catch (QueryTimeoutException e){
        
        	LOG.error("Query Time out.");
            return response.createOperationResponse("SQL Request Timeout.",504).toString();
        
        } catch (InvalidResultSetAccessException e) {
        	
        	response.createOperationResponse("Request could not be completed due to an internal server error.",500).toString();
   	    
        } 
   	    catch (DataAccessException e)
   	    {
   		 
   	       return response.createOperationResponse("Request could not be completed due to an internal server error.",500).toString();
   	    
   	    }
         
          return response.createOperationResponse("Monitor successfully created.",201).toString();
        
        }
   
   
    @Override
    public String readMonitor(int Merchant_id){
        
        String sqlstmt = "SELECT account_userid, merchant_id, lastname FROM Employee " +
        		"WHERE merchant_id = ? and lastname = ?"; 
        
        List<java.util.Map<String,Object>> rows = null;
        
        JSONArray jArray = new JSONArray();
       
        try {
        
	        rows = jdbcTemplate.queryForList(sqlstmt, Merchant_id, "monitor");
	        
	       
	        
	        if (rows.size() == 0){
	        	
	        	jArray.put(response.createOperationResponse("No Content.",204));
	        	
	        	return jArray.toString();
	        	
	        }
	        
        } catch (QueryTimeoutException e){
        	jArray.put(response.createOperationResponse("SQL Request Timeout.",504));	
    	    
            return jArray.toString();
        
        } catch (InvalidResultSetAccessException e) {
        	
        	jArray.put(response.createOperationResponse("Request could not be completed due to an internal server error.",500));	
    	    return jArray.toString();
        } 
   	    catch (DataAccessException e){
   		 
   	    	jArray.put(response.createOperationResponse("Request could not be completed due to an internal server error.",500));	
   	       return jArray.toString();
   	    
   	    }
        
        for (java.util.Map<String, Object> row : rows) {
            
            JSONObject obj = new JSONObject();
            try {
				obj.put("userid", (String)row.get("account_userid"));
				obj.put("merchantid", (Long)row.get("merchant_id"));
	            obj.put("lastname", (String)row.get("lastname"));
	            jArray.put(obj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				jArray.put(response.createOperationResponse("Request could not be completed due to an internal server error.",500));
				LOG.error("JSON Exception");
				return jArray.toString();
				
			}
           
        }  
        
        jArray.put(response.createOperationResponse("Sucessful operation.",200));
       
        
        return jArray.toString();
    }

    @Override
    public String deleteMonitor(int merchant_id) {
        // TODO Auto-generated method stub
        
    	 try {
    	
           String sqlstmt = "delete from Employee where merchant_id = ? and lastname = ?"; 
           jdbcTemplate.update(sqlstmt, merchant_id,  "monitor");
         
    	 } catch (QueryTimeoutException e){
    	    
    		return response.createOperationResponse("SQL Request Timeout.",504).toString();
    		 
    	 } catch (InvalidResultSetAccessException e) 
    	 {
    		 System.out.println("error here");
    		 return response.createOperationResponse("Request could not be completed due to an internal server error.",500).toString();
    	 } 
    	 catch (DataAccessException e)
    	 {
    		 response.createOperationResponse("Request could not be completed due to an internal server error.",500).toString();
    	 }
    	
    	 return response.createOperationResponse("Account deleted successfully.",204).toString();
         
    }
   
    private boolean monitorExists(String userid) {
        String sql = "SELECT count(*) FROM employee WHERE userid = ? and lastname = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, userid, "monitor");
        if(count == 0) {
            return false;
        } else {
            return true;
        }
    }
    private boolean monitorLocationExists(int locID, int monID) {
        String sql = "SELECT count(*) FROM merchantlochost WHERE location_id = ? and person_id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, locID, monID);
        if(count == 0) {
            return false;
        } else {
            return true;
        }
    }
    private boolean monitorForLocationExists(int locationIdentifier) {
        String sql = "SELECT " +
                "count(*) " +
            "FROM Account " + 
                "JOIN Employee ON Employee.`account_userid` = Account.`userid` " +
                "JOIN MerchantLocHost ON Employee.`id` = MerchantLocHost.`person_id` " + 
                "JOIN MerchantLocation ON MerchantLocation.`id` = MerchantLocHost.`location_id` " +
                "JOIN Merchant on Merchant.id = MerchantLocation.merchant_id " +
            "WHERE " +  
                "Account.userid LIKE '%.monitor' " +
                "AND MerchantLocHost.`location_id` = ? ";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, locationIdentifier);
        if(count == 0) {
            return false;
        } else {
            return true;
        }
    }
    @Override
    public String monitorForLocation(int locationIdentifier) {
        String query =
            "SELECT " +
                "Merchant.id as merchant_id, " +
                "MerchantLocation.id as location_id, " +
                "Account.userid as user_id, " +
                "MerchantLocation.kiosk_info_id as kiosk_info_id, " +
                "Employee.id as employee_id, " +
                "Employee.firstName as first_name, " +
                "Employee.lastName as last_name " +
            "FROM Account " + 
                "JOIN Employee ON Employee.`account_userid` = Account.`userid` " +
                "JOIN MerchantLocHost ON Employee.`id` = MerchantLocHost.`person_id` " + 
                "JOIN MerchantLocation ON MerchantLocation.`id` = MerchantLocHost.`location_id` " +
                "JOIN Merchant on Merchant.id = MerchantLocation.merchant_id " +
            "WHERE " +  
                "Account.userid LIKE '%.monitor' " +
                "AND MerchantLocHost.`location_id` = ? ";

        Monitor monitor = new Monitor();
        JSONObject json = new JSONObject();
        try {
        	if (monitorForLocationExists(locationIdentifier)){
	            monitor = jdbcTemplate.queryForObject(
	                query,
	                new Object[] { locationIdentifier},
	                new MonitorRowMapper()
	            );
	            
	            json = monitor.toJSON();
	            json.put("id", 200);
	            json.put("description", "Successful Operation");
	            
        	} else {
        		return response.createOperationResponse("No Content.",204).toString();
        	}
        } catch (EmptyResultDataAccessException e) {
            System.out.println(
                String.format("Unable to find Monitor Account for Location %d", locationIdentifier)
            );
            response.createOperationResponse("No Content.",204).toString();
        } catch (JSONException e) {
			// TODO Auto-generated catch block
        	return response.createOperationResponse("Request could not be completed due to an internal server error.",500).toString();
		}
        
        return json.toString();
    } 
    
  
	@Override
	public String addLocationMonitor(int locId, int monId) {
		// TODO Auto-generated method stub
		 try {
			if (!monitorLocationExists(locId, monId)){
				String sqlstmt = "INSERT INTO merchantlochost (location_id, person_id) values (?, ?)"; 
		        jdbcTemplate.update(sqlstmt, locId,  monId);
		        LOG.trace("Monitor Successfully Created.");
			} else {
	
	                
	            	LOG.error("Monitor Account Location Already Exists.");
	                
	                return response.createOperationResponse("Monitor Account Location Already Exists.",409).toString();
	        }
		 }
		catch (QueryTimeoutException e){
        
        	LOG.error("Query Time out.");
            return response.createOperationResponse("SQL Request Timeout.",504).toString();
        
        } catch (InvalidResultSetAccessException e) {
        	
   		 return response.createOperationResponse("Request could not be completed due to an internal server error.",500).toString();
   	    
        } 
   	    catch (DataAccessException e)
   	    {
   		 
   	       return response.createOperationResponse("Request could not be completed due to an internal server error.",500).toString();
   	    
   	    }
         
          return response.createOperationResponse("Location Monitor Added.",201).toString();
        
        
	}


	@Override
	public String removeLocationMonitor(int locId, int monId) {
		// TODO Auto-generated method stub
		     try {
	    	   
		    	 
		       if (monitorLocationExists(locId, monId)) {
		           String sqlstmt = "delete from merchantlochost where location_id = ? and person_id = ?"; 
		           jdbcTemplate.update(sqlstmt, locId,  monId);
		       }  else {
		    	   response.createOperationResponse("No Content", 404);
		       }
	         
	    	 } catch (QueryTimeoutException e){
	    	    
	    		return response.createOperationResponse("SQL Request Timeout.",504).toString();
	    		 
	    	 } catch (InvalidResultSetAccessException e) 
	    	 {
	    		 System.out.println("error here");
	    		 response.createOperationResponse("Request could not be completed due to an internal server error.",500).toString();
	    	 } 
	    	 catch (DataAccessException e)
	    	 {
	    		return response.createOperationResponse("Request could not be completed due to an internal server error.",500).toString();
	    	 }
	    	
	    	 return response.createOperationResponse("Location Monitor Removed.",204).toString();
	}


	@Override
	public String addQueueMonitor(int queueID, int monID) {
		// TODO Auto-generated method stub
		try {
			if (!queueMonitorExists(queueID, monID)){
				String sqlstmt = "INSERT INTO queuehost (queue_id, person_id) values (?, ?)"; 
		        jdbcTemplate.update(sqlstmt, queueID,  monID);
		        LOG.trace("Monitor Successfully Created.");
			} else {
	
	                
	            	LOG.error("Queue Monitor Already Exists.");
	                
	                return response.createOperationResponse("Queue Monitor Already Exists.",409).toString();
	        }
		 }
		catch (QueryTimeoutException e){
        
        	LOG.error("Query Time out.");
            return response.createOperationResponse("SQL Request Timeout.",504).toString();
        
        } catch (InvalidResultSetAccessException e) {
        	
   		 return response.createOperationResponse("Request could not be completed due to an internal server error.",500).toString();
   	    
        } 
   	    catch (DataAccessException e)
   	    {
   		 
   	       return response.createOperationResponse("Request could not be completed due to an internal server error.",500).toString();
   	    
   	    }
         
          return response.createOperationResponse("Queue Monitor Added.",201).toString();
        
        
	}
	private boolean queueMonitorExists(int queueID, int monID) {
        String sql = "SELECT count(*) FROM queuehost WHERE queue_id = ? and person_id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, queueID, monID);
        if(count == 0) {
            return false;
        } else {
            return true;
        }
    }


	@Override
	public String removeQueueMonitor(int queueId, int monId) {
		// TODO Auto-generated method stub
		try {
			if (queueMonitorExists(queueId, monId)){
	           String sqlstmt = "delete from queuehost where queue_id = ? and person_id = ?"; 
	           jdbcTemplate.update(sqlstmt, queueId,  monId);
			} else {
				response.createOperationResponse("No Content", 404);
			}
	         
	    	 } catch (QueryTimeoutException e){
	    	    
	    		return response.createOperationResponse("SQL Request Timeout.",504).toString();
	    		 
	    	 } catch (InvalidResultSetAccessException e) 
	    	 {
	    		 
	    		 return response.createOperationResponse("Request could not be completed due to an internal server error.",500).toString();
	    	 } 
	    	 catch (DataAccessException e)
	    	 {
	    		return response.createOperationResponse("Request could not be completed due to an internal server error.",500).toString();
	    	 }
	    	
	    	 return response.createOperationResponse("Location Monitor Removed.",204).toString();
	}
}

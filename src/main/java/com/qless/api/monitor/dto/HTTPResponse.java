package com.qless.api.monitor.dto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HTTPResponse {
	
	ErrorResponseDTO errDetails = new ErrorResponseDTO();
	private Logger LOG = LoggerFactory.getLogger(HTTPResponse.class);
	JSONObject statusObject = new JSONObject();
	
    JSONArray jArray = new JSONArray();
	    

	public  JSONObject createOperationResponse(String msg, int code)
    {
		try {  
			
			statusObject.put("id", code);
			statusObject.put("description", msg);
            
	        
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LOG.error("JSON Exception");
		}
        return statusObject;
    }
}

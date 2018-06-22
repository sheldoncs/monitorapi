package com.qless.api.monitor;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qless.CustomMonitorApiApplication;
import com.qless.api.monitor.controller.MonitorController;
import com.qless.api.monitor.model.LocationMonitor;
import com.qless.api.monitor.model.Monitor;
import com.qless.api.monitor.model.QueueHost;
import com.qless.api.monitor.service.MonitorService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MonitorController.class, secure = false)
@ContextConfiguration(classes={CustomMonitorApiApplication.class})

public class CustomMonitorControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private MonitorService monitorService;
	
	
	@Test 
	public void readMonitor(){
		
		try {
			mvc.perform(MockMvcRequestBuilders.get("/qless/api/v1/monitor/accounts/1").
					contentType(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void MonitorCreate(){
		Monitor monitor = new Monitor();
		monitor.setMerchantId(17);
		monitor.setUserid("ggggg.monitor");
		monitor.setPwd("kleptomaniac");
		try {
			 
			mvc.perform(MockMvcRequestBuilders.post("/qless/api/v1/monitor/create")
					.content(asJsonString(monitor))
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON));
			 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	@Test 
	public void addLocationMonitor(){
		
		LocationMonitor locMon = new LocationMonitor();
		locMon.setAcctId(40);
		locMon.setLocId(200);
		
		try {
			 
			mvc.perform(MockMvcRequestBuilders.post("/qless/api/v1/monitor/add/location")
					.content(asJsonString(locMon))
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON));
			 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test 
	public void addQueueMonitor(){
		
		QueueHost queueHost = new QueueHost();
		queueHost.setQueueId(40);
		queueHost.setMonitorId(200);
		
		try {
			 
			mvc.perform(MockMvcRequestBuilders.post("/qless/api/v1/monitor/add/queue")
					.content(asJsonString(queueHost))
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON));
			 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test 
	public void removeLocationMonitor(){
		
		try {
			 
			 mvc.perform(MockMvcRequestBuilders.delete("/qless/api/v1/monitor/remove/location/{locID}/{monID}", 200, 40)
						.contentType(MediaType.APPLICATION_JSON_VALUE))
						.andExpect(status().isOk());
			 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	///remove/queue/{queueID}/{monID}
	@Test 
	public void removeQueueMonitor(){
		
		try {
			 
			 mvc.perform(MockMvcRequestBuilders.delete("/qless/api/v1/monitor/remove/queue/{queueID}/{monID}", 200, 40)
						.contentType(MediaType.APPLICATION_JSON_VALUE))
						.andExpect(status().isOk());
			 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test 
	public void addQueue(){
		
		QueueHost queueHost = new QueueHost();
		queueHost.setMonitorId(100);
		queueHost.setQueueId(15);
		
		try {
			 
			mvc.perform(MockMvcRequestBuilders.post("/qless/api/v1/monitor/add/queue")
					.content(asJsonString(queueHost))
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON));
			 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}  
	@Test
	public void MonitorDelete(){
	    
		 try {
			 
			 mvc.perform(MockMvcRequestBuilders.delete("/qless/api/v1/monitor/delete/{merchant_id}", 0)
						.contentType(MediaType.APPLICATION_JSON_VALUE))
						.andExpect(status().isOk());
			 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

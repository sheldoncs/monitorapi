package com.qless.api.monitor.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qless.api.monitor.model.LocationMonitor;
import com.qless.api.monitor.model.Monitor;
import com.qless.api.monitor.model.QueueHost;
import com.qless.api.monitor.service.MonitorService;


@RestController
@RequestMapping(path = "/qless/api/v1/monitor", produces = MediaType.APPLICATION_JSON_VALUE )
public class MonitorController {
    
    
    private MonitorService monitorService;

    
    @Autowired
    public void setFieldService(MonitorService fieldService) {
        this.monitorService = fieldService;
    }

    
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String addMonitor(@RequestBody Monitor monitor) {
        
        String status = monitorService.createNewMonitor(monitor);
        
        return status;
        
    }
    @RequestMapping(path = "/add/queue", method = RequestMethod.POST)
    public String addQueueMonitor(@RequestBody QueueHost queueHost) {
        
        String result = monitorService.addQueueMonitor(queueHost.getQueueId(), 
        		                                              queueHost.getMonitorId());
        
        return result;
        
    }
    @RequestMapping(path = "/add/location", method = RequestMethod.POST)
    public String addLocationMonitor(@RequestBody LocationMonitor locMonitor) {
        
    	String  result = monitorService.addLocationMonitor(locMonitor.getLocId(), locMonitor.getAcctId());
        return result;
        
    }
    @RequestMapping(path = "/remove/location/{locID}/{monID}", method = RequestMethod.DELETE)
    public String removeLocationMonitor(@PathVariable int locID, @PathVariable int monID) {
        
    	
        return monitorService.removeLocationMonitor(locID, monID);
        
    }
    //:
    @RequestMapping(path = "/remove/queue/{queueID}/{monID}", method = RequestMethod.DELETE)
    public String removeQueueMonitor(@PathVariable int queueID, @PathVariable int monID) {
        
    	
        return monitorService.removeQueueMonitor(queueID, monID);
        
    }
    
    @RequestMapping(path = "/accounts/{merchant_id}", method = RequestMethod.GET)
    public String readMonitor(@PathVariable int merchant_id) {
    
        String val = "";
        String a = monitorService.readMonitor(merchant_id);
        
        System.out.println(a);
        
        if (a == null)
            val = "empty";
        else
            val = a.toString();
        return val;
    }

    @RequestMapping(path = "/location/{locationIdentifier}", method = RequestMethod.GET)
    public String monitorForLocation(@PathVariable int locationIdentifier)
    {
        
         String  monitor = monitorService.monitorForLocation(locationIdentifier);
        

        return monitor.toString();
    }
    @RequestMapping(value = "/delete/{merchant_id}", method = RequestMethod.DELETE)
    public String deleteMonitor(@PathVariable int merchant_id) {
          
        return monitorService.deleteMonitor(merchant_id);
    }
}

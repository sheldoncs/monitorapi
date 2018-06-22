package com.qless.api.monitor.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.qless.api.monitor.model.Monitor;
import com.qless.api.monitor.repository.MonitorDbDriver;


@Service
public class MonitorService  implements IMonitorService{

    private MonitorDbDriver monitorDAO;
    
    
    @Autowired	
    public void setFieldRepository(MonitorDbDriver monitorDAO) {
        this.monitorDAO = monitorDAO;
    }
    
    
    @Override
    public String createNewMonitor(Monitor mon) {
        // TODO Auto-generated method stub
        String status = monitorDAO.createNewMonitor(mon);
        
        return status;
    }

    @Override
    public String deleteMonitor(int merchant_id) {
        // TODO Auto-generated method stub
        return monitorDAO.deleteMonitor(merchant_id);
    }

   

    @Override
    public String readMonitor(int Merchant_id) {
        // TODO Auto-generated method stub
        
        return monitorDAO.readMonitor(Merchant_id);
    }

    @Override
    public String monitorForLocation(int locationIdentifier) 
    {
        String monitor = this.monitorDAO.monitorForLocation(locationIdentifier);
       
        return monitor;
    }


	@Override
	public String addLocationMonitor(int locId, int monId) {
		// TODO Auto-generated method stub
		
		return monitorDAO.addLocationMonitor(locId, monId);
	}


	@Override
	public String removeLocationMonitor(int locId, int monId) {
		// TODO Auto-generated method stub
		
		return monitorDAO.removeLocationMonitor(locId,monId);
	}


	@Override
	public String addQueueMonitor(int queueID, int monId) {
		// TODO Auto-generated method stub
		
		return monitorDAO.addQueueMonitor(queueID, monId);
	}


	@Override
	public String removeQueueMonitor(int queueId, int monId) {
		// TODO Auto-generated method stub
		return monitorDAO.removeQueueMonitor(queueId, monId);
	}

}

package com.qless.api.monitor.repository;

import com.qless.api.monitor.model.Monitor;


public interface IDbDriver {

	public String createNewMonitor(Monitor mon);

	public String addQueueMonitor(int queueID, int monID);
	
	String deleteMonitor(int merchant_id);

	public String readMonitor(int Merchant_id);
	
	public String monitorForLocation(int locationIdentifier);
	
	public String addLocationMonitor(int locId, int monId);
	
	public String removeLocationMonitor(int locId, int monId);
	
	public String removeQueueMonitor(int queueId, int monId);
	
	
	
}
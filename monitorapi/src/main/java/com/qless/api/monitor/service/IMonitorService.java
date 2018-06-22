package com.qless.api.monitor.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import org.springframework.stereotype.Service;

import com.qless.api.monitor.model.Monitor;

public interface IMonitorService  {
    
    String createNewMonitor(Monitor mon);
    String deleteMonitor(int merchant_id);
    String readMonitor(int Merchant_id);
    String monitorForLocation(int locationIdentifier);
    String addLocationMonitor(int locId, int monId);
    String removeLocationMonitor(int locId, int monId);
    String addQueueMonitor(int queue_id, int monId);
    String removeQueueMonitor(int queueId, int monId);
}

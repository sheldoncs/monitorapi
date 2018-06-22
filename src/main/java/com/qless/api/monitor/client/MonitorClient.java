package com.qless.api.monitor.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.qless.api.monitor.model.LocationMonitor;
import com.qless.api.monitor.model.Monitor;

public class MonitorClient {
    public void delete(){
    	// TODO Auto-generated method stub
		
		
    			RestTemplate restTemplate = new RestTemplate();
    			String fooResourceUrl
    			  = "http://localhost:8081/monitorapi/qless/api/v1/monitor/delete";
    			ResponseEntity<String> response
    			  = restTemplate.getForEntity(fooResourceUrl + "/1", String.class);
    			
    			String result = restTemplate.getForObject(fooResourceUrl + "/1", String.class);
    			System.out.println(result);
    }
	public void create(){
 		
		 RestTemplate rt = new RestTemplate();
		 String fooResourceUrl
		  = "http://localhost:8081/monitorapi/qless/api/v1/monitor/create";
		
		 Monitor mon = new Monitor();
		 mon.setUserid("qqqqqql.monitor");
		 mon.setMerchantId(8);
		 mon.setPwd("GermanShepherd");
		 
		String monitor = rt.postForObject(fooResourceUrl, mon, String.class);
		
		System.out.println(monitor);
		 
	}
	public void createLocationMonitor(){
 		
		 RestTemplate rt = new RestTemplate();
		 String fooResourceUrl
		  = "http://localhost:8081/monitorapi/qless/api/v1/monitor/add/location";
		
		 LocationMonitor mon = new LocationMonitor();
		 mon.setLocId(25);
		 mon.setAcctId(35);
		 
		String monitor = rt.postForObject(fooResourceUrl, mon, String.class);
		
		System.out.println(monitor);
		 
	}
	public void read(){

		RestTemplate restTemplate = new RestTemplate();
		String fooResourceUrl
		  = "http://localhost:8081/monitorapi/qless/api/v1/monitor/accounts";
		/*ResponseEntity<String> response
		  = restTemplate.getForEntity(fooResourceUrl + "/8", String.class);
		*/
		String result =restTemplate.getForObject(fooResourceUrl + "/8", String.class);
		System.out.println(result);
		// 
	}
	public static void main(String[] args) {
		MonitorClient tst = new MonitorClient();
		tst.read();
		//assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		
	}

}

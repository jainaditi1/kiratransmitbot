package com.transmitapp.kira.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.transmitapp.kira.common.Headers;
import com.transmitapp.kira.domain.BusInfo;

enum BusStops {
    WEST_BOUND("west bound"),
    KING_ST_WEST_AT_PETER_ST_WEST_SIDE("King St West At Peter St West Side"),
    KING_ST_WEST_AT_JOHN_ST_EAST_SIDE("King St West At John St East Side"), 
    KING_ST_WEST_AT_JOHN_ST_WEST_SIDE("King St West At John St West Side"), 
    EAST_BOUND("East bound");
	
    private final String busStop;
    private BusStops(String busStop) {
        this.busStop = busStop;
    }

    public String getBusStop() {
        return busStop;
    }
}

@RestController
@RequestMapping("/bus/status")
public class TransmitController {

	private BusStops[] stopsNames = null;
	private HttpHeaders httpHeaders = null;
	private Headers headers = new Headers();
	
	@Value("${slackincomingwebhookurl}")
    private String webhookUrl;
	
	
	@Value("${restbusinfourl}")
    private String restBusInfoUrl;
	
	@Autowired
	private TransmitProcessor transmitProcessor;
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<BusInfo> getBusStatus() {
		
		headers.setHeaders(httpHeaders);
		stopsNames = BusStops.values();

		List<BusInfo> stops = transmitProcessor.getStopsArray();
		List<BusInfo> info = new ArrayList<BusInfo>();
		
		for (int m = 0; m < stopsNames.length; m++) {
			boolean stopFound = false;

			for (int l = 0; l < stops.size(); l++) {

				if (stops.get(l).getStopName().equals(stopsNames[m].getBusStop())) {
					stopFound = true;
					String id = stops.get(l).getId();
					info = transmitProcessor.getUpcomingTimingsAndPrediction(id);
					
					for (int i = 0; i < info.size(); i++) {
						String messageToPost = "Bus ID "+ id +" will arrive in " + info.get(i).getArrivingInMinutes() + " minutes. /n" + info.get(i).getDelayReason();
						postToSlackChannel(messageToPost);
					}
				}	
			}
			
			if (!stopFound) {
				RestTemplate template = new RestTemplate();
				HttpEntity<String> entity = new HttpEntity<String>(
						"{'text':" + "'" + stopsNames[m] + " not found!'" + "}", httpHeaders);
				template.postForObject(webhookUrl, entity, String.class);
			}
		}
		
		return info;
		
	}


	private void postToSlackChannel(String messageToPost) {
		RestTemplate template = new RestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>("{'text':" + "'" + messageToPost + "'" + "}",
				httpHeaders);
		String result = template.postForObject(webhookUrl, entity, String.class);
		System.out.println("Slack bot response : " + result);
	}
	
		
}

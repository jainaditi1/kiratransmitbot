package com.transmitapp.kira.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.transmitapp.kira.domain.BusInfo;

@Component("transmitProcessor")
public class TransmitProcessor {

	@Value("${restbusinfourl}")
	private String restBusInfoUrl;

	public List<BusInfo> getStopsArray() {
		RestTemplate template = new RestTemplate();
		String allBuses = template.getForObject(restBusInfoUrl, String.class);
		JSONObject buses = new JSONObject(allBuses);
		JSONArray stops = buses.getJSONArray("stops");
		List<BusInfo> allBusesInfo = new ArrayList<BusInfo>();
		for (int l = 0; l < stops.length(); l++) {
			BusInfo info = new BusInfo();
			JSONObject stopDetail = stops.getJSONObject(l);
			info.setId(stopDetail.getString("id"));
			info.setStopName(stopDetail.getString("title"));
			allBusesInfo.add(info);
		}

		return allBusesInfo;
	}

	public List<BusInfo> getUpcomingTimingsAndPrediction(String id) {
		String url = restBusInfoUrl + "stops/" + id + "/predictions";
		RestTemplate template = new RestTemplate();
		Object[] response = template.getForObject(url, Object[].class);
		JSONArray jsonarray = new JSONArray(response);
		List<BusInfo> allBusesInfo = new ArrayList<BusInfo>();
		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject jsonobject = jsonarray.getJSONObject(i);
			BusInfo info = parsePredictionAndBuilsMessage(jsonobject, id);
			allBusesInfo.add(info);
		}

		return allBusesInfo;
	}

	private BusInfo parsePredictionAndBuilsMessage(JSONObject jsonobject, String id) {
		BusInfo info = new BusInfo();
		info.setId(id);
		JSONArray jArrayValues = jsonobject.getJSONArray("values");
		int min = 0;
		for (int k = 0; k < jArrayValues.length(); k++) {
			JSONObject prediction = jArrayValues.getJSONObject(k);
			min = prediction.getInt("minutes");
			info.setArrivingInMinutes(min);
		}

		JSONObject link = jsonobject.getJSONObject("_links");
		JSONObject self = link.getJSONObject("self");
		String predictionMessage = self.get("title").toString();
		info.setDelayReason(predictionMessage);
		return info;
	}

}

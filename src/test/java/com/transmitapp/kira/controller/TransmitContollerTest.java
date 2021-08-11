package com.transmitapp.kira.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.transmitapp.kira.domain.BusInfo;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application.properties")
public class TransmitContollerTest {

	@Autowired
    private TransmitProcessor processor;

    @Autowired
    private TransmitController controller;
	

	@Test
	public void testGetBusStatus() {
		List<BusInfo> info = controller.getBusStatus();
	 
	    assertEquals(info.get(0).getArrivingInMinutes(), 3);
	    assertEquals(info.get(0).getStop(), "east bound");
	 }
	
	@Before
	public void setUp() {
		List<BusInfo> info = new ArrayList<BusInfo>();
		BusInfo businfo = new BusInfo();
		businfo.setArrivingInMinutes(3);
		businfo.setDelayReason("construction going on");
		businfo.setId("50453");
		businfo.setStop("east bound");
		info.add(businfo);
		
		businfo = new BusInfo();
		businfo.setArrivingInMinutes(13);
		businfo.setDelayReason("Diversion");
		businfo.setId("50453");
		businfo.setStop("west bound");
		info.add(businfo);
		
		
		Mockito.when(processor.getUpcomingTimingsAndPrediction("50453")).thenReturn(info);
	}

}

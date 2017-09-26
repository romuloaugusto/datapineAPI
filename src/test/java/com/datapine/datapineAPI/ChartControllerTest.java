package com.datapine.datapineAPI;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.datapine.datapineAPI.controller.ChartController;
import com.datapine.datapineAPI.model.RequestChart;
import com.datapine.datapineAPI.model.ResponseChart;
import com.datapine.datapineAPI.model.Serie;
import com.datapine.datapineAPI.service.ChartService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ChartController.class, secure = false)
public class ChartControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ChartService chartService;
	
	@Test
	public void getChartWithEmptyDimensionsAndEmptyMeasures() throws Exception {
		
		String requestChartJson = "{\"dimensions\": [],\"measures\":[]}";
		
		String responseChartJson = "{\"categories\":[],\"series\":[]}";
		
		ResponseChart responseChart = new ResponseChart(new ArrayList<>(), new ArrayList<>());
		
		Mockito.when(chartService.getChart(Mockito.any(RequestChart.class))).thenReturn(responseChart);
		
		// Send RequestChart as body to /chart
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/chart")
				.accept(MediaType.APPLICATION_JSON).content(requestChartJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), result.getResponse().getStatus());
		
		JSONAssert.assertEquals(responseChartJson, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getChartWithDimensionsTeamAndMeasuresChampions() throws Exception {
		
		String requestChartJson = "{\"dimensions\": [\"team\"],\"measures\":[\"champions\"]}";
		
		String responseChartJson = "{\"categories\":[\"Real Madrid\",\"Barcelona\",\"Bayern Munich\",\"Liverpool\",\"Milan\"],"
				+ "\"series\":[{\"name\":\"champions\",\"data\": [12.0, 5.0, 5.0, 5.0, 7.0]}]}";
		
		ResponseChart responseChart = new ResponseChart(getAllCategories(), Arrays.asList(getChampionsSerie()));
		
		Mockito.when(chartService.getChart(Mockito.any(RequestChart.class))).thenReturn(responseChart);
		
		// Send RequestChart as body to /chart
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/chart")
				.accept(MediaType.APPLICATION_JSON).content(requestChartJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), result.getResponse().getStatus());
		
		JSONAssert.assertEquals(responseChartJson, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getChartWithDimensionsTeamAndMeasuresChampionsAndLeaguesTest() throws Exception {
		
		String requestChartJson = "{\"dimensions\": [\"team\"],\"measures\":[\"champions\",\"leagues\"]}";
		
		String responseChartJson = "{\"categories\":[\"Real Madrid\",\"Barcelona\",\"Bayern Munich\",\"Liverpool\",\"Milan\"],"
				+ "\"series\":[{\"name\":\"champions\",\"data\": [12.0, 5.0, 5.0, 5.0, 7.0]},{\"name\":\"leagues\",\"data\": [33.0, 24.0, 26.0, 18.0, 18.0]}]}";
		
		ResponseChart responseChart = new ResponseChart(getAllCategories(), Arrays.asList(getChampionsSerie(), getLeaguesSerie()));
		
		Mockito.when(chartService.getChart(Mockito.any(RequestChart.class))).thenReturn(responseChart);

		// Send RequestChart as body to /chart
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/chart")
				.accept(MediaType.APPLICATION_JSON).content(requestChartJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), result.getResponse().getStatus());
		
		JSONAssert.assertEquals(responseChartJson, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getChartWithDimensionsTeamAndMeasuresChampionsAndLeaguesAndRevenueTest() throws Exception {
		
		String requestChartJson = "{\"dimensions\": [\"team\"],\"measures\":[\"champions\",\"leagues\",\"revenue\"]}";
		
		String responseChartJson = "{\"categories\":[\"Real Madrid\",\"Barcelona\",\"Bayern Munich\",\"Liverpool\",\"Milan\"],"
				+ "\"series\":[{\"name\":\"champions\",\"data\": [12.0, 5.0, 5.0, 5.0, 7.0]},"
				+ "{\"name\":\"leagues\",\"data\": [33.0, 24.0, 26.0, 18.0, 18.0]},"
				+ "{\"name\":\"revenue\",\"data\": [625.0, 620.0, 600.0, 400.0, 250.0]}]}";
		
		List<Serie> series = new ArrayList<>();
		series.add(getChampionsSerie());
		series.add(getLeaguesSerie());
		series.add(getRevenueSerie());
		
		ResponseChart responseChart = new ResponseChart(getAllCategories(), Arrays.asList(getChampionsSerie(), 
				getLeaguesSerie(), getRevenueSerie()));
		
		Mockito.when(chartService.getChart(Mockito.any(RequestChart.class))).thenReturn(responseChart);
		
		// Send RequestChart as body to /chart
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/chart")
				.accept(MediaType.APPLICATION_JSON).content(requestChartJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), result.getResponse().getStatus());
		
		JSONAssert.assertEquals(responseChartJson, result.getResponse().getContentAsString(), false);
	}

	private Serie getRevenueSerie() {
		return new Serie("revenue", Arrays.asList(625.0, 620.0, 600.0, 400.0, 250.0));
	}

	private Serie getLeaguesSerie() {
		return new Serie("leagues", Arrays.asList(33.0, 24.0, 26.0, 18.0, 18.0));
	}

	private Serie getChampionsSerie() {
		return new Serie("champions", Arrays.asList(12.0, 5.0, 5.0, 5.0, 7.0));
	}

	private List<String> getAllCategories() {
		List<String> categories = new ArrayList<>();
		categories.add("Real Madrid");
		categories.add("Barcelona");
		categories.add("Bayern Munich");
		categories.add("Liverpool");
		categories.add("Milan");
		
		return categories;
	}

}

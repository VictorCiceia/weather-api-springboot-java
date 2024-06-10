package org.adaschool.Weather.controller;

import org.adaschool.Weather.data.WeatherReport;
import org.adaschool.Weather.service.WeatherReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WeatherReportControllerTest {

    @Mock
    private WeatherReportService weatherReportService;

    @InjectMocks
    private WeatherReportController weatherReportController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(weatherReportController).build();
    }

    @Test
    void coordenadasCorrectarDebeRetornarExitosamente() throws Exception {
        double latitude = 37.8267;
        double longitude = -122.4233;

        WeatherReport weatherReport = new WeatherReport();
        weatherReport.setTemperature(25.0);
        weatherReport.setHumidity(60.0);

        when(weatherReportService.getWeatherReport(latitude, longitude)).thenReturn(weatherReport);

        mockMvc.perform(get("/v1/api/weather-report")
                        .param("latitude", String.valueOf(latitude))
                        .param("longitude", String.valueOf(longitude))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"temperature\":25.0,\"humidity\":60.0}"));
    }


    @Test
    void sinLatitudDebeNoRetornarExitosamente() throws Exception {
        double longitude = -122.4233;

        mockMvc.perform(get("/v1/api/weather-report")
                        .param("longitude", String.valueOf(longitude))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void sinLongitudDebeNoRetornarExitosamente() throws Exception {
        double latitude = 37.8267;

        mockMvc.perform(get("/v1/api/weather-report")
                        .param("latitude", String.valueOf(latitude))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void coordenadasCorrectarNegativasDebeRetornarExitosamente() throws Exception {
        double latitude = -37.8267;
        double longitude = -122.4233;

        WeatherReport weatherReport = new WeatherReport();
        weatherReport.setTemperature(15.0);
        weatherReport.setHumidity(70.0);

        when(weatherReportService.getWeatherReport(latitude, longitude)).thenReturn(weatherReport);

        mockMvc.perform(get("/v1/api/weather-report")
                        .param("latitude", String.valueOf(latitude))
                        .param("longitude", String.valueOf(longitude))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"temperature\":15.0,\"humidity\":70.0}"));
    }

    @Test
    void coordenadasCeroDebeRetornarExitosamente() throws Exception {
        double latitude = 0.0;
        double longitude = 0.0;

        WeatherReport weatherReport = new WeatherReport();
        weatherReport.setTemperature(20.0);
        weatherReport.setHumidity(50.0);

        when(weatherReportService.getWeatherReport(latitude, longitude)).thenReturn(weatherReport);

        mockMvc.perform(get("/v1/api/weather-report")
                        .param("latitude", String.valueOf(latitude))
                        .param("longitude", String.valueOf(longitude))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"temperature\":20.0,\"humidity\":50.0}"));
    }

}

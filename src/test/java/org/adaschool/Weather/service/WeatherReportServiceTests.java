package org.adaschool.Weather.service;

import org.adaschool.Weather.data.WeatherApiResponse;
import org.adaschool.Weather.data.WeatherReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class WeatherReportServiceTest {

    private RestTemplate restTemplate;
    private WeatherReportService weatherReportService;

    @BeforeEach
    void setUp() {
        restTemplate = Mockito.mock(RestTemplate.class);
        weatherReportService = new WeatherReportService(restTemplate);
    }

    @Test
    void coordenadasCorrectarDebeRetornarExitosamente() {
        double latitude = 37.8267;
        double longitude = -122.4233;

        WeatherApiResponse response = new WeatherApiResponse();
        WeatherApiResponse.Main main = new WeatherApiResponse.Main();
        main.setTemperature(25.0);
        main.setHumidity(60.0);
        response.setMain(main);

        when(restTemplate.getForObject(anyString(), Mockito.eq(WeatherApiResponse.class))).thenReturn(response);

        WeatherReport report = weatherReportService.getWeatherReport(latitude, longitude);

        assertEquals(25.0, report.getTemperature());
        assertEquals(60.0, report.getHumidity());
    }

    @Test
    void coordenadasCorrectarNegativasDebeRetornarExitosamente() {
        double latitude = -37.8267;
        double longitude = -122.4233;

        WeatherApiResponse response = new WeatherApiResponse();
        WeatherApiResponse.Main main = new WeatherApiResponse.Main();
        main.setTemperature(15.0);
        main.setHumidity(70.0);
        response.setMain(main);

        when(restTemplate.getForObject(anyString(), Mockito.eq(WeatherApiResponse.class))).thenReturn(response);

        WeatherReport report = weatherReportService.getWeatherReport(latitude, longitude);

        assertEquals(15.0, report.getTemperature());
        assertEquals(70.0, report.getHumidity());
    }

    @Test
    void coordenadasCeroDebeRetornarExitosamente() {
        double latitude = 0.0;
        double longitude = 0.0;

        WeatherApiResponse response = new WeatherApiResponse();
        WeatherApiResponse.Main main = new WeatherApiResponse.Main();
        main.setTemperature(20.0);
        main.setHumidity(50.0);
        response.setMain(main);

        when(restTemplate.getForObject(anyString(), Mockito.eq(WeatherApiResponse.class))).thenReturn(response);

        WeatherReport report = weatherReportService.getWeatherReport(latitude, longitude);

        assertEquals(20.0, report.getTemperature());
        assertEquals(50.0, report.getHumidity());
    }

    @Test
    void errorConexionDebeNoRetornarExitosamente() {
        double latitude = 37.8267;
        double longitude = -122.4233;

        when(restTemplate.getForObject(anyString(), Mockito.eq(WeatherApiResponse.class))).thenThrow(new RuntimeException("Algo salio mal"));

        assertThrows(RuntimeException.class, () -> weatherReportService.getWeatherReport(latitude, longitude));
    }
}

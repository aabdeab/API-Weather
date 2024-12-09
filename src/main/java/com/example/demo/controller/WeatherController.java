package com.example.demo.controller;

import com.example.demo.services.WeatherService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")

public class WeatherController {

    private final WeatherService weatherService;
    public WeatherController(WeatherService weatherService){
        this.weatherService=weatherService;
    }

    // Fetch current weather by location
    @GetMapping("/{location}")
    public ResponseEntity<String> getWeatherByLocation(@PathVariable String location) {
        String weatherData = weatherService.fetchWeatherData(location);
        return ResponseEntity.ok(weatherData);
    }

    // Fetch weather by location and date range
    @GetMapping("/{location}/{startDate}/{endDate}")
    public ResponseEntity<String> getWeatherByDateRange(
            @PathVariable String location,
            @PathVariable String startDate,
            @PathVariable String endDate) {
        String weatherData = weatherService.fetchWeatherDataByDateRange(location, startDate, endDate);
        return ResponseEntity.ok(weatherData);
    }
}

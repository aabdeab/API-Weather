package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Service
public class WeatherService   {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    private static final String WEATHER_CACHE_PREFIX = "weather:";

    @Value("${weather.API_KEY}")
    private String apiKey;

    @Value("${weather.api.base-url}")
    private String baseUrl;


    public String fetchWeatherData(String location) {
        String cacheKey = WEATHER_CACHE_PREFIX + location;

        // Check if the data is already in Redis
        String cachedData = (String) redisTemplate.opsForValue().get(cacheKey);
        if (cachedData != null) {
            return cachedData; // Return cached data
        }

        // Fetch data from the external API
        try {
            String url = String.format("%s/%s?key=%s", baseUrl, location, apiKey);
            String response = restTemplate.getForObject(url, String.class);

            // Cache the result in Redis for 1 hour
            redisTemplate.opsForValue().set(cacheKey, response, Duration.ofHours(1));

            return response;
        } catch (HttpClientErrorException e) {
            return "Error fetching weather data: " + e.getMessage();
        }
    }
    public String fetchWeatherDataByDateRange(String location, String startDate, String endDate) {
        String cacheKey = String.format("%s:%s:%s", WEATHER_CACHE_PREFIX + location, startDate, endDate);

        // Check if the data is already in Redis
        String cachedData = (String) redisTemplate.opsForValue().get(cacheKey);
        if (cachedData != null) {
            return cachedData; // Return cached data
        }

        // Fetch data from the external API
        try {
            String url = String.format("%s/%s/%s/%s?key=%s", baseUrl, location, startDate, endDate, apiKey);
            String response = restTemplate.getForObject(url, String.class);

            // Cache the result in Redis for 1 hour
            redisTemplate.opsForValue().set(cacheKey, response, Duration.ofHours(1));

            return response;
        } catch (HttpClientErrorException e) {
            return "Error fetching weather data: " + e.getMessage();
        }
    }
}

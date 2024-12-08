package com.example.demo.controller;


import com.example.demo.config.RedisConfig;
import com.example.demo.services.WeatherService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@AllArgsConstructor
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;
    private final RedisConfig redisConfig;




}

package com.weather.api.tiresias.controller;

import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TiresiasApiController {

    @GetMapping(value = "/hello")
    public String helloWorld() {
        JsonObject obj =new JsonObject();

        obj.addProperty("title", "테스트3");
        obj.addProperty("content", "테스트3 내용");

        JsonObject data = new JsonObject();

        data.addProperty("time", "12:00");
        obj.add("data", data);

        return obj.toString();
    }

}

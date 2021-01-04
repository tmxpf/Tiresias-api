package com.weather.api.tiresias.controller;

import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
    
    @GetMapping("api/now")
    public String now() {
        StringBuffer result = new StringBuffer();
        result.append("안녕하세요. 현재 서버 시간은 ");
        result.append(new Date());
        result.append("입니다.");

        return result.toString();
    }

}

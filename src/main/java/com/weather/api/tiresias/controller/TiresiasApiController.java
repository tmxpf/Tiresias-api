package com.weather.api.tiresias.controller;

import com.google.gson.JsonObject;
import com.weather.api.tiresias.utils.VideoFileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("api/video-convert")
    public Map<String, Object> convertVideo(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest multipartHttpServletRequest) {

        Map<String, Object> result = new HashMap<>();

        try {
            ClassPathResource resource = new ClassPathResource("transcoding/originVideos/file_example_AVI_1280_1_5MG.avi");
            Path path = Paths.get(resource.getURI());

            String rootPath = FileSystemView.getFileSystemView().getHomeDirectory().toString();

            Path path2 = Paths.get("C:/Users/Gyu/Desktop/transcoding/test1.png");
            String contentType = Files.probeContentType(path2);

//            VideoFileUtils.convertVideo("C:/Users/Gyu/Desktop/transcoding/originVideos", "file_example_AVI_1280_1_5MG.avi", "C:/Users/Gyu/Desktop/transcoding/convertedVideos", "result.mp4");
            VideoFileUtils.convertVideo2();
            result.put("result", "success");
        } catch(Exception e) {
            result.put("result", "error");
            e.printStackTrace();
        }

        return result;
    }

}

package com.weather.api.tiresias.utils;

import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@Component
public class VideoFileUtils {

    @Value("${key.ffmpeg.path}")    // application.yml 파일에서 프로퍼티로 설정한다.
    private String FFMPEG_PATH;

    @Value("${key.ffmpeg.path}")
    private String FFPROBE_PATH;

    private FFmpeg ffmpeg;
    private FFprobe ffprobe;

    @PostConstruct
    public void init() {
        try {
            ffmpeg = new FFmpeg(FFMPEG_PATH);
            Assert.isTrue(ffmpeg.isFFmpeg());

            ffprobe = new FFprobe(FFPROBE_PATH);
            Assert.isTrue(ffprobe.isFFprobe());

            log.debug("videoFileUtils init complete.");
        } catch (Exception e) {
            log.debug("videoFileUtils init fail.", e);
        }
    }

    public void getMediaInfo(String filePath) throws IOException {
        FFmpegProbeResult probeResult = ffprobe.probe(filePath);

        if(log.isDebugEnabled()) {
            log.debug("========== VideoFileUtils.getMediaInfo() ==========");
            log.debug("filename : {}", probeResult.getFormat().filename);
            log.debug("format_name : {}", probeResult.getFormat().format_name);
            log.debug("format_long_name : {}", probeResult.getFormat().format_long_name);
            log.debug("tags : {}", probeResult.getFormat().tags.toString());
            log.debug("duration : {} second", probeResult.getFormat().duration);
            log.debug("size : {} byte", probeResult.getFormat().size);
            log.debug("width : {} px", probeResult.getStreams().get(0).width);
            log.debug("height : {} px", probeResult.getStreams().get(0).height);
            log.debug("===================================================");
        }
    }

    public void createThumbnail(String filePath, String thumbnailPath) {
        FFmpegBuilder builder = new FFmpegBuilder()
                .overrideOutputFiles(true)  // 오버라이드 여부
                .setInput(filePath) // 썸네일 생성대상 파일
                .addExtraArgs("-ss", "00:00:05")    // 썸네일 추출 시작점
                .addOutput(thumbnailPath)   // 썸네일 파일의 Path
                .setFrames(100) // 프레임 수
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder).run();
    }
}

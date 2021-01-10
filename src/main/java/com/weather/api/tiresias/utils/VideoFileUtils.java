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
import ws.schild.jave.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@Slf4j
//@Component
public final class VideoFileUtils {

    //    @Value("${key.ffmpeg.path}")    // application.yml 파일에서 프로퍼티로 설정한다.
    private static String FFMPEG_PATH;

    //    @Value("${key.ffmpeg.path}")
    private static String FFPROBE_PATH;

    private static FFmpeg ffmpeg;
    private static FFprobe ffprobe;

    @PostConstruct
    public static void init() {
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

    public static void getMediaInfo(String filePath) throws IOException {
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

    public static void createThumbnail(String filePath, String thumbnailPath) {
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

    public static void convertVideo(String inputPath, String inputFileName, String outputPath, String outputFileName) {
        FFMPEG_PATH = inputPath;    // ex) "C:/Users/Gyu/Desktop";
        FFPROBE_PATH = outputPath;

        try {
            ffmpeg = new FFmpeg(FFMPEG_PATH);
            ffprobe = new FFprobe();

            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(inputFileName)    // Filename, or a FFmpegProbeResult. ex) "file_example_AVI_1280_1_5MG.avi"
                    .overrideOutputFiles(true)  // Override the output if it exists
                    .addOutput(outputFileName)    // Filename for the destination
                        .setFormat("mp4")   // Format is inferred from filename, or can be set
                        .setTargetSize(1447_000)    // Aim for a 250KB file
                        .disableSubtitle()  // No subtiles

                        .setAudioChannels(1)    // Mono audio
                        .setAudioCodec("aac")   // using the aac codec
                        .setAudioSampleRate(48_000) // at 48KHz
                        .setAudioBitRate(32768) // at 32 kbit/s

                        .setVideoCodec("libx264")   // Video using x264
                        .setVideoFrameRate(24, 1)   // at 24 frames per second
                        .setVideoResolution(640, 480)   // at 640x480 resolution

                        .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)   // Allow FFmpeg to use experimental specs
                        .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

            // Run a one-pass encode
            executor.createJob(builder).run();

            // Or run a two-pass encode (which is better quality at the cost of being slower)
//            executor.createTwoPassJob(builder).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void convertVideo2() {
        File source = new File("C:/Users/Gyu/Desktop/transcoding/originVideos/file_example_AVI_1280_1_5MG.avi");
        File target = new File("C:/Users/Gyu/Desktop/transcoding/convertedVideos/result.mp4");

//        System.out.println("pre-conversion path:" + oldPath);
//        System.out.println("converted path:" + newPath);

        AudioAttributes audio = new AudioAttributes();
        audio.setCodec ( "libmp3lame"); // audio coding format
        audio.setBitRate(800000);
        audio.setChannels(1);
        //audio.setSamplingRate(new Integer(22050));

        VideoAttributes video = new VideoAttributes();
        video.setCodec ( "libx264"); // video encoding format
        video.setBitRate(3200000);
        video.setFrameRate (15); // small digital set, the video will Caton

        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp4");
        attrs.setAudioAttributes(audio);
        attrs.setVideoAttributes(video);
        Encoder encoder = new Encoder();
        MultimediaObject multimediaObject = new MultimediaObject(source);

        try {
            System.out.println("avi conversion start switch MP4 ---:" + new Date());
            encoder.encode(multimediaObject, target, attrs);
            System.out.println("avi switch MP4 --- End Conversion:" + new Date ());
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InputFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (EncoderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

package com.baizhi.util;


import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.IOException;


public class VideoUtil {


    /**
     * 获取视频文件的播放长度(mp4、mov格式)
     * @param videoPath
     * @return 单位为毫秒
     */



    /**
     * 得到语音或视频文件时长,单位秒
     * @param filePath
     * @return
     * @throws IOException
     */
    public static long getDuration(String filePath) throws IOException {
        String format = getVideoFormat(filePath);
        long result = 0;
        if("wav".equals(format)){
            result = AudioUtil.getDuration(filePath).intValue();
        }else if("mp3".equals(format)){
            result = AudioUtil.getMp3Duration(filePath).intValue();
        }

        return result;
    }

    /**
     * 得到语音或视频文件时长,单位秒
     * @param filePath
     * @return
     * @throws IOException
     */
    public static long getDuration(String filePath,String format) throws IOException {
        long result = 0;
        if("wav".equals(format)){
            result = AudioUtil.getDuration(filePath).intValue();
        }else if("mp3".equals(format)){
            result = AudioUtil.getMp3Duration(filePath).intValue();
        }

        return result;
    }


    /**
     * 得到文件格式
     * @param path
     * @return
     */
    public static String getVideoFormat(String path){
        return  path.toLowerCase().substring(path.toLowerCase().lastIndexOf(".") + 1);
    }


}



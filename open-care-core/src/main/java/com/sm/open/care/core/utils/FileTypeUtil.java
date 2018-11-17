package com.sm.open.care.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: FileTypeUtil
 * @Description: 简单文件类型工具类
 * @Author yangtongbin
 * @Date 2018/10/7
 */
public class FileTypeUtil {

    /**
     * 图片类型
     */
    private static List<String> imgList = Arrays.asList(
            "bmp", "jpg", "jpeg", "png", "tiff", "gif", "pcx",
            "tga", "exif", "fpx", "svg", "psd", "cdr", "pcd",
            "dxf", "ufo", "eps", "ai", "raw", "wmf"
    );
    /**
     * 文档类型
     */
    private static List<String> documentList = Arrays.asList(
            "txt", "doc", "docx", "xls", "xlsx", "htm", "html", "jsp", "rtf", "wpd", "pdf", "ppt");

    /**
     * 视频类型
     */
    private static List<String> videoList = Arrays.asList(
            "mp4", "avi", "mov", "wmv", "asf", "navi", "3gp", "mkv", "f4v", "rmvb", "webm");

    /**
     * 音频类型
     */
    private static List<String> audioList = Arrays.asList(
            "mp3", "wma", "wav", "mod", "ra", "cd", "md", "asf",
            "aac", "vqf", "ape", "mid", "ogg", "m4a", "vqf");

    public static String getFileTypeEnum(String fileType) {
        if (StringUtils.isBlank(fileType)) {
            return null;
        }
        fileType = fileType.toLowerCase();
        if (imgList.contains(fileType)) {
            return FileTypeEnum.IMG.getCode();
        } else if (audioList.contains(fileType)) {
            return FileTypeEnum.AUDIO.getCode();
        } else if (videoList.contains(fileType)) {
            return FileTypeEnum.VIDEO.getCode();
        } else if (documentList.contains(fileType)) {
            return FileTypeEnum.DOCUMENT.getCode();
        } else {
            return FileTypeEnum.OTHER.getCode();
        }
    }

    public enum FileTypeEnum {
        IMG("1", "图片"),
        AUDIO("2", "音频"),
        VIDEO("3", "视频"),
        DOCUMENT("4", "文档"),
        OTHER("9", "其它");

        private String code;
        private String name;

        private FileTypeEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}

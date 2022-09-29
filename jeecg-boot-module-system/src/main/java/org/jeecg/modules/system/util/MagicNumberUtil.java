package org.jeecg.modules.system.util;

import java.util.HashMap;
import java.util.Map;

/**
 *@discrption:魔数工具类
 */
public class MagicNumberUtil {

    /**
     * 图片的魔数值
     */
    private final static Map<String, String> IMAGE_TYPE = new HashMap(4) {{
        put("89504E470D", "png");
        put("FFd8FFE000", "jpg");
        put("89504E47", "png");
        put("FFd8FF", "jpg");
        put("52494646", "jpg");
        put("89504E47", "png");
    }};

    private final static Map<String, String> VIDEO_TYPE = new HashMap(2) {{
        put("000000", "mp4");
    }};
    private final static Map<String, String> AUDIO_TYPE = new HashMap(2) {{
        put("232141", "mp3");
        put("7B2265", "amr");
    }};
    private final static Map<String, String> text_TYPE = new HashMap(2) {{
        put("D0CF11E0", "doc");
        put("D0CF11E0", "xls");

        put("25504446", "pdf");
        put("504B0304", "docx");
        put("504B0304", "xlsx");

        put("75736167", "txt");
    }};
    //取16位的编码
    public static String getHex(byte[] data,int magicNumberLength){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <magicNumberLength/2; i++) {
            sb.append(Integer.toHexString(data[i] >> 4 & 0xF));
            sb.append(Integer.toHexString(data[i] & 0xF));
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 是否是视频格式
     * @param data
     * @return
     */
    public static boolean isVideoFormat(byte[] data){
        //默认取10位作为魔数值
        return VIDEO_TYPE.keySet().contains(getHex(data,6));
    }

    /**
     * 是否是音频
     * @param data
     * @return
     */
    public static boolean isAudio(byte[] data) {
        return isAudio(getHex(data, 6));
    }

    /**
     * 是否是音频 重载
     * @param hex
     * @return
     */
    public static boolean isAudio(String hex) {
        return AUDIO_TYPE.keySet().contains(hex);
    }
    /**
     * 是否是音视频
     * @param hex
     * @return
     */
    public static boolean isVideo(String hex) {
        return VIDEO_TYPE.keySet().contains(hex);
    }
    /**
     * 是否是图片
     * @param hex
     * @return
     */
    public static boolean isImg(String hex) {
        return IMAGE_TYPE.keySet().contains(hex);
    }

    /**
     * 是否是文档
     * @param hex
     * @return
     */
    public static boolean isText(String hex) {
        return text_TYPE.keySet().contains(hex);
    }

    //取图片后缀
    public static String getSuffix(String hex) {
        if (IMAGE_TYPE.containsKey(hex)) {
            return IMAGE_TYPE.get(hex);
        }
        return null;
    }

    //取音频后缀
    public static String getAudioSuffix(String hex) {
        if (AUDIO_TYPE.containsKey(hex)) {
            return AUDIO_TYPE.get(hex);
        }
        return null;
    }
}


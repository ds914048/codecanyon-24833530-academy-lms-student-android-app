package com.creativeitem.academy.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoConfig {
    public VideoConfig() {
    }

    private static final String API_KEY = "AIzaSyDHaVV-OW5PQ6HKtvlkh9H45uh2nnxZ6qE";

    public static String getApiKey() {
        return API_KEY;
    }

    /*public static String extractYoutubeId(String videoUrl) {

        String video_id = "";
        if (videoUrl != null && videoUrl.trim().length() > 0) {
            String expression = "^.*((youtu.be" + "\\/)" + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*";
            CharSequence input = videoUrl;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                String groupIndex1 = matcher.group(7);
                if (groupIndex1 != null && groupIndex1.length() == 11)
                    video_id = groupIndex1;
            }
        }

        return video_id;
    }*/

//    public static String extractYoutubeId(String videoUrl) {
//        String vId = null;
//        Pattern pattern = Pattern.compile(
//                "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
//                Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(videoUrl);
//        if (matcher.matches()){
//            vId = matcher.group(1);
//        }
//        return vId;
//    }

    public static String extractVimeoId(String videoUrl) {
        Pattern pattern = Pattern.compile("https?://(?:www\\.|player\\.)?vimeo.com/(?:channels/(?:\\w+/)?|groups/([^/]*)/videos/|album/(\\d+)/video/|video/|)(\\d+)(?:$|/|\\?)");
        Matcher matcher = pattern.matcher(videoUrl);
        matcher.find();
        return matcher.group(3);
    }


    static String youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";
    static String[] videoIdRegex = { "\\?vi?=([^&]*)","watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)"};
    public static String extractYoutubeId(String url) {
        String youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url);

        for(String regex : videoIdRegex) {
            Pattern compiledPattern = Pattern.compile(regex);
            Matcher matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain);

            if(matcher.find()){
                return matcher.group(1);
            }
        }

        return null;
    }

    public static String youTubeLinkWithoutProtocolAndDomain(String url) {
        Pattern compiledPattern = Pattern.compile(youTubeUrlRegEx);
        Matcher matcher = compiledPattern.matcher(url);

        if(matcher.find()){
            return url.replace(matcher.group(), "");
        }
        return url;
    }
}

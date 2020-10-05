package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class JsonUtil {

    public static String toPrettyJson(Tweet postTweet) throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        m.enable(SerializationFeature.INDENT_OUTPUT);
        return m.writeValueAsString(postTweet);
    }

    public static Tweet toObjectFromJson(String jsonStr, Class<Tweet> tweetClass) throws IOException {
        ObjectMapper m = new ObjectMapper();
        m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return (Tweet) m.readValue(jsonStr, tweetClass);
    }
}

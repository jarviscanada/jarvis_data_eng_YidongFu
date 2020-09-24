package ca.jrvs.apps.twitter.dao;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.net.URI;

public class TwitterHttpHelperTest {

    @Test
    public void httpGet() throws Exception{
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");
        System.out.println(CONSUMER_KEY+"|"+CONSUMER_SECRET+"|"+ACCESS_TOKEN+"|"+TOKEN_SECRET);
        // Create components
        HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
        HttpResponse response = httpHelper.httpGet(new URI("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=maskedventures"));
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}
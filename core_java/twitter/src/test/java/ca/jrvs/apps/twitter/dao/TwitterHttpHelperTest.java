package ca.jrvs.apps.twitter.dao;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.net.URI;

public class TwitterHttpHelperTest {

    @Test
    public void httpGet() throws Exception{
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        System.out.println(consumerKey+"|"+consumerSecret+"|"+accessToken+"|"+tokenSecret);
        // Create components
        HttpHelper httpHelper = new TwitterHttpHelper();
        HttpResponse response = httpHelper.httpGet(new URI("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=maskedventures"));
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}
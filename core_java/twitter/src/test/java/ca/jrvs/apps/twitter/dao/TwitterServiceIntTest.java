package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TwitterServiceIntTest {

    private TwitterService service;
    private TwitterDao dao;

    @Before
    public void setUp() throws Exception {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        System.out.println(consumerKey+"|"+consumerSecret+"|"+accessToken+"|"+tokenSecret);
        //set up dependency
        HttpHelper httpHelper = new TwitterHttpHelper();
        //pass dependency
        this.dao = new TwitterDao(httpHelper);
        this.service = new TwitterService(dao);
    }

    private final String testTweetStr =  "{\n" +
            "    \"created_at\": \"Thu Sep 24 14:23:29 +0000 2020\",\n" +
            "    \"id\": 1309136378509303809,\n" +
            "    \"id_str\": \"1309136378509303809\",\n" +
            "    \"text\": \"so008411\",\n" +
            "    \"truncated\": false,\n" +
            "    \"entities\": {\n" +
            "        \"hashtags\": [],\n" +
            "        \"symbols\": [],\n" +
            "        \"user_mentions\": [],\n" +
            "        \"urls\": []\n" +
            "    },\n" +
            "    \"source\": \"<a href=\\\"https://postmanexample.com\\\" rel=\\\"nofollow\\\">Yidong</a>\",\n" +
            "    \"in_reply_to_status_id\": null,\n" +
            "    \"in_reply_to_status_id_str\": null,\n" +
            "    \"in_reply_to_user_id\": null,\n" +
            "    \"in_reply_to_user_id_str\": null,\n" +
            "    \"in_reply_to_screen_name\": null,\n" +
            "    \"user\": {\n" +
            "        \"id\": 1293665654113996801,\n" +
            "        \"id_str\": \"1293665654113996801\",\n" +
            "        \"name\": \"Yidong Fu\",\n" +
            "        \"screen_name\": \"YidongFu\",\n" +
            "        \"location\": \"\",\n" +
            "        \"description\": \"\",\n" +
            "        \"url\": null,\n" +
            "        \"entities\": {\n" +
            "            \"description\": {\n" +
            "                \"urls\": []\n" +
            "            }\n" +
            "        },\n" +
            "        \"protected\": false,\n" +
            "        \"followers_count\": 0,\n" +
            "        \"friends_count\": 0,\n" +
            "        \"listed_count\": 0,\n" +
            "        \"created_at\": \"Wed Aug 12 21:48:30 +0000 2020\",\n" +
            "        \"favourites_count\": 0,\n" +
            "        \"utc_offset\": null,\n" +
            "        \"time_zone\": null,\n" +
            "        \"geo_enabled\": true,\n" +
            "        \"verified\": false,\n" +
            "        \"statuses_count\": 37,\n" +
            "        \"lang\": null,\n" +
            "        \"contributors_enabled\": false,\n" +
            "        \"is_translator\": false,\n" +
            "        \"is_translation_enabled\": false,\n" +
            "        \"profile_background_color\": \"F5F8FA\",\n" +
            "        \"profile_background_image_url\": null,\n" +
            "        \"profile_background_image_url_https\": null,\n" +
            "        \"profile_background_tile\": false,\n" +
            "        \"profile_image_url\": \"http://abs.twimg.com/sticky/default_profile_images/default_profile_normal.png\",\n" +
            "        \"profile_image_url_https\": \"https://abs.twimg.com/sticky/default_profile_images/default_profile_normal.png\",\n" +
            "        \"profile_link_color\": \"1DA1F2\",\n" +
            "        \"profile_sidebar_border_color\": \"C0DEED\",\n" +
            "        \"profile_sidebar_fill_color\": \"DDEEF6\",\n" +
            "        \"profile_text_color\": \"333333\",\n" +
            "        \"profile_use_background_image\": true,\n" +
            "        \"has_extended_profile\": true,\n" +
            "        \"default_profile\": true,\n" +
            "        \"default_profile_image\": true,\n" +
            "        \"following\": false,\n" +
            "        \"follow_request_sent\": false,\n" +
            "        \"notifications\": false,\n" +
            "        \"translator_type\": \"none\"\n" +
            "    },\n" +
            "    \"geo\": {\n" +
            "        \"type\": \"Point\",\n" +
            "        \"coordinates\": [\n" +
            "            1.0,\n" +
            "            -1.0\n" +
            "        ]\n" +
            "    },\n" +
            "    \"coordinates\": {\n" +
            "        \"type\": \"Point\",\n" +
            "        \"coordinates\": [\n" +
            "            -1.0,\n" +
            "            1.0\n" +
            "        ]\n" +
            "    },\n" +
            "    \"place\": null,\n" +
            "    \"contributors\": null,\n" +
            "    \"is_quote_status\": false,\n" +
            "    \"retweet_count\": 0,\n" +
            "    \"favorite_count\": 0,\n" +
            "    \"favorited\": false,\n" +
            "    \"retweeted\": false,\n" +
            "    \"lang\": \"und\"\n" +
            "}";
    @Test
    public void postTweet() throws IOException {
        Tweet testTweet = JsonUtil.toObjectFromJson(testTweetStr, Tweet.class);

        Tweet tweet = service.postTweet(testTweet);

        assertEquals(tweet.getText(), testTweet.getText());
        assertNotNull(tweet.getCoordinates());
    }

    @Test
    public void showTweet() throws URISyntaxException {
        String id = "210462857140252672";

        Tweet tweet = service.showTweet(id, null);

        assertEquals(tweet.getIdStr(), id);
    }

    @Test
    public void deleteTweets() throws URISyntaxException {
        String[] ids = new String[2];

        ids[0] = "1309201345266831366";
        ids[1] = "1309201452896858113";

        List<Tweet> tweets = service.deleteTweets(ids);

        assertEquals(tweets.get(0).getIdStr(), ids[0]);
        assertEquals(tweets.get(1).getIdStr(), ids[1]);
    }
}
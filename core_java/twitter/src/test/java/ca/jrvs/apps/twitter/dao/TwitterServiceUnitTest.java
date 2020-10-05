package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

    @Mock
    CrdDao dao;

    @InjectMocks
    TwitterService service;

    @Test
    public void postTweet() throws IOException {
        when(dao.create(any())).thenReturn(new Tweet());
        service.postTweet(TweetUtil.buildTweet("test", 1.0, 5.0));
    }

    @Test
    public void showTweet() throws URISyntaxException {
        when(dao.create(any())).thenReturn(new Tweet());

        service.showTweet("1308452127627513857", null);
    }

    @Test
    public void deleteTweets() throws URISyntaxException {
        when(dao.create(any())).thenReturn(new Tweet());
        String[] deleteStrings = new String[2];

        deleteStrings[0] = "1308452127627513857";
        deleteStrings[1] = "1309136378509303809";

        service.deleteTweets(deleteStrings);
    }
}
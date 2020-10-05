package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@org.springframework.stereotype.Service

public class TwitterService implements Service {

    private CrdDao dao;

    @Autowired
    public TwitterService(CrdDao dao) {
        this.dao = dao;
    }

    @Override
    public Tweet postTweet(Tweet tweet) throws IllegalArgumentException{
        validatePostTweet(tweet);
        return (Tweet) dao.create(tweet);
    }

    private void validatePostTweet(Tweet tweet) throws IllegalArgumentException {
        if (countWords(tweet.getText()) > 140){
            throw new IllegalArgumentException("Tweet exceeds 140 words");
        }

        if (tweet.getCoordinates().getCoordinates().get(0)  > 90 ) {
            throw new IllegalArgumentException("lon out of range");
        }

        if (tweet.getCoordinates().getCoordinates().get(1) < -90) {
            throw new IllegalArgumentException("lat out of range");
        }
    }


    @Override
    public Tweet showTweet(String id, String[] fields) throws URISyntaxException {
        validateId(id);

        return (Tweet) dao.findById(id);
    }


    @Override
    public List<Tweet> deleteTweets(String[] ids) throws URISyntaxException {
        List deleteTweets = new ArrayList();

        for (String id : ids){
            validateId(id);
            deleteTweets.add(dao.deleteById(id));
        }

        return deleteTweets;
    }

    private void validateId(String id) {
        for (char c : id.toCharArray())
        {
            if (!Character.isDigit(c)) {
                throw new IllegalArgumentException("Invalid ID");
            }
        }

    }

    public static int countWords(String text) {
        if (text == null || text.isEmpty()) { return 0; }

        StringTokenizer tokens = new StringTokenizer(text);
        return tokens.countTokens();
    }
}

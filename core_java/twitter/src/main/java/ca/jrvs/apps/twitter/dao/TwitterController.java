package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static ca.jrvs.apps.twitter.dao.TweetUtil.buildTweet;

public class TwitterController implements Controller {

    private static final String COORD_SEP = ":";
    private static final String COMMA = ",";

    private Service service;

    public TwitterController(Service service) {
        this.service = service;
    }

    /**
     * Parse user argument and post a tweet by calling service classes
     *
     * @param args
     * @return a posted tweet
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public Tweet postTweet(String[] args) throws IOException {
        if (args.length != 3){
            throw new IllegalArgumentException(
                    "USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
        }

        String tweet_txt = args[1];
        String coord = args[2];
        String[] coordArray = coord.split(COORD_SEP);

        if (coordArray.length != 2 || StringUtils.isEmpty(tweet_txt)) {
            throw new IllegalArgumentException(
                    "Invalid location format\nUSAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
        }

        Double lat = null;
        Double lon = null;

        try{
            lat = Double.parseDouble(coordArray[0]);
            lon = Double.parseDouble(coordArray[1]);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid location format\nUSAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"",
                    e);
        }

        Tweet postTweet = buildTweet(tweet_txt, lon, lat);
        return service.postTweet(postTweet);
    }

    /**
     * Parse user argument and search a tweet by calling service classes
     *
     * @param args
     * @return a tweet
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public Tweet showTweet(String[] args) throws URISyntaxException {
        if (args.length != 2){
            throw new IllegalArgumentException(
                    "USAGE: TwitterCLIApp show \"tweet_id\" ");
        }

        return service.showTweet(args[1], null);
    }

    /**
     * Parse user argument and delete tweets by calling service classes
     *
     * @param args
     * @return a list of deleted tweets
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public List<Tweet> deleteTweet(String[] args) throws URISyntaxException {
        if (args.length != 2){
            throw new IllegalArgumentException(
                    "USAGE: TwitterCLIApp delete \"tweet_id\" ");
        }

        return service.deleteTweets(args[1].split(COMMA));
    }
}

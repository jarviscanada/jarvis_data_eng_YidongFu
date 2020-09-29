package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;

import java.util.ArrayList;

public class TweetUtil {

    public static Tweet buildTweet(String text, Double lon, Double lat) {
        Tweet tweet = new Tweet();

        Coordinates coordinates = new Coordinates();

        ArrayList<Double> cd = new ArrayList<Double>();
        cd.add(lon);
        cd.add(lat);

        coordinates.setCoordinates(cd);

        tweet.setText(text);
        tweet.setCoordinates(coordinates);

        return tweet;
    }
}

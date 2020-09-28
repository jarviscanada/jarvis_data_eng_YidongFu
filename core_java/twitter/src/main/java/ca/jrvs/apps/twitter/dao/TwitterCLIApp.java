package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.URISyntaxException;

public class TwitterCLIApp {

    public static final String USAGE = "USAGE: TwitterCLIApp post|show|delete [options]";

    private Controller controller;

    public TwitterCLIApp(Controller controller) {
        this.controller = controller;
    }
    public static void main(String[] args) throws IOException, URISyntaxException {
        //Get secrets from env vars
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        //Create components and chain dependencies
        HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
        CrdDao dao = new TwitterDao(httpHelper);
        Service service = new TwitterService(dao);
        Controller controller = new TwitterController(service);
        TwitterCLIApp app = new TwitterCLIApp(controller);

        //start app
        app.run(args);
    }

    private void run(String[] args) throws IOException, URISyntaxException {
        if (args.length == 0) {
            throw new IllegalArgumentException(USAGE);
        }
        switch (args[0].toLowerCase()){
            case "post":
                printTweet(controller.postTweet(args));
                break;
            case "show":
                printTweet(controller.showTweet(args));
                break;
            case "delete":
                controller.deleteTweet(args).forEach(this::printTweet);
                break;
            default:
                throw new IllegalArgumentException(USAGE);
        }
    }

    private void printTweet(Tweet tweet) {
        try{
            System.out.println(JsonUtil.toPrettyJson(tweet));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to convert tweet object to string", e);
        }
    }
}

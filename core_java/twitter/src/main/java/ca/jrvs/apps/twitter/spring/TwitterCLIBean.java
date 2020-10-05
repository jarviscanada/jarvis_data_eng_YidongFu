package ca.jrvs.apps.twitter.spring;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.dao.*;
import ca.jrvs.apps.twitter.service.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.net.URISyntaxException;

//@Configuration
public class TwitterCLIBean {

    public static void main(String[] args) throws IOException, URISyntaxException {
        ApplicationContext context = new AnnotationConfigApplicationContext(TwitterCLIBean.class);
        TwitterCLIApp app = context.getBean(TwitterCLIApp.class);
        app.run(args);
    }

    @Bean
    public TwitterCLIApp twitterCLIApp (Controller controller) {
        return new TwitterCLIApp(controller);
    }

    @Bean
    public Controller controller(Service service) {
        return new TwitterController(service);
    }

    @Bean
    public Service service(CrdDao dao) {
        return new TwitterService(dao);
    }

    @Bean
    public CrdDao crdDao (HttpHelper httpHelper) {
        return new TwitterDao(httpHelper);
    }

    @Bean
    HttpHelper helper() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        return new TwitterHttpHelper();
    }

}

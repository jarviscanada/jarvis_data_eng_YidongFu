package ca.jrvs.apps.twitter.model;

public class Entities {
    private Hashtag hashTag;
    private UserMention userMention;

    public Hashtag getHashTag() {
        return hashTag;
    }

    public void setHashTag(Hashtag hastTag) {
        this.hashTag = hastTag;
    }

    public UserMention getUserMention() {
        return userMention;
    }

    public void setUserMention(UserMention userMention) {
        this.userMention = userMention;
    }
}

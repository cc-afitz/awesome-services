package de.codecentric.awesome.recommendation.core;

/**
 * Created by afitz on 15.03.16.
 */
public class User {
    private String id;

    public User(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

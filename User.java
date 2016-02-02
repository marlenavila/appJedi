package com.example.marlen.appjedi;

/**
 * Created by marlen on 02/02/2016.
 */
public class User {
    private String name;
    private String password;
    private Integer points;

    User(String name, String password, int points){
        this.name = name;
        this.password = password;
        this.points = points;
    }

    User(){}

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getPassword(){ return password; }
    public void setPassword(String password){ this.password = password; }

    public Integer getPoints(){ return points; }
    public void setPoints(int points){ this.points = points; }
}
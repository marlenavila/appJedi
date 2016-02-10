package com.example.marlen.appjedi;

/**
 * Created by marlen on 02/02/2016.
 */
public class User {
    private String name;
    private String password;
    private Integer points;
    //private String image;

    User(String name, String password, int points){
        this.name = name;
        this.password = password;
        this.points = points;
        //this.image = image;
    }

    User(){}

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getPassword(){ return password; }
    public void setPassword(String password){ this.password = password; }

    public Integer getPoints(){ return points; }
    public void setPoints(int points){ this.points = points; }


    //TODO en teoria el plan era trabajar todo el rato con strings i luego pasar las imagenes a uri
    //TODO pero parece que no está bien o algo como ya habréis podido ver..
    //public String getImage(){ return image; }
    //public void setImage(String image){ this.image = image; }
}
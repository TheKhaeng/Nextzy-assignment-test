package com.example.thekhaeng.androidpokemongochallenge.http.dao;

import lombok.ToString;

/**
 * Created by TheKhaeng on 9/5/2016.
 */

@ToString
public class MessageDao{
    private String message;

    public MessageDao(){

    }

    public MessageDao( String msg){
       message = msg;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage( String message ){
        this.message = message;
    }
}

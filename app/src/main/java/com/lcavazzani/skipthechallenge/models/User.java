package com.lcavazzani.skipthechallenge.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by leonardoCavazzani on 3/20/18.
 */


public class User {

    private int credits = 0;

    private String jsonSug = "";

    private ArrayList<String> mylist = new ArrayList<String>();

    private JSONObject mUserJson;
    private String mUserId = "";

    public JSONArray getmCart() {
        return mCart;
    }

    public void setmCart(JSONArray mCart) {
        this.mCart = mCart;
    }
    public void addTomCart(JSONObject object){
        this.mCart.put(object);
    }

    private JSONArray mCart = new JSONArray();


    private String mUserToken = "";
    private String mUserEmail = "";


    public void userReset(){

        this.credits = 0;
        this.jsonSug = "";
        this.mylist = new ArrayList<String>();
        this.mUserId = "";
        this.mUserToken = "";
    }

    public JSONObject getmUserJson() {
        return mUserJson;
    }

    public void setmUserJson(JSONObject mUserJson) {
        this.mUserJson = mUserJson;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public String getmUserToken() {
        return mUserToken;
    }

    public void setmUserToken(String mUserToken) {
        this.mUserToken = mUserToken;
    }

    private static final User usuario = new User();

    public static User getInstance() {
        return usuario;
    }

    public User() {
    }

    public User(JSONObject userJson) {
        try {
            mUserJson = new JSONObject((Map) userJson);
            mUserId = mUserJson.getString("id");
            mUserToken = mUserJson.getString("ahazou_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void addCredits(int finalCreditos) {
        this.credits += finalCreditos;
    }

    public String getJsonSug() {
        return jsonSug;
    }

    public void setJsonSug(String jsonSug) {
        this.jsonSug = jsonSug;
    }


    public ArrayList<String> getMylist() {
        return mylist;
    }

    public void setMylist(ArrayList<String> mylist) {
        this.mylist = mylist;
    }

    public void addMyList(String unlock){
        mylist.add(unlock);
    }
    public void newMyList(JSONArray jason) throws JSONException {
        if (jason != null) {
            for (int i=0;i<jason.length();i++){
                mylist.add(jason.getString(i));
            }
        }
    }


    public String getmUserEmail() {
        return mUserEmail;
    }

    public void setmUserEmail(String mUserEmail) {
        this.mUserEmail = mUserEmail;
    }

}

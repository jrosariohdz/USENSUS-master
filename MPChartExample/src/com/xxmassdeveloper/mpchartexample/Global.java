package com.xxmassdeveloper.mpchartexample;

import retrofit.RestAdapter;

public class Global {
    String URL_SERVIDOR = "http://192.168.0.1/";
    RestAdapter restadpter = new RestAdapter.Builder().setEndpoint(URL_SERVIDOR).build();

    public RestAdapter getRestadpter() {
        return restadpter;
    }

    public String getURL_SERVIDOR() {
        return URL_SERVIDOR;
    }
    public void setURL_SERVIDOR(String URL_SERVIDOR) {
        this.URL_SERVIDOR = URL_SERVIDOR;
    }


}

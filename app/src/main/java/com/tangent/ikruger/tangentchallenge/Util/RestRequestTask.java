package com.tangent.ikruger.tangentchallenge.Util;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan Kruger on 2016-10-29.
 */

public class RestRequestTask extends AsyncTask<URL, Void, String> {

    private List<RestResult> interfaces;

    private String METHOD = "GET";
    private String URL = "";
    private String CONTENT_TYPE = "application/json";
    private String DATA = "";

    private URL RestURL;

    private boolean RestRequestFailed = false;

    public RestRequestTask (String url, String method, String contentType, String data) {
        this.URL = url;
        this.METHOD = method;
        this.CONTENT_TYPE = contentType;
        this.DATA = data;

        interfaces = new ArrayList<>();

        try {

            RestURL = new URL(this.URL);

        }catch (MalformedURLException ex){
            this.RestRequestFailed = true;
            for (RestResult r : interfaces){
                r.onRestError(ex.getLocalizedMessage());
            }
        }
    }

    public RestRequestTask (String url, String method, String data) {
        this(url, method, "application/json", data);
    }

    public RestRequestTask (String url, String data) {
        this(url, "GET", "application/json", data);
    }

    public String getMETHOD() {
        return METHOD;
    }

    public void setMETHOD(String METHOD) {
        this.METHOD = METHOD;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getCONTENT_TYPE() {
        return CONTENT_TYPE;
    }

    public void setCONTENT_TYPE(String CONTENT_TYPE) {
        this.CONTENT_TYPE = CONTENT_TYPE;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    @Override
    protected String doInBackground(URL... params) {
        if (RestURL == null) {
            this.RestRequestFailed = true;
            return "Missing URL";
        }

        try {
            HttpURLConnection connection = (HttpURLConnection) this.RestURL.openConnection();
            connection.setRequestMethod(this.METHOD);
            connection.setRequestProperty("Content-Type",this.getCONTENT_TYPE());
            connection.setDoOutput(true);

            OutputStreamWriter dataWriter = new OutputStreamWriter(connection.getOutputStream());

            dataWriter.write(this.DATA);

            dataWriter.flush();

            dataWriter.close();

            if(connection.getResponseCode() == 200){

                String Result = convertStreamToString(connection.getInputStream());
                connection.getInputStream().close();
                connection.disconnect();

                return Result;

            }else{

                this.RestRequestFailed = true;

                String Error = convertStreamToString(connection.getErrorStream());
                connection.getErrorStream().close();
                connection.disconnect();

                return Error;
            }

        } catch (IOException ioEx ){
            this.RestRequestFailed = true;
            return ioEx.getLocalizedMessage();
        } catch (Exception ex){
            this.RestRequestFailed = true;
            return ex.getLocalizedMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(this.RestRequestFailed){
            for (RestResult r : interfaces){
                r.onRestError(s);
            }
        }else{
            for (RestResult r : interfaces){
                r.onRestResult(s);
            }
        }

    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    public void registerRestResultListener(RestResult restResult){
        interfaces.add(restResult);
    }

    public boolean deregisterRestResultListener(RestResult restResult){
        return interfaces.remove(restResult);
    }

    private String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public interface RestResult {

        void onRestResult (String result);

        void onRestError (String error);

    }

}

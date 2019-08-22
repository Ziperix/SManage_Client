package helpers;

import java.io.*;
import java.net.*;
import org.json.*;
import org.apache.http.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


public class API
{
    public static boolean TestConnection(String address, String port)
    {
        try
        {
            String url = "http://" + address + ":" + port + "/api/test";
            URL getUrl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) getUrl.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public static JSONObject ApiLogin(String username, String password) throws FileNotFoundException
    {
        Config config = new Config();
        try
        {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(String.format("http://%s:%s/api/user/login", config.getAddress(), config.getPort()));

            StringEntity jsonPost = new StringEntity(String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password));
            jsonPost.setContentType("application/json");
            postRequest.setEntity(jsonPost);

            HttpResponse httpResponse = httpClient.execute(postRequest);

            if (httpResponse.getStatusLine().getStatusCode() != 200 && httpResponse.getStatusLine().getStatusCode() != 400)
            {
                return new JSONObject(String.format("{\"code\": \"unknown\", \"reason\": \"Server responded with error %s\"}", httpResponse.getStatusLine().getStatusCode()));
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((httpResponse.getEntity().getContent())));

            httpClient.getConnectionManager().shutdown();

            return new JSONObject(bufferedReader.readLine());
        }
        catch (MalformedURLException e)
        {
            return new JSONObject("{\"code\": \"E9\", \"reason\": \"MalformedURLException, check your server configuration\"}");
        }
        catch (IOException e)
        {
            return new JSONObject("{\"code\": \"E8\", \"reason\": \"Connection failed, check your server configuration\"}");
        }
    }
}
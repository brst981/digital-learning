package app.com.digitallearning.WebServices;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author Shalvi Sharma
 */
public class WSAdapter {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    public static String getJSONObject(String url) {


        // Making HTTP request
        try {
            // defaultHttpClient
            HttpParams httpParams = new BasicHttpParams();
            int some_reasonable_timeout = (int) (70 * 1000);
            HttpConnectionParams.setConnectionTimeout(httpParams,
                    some_reasonable_timeout);
            HttpConnectionParams.setSoTimeout(httpParams,
                    some_reasonable_timeout);
            HttpClient httpclient = new DefaultHttpClient();
            @SuppressWarnings("unused")
            HttpContext localContext = new BasicHttpContext();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = null;
            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("Exception=======", "" + e.getMessage());
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "");
            }
            is.close();
            // .d("value response", sb.toString());
            json = sb.toString();
            // Log.d("String Response", sb.toString());
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        // try {
        // jObj = new JSONObject(json);
        // } catch (JSONException e) {
        // Log.e("JSON Parser", "Error parsing data " + e.toString());
        // }

        // try parse the string to a JSON object

        // return JSON String
        return json;

    }

    public static String postJSONObject(String url,
                                        List<NameValuePair> namevaluepairs) {
        HttpResponse response;
        try {
            Log.e("String Response?????", "enter this method");
            // defaultHttpClient
            HttpParams httpParams = new BasicHttpParams();
            int some_reasonable_timeout = (int) (70 * 1000);
            HttpConnectionParams.setConnectionTimeout(httpParams,
                    some_reasonable_timeout);
            HttpConnectionParams.setSoTimeout(httpParams,
                    some_reasonable_timeout);
            HttpClient httpclient = new DefaultHttpClient(httpParams);

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(namevaluepairs));
            // HttpResponse response = null;
            response = httpclient.execute(httpPost);

            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "");
            }
            is.close();

            // Log.d("value response", sb.toString());
            json = sb.toString();

        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        // try parse the string to a JSON object
        // return JSON String
        return json;

    }


}

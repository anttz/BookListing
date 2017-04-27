package com.example.android.booklisting;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tzouanakos on 13/03/2017.
 */

public class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getName();

    private static URL createURL(String stringUrl) {
        if (stringUrl == null) {
            return null;
        }
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String makeHTTPBookRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving book JSON results", e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilderOutput = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String stringLine = bufferedReader.readLine();
            while (stringLine != null) {
                stringBuilderOutput.append(stringLine);
                stringLine = bufferedReader.readLine();
            }
        }
        return stringBuilderOutput.toString();
    }

    private static List<Book> extractFromJSON(String bookJSONResponse) {
        if (TextUtils.isEmpty(bookJSONResponse)) {
            return null;
        }
        List<Book> booksList = new ArrayList<>();
        try {
            JSONObject baseJSONObject = new JSONObject(bookJSONResponse);
            JSONArray booksArray = baseJSONObject.getJSONArray("items");
            String title = null;
            JSONArray authorJSONArray = null;
            for (int i = 0; i < booksArray.length(); i++) {
                JSONObject currentBook = booksArray.getJSONObject(i);
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");
                String infoLink = null;
                if (volumeInfo.has("title")) {
                    title = volumeInfo.getString("title");
                }
                if (volumeInfo.has("authors")) {
                    authorJSONArray = volumeInfo.getJSONArray("authors");
                }
                String author = authorJSONArray.getString(0);
                if (volumeInfo.has("infoLink")) {
                    infoLink = volumeInfo.getString("infoLink");
                }
                Book book = new Book(title, author, infoLink);
                booksList.add(book);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON response ", e);
        }
        return booksList;
    }

    public static List<Book> fetchBookData(String requestUrl) {
        URL url = createURL(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHTTPBookRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem occured while making HTTP request ", e);
        }
        List<Book> booksList = extractFromJSON(jsonResponse);
        return booksList;
    }
}

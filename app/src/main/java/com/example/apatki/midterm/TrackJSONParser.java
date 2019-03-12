package com.example.apatki.midterm;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TrackJSONParser extends AsyncTask<String, Integer, ArrayList<Track>> {

    IData iData;
    ArrayList<Track> result = new ArrayList<>();

    TrackJSONParser(IData iData) {
        this.iData = iData;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        iData.preProcessing();
    }

    @Override
    protected void onPostExecute(ArrayList<Track> track) {
        super.onPostExecute(track);

        if (track != null) {
            Log.d("PostExecute", track.toString());
            iData.handleListData(track);
            iData.finishProcessing();
        } else
            Log.d("PostExecute", "null Request");

    }

    @Override
    protected ArrayList<Track> doInBackground(String... strings) {
        HttpURLConnection connection = null;


        try {
            Log.d("URL", strings[0]);
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            String json = IOUtils.toString(connection.getInputStream(), "UTF-8");

            JSONObject root = new JSONObject(json);

            JSONObject message = (JSONObject) root.get("message");
            JSONObject body = (JSONObject) message.get("body");
            JSONArray articlesJSON = body.getJSONArray("track_list");

            for (int i = 0; i < articlesJSON.length(); i++) {
                JSONObject articleJson = articlesJSON.getJSONObject(i);
                Track track = new Track();

                JSONObject jsonObject = (JSONObject) articleJson.get("track");
                track.setTrack(jsonObject.getString("track_name"));
                track.setAlbum(jsonObject.getString("album_name"));
                track.setArtist(jsonObject.getString("artist_name"));

                DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
                track.setDate(format.parse(jsonObject.getString("updated_time")));

                track.setUrl(jsonObject.getString("track_share_url"));

                result.add(track);
                publishProgress(i + 1);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }

        return result;
    }

    public static interface IData {
        public void handleListData(ArrayList<Track> data);
        public void preProcessing();
        public void finishProcessing();
    }
}

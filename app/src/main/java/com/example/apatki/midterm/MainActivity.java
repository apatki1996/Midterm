package com.example.apatki.midterm;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

// API key 2fb9ccca553152a75ebbf2c0e9091c49

public class MainActivity extends AppCompatActivity implements TrackJSONParser.IData {

//    URL https://api.musixmatch.com/ws/1.1/track.search?apikey=2fb9ccca553152a75ebbf2c0e9091c49

    EditText editText;
    SeekBar seekBar;
    TextView limit;
    int results_limit;
    boolean rating = true;
    RadioGroup radioGroup;

    ProgressBar progressBar;
    ListView listView;
    ListViewAdapter adapter;

    ArrayList<Track> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_text);
        seekBar = findViewById(R.id.seek_bar);
        limit = findViewById(R.id.text_limit);
        limit.setText("25");

        results_limit = 25;

        listView = findViewById(R.id.listView);

        progressBar = findViewById(R.id.progress_circular);

        seekBar.setProgress(20);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                limit.setText(Integer.toString(progress + 5));
                results_limit = progress + 5;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isConnected()) {

                    String[] words = editText.getText().toString().split(" ");
                    StringBuilder keywords = new StringBuilder();
                    for (String s : words) {
                        keywords.append(s).append("+");
                    }

                    keywords.deleteCharAt(keywords.length() - 1);

                    Log.d("string", keywords.toString());

                    if (rating)
                        new TrackJSONParser(MainActivity.this).execute("https://api.musixmatch.com/ws/1.1/track.search?q=" +
                                keywords.toString() + "&page_size=" +
                                results_limit +
                                "&apikey=2fb9ccca553152a75ebbf2c0e9091c49&s_track_rating=desc");
                    else
                        new TrackJSONParser(MainActivity.this).execute("https://api.musixmatch.com/ws/1.1/track.search?q=" +
                                keywords.toString() + "&page_size=" +
                                results_limit +
                                "&apikey=2fb9ccca553152a75ebbf2c0e9091c49&s_artist_rating=desc");


                } else
                    Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void onRadioButtonClicked(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButton1:
                if(isChecked)
                    rating = true;
//                    Log.d("switch", "radio button 1");
                break;

            case R.id.radioButton2:
                if(isChecked)
                    rating = false;
//                    Log.d("switch", "radio button 2");
                break;
        }
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    @Override
    public void handleListData(ArrayList<Track> data) {
        if (data != null) {
            this.data = data;
            radioGroup = findViewById(R.id.radioGroup);
        } else
            Toast.makeText(MainActivity.this, "No tracks found", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void preProcessing() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishProcessing() {

        progressBar.setVisibility(View.INVISIBLE);
        adapter = new ListViewAdapter(this, R.layout.item, data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = data.get(position).getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton radioButton = findViewById(checkedId);
//                Log.d("checked", "Button checked " + radioButton.getText().toString());
                String[] words = editText.getText().toString().split(" ");
                StringBuilder keywords = new StringBuilder();
                for (String s : words) {
                    keywords.append(s).append("+");
                }

                keywords.deleteCharAt(keywords.length() - 1);

                if (rating)
                    new TrackJSONParser(MainActivity.this).execute("https://api.musixmatch.com/ws/1.1/track.search?q=" +
                            keywords.toString() + "&page_size=" +
                            results_limit +
                            "&apikey=2fb9ccca553152a75ebbf2c0e9091c49&s_track_rating=desc");
                else
                    new TrackJSONParser(MainActivity.this).execute("https://api.musixmatch.com/ws/1.1/track.search?q=" +
                            keywords.toString() + "&page_size=" +
                            results_limit +
                            "&apikey=2fb9ccca553152a75ebbf2c0e9091c49&s_artist_rating=desc");

            }
        });
    }



}


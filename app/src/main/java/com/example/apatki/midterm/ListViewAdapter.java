package com.example.apatki.midterm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter {
    private String pattern = "MM-dd-yyyy";
    private DateFormat dateFormat = new SimpleDateFormat(pattern);

    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Track
        Track track = (Track) getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.textView1 = convertView.findViewById(R.id.item_track);
            viewHolder.textView2 = convertView.findViewById(R.id.item_album);
            viewHolder.textView3 = convertView.findViewById(R.id.item_date);
            viewHolder.textView4 = convertView.findViewById(R.id.item_artist);
            viewHolder.textView5 = convertView.findViewById(R.id.track);
            viewHolder.textView6 = convertView.findViewById(R.id.artist);
            viewHolder.textView7 = convertView.findViewById(R.id.price);
            viewHolder.textView8 = convertView.findViewById(R.id.date);
//            viewHolder.progressBar = convertView.findViewById(R.id.progress_circular);

            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

//        viewHolder.progressBar.setVisibility(View.INVISIBLE);

        viewHolder.textView1.setVisibility(View.VISIBLE);
        viewHolder.textView1.setText(track.getTrack());
        viewHolder.textView2.setVisibility(View.VISIBLE);
        viewHolder.textView2.setText(track.getAlbum());
        viewHolder.textView3.setVisibility(View.VISIBLE);
        Date date = track.getDate();
        viewHolder.textView3.setText(dateFormat.format(date));
        viewHolder.textView4.setVisibility(View.VISIBLE);
        viewHolder.textView4.setText(track.getArtist());

        viewHolder.textView5.setVisibility(View.VISIBLE);
        viewHolder.textView6.setVisibility(View.VISIBLE);
        viewHolder.textView7.setVisibility(View.VISIBLE);
        viewHolder.textView8.setVisibility(View.VISIBLE);


        return convertView;

//        return super.getView(position, convertView, parent);
    }

    private static class ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
        TextView textView6;
        TextView textView7;
        TextView textView8;
//        ProgressBar progressBar;

    }
}

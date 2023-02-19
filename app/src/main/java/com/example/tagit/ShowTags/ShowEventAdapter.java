package com.example.tagit.ShowTags;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tagit.Models.TagEventsModel;
import com.example.tagit.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ShowEventAdapter extends RecyclerView.Adapter<ShowEventAdapter.ShowEventHolder> {

    ArrayList<TagEventsModel> tagEventsModelArrayList = new ArrayList<>();

    public ShowEventAdapter(ArrayList<TagEventsModel> arrayList) {
        this.tagEventsModelArrayList = arrayList;
    }

    @NonNull
    @Override
    public ShowEventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.show_event_adapter_layout, parent, false);
        return new ShowEventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowEventHolder holder, int position) {
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String dateToDisplay = "";
        try {
            Date parsedDate = f.parse(tagEventsModelArrayList.get(position).getEventDate());
            f = new SimpleDateFormat("MMMM dd, yyyy");
            dateToDisplay = f.format(parsedDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        holder.eventDetailTV.setText(dateToDisplay);
    }

    @Override
    public int getItemCount() {
        return tagEventsModelArrayList.size();
    }

    public class ShowEventHolder extends RecyclerView.ViewHolder {

        TextView eventDetailTV;
        View view;
        public ShowEventHolder(@NonNull View itemView) {
            super(itemView);
            eventDetailTV = itemView.findViewById(R.id.event_detail_name_tv);
            view = itemView;
        }
    }
}

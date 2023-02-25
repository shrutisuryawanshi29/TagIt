package com.example.tagit.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;

import com.example.tagit.AddTag.AddTagAdapter;
import com.example.tagit.AddTag.AddTagDialog;
import com.example.tagit.AddTag.OnTagClickListener;
import com.example.tagit.Database.DBHandler;
import com.example.tagit.Models.EventsModel;
import com.example.tagit.Models.TagModel;
import com.example.tagit.R;
import com.example.tagit.ShowTags.AllTagsListActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements OnTagClickListener {

    CalendarView calendarView;
    RecyclerView recyclerView;
    Button btnShowTag, btnShowListOfTags;
    String selectedDate;
    DBHandler dbHandler;
    ArrayList<TagModel> eventsModelArrayList = new ArrayList<>();
    AddTagAdapter addTagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        dbHandler = new DBHandler(this);

        calendarView = findViewById(R.id.calendarView);
        recyclerView = findViewById(R.id.main_recycler_view);
        btnShowTag = findViewById(R.id.show_tag_btn);
        btnShowListOfTags = findViewById(R.id.show_all_tags_btn);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        selectedDate = sdf.format(new Date(calendarView.getDate()));

        btnShowTag.setOnClickListener(view -> {
            calendarView.getDate();
            AddTagDialog tagDialog = new AddTagDialog(this, selectedDate);
            tagDialog.show();
            Window window = tagDialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        });

        btnShowListOfTags.setOnClickListener(view -> {
            Intent intent = new Intent(this, AllTagsListActivity.class);
            startActivity(intent);
        });

        // In this Listener have one method
        // and in this method we will
        // get the value of year, month, dayofmonth
        calendarView.setOnDateChangeListener((calendarView, i, i1, i2) -> {
            selectedDate = i+"-"+ String.format("%02d", (i1+1)) +"-"+String.format("%02d",i2);
            setCalenderData();
        });

        setCalenderData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setCalenderData();
    }

    void setCalenderData(){
        eventsModelArrayList = dbHandler.fetchTagFromDate(selectedDate);

        addTagAdapter = new AddTagAdapter(this, eventsModelArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(addTagAdapter);
    }

    void showAlertDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this event associated with '"+eventsModelArrayList.get(position).getTagName()+"' tag?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dbHandler.deleteEvent(selectedDate, eventsModelArrayList.get(position).getTagName());
                        eventsModelArrayList.remove(position);
                        addTagAdapter.notifyItemRemoved(position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClickOfTag(int position) {

    }

    @Override
    public void onDeleteOfTag(int position) {
        showAlertDialog(position);
    }
}
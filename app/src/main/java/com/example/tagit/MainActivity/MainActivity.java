package com.example.tagit.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.tagit.Database.DBHandler;
import com.example.tagit.Models.EventsModel;
import com.example.tagit.Models.TagModel;
import com.example.tagit.R;
import com.example.tagit.ShowTags.AllTagsListActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    RecyclerView recyclerView;
    Button btnShowTag, btnShowListOfTags;
    String selectedDate;
    DBHandler dbHandler;
    ArrayList<TagModel> eventsModelArrayList = new ArrayList<>();

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

        AddTagAdapter addTagAdapter = new AddTagAdapter(null, eventsModelArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(addTagAdapter);
    }
}
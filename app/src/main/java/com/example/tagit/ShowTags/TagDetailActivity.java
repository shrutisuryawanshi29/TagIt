package com.example.tagit.ShowTags;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tagit.Database.DBHandler;
import com.example.tagit.Models.TagEventsModel;
import com.example.tagit.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class TagDetailActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout listTagsLL;
    TextView tagNameTitleTv;
    EditText addDescriptionET;
    Button tagColor1Btn, tagColor2Btn, tagColor3Btn, tagColor4Btn, tagColor5Btn;
    View view_selected_1, view_selected_2, view_selected_3, view_selected_4, view_selected_5;

    Button tagColor6Btn, tagColor7Btn, tagColor8Btn, tagColor9Btn, tagColor10Btn;
    View view_selected_6, view_selected_7, view_selected_8, view_selected_9, view_selected_10;
    Button discardChangesBtn, updateTagBtn;
    RecyclerView tag_details_recyclerView;
    DBHandler dbHandler;
    ArrayList<TagEventsModel> tagEventsModelArrayList = new ArrayList<>();

    boolean[] isColorSelectedArray = new boolean[]{false, false, false, false, false, false, false, false, false, false};
    String[] colorArray = new String[]{"78AD92", "A0C3D2", "78ADAC", "7893AD", "9278AD", "C3B091", "EAC7C7", "DBC970", "E6BA95", "FFAB91"};

    String selectedTagName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_detail);

        dbHandler = new DBHandler(this);
        Intent intent = getIntent();
        selectedTagName = intent.getStringExtra("selectedTagName");
        setIdsForViews();
        fetchTagDetails();
        setDataForViews();
    }

    void setIdsForViews() {
        listTagsLL = findViewById(R.id.list_tags_ll);
        tagNameTitleTv = findViewById(R.id.tag_name_title_tv);
        addDescriptionET = findViewById(R.id.et_add_description);
        tagColor1Btn = findViewById(R.id.btn_tag_color1);
        tagColor2Btn = findViewById(R.id.btn_tag_color2);
        tagColor3Btn = findViewById(R.id.btn_tag_color3);
        tagColor4Btn = findViewById(R.id.btn_tag_color4);
        tagColor5Btn = findViewById(R.id.btn_tag_color5);
        tagColor6Btn = findViewById(R.id.btn_tag_color6);
        tagColor7Btn = findViewById(R.id.btn_tag_color7);
        tagColor8Btn = findViewById(R.id.btn_tag_color8);
        tagColor9Btn = findViewById(R.id.btn_tag_color9);
        tagColor10Btn = findViewById(R.id.btn_tag_color10);
        discardChangesBtn = findViewById(R.id.btn_discard_changes);
        updateTagBtn = findViewById(R.id.btn_update_tag);
        tag_details_recyclerView = findViewById(R.id.tag_details_recyclerView);
        view_selected_1 = findViewById(R.id.view_selected_1);
        view_selected_2 = findViewById(R.id.view_selected_2);
        view_selected_3 = findViewById(R.id.view_selected_3);
        view_selected_4 = findViewById(R.id.view_selected_4);
        view_selected_5 = findViewById(R.id.view_selected_5);
        view_selected_6 = findViewById(R.id.view_selected_6);
        view_selected_7 = findViewById(R.id.view_selected_7);
        view_selected_8 = findViewById(R.id.view_selected_8);
        view_selected_9 = findViewById(R.id.view_selected_9);
        view_selected_10 = findViewById(R.id.view_selected_10);

        tagColor1Btn.setOnClickListener(this);
        tagColor2Btn.setOnClickListener(this);
        tagColor3Btn.setOnClickListener(this);
        tagColor4Btn.setOnClickListener(this);
        tagColor5Btn.setOnClickListener(this);
        tagColor6Btn.setOnClickListener(this);
        tagColor7Btn.setOnClickListener(this);
        tagColor8Btn.setOnClickListener(this);
        tagColor9Btn.setOnClickListener(this);
        tagColor10Btn.setOnClickListener(this);
        discardChangesBtn.setOnClickListener(this);
        updateTagBtn.setOnClickListener(this);

    }

    void fetchTagDetails() {
        tagEventsModelArrayList = dbHandler.fetchAllDataFromTagEventsTable(selectedTagName);
        Collections.sort(tagEventsModelArrayList, new Comparator<TagEventsModel>() {
            DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            @Override
            public int compare(TagEventsModel tagEventsModel, TagEventsModel t1) {
                try {
                    return f.parse(tagEventsModel.getEventDate()).compareTo(f.parse(t1.getEventDate()));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    void setDataForViews() {
        tagNameTitleTv.setText(selectedTagName);

        // handled this condition if data in tag table but event not added
        // it returns empty as we have used two tables
        if (tagEventsModelArrayList.size() > 0) {
            setDyanamicStatusBarColor(tagEventsModelArrayList.get(0).getTagColor());
            listTagsLL.setBackgroundColor(Color.parseColor("#" + tagEventsModelArrayList.get(0).getTagColor()));
            addDescriptionET.setText(tagEventsModelArrayList.get(0).getTagDescription());
            int position = Arrays.asList(colorArray).indexOf(tagEventsModelArrayList.get(0).getTagColor());
            setValuesInSelectedArray(position);
            setAdapterData();
        } else {
            setDyanamicStatusBarColor(colorArray[0]);
            listTagsLL.setBackgroundColor(Color.parseColor("#" + colorArray[0]));
            setValuesInSelectedArray(0);
        }
    }

    void setAdapterData() {
        ShowEventAdapter showEventAdapter = new ShowEventAdapter(tagEventsModelArrayList);
        tag_details_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tag_details_recyclerView.setAdapter(showEventAdapter);
    }

    void setDyanamicStatusBarColor(String tagColor) {
        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(Color.parseColor("#" + tagColor));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tag_color1:
                setValuesInSelectedArray(0);
                break;

            case R.id.btn_tag_color2:
                setValuesInSelectedArray(1);
                break;

            case R.id.btn_tag_color3:
                setValuesInSelectedArray(2);
                break;

            case R.id.btn_tag_color4:
                setValuesInSelectedArray(3);
                break;

            case R.id.btn_tag_color5:
                setValuesInSelectedArray(4);
                break;

            case R.id.btn_tag_color6:
                setValuesInSelectedArray(5);
                break;

            case R.id.btn_tag_color7:
                setValuesInSelectedArray(6);
                break;

            case R.id.btn_tag_color8:
                setValuesInSelectedArray(7);
                break;

            case R.id.btn_tag_color9:
                setValuesInSelectedArray(8);
                break;

            case R.id.btn_tag_color10:
                setValuesInSelectedArray(9);
                break;

            case R.id.btn_discard_changes:
                setDataForViews();
                break;

            case R.id.btn_update_tag:
                updateTagTable();
                break;
        }
    }

    void setValuesInSelectedArray(int position) {
        isColorSelectedArray = new boolean[]{false, false, false, false, false, false, false, false, false, false};
        isColorSelectedArray[position] = true;

        view_selected_1.setVisibility((position + 1) == 1 ? View.VISIBLE : View.INVISIBLE);
        view_selected_2.setVisibility((position + 1) == 2 ? View.VISIBLE : View.INVISIBLE);
        view_selected_3.setVisibility((position + 1) == 3 ? View.VISIBLE : View.INVISIBLE);
        view_selected_4.setVisibility((position + 1) == 4 ? View.VISIBLE : View.INVISIBLE);
        view_selected_5.setVisibility((position + 1) == 5 ? View.VISIBLE : View.INVISIBLE);
        view_selected_6.setVisibility((position + 1) == 6 ? View.VISIBLE : View.INVISIBLE);
        view_selected_7.setVisibility((position + 1) == 7 ? View.VISIBLE : View.INVISIBLE);
        view_selected_8.setVisibility((position + 1) == 8 ? View.VISIBLE : View.INVISIBLE);
        view_selected_9.setVisibility((position + 1) == 9 ? View.VISIBLE : View.INVISIBLE);
        view_selected_10.setVisibility((position + 1) == 10 ? View.VISIBLE : View.INVISIBLE);
    }

    int gtIndexOfColor() {
        for (int i = 0; i < isColorSelectedArray.length; i++) {
            if (isColorSelectedArray[i]) return i;
        }

        return 0;
    }

    void updateTagTable() {
        String desc = addDescriptionET.getText().toString().trim();
        int colorIndex = gtIndexOfColor();
        String color = colorArray[colorIndex];
        dbHandler.updateValuesInTagTable(selectedTagName, desc, color);
    }
}
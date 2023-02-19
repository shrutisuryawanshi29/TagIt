package com.example.tagit.AddTag;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tagit.Database.DBHandler;
import com.example.tagit.R;
import com.example.tagit.Models.TagModel;

import java.util.ArrayList;

public class AddTagDialog extends Dialog implements View.OnClickListener, OnTagClickListener {
    Activity activity;
    EditText addTagEt;
    Button saveBtn;
    private DBHandler dbHandler;
    ArrayList<TagModel> tagModelArrayList = new ArrayList<>();
    RecyclerView listOfTagsRV;
    String selectedDate;

    public AddTagDialog(Activity a, String selectedDate) {
        super(a);
        this.activity = a;
        this.selectedDate = selectedDate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_tag_dialog);
        dbHandler = new DBHandler(activity);
        addTagEt = findViewById(R.id.et_add_tag);
        saveBtn = findViewById(R.id.btn_add_tag);
        listOfTagsRV = findViewById(R.id.list_of_tags_rv);

        saveBtn.setOnClickListener(view -> {
            String tagName = addTagEt.getText().toString();
            if (!tagName.isEmpty()) {
                dbHandler.addNewTagIntoTable(tagName.trim(), "", "78AD92");
                dismiss();
            }
        });

        tagModelArrayList = dbHandler.fetchAllTags();
        setAdapterData();
    }

    public void setAdapterData() {
        AddTagAdapter addTagAdapter = new AddTagAdapter(this, tagModelArrayList);
        listOfTagsRV.setLayoutManager(new LinearLayoutManager(activity));
        listOfTagsRV.setAdapter(addTagAdapter);
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }

    @Override
    public void onClickOfTag(int position) {
        dbHandler.addNewEventIntoTable(selectedDate, tagModelArrayList.get(position).getTagName());
        dismiss();
    }
}

package com.example.tagit.AddTag;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
    AddTagAdapter addTagAdapter;

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
            if (!tagName.isEmpty() && !checkIfDataAlreadyExist(tagName)) {
                dbHandler.addNewTagIntoTable(tagName.trim(), "", "78AD92");
                Toast.makeText(activity, "Tag Created!", Toast.LENGTH_SHORT).show();
                tagModelArrayList.add(new TagModel(tagName.trim(), "", "78AD92"));
                addTagAdapter.notifyDataSetChanged();
            }
        });

        tagModelArrayList = dbHandler.fetchAllTags();
        setAdapterData();
    }

    boolean checkIfDataAlreadyExist(String tn) {
        for(TagModel tagModel: tagModelArrayList) {
            if (tagModel.getTagName().equals(tn)) return true;
        }
        return false;
    }

    public void setAdapterData() {
        addTagAdapter = new AddTagAdapter(this, tagModelArrayList);
        listOfTagsRV.setLayoutManager(new LinearLayoutManager(activity));
        listOfTagsRV.setAdapter(addTagAdapter);
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }

    void showAlertDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure you want to delete the tag '"+tagModelArrayList.get(position).getTagName()+"' and all the events associated with tag?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dbHandler.deleteTag(tagModelArrayList.get(position).getTagName());
                        tagModelArrayList.remove(position);
                        addTagAdapter.notifyItemRemoved(position);
                        Toast.makeText(activity, "Tag Deleted!", Toast.LENGTH_SHORT).show();

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
        dbHandler.addNewEventIntoTable(selectedDate, tagModelArrayList.get(position).getTagName());
        dismiss();
    }

    @Override
    public void onDeleteOfTag(int position) {
        showAlertDialog(position);
    }
}

package com.example.tagit.ShowTags;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tagit.AddTag.AddTagAdapter;
import com.example.tagit.AddTag.OnTagClickListener;
import com.example.tagit.Database.DBHandler;
import com.example.tagit.Models.TagModel;
import com.example.tagit.R;

import java.util.ArrayList;

public class AllTagsListActivity extends AppCompatActivity implements OnTagClickListener {

    RecyclerView tagsRecyclerView;
    ArrayList<TagModel> tagModelArrayList = new ArrayList<>();
    DBHandler dbHandler;
    ImageButton backBtnListTags;

    AddTagAdapter addTagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tags_list);

        dbHandler = new DBHandler(this);
        tagsRecyclerView = findViewById(R.id.list_tags_recycler_view);
        backBtnListTags = findViewById(R.id.back_btn_list_tags);

        backBtnListTags.setOnClickListener(view -> {
            finish();
        });

        setAdapterData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setAdapterData();
    }

    public void setAdapterData() {
        tagModelArrayList = dbHandler.fetchAllTags();

        addTagAdapter = new AddTagAdapter(this, tagModelArrayList);
        tagsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tagsRecyclerView.setAdapter(addTagAdapter);
    }

    void showAlertDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete the tag '"+tagModelArrayList.get(position).getTagName()+"' and all the events associated with tag?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dbHandler.deleteTag(tagModelArrayList.get(position).getTagName());
                        tagModelArrayList.remove(position);
                        addTagAdapter.notifyItemRemoved(position);
                        Toast.makeText(getApplicationContext(), "Tag Deleted!", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this, TagDetailActivity.class);
        intent.putExtra("selectedTagName", tagModelArrayList.get(position).getTagName());
        startActivity(intent);
    }

    @Override
    public void onDeleteOfTag(int position) {
        showAlertDialog(position);
    }
}
package com.example.tagit.ShowTags;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tags_list);

        dbHandler = new DBHandler(this);
        tagsRecyclerView = findViewById(R.id.list_tags_recycler_view);

        setAdapterData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setAdapterData();
    }

    public void setAdapterData() {
        tagModelArrayList = dbHandler.fetchAllTags();

        AddTagAdapter addTagAdapter = new AddTagAdapter(this, tagModelArrayList);
        tagsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tagsRecyclerView.setAdapter(addTagAdapter);
    }

    @Override
    public void onClickOfTag(int position) {
        Intent intent = new Intent(this, TagDetailActivity.class);
        intent.putExtra("selectedTagName", tagModelArrayList.get(position).getTagName());
        startActivity(intent);
    }
}
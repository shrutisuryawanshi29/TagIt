package com.example.tagit.AddTag;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tagit.R;
import com.example.tagit.Models.TagModel;

import java.util.ArrayList;

public class AddTagAdapter extends RecyclerView.Adapter<AddTagAdapter.TagViewHolder> {

    ArrayList<TagModel> tagModelArrayList;
    OnTagClickListener listener;

    public AddTagAdapter(OnTagClickListener listener, ArrayList<TagModel> tagModelArrayList) {
        this.listener = listener;
        this.tagModelArrayList = tagModelArrayList;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.add_tag_adapter_layout, parent,false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        holder.tagNameTv.setText(tagModelArrayList.get(position).getTagName());
        GradientDrawable drawable = (GradientDrawable) holder.tagNameLL.getBackground();
        drawable.setColor(Color.parseColor("#"+tagModelArrayList.get(position).getTagColor()));

        holder.tagNameTv.setOnClickListener(view -> {
            listener.onClickOfTag(position);
        });

        holder.deleteBtn.setOnClickListener(view -> {
            listener.onDeleteOfTag(position);
        });
    }

    @Override
    public int getItemCount() {
        return tagModelArrayList.size();
    }

    public void updateList(ArrayList<TagModel> temp) {
        tagModelArrayList = temp;
        notifyDataSetChanged();
    }

    public class TagViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout tagNameLL;
        TextView tagNameTv;
        View view;
        ImageButton deleteBtn;

        public TagViewHolder(@NonNull View itemView) {
            super(itemView);
            tagNameTv = itemView.findViewById(R.id.tag_name_tv);
            tagNameLL = itemView.findViewById(R.id.tag_name_ll);
            deleteBtn = itemView.findViewById(R.id.delete_btn);

            view = itemView;
        }
    }
}

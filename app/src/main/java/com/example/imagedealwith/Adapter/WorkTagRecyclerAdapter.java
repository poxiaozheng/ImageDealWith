package com.example.imagedealwith.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.imagedealwith.R;

import java.util.List;

public class WorkTagRecyclerAdapter extends RecyclerView.Adapter<WorkTagRecyclerAdapter.WorkTagViewHolder> {

    private List<String> tags;

    public WorkTagRecyclerAdapter(@NonNull List<String> tags) {
        this.tags = tags;
    }

    @NonNull
    @Override
    public WorkTagViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.works_tags, viewGroup,false);
        return new WorkTagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkTagViewHolder workTagViewHolder, int i) {
        String tag = tags.get(i);
        if(i == tags.size()-1) {
            workTagViewHolder.divider.setVisibility(View.GONE);
        }
        else {
            workTagViewHolder.divider.setVisibility(View.VISIBLE);
        }
        workTagViewHolder.text.setText(tag);
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public class WorkTagViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.WorkTagDivider)
        TextView divider;

        @BindView(R.id.WorkTagText)
        TextView text;

        public WorkTagViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

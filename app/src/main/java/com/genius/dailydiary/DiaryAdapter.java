package com.genius.dailydiary;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {

    Context context;
    DatabaseReference databaseReference;
    ArrayList<DiaryModel> diaryModelArrayList;

    public DiaryAdapter(Context context,DatabaseReference reference, ArrayList<DiaryModel> diaryModelArrayList) {
        this.context = context;
        databaseReference = reference;
        this.diaryModelArrayList = diaryModelArrayList;
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.single_note_layout,viewGroup,false);

        return new DiaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DiaryViewHolder holder, int i) {

        final DiaryModel diaryModel = diaryModelArrayList.get(i);
        holder.notetitle.setText("Title: "+diaryModel.getTitle());
        holder.noteTime.setText(diaryModel.getTimestap());
        holder.note.setText(diaryModel.getContent());

        holder.deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(diaryModel.getContentId()).removeValue();
            }
        });



    }

    @Override
    public int getItemCount() {
        return diaryModelArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class DiaryViewHolder extends RecyclerView.ViewHolder{

        TextView notetitle,noteTime,note;
        ImageView deleteNote;
        public DiaryViewHolder(@NonNull View itemView) {
            super(itemView);


            noteTime = itemView.findViewById(R.id.note_time);
            notetitle = itemView.findViewById(R.id.note_title);
            note = itemView.findViewById(R.id.note_content);
            deleteNote = itemView.findViewById(R.id.delete_note);

        }
    }
}

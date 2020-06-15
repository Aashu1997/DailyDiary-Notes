package com.genius.dailydiary;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class NoteViewHolder extends RecyclerView.ViewHolder
{
    View mview;

    TextView textTitle, textTime;
    CardView noteCard;

    public NoteViewHolder(View itemView) {
        super(itemView);

        mview=itemView;

        textTitle=mview.findViewById(R.id.note_title);
        textTime=mview.findViewById(R.id.note_time);
        noteCard=mview.findViewById(R.id.note_card);
    }

    public void setNoteTitle(String title)
    {
         textTitle.setText(title);
    }

    public void setNoteTime(String time)
    {
          textTime.setText(time);
    }
}

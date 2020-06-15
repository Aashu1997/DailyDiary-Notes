package com.genius.dailydiary;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth  fAuth;
    private RecyclerView mNotesList;
    private GridLayoutManager gridLayoutManager;

    private DatabaseReference fNotesDatabase;

    ArrayList<DiaryModel> diaryArrayList;
    DiaryAdapter diaryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();
        updatedUI();

        mNotesList = findViewById(R.id.main_notes_list);

        gridLayoutManager=new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false);

        mNotesList.setHasFixedSize(true);
        mNotesList.setLayoutManager(new LinearLayoutManager(this    ));


        if(fAuth.getCurrentUser() != null) {
            fNotesDatabase= FirebaseDatabase.getInstance().getReference().child("Notes").child(fAuth.getCurrentUser().getUid());
            setDataToAdapter();
        }



    }

    public void setDataToAdapter(){
        fNotesDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                diaryArrayList = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    DiaryModel diaryModel = postSnapshot.getValue(DiaryModel.class);

                    diaryModel.setContentId(postSnapshot.getKey());

                    diaryArrayList.add(diaryModel);


                }

                diaryAdapter = new DiaryAdapter(MainActivity.this,fNotesDatabase,diaryArrayList);
                mNotesList.setAdapter(diaryAdapter);
                diaryAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void updatedUI()
    {
        if(fAuth.getCurrentUser() !=null) {
            Log.i("MainActivity","fAuth !=null");
        }
        else
        {
                    Intent startIntent = new Intent(MainActivity.this,StartActivity.class);
                    startActivity(startIntent);
                    finish();
                    Log.i("MainActivity","fAuth==null");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId())
        {
            case R.id.main_new_note_btn:
            Intent newIntent = new Intent(MainActivity.this,NewNoteActivity.class);
            startActivity(newIntent);
            break;
        }

        return true;
    }
}

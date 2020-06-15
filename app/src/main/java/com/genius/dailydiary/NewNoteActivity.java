package com.genius.dailydiary;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NewNoteActivity extends AppCompatActivity {

    private Button btnCreate;
    private EditText etTitle,etContent;
    private Toolbar mToolbar;

    private FirebaseAuth fAuth;
    private DatabaseReference fNotesDatabase;

    private Menu mainMenu;
    private String noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        try{
            noteId = getIntent().getStringExtra("noteId");

            if(noteId.equals("no")){
                mainMenu.getItem(0).setVisible(false);
            }

            mainMenu.getItem(0).setVisible(false);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        btnCreate=findViewById(R.id.new_note_btn);
        etTitle=findViewById(R.id.new_note_title);
        etContent=findViewById(R.id.new_note_content);
        mToolbar=findViewById(R.id.new_note_toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fAuth=FirebaseAuth.getInstance();
        fNotesDatabase= FirebaseDatabase.getInstance().getReference().child("Notes").child(fAuth.getCurrentUser().getUid());

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title=etTitle.getText().toString().trim();
                String content=etContent.getText().toString().trim();

                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content))
                {
                    createNote(title,content);
                }
                else
                {
                    Snackbar.make(v,"Fill empty Fields",Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void createNote(String title, String content)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        if(fAuth.getCurrentUser() != null)
        {
            final DatabaseReference newNoteRef = fNotesDatabase.push();
            final Map noteMap = new HashMap();
            noteMap.put("title",title);
            noteMap.put("content",content);
            noteMap.put("timestap",currentDateandTime);

            Thread mainThread = new Thread(new Runnable() {
                @Override
                public void run()
                {
                    newNoteRef.setValue(noteMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(NewNoteActivity.this, "Note added to database", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(NewNoteActivity.this, "ERROR!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            });
            mainThread.start();


        }
        else
        {
            Toast.makeText(this, "USERS IS NOT SIGNED IN", Toast.LENGTH_SHORT).show();
        }

    }

    private void deleteNote()
    {
          fNotesDatabase.child(noteId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull Task<Void> task) {

                  if(task.isSuccessful())
                  {
                      Toast.makeText(NewNoteActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
                      noteId="no";
                      finish();
                  }else {
                      Log.e("NewNoteActivity", task.getException().toString());
                      Toast.makeText(NewNoteActivity.this, "ERROR: " + task.getException(), Toast.LENGTH_SHORT).show();
                  }
              }
          });
    }
}

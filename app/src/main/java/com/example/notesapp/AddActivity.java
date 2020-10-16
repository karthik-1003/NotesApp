package com.example.notesapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class AddActivity extends AppCompatActivity {

    public static final String EXTRA_DATA_TITLE = "extra_title_to_be_updated";
    public static final String EXTRA_DATA_DESCRIPTION = "extra_description_to_be_updated";
    public static final String EXTRA_DATA_ID = "extra_id_to_be_updated";
    AddActivityViewModel addActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final EditText titleET = findViewById(R.id.title);
        final EditText descriptionET = findViewById(R.id.description);
        final Button add = findViewById(R.id.add);

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString(EXTRA_DATA_TITLE, " ");
            String description = extras.getString(EXTRA_DATA_DESCRIPTION, " ");
            Long id = extras.getLong(EXTRA_DATA_ID, 0L);

            if (title != null) {
                titleET.setText(title);
            }

            if (description != null) {
                descriptionET.setText(description);
            }

            add.setText("Update");
        }

        addActivityViewModel = new ViewModelProvider(this).get(AddActivityViewModel.class);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleET.getText().toString();
                String description = descriptionET.getText().toString();

                if (extras != null && extras.containsKey(EXTRA_DATA_ID)) {
                    Long id = extras.getLong(EXTRA_DATA_ID);
                    Notes notes = new Notes(id, title, description);
                    notes.setId(id);
                    notes.setTitle(title);
                    notes.setDescription(description);

                }

                if (title.isEmpty() || description.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Empty Notes", Toast.LENGTH_SHORT).show();
                } else {
                    Notes notes = new Notes(title, description);
                    addActivityViewModel.insertNotes(notes);
                    setResult(RESULT_OK);
                    finish();
                }

            }
        });

    }
}

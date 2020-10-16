package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import adapter.NotesPagingAdapter;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_DATA_TITLE = "extra_title_to_be_updated";
    public static final String EXTRA_DATA_DESCRIPTION = "extra_description_to_be_updated";
    public static final String EXTRA_DATA_ID = "extra_id_to_be_updated";
    public static final int NEW_NOTES_REQUEST_CODE = 1;
    public static final int UPDATE_NOTES_REQUEST_CODE = 2;
    private MainActivityViewModel mainActivityViewModel;
    private Notes notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButton = findViewById(R.id.FAB);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, NEW_NOTES_REQUEST_CODE);
            }
        });

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        final NotesPagingAdapter notesPagingAdapter = new NotesPagingAdapter();

        recyclerView.setAdapter(notesPagingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mainActivityViewModel.pagedListLiveData.observe(this, new Observer<PagedList<Notes>>() {
            @Override
            public void onChanged(PagedList<Notes> notes) {
                notesPagingAdapter.submitList(notes);
            }
        });

        notesPagingAdapter.setOnItemClickListener(new NotesPagingAdapter.ClickListener() {
            @Override
            public void OnItemClick(int position, View view) {
                Notes currentNotes = notesPagingAdapter.getNotesAtposition(position);
                launchUpdateNotes(currentNotes);
            }
        });

        final ConstraintLayout constraintLayout = findViewById(R.id.constraint_layout);

        final Snackbar snackbar = Snackbar.make(constraintLayout, "Notes deleted", BaseTransientBottomBar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainActivityViewModel.insertNotes(notes);
                    }
                });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                notes = notesPagingAdapter.getNotesAtposition(position);
                mainActivityViewModel.deleteNotes(notes);
                snackbar.show();

            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void launchUpdateNotes(Notes notes) {
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        intent.putExtra(EXTRA_DATA_TITLE, notes.getTitle());
        intent.putExtra(EXTRA_DATA_DESCRIPTION, notes.getDescription());
        intent.putExtra(EXTRA_DATA_ID, notes.getId());
        startActivityForResult(intent, UPDATE_NOTES_REQUEST_CODE);
    }
}
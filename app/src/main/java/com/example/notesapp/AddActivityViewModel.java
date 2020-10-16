package com.example.notesapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

public class AddActivityViewModel extends AndroidViewModel {

    NotesRepository notesRepository;
    LiveData<PagedList<Notes>> pagedListLiveData;


    public AddActivityViewModel(@NonNull Application application) {
        super(application);
        notesRepository = NotesRepository.getRepository(application);
        pagedListLiveData = notesRepository.getAll();
    }

    public void insertNotes(Notes notes) {
        notesRepository.insertNotes(notes);
    }

    public void updateNotes(Notes notes) {
        notesRepository.updateNotes(notes);
    }
}

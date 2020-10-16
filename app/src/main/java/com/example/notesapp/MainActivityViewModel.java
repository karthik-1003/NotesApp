package com.example.notesapp;

import android.app.Application;


import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

public class MainActivityViewModel extends AndroidViewModel {

    NotesRepository notesRepository;
    LiveData<PagedList<Notes>> pagedListLiveData;

    public MainActivityViewModel(Application application) {
        super(application);
        notesRepository = NotesRepository.getRepository(application);
        pagedListLiveData = notesRepository.getAll();
    }

    public void insertNotes(Notes notes) {
        notesRepository.insertNotes(notes);
    }

    public void deleteNotes(Notes notes) {
        notesRepository.deleteNotes(notes);
    }


}

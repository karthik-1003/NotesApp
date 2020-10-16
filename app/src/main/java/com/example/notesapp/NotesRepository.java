package com.example.notesapp;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotesRepository {

    private static NotesRepository repository = null;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    private int PAGE_SIZE = 15;
    private NotesDao notesDao;

    private NotesRepository(Application application) {
        NotesDatabase notesDatabase = NotesDatabase.getINSTANCE(application);
        notesDao = notesDatabase.notesDao();
    }

    public static NotesRepository getRepository(Application application) {
        if (repository == null) {
            synchronized (NotesRepository.class) {
                if (repository == null) {
                    repository = new NotesRepository(application);
                }
            }
        }
        return repository;
    }

    public void insertNotes(final Notes notes) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                notesDao.insertNotes(notes);
            }
        });
    }

    public void updateNotes(final Notes notes) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                notesDao.updateNotes(notes);
            }
        });
    }

    public void deleteNotes(final Notes notes) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                notesDao.deleteNotes(notes);
            }
        });
    }

    public LiveData<PagedList<Notes>> getAll() {
        LiveData data = new LivePagedListBuilder<>(notesDao.getAll(), PAGE_SIZE).build();
        return data;
    }
}

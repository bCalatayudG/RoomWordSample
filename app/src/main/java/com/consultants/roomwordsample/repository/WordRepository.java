package com.consultants.roomwordsample.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.consultants.roomwordsample.repository.room.Word;
import com.consultants.roomwordsample.repository.room.WordDAO;
import com.consultants.roomwordsample.repository.room.WordRoomDatabase;

import java.util.List;

public class WordRepository {

    private WordDAO wordDAO;
    private LiveData<List<Word>> mAllWords;

    //Constructor
    public WordRepository(Application application){
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        wordDAO = db.wordDAO();
        mAllWords = wordDAO.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void insert(Word word){
        new insertAsyncTask(wordDAO).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDAO mAsyncTaskDao;

        insertAsyncTask(WordDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}

package com.example.david.simplemath.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.david.simplemath.models.ArrayModel;
import com.example.david.simplemath.models.HighscoreModel;
import com.example.david.simplemath.models.LessGreaterModel;
import com.example.david.simplemath.models.PlusMinusModel;
import com.example.david.simplemath.models.RomanArabianModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by david
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    String DB_PATH = null;
    private static String DB_NAME = "databaseSimpleMath";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 10);
        this.myContext = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
        Log.e("Path 1", DB_PATH);
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    public void closeDatabase() {
        if (myDataBase != null) {
            myDataBase.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();

            }
    }

    public ArrayList<RomanArabianModel> getQuestionsRomanArabian() {
        RomanArabianModel roman = null;
        ArrayList<RomanArabianModel> romanList = new ArrayList<>();
        openDataBase();
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM CategoryRomanArabian", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            roman = new RomanArabianModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
            romanList.add(roman);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return romanList;
    }

    public ArrayList<ArrayModel> getQuestionsArray() {
        ArrayModel arrayModel = null;
        ArrayList<ArrayModel> arrayModelList = new ArrayList<>();
        openDataBase();
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM CategoryArray", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayModel = new ArrayModel(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5));
            arrayModelList.add(arrayModel);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return arrayModelList;
    }

    public ArrayList<PlusMinusModel> getQuestionsPlusMinus() {
        PlusMinusModel plusMinusModel = null;
        ArrayList<PlusMinusModel> plusMinusModelList = new ArrayList<>();
        openDataBase();
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM CategoryPlusMinus", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            plusMinusModel = new PlusMinusModel(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5));
            plusMinusModelList.add(plusMinusModel);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return plusMinusModelList;
    }


    public ArrayList<LessGreaterModel> getQuestionsLessGreater() {
        LessGreaterModel lessGreaterModel = null;
        ArrayList<LessGreaterModel> lessGreaterModelList = new ArrayList<>();
        openDataBase();
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM CategoryLessGreater", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            lessGreaterModel = new LessGreaterModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            lessGreaterModelList.add(lessGreaterModel);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return lessGreaterModelList;
    }

    public ArrayList<HighscoreModel> getHighscores() {
        HighscoreModel highscoreModel = null;
        ArrayList<HighscoreModel> highscoreModelList = new ArrayList<>();
        openDataBase();
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM Highscore ORDER BY score DESC, date DESC LIMIT 3", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            highscoreModel = new HighscoreModel(cursor.getString(1), cursor.getString(2));
            highscoreModelList.add(highscoreModel);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return highscoreModelList;
    }


}

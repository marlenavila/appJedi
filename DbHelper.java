package com.example.marlen.appjedi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DbHelper extends SQLiteOpenHelper {

    public static String CN_NAME = "userName";

    public static String CN_PASS = "password";

    public static String CN_POINTS = "points";

    public static String CN_NOTIFICATION = "notification";

    public static String CN_PIC = "image";

    //Declaracion del nombre de la base de datos
    public static final int DATABASE_VERSION = 1;

    //Declaracion global de la version de la base de datos
    public static final String DATABASE_NAME = "user.sqlite";

    //Declaracion del nombre de la tabla
    public static final String USER_TABLE ="User";

    //sentencia global de creacion de la base de datos
    public static final String USER_TABLE_CREATE = "CREATE TABLE " + USER_TABLE + " ("
            + CN_NAME + " TEXT PRIMARY KEY UNIQUE,"
            + CN_PASS + " TEXT,"
            + CN_POINTS + " INTEGER,"
            + CN_NOTIFICATION + " TEXT,"
            + CN_PIC + " TEXT);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_CREATE);
    }

    //obtener una lista de users
    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_NAME,CN_POINTS};
        Cursor c = db.query(
                USER_TABLE,  // The table to query
                columns,                                // The columns to return
                null,                                   // The columns for the WHERE clause
                null,                                   // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                CN_POINTS + " ASC"                     // The sort order
        );
        return c;
    }

    public Cursor getUser(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {CN_NAME,CN_PASS,CN_POINTS,CN_NOTIFICATION,CN_PIC};
        String[] where = {userName};
        Cursor c = db.query(
                USER_TABLE,  // The table to query
                columns,                                    // The columns to return
                "userName=?",                                   // The columns for the WHERE clause
                where,                                      // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                null                                        // The sort order
        );
        return c;
    }

    public void createUser (ContentValues values, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(
                tableName,
                null,
                values);
    }

    public void updatePoints(String name, Integer points){ //actualitzar punts després d'una partida
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CN_POINTS,points);
        db.update(USER_TABLE, cv, CN_NAME + "=?", new String[]{name});
    }

    public void updatePassword(String name, String new_pass){ //actualitzar password
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CN_PASS,new_pass);
        db.update(USER_TABLE, cv, CN_NAME + "=?", new String[]{name});
    }

    public void updatePic(String name, String new_pic){ //actualitzar imatge perfil
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CN_PIC,new_pic);
        db.update(USER_TABLE, cv, CN_NAME + "=?", new String[]{name});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
package com.example.ammar.dictionary;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.net.URI;

public class DictionaryProvider extends ContentProvider {

    private static UriMatcher mUriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
    private DictDbHelper mDictDbHelper;
    private SQLiteDatabase sdb;
    static{
        mUriMatcher.addURI("com.example.ammar.dictionary.DictionaryProvider","entries",1);
    }




    @Override
    public boolean onCreate() {
        mDictDbHelper =new DictDbHelper(getContext());
        sdb=mDictDbHelper.getReadableDatabase();
    return true;
    }


    //@Nullable
    //@Override
   /* public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder, @Nullable CancellationSignal cancellationSignal) {
        //return super.query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignal);
        if(mUriMatcher.match(uri)==1){
            return sdb.query("entries",projection,selection,selectionArgs,null,null,sortOrder);
        }

        String[] cols = {"_ID"};
        return new MatrixCursor(cols);
    }*/
   /* @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }*/


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        if(mUriMatcher.match(uri)==1){
            return sdb.query("entries",strings,s,strings1,null,null,s1);
        }

        String[] cols = {"_ID"};
        return new MatrixCursor(cols);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }



    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }


}


package com.example.ammar.dictionary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DictDbHelper extends SQLiteOpenHelper {
    final private static String DB_NAME= "Dictionary";
    final private static int DB_VERSION=1;
    private Context myContext;
    private String DB_PATH="/data/data/";
    private SQLiteDatabase myDb;

    public DictDbHelper(Context c) {
        super(c, DB_NAME, null, DB_VERSION);
        myContext=c;
        this.DB_PATH=this.DB_PATH+myContext.getPackageName()+"/databases/";
        createDb();
        openDataBase();
    }

    private void createDb(){
        if(!checkDb())      //database doesnt exists
        {
           // this.getReadableDatabase();
            try{
            copyDb();

            }
            catch(Exception e){

                Log.d("COPYERROR",e.toString());
            }
        }


    }


    private void copyDb(){
        try {
            InputStream myInput = myContext.getAssets().open(DB_NAME);
            String outFileName = DB_PATH + DB_NAME;

            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1000];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();

        }catch(Exception e){

            Log.d("COPYERROR",e.toString());

        }

    }

    private boolean checkDb(){

        SQLiteDatabase prevDb=null;
        try{
            String path=DB_PATH+DB_NAME;
            prevDb=SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);

        }
        catch(Exception e){
            Log.d("CHECKERROR",e.toString());

        }

        if(prevDb!=null){

            return true;
      }
        return false;


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }
	
	
	
    private void openDataBase()  {
        String myPath = DB_PATH + DB_NAME;
        try {
            myDb = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch(Exception e){
            Log.d("OPENERROR",e.toString());
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            copyDb();

    }


    public synchronized void close() {
        if (myDb != null)
            myDb.close();
        super.close();
    }

    public Cursor query(String table,
                        String[] columns,
                        String selection,
                        String[] selectionArgs,
                        String groupBy,
                        String having,
                        String orderBy){
        return myDb.query(table,columns,selection,selectionArgs,groupBy,having,orderBy);
    }
	
	

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return this.myDb;
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return this.myDb;
    }
}

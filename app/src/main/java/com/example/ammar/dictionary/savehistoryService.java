package com.example.ammar.dictionary;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class savehistoryService extends Service {

   // DictDbHelper dbhelper;
    private final String filename="history.txt";


    @Override
    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
      //  this.stopSelf();


        Toast.makeText(this,"Service Started",Toast.LENGTH_SHORT).show();
        ArrayList<String> wordsSearched;

        wordsSearched=intent.getStringArrayListExtra("searches");


        for(int i=0;i<wordsSearched.size();++i){
            Log.d("PASSED",wordsSearched.get(i));
        }

        try {
           FileOutputStream os = openFileOutput(filename,MODE_PRIVATE);


           //FileWriter fw=new FileWriter(file);
            //BufferedWriter bw=new BufferedWriter(fw);
            for(int i=0;i<wordsSearched.size();++i)
            {
            os.write(wordsSearched.get(i).getBytes());
            os.write('\n');
            os.flush();
            }
            os.close();
        }
        catch(Exception e){
            Log.d("FILEERROR",e.toString());
        }

        stopSelf();

        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}

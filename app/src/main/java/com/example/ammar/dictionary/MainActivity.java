package com.example.ammar.dictionary;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
        //Intent serviceStarter;
    private DictDbHelper dbHelper;
    private EditText search;
    private List<String> resultList;      //definitions
    private ListView lv;
    private ArrayAdapter<String> ap;
    private Button lookup,export;
    private ArrayList<String> history;
    private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  uri=Uri.parse("content://com.example.ammar.dictionary.DictionaryProvider/entries");
        resultList=new ArrayList<String>();
         dbHelper=new DictDbHelper(this);


        search=(EditText)findViewById(R.id.search);
        lv=(ListView)findViewById(R.id.meanings_lv);
        history=new ArrayList<String>();

        ap=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,resultList);
        lv.setAdapter(ap);

        lookup=(Button) findViewById(R.id.lookup);
        lookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                 String txt=search.getText().toString();
                if(setList(txt)) {
                   ap.notifyDataSetChanged();
                    history.add(txt);
                /*    Intent intent=new Intent(Intent.ACTION_VIEW);
                    //intent.setData(Uri.parse("mainact://"));
                    Intent chooser=Intent.createChooser(intent,"RUNAPP");
                    startActivity(chooser);
                */}

                }
                catch (Exception e){
                    Log.d("VAL",e.toString());
                }

            }
        });
        i=new Intent(this,savehistoryService.class);
        i.putStringArrayListExtra("searches",history);
        export=(Button)findViewById(R.id.exportbtn);
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(i);
                try{
                    readFromFile();
                }
                catch (Exception e){
                    e.printStackTrace();
                }



            }
        });

    }

    private void readFromFile() throws Exception{

        FileInputStream fin=openFileInput("history.txt");

        int size=fin.available();
        byte []a=new byte[size];
        int x;
        x=fin.read(a);
        String s=new String(a);
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        dbHelper.close();
    }





    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            resultList.clear();
            for (int i = 0; i < ap.getCount(); ++i) {
                resultList.add(ap.getItem(i));
            }

            outState.putStringArrayList("resultList", (ArrayList<String>) resultList);
            // outState.putSerializable("");
            outState.putString("SearchText", search.getText().toString());

            outState.putStringArrayList("history",(ArrayList<String>) history);
        }
        catch (Exception e){
            Log.d("SAVESTATE",e.toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try {
            resultList = savedInstanceState.getStringArrayList("resultList");
            history=savedInstanceState.getStringArrayList("history");
            ap.clear();
            ap.addAll(resultList);
            ap.notifyDataSetChanged();
            search.setText(savedInstanceState.getString("SearchText"));
        }
        catch(Exception e){
            Log.d("RESTORE",e.toString());

        }
    }

    private boolean setList(String typedin) throws Exception{
    boolean r=false;

    String[] args=new String[]{typedin};
    //args[0]=typedin;
    Cursor cursor=dbHelper.query("entries",null,"word like ?",args,null,null,null) ;  //resultList.clear();
    if(cursor!=null && cursor.getCount()>0){
        ap.clear();
        while(cursor.moveToNext()){
            ap.add(cursor.getString(0)+"   "+cursor.getString(1)+'\n'+cursor.getString(2));
            Log.d("VAL",cursor.getString(0)+"   "+cursor.getString(1)+'\n'+cursor.getString(2));
        }
        r=true;
    }

    return r;
    }

}


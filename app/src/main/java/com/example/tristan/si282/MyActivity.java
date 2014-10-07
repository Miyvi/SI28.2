package com.example.tristan.si282;

import android.app.ActionBar;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MyActivity extends ActionBarActivity {
    private JSONObject test=null;


    String res;
    ArrayList<TextView> lst_mots = new ArrayList<TextView>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        test = getJSONObject("text.JSON");
        //text_txt();


        LinearLayout ll = (LinearLayout) findViewById(R.id.linear);






        String tester="Je mange des pommes de terre";
        final String[] phrase=get_mots(tester);
        res="";

        for(int i=0;i<phrase.length;i++)
        {
            final TextView txt = new TextView(this);
            final int pos=i;
            txt.setText(phrase[i]+" ");
            txt.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.FILL_PARENT));
            ll.addView(txt);
            //res+=get_syno(phrase[i])+" ";

            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(get_syno(phrase[pos]).compareTo("")!=0)txt.setText(get_syno(phrase[pos])+" ");
                }
            });
        }




    }

        public String[] get_mots(String chaine)
    {
        String[] res=chaine.split(" ");
        for(int i=0;i<res.length;i++)res[i]=res[i].toLowerCase();
        return res;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public String get_syno(String mot) {
        JSONArray enigme = null;
        String find = "";
        try {
            enigme = test.getJSONArray("liste");

            int i = 0;
            while (find.compareTo("") == 0 && i < enigme.length()) {
                JSONObject ret = enigme.getJSONObject(i);

                if (ret.getString("mot").compareTo(mot) == 0)
                {
                    find=ret.getJSONArray("synonyme").getString(0);
                }

                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return find;
    }
/*
    public void text_txt()
    {
        final TextView textView = (TextView) findViewById(R.id.textView);

        textView.setText("test");

        JSONArray enigme = null;
        try {
            enigme = test.getJSONArray("liste");
            JSONObject ret=enigme.getJSONObject(0);


            textView.setText(ret.getString("mot"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
*/

    public JSONObject getJSONObject(String Fichier)
    {
        BufferedReader reader = null;
        JSONObject parser=null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open(Fichier)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json;
        StringBuilder buff = new StringBuilder();

        try {

            while ((json = reader.readLine()) != null) {
                buff.append(json + "\n");
            }
            try {
                parser = new JSONObject(buff.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parser;
    }
}

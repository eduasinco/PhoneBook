package com.example.eduardorodriguez.phonebook;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    EditText search;
    ListView contact_list;
    ArrayList<SingleItem> data;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = (EditText) findViewById(R.id.search);
        contact_list = (ListView) findViewById(R.id.contact_list);

        new RetrieveContactTask().execute("https://gist.github.com/vladiacob/b751d9e5e92c8926ef4d1ccebac88142");

        data = new ArrayList<>();
        SingleItem item;

        search.addTextChangedListener(this);

        adapter = new Adapter(this, data);
        contact_list.setAdapter(adapter);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        this.adapter.getFilter().filter(s);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    class RetrieveContactTask extends AsyncTask<String, Void, JsonObject> {

        protected JsonObject doInBackground(String... urls) {
            try {
                StringBuilder result = new StringBuilder();
                URL url = new URL("http://www.mocky.io/v2/581335f71000004204abaf83");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                rd.close();

                JsonObject jsonObject = new JsonParser().parse(result.toString()).getAsJsonObject();
                return jsonObject;
            } catch (Exception e) {
                System.out.println("HOLAAAAAA: " + e);
                return null;
            }
        }

        protected void onPostExecute(JsonObject json) {
            System.out.println(json);
            ArrayList<SingleItem> data = new ArrayList<>();
            for(JsonElement je: json.getAsJsonArray("contacts")){
                JsonObject jo = je.getAsJsonObject();
                SingleItem si = new SingleItem(jo.get("name").toString(), jo.get("phone_number").toString(), jo.get("address").toString());
                data.add(si);
            }
            adapter = new Adapter(MainActivity.this, data);
            contact_list.setAdapter(adapter);
        }
    }
}

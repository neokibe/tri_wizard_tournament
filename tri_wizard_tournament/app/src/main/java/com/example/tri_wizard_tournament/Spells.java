package com.example.tri_wizard_tournament;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import static com.example.tri_wizard_tournament.ApplicationClass.HOUSES_URL;
import static com.example.tri_wizard_tournament.ApplicationClass.SPELLS_URL;

public class Spells extends AppCompatActivity {

    ListView lv_spells;
    RequestQueue queue;
    List<Object> id_nums = new ArrayList<>();
    Object charactersBack = new Object();
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spells);

        lv_spells = findViewById(R.id.lv_spells);
        queue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);

        progressDialog.setTitle("Retrieving  Spells");
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final SpellsListAsync spellsListAsync = new SpellsListAsync();
        spellsListAsync.execute();

        lv_spells.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = (int) id;
                Intent intent = new Intent(getApplicationContext(), CharacterActivity.class);
                intent.putExtra("_id", id_nums.get(index).toString());

                startActivity(intent);
            }
        });
    }

    private class SpellsListInner {
        private SpellsListInner() {
            getAllSpells();
        }

        private List<Object> returnAllSpells(List<Object> _id_nums) {
            id_nums = _id_nums;
            return id_nums;
        }

        private List<Object> returnAllSpells() {
            return id_nums;
        }

        private void getAllSpells() {
            StringRequest jsonObjectRequest = new StringRequest
                    (Request.Method.GET, SPELLS_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                List<Object> spells = ApplicationClass.getValuesForGivenKey(response, "spell");
                                SpellsAdapter spellsAdapter = new SpellsAdapter(spells, getApplicationContext());
                                List<Object> id_nums = ApplicationClass.getValuesForGivenKey(response, "_id");
                                lv_spells.setAdapter(spellsAdapter);

                                if (id_nums.size() > 0 && spells.size() > 0) {
                                    returnAllSpells(id_nums);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
            queue.add(jsonObjectRequest);
        }
    }

    public class SpellsListAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);
                new SpellsListInner();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(Void void1) {
            progressDialog.dismiss();
        }
    }
}
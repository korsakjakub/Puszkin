package jakubkorsak.puszkin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class SNPlanView extends AppCompatActivity {

    private ListView list;
    private String pressedItem;
    String sender;
    String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snplan_view);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolbar.setTitleTextColor(Color.WHITE);
        sender = getIntent().getStringExtra("sender");

        list = (ListView) findViewById(R.id.lista);
        ArrayList<String> arrayList = new ArrayList<>();

        //ładowanie danych do listy i nadanie nazwy toolbarowi
        if(sender.equals("Gabinety")){
            arrayList.addAll(Arrays.asList(Sources.Gabinety));
            toolbar.setTitle("Gabinety");
        }else if(sender.equals("Nauczyciele")){
            toolbar.setTitle("Nauczyciele");
            arrayList.addAll(Arrays.asList(Sources.Nauczyciele));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row, arrayList);

        list.setAdapter(adapter);
        list.setClickable(true);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View v, int position, long id){

                pressedItem = list.getItemAtPosition(position).toString();

                //np "Jan Kowalski" -> "n1"
                if(sender.equals("Gabinety")){
                    tag = Sources.getID(pressedItem, "s", Sources.Gabinety);
                }else
                if(sender.equals("Nauczyciele")){
                    tag = Sources.getID(pressedItem, "n", Sources.Nauczyciele);
                }


                Intent goToPlanView = new Intent(SNPlanView.this, PlanView.class);
                Bundle b = new Bundle();
                b.putString(Sources.SENDER_ACTIVITY, Sources.TYPE_OF_WEB_VIEW[0]);
                b.putString(Sources.TAG, tag);
                goToPlanView.putExtras(b);
                startActivity(goToPlanView);
            }

        });

    }
}

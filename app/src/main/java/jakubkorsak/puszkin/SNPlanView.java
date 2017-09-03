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

public class SNPlanView extends AppCompatActivity {

    String sender;
    String tag;
    private ListView list;
    private String pressedItem;

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
        final String nauczycieleRaw = FileHandling.readFileAsString("nauczyciele", getApplicationContext());


        // List nauczyciele = new ArrayList();
        // nauczyciele.addAll(Arrays.asList(nauczycieleRaw.split("\n")));
        // nauczyciele.addAll(nauczycieleRaw.split("\n"));

        list = (ListView) findViewById(R.id.lista);

        //List<String> output = Arrays.asList(nauczycieleRaw.split("\n"));
        final String[] output;

        //ładowanie danych do listy i nadanie nazwy toolbarowi
        if(sender.equals("Gabinety")){
            toolbar.setTitle("Gabinety");
            output = Sources.Gabinety;
        } else {
            toolbar.setTitle("Nauczyciele");
            output = nauczycieleRaw.split("\\)");
            for (int i = 0; i <= output.length - 1; i++) {
                output[i] += ")";
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row, output);

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
                    tag = Sources.getID(pressedItem, "n", output);
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

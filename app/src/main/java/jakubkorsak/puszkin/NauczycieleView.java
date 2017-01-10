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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class NauczycieleView extends AppCompatActivity {
    private ListView list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nauczyciele);
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
        list = (ListView) findViewById(R.id.listView1);
        ArrayList<String> carL = new ArrayList<>();
        carL.addAll(Arrays.asList(Sources.Nauczyciele));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row, carL);
        list.setAdapter(adapter);
        list.setClickable(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Object sender = list.getItemAtPosition(position);
                String str = Sources.getID((String) sender, "n", Sources.Nauczyciele);
                Intent intent = new Intent(NauczycieleView.this, ParsedHTMLViewer.class);//WebViewActivity.class);
                Bundle b = new Bundle();
                b.putString(Sources.SENDER_ACTIVITY, Sources.TYPE_OF_WEB_VIEW[0]);
                b.putString(Sources.TAG, str);
                intent.putExtras(b);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Wybrano: " + sender, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

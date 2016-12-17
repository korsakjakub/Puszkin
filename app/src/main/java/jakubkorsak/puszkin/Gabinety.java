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

public class Gabinety extends AppCompatActivity {
    private ListView list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gabinety);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert toolbar != null;
        toolbar.setTitleTextColor(Color.WHITE);

        list = (ListView) findViewById(R.id.listView1);

        String clasess[] = {
                "j.polski (1)",
                "j.polski (14)",
                "j.polski (15)",
                "język obcy (17)",
                "j.niemiecki (26)",
                "matematyka (27)",
                "j. angielski (28)",
                "j. angielski (29)",
                "j. angielski (30)",
                "j.polski (31)",
                "j. angielski (32)",
                "historia/po (37)",
                "historia (38)",
                "inf./j. niem (39)",
                "informatyka/ F.inf. (40)",
                "informatyka (41)",
                "biologia (42)",
                "j. obcy (43)",
                "geografia (47)",
                "fizyka/matem. (48)",
                "fizyka/matemat. (49)",
                "chemia (51)",
                "matematyka (52)",
                "j. angielski (54)",
                "religia (SK)",
                "WF (G1)",
                "WF (G2)",
                "WF (G3)",
                "WF (HS1)",
                "l. wych (SU)",
                "j. polski (5)",
                "j. obcy (czyt)"
        };

        ArrayList<String> carL = new ArrayList<>();
        carL.addAll( Arrays.asList(clasess) );

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row, carL);

        list.setAdapter(adapter);
        list.setClickable(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Object o = list.getItemAtPosition(position);
                String str = (String)o;
                Toast.makeText(getApplicationContext(),"Wybrano: "+str,Toast.LENGTH_SHORT).show();
                /**
                * tutaj przechwytuję Sender i wpisuję go do String
                * */

                Intent intent = new Intent(Gabinety.this,InnePlany.class);
                intent.putExtra("nauczyciel",str);
                startActivity(intent);
            }
        });
    }

}

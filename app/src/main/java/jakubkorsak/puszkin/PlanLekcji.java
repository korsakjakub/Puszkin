package jakubkorsak.puszkin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class PlanLekcji extends AppCompatActivity {

    private static final int[] BUTTON_IDS = {
            R.id.klasa1,
            R.id.klasa2,
            R.id.klasa3,
            R.id.klasa4,
            R.id.klasa5,
            R.id.klasa6,
            R.id.klasa7,
            R.id.klasa8,
            R.id.klasa9,
            R.id.klasa10,
            R.id.klasa11,
            R.id.klasa12,
            R.id.klasa13,
            R.id.klasa14,
            R.id.klasa15,
            R.id.klasa16,
            R.id.klasa17,
            R.id.klasa18,
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_lekcji);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert toolbar != null;
        toolbar.setTitleTextColor(Color.WHITE);

        List<Button> buttons = new ArrayList<>(BUTTON_IDS.length);
        for(int id : BUTTON_IDS){
            Button button = (Button)findViewById(id);
            assert button != null;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c(v);
                }
            });
            buttons.add(button);
        }


        Button nauczyciele = (Button)findViewById(R.id.Nauczyciele);
        assert nauczyciele != null;
        nauczyciele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanLekcji.this , Nauczyciele.class);
                startActivity(intent);
            }
        });
        Button gabinety = (Button)findViewById(R.id.gabinety);
        assert gabinety != null;
        gabinety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanLekcji.this , Gabinety.class);
                startActivity(intent);
            }
        });
    }
    void c(View v){

        Intent intentPlain = new Intent(PlanLekcji.this,PlainView.class);
        Button button = (Button) v;


        String tag = button.getTag().toString();

        FileHandler.writeStringAsFile(tag,"save",getApplicationContext());

        Bundle b = new Bundle();
        b.putString("tag", tag);

        intentPlain.putExtras(b);
        startActivity(intentPlain);

    }
}

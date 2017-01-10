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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolbar.setTitleTextColor(Color.WHITE);

        /*
         * Tablica z przyciskami (jest ich dużo ok?)
         * używam pętli for żeby oszczędzić miejsca
         */
        List<Button> buttons = new ArrayList<>(BUTTON_IDS.length);
        for(int id : BUTTON_IDS){
            Button button = (Button)findViewById(id);
            assert button != null;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonOnClick(v);
                }
            });
            buttons.add(button);
        }
        Button nauczyciele = (Button)findViewById(R.id.Nauczyciele);
        assert nauczyciele != null;
        nauczyciele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanLekcji.this, NauczycieleView.class);
                startActivity(intent);
            }
        });
        Button gabinety = (Button)findViewById(R.id.gabinety);
        assert gabinety != null;
        gabinety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanLekcji.this, SaleView.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Inicjujemy przycisk, zapisujemy do "tag" jego tag po czym wpisujemy do pamięci urządzenia
     * intentem przenosimy tag i SENDER_ACTIVITY
     * zmieniamy activity
     *
     * @param v nasz sender
     */
    void buttonOnClick(View v) {
        Intent intentPlain = new Intent(PlanLekcji.this, ParsedHTMLViewer.class);//WebViewActivity.class);
        Button button = (Button) v;
        String tag = Sources.getID(button.getTag().toString(), "o", Sources.index);
        FileHandling.writeStringAsFile(Sources.getIndex(tag, "o", Sources.index), Sources.zrodla[0], getApplicationContext());
        Bundle b = new Bundle();
        b.putString(Sources.TAG, tag);
        b.putString(Sources.SENDER_ACTIVITY, Sources.TYPE_OF_WEB_VIEW[0]);
        intentPlain.putExtras(b);
        startActivity(intentPlain);

    }
}

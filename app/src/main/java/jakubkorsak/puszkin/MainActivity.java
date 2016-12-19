package jakubkorsak.puszkin;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button planSave;
    TextView planSaveText;
    Button zastepstwa;
    Button planLekcji;
    Button harmonogram;
    Button dzienniczek;
    Button settings;
    Button facebook;
    Button puszkinowaPomoc;
    Button twojaKlasa;
    TextView twojaKlasaText;

    int onBackCounter = 0;

    Toolbar toolbar;

    public static String revCH(String ch) {
        switch (ch.toUpperCase()) {
            case "1A":
                ch = "1";
                break;
            case "1B":
                ch = "2";
                break;
            case "1C":
                ch = "3";
                break;
            case "1D":
                ch = "4";
                break;
            case "1E":
                ch = "5";
                break;
            case "1F":
                ch = "6";
                break;
            case "2A":
                ch = "7";
                break;
            case "2B":
                ch = "8";
                break;
            case "2C":
                ch = "9";
                break;
            case "2D":
                ch = "10";
                break;
            case "2E":
                ch = "11";
                break;
            case "2F":
                ch = "12";
                break;
            case "3A":
                ch = "13";
                break;
            case "3B":
                ch = "14";
                break;
            case "3C":
                ch = "15";
                break;
            case "3D":
                ch = "16";
                break;
            case "3E":
                ch = "17";
                break;
            case "3F":
                ch = "18";
                break;

        }
        return ch;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert toolbar != null;
        toolbar.setTitleTextColor(Color.WHITE);


        planLekcji= (Button) findViewById(R.id.plan_lekcji);
        assert planLekcji != null;
        planLekcji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlanLekcji.class);
                startActivity(intent);
            }
        });

        zastepstwa = (Button) findViewById(R.id.zastepstwa);
        assert zastepstwa != null;
        zastepstwa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Zastepstwa.class);
                startActivity(intent);
            }
        });

        harmonogram = (Button) findViewById(R.id.harmonogram);
        assert harmonogram != null;
        harmonogram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Harmonogram.class);
                startActivity(intent);
            }
        });

        dzienniczek = (Button) findViewById(R.id.dzienniczek);
        assert dzienniczek != null;
        dzienniczek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Dzienniczek.class);
                startActivity(intent);
            }
        });

        planSaveText = (TextView)findViewById(R.id.text_ostatnia);
        planSave = (Button) findViewById(R.id.plan_save);
        setTextAndTagAndSetVisibilityOfTheButtonsView(planSave, planSaveText, Settings.zrodla[1]);
        planSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlainView.class);
                Button button = (Button) v;
                    String tag = button.getTag().toString();
                    Bundle b = new Bundle();
                    b.putString("tag", revCH(tag));
                    intent.putExtras(b);
                    startActivity(intent);
            }
        });

        twojaKlasa = (Button)findViewById(R.id.twoja_klasa);
        twojaKlasaText = (TextView)findViewById(R.id.text_twoja);
        setTextAndTagAndSetVisibilityOfTheButtonsView(twojaKlasa, twojaKlasaText, Settings.zrodla[1]);
        twojaKlasa.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, PlainView.class);
                Button button = (Button) v;
                String tag = button.getTag().toString();
                Bundle b = new Bundle();
                b.putString("tag", revCH(tag));
                intent.putExtras(b);
                startActivity(intent);
            }
        });


        settings = (Button) findViewById(R.id.info);
        assert settings != null;
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }
        });

        facebook = (Button) findViewById(R.id.facebook);
        assert facebook != null;
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/n/?puszkingorzow";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(url));
                startActivity(browserIntent);
            }
        });
        puszkinowaPomoc = (Button) findViewById(R.id.facebook2);
        assert puszkinowaPomoc != null;
        puszkinowaPomoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/groups/puszkinowapomoc";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(url));
                startActivity(browserIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetOnBackCounter();
        setTextAndTagAndSetVisibilityOfTheButtonsView(planSave, planSaveText, Settings.zrodla[0]);
        setTextAndTagAndSetVisibilityOfTheButtonsView(twojaKlasa, twojaKlasaText, Settings.zrodla[1]);
    }

    @Override
    public void onBackPressed() {

        onBackCounter++;
        if (onBackCounter == 1)
            Toast.makeText(getApplicationContext(), "Naciśnij jeszcze raz aby wyjść", Toast.LENGTH_SHORT).show();
        if (onBackCounter > 1) {
            finish();
            resetOnBackCounter();
        }
    }

    public String ch(String ch) {
        switch (ch) {
            case "1":
                ch = "1A";
                break;
            case "2":
                ch = "1B";
                break;
            case "3":
                ch = "1C";
                break;
            case "4":
                ch = "1D";
                break;
            case "5":
                ch = "1E";
                break;
            case "6":
                ch = "1F";
                break;
            case "7":
                ch = "2A";
                break;
            case "8":
                ch = "2B";
                break;
            case "9":
                ch = "2C";
                break;
            case "10":
                ch = "2D";
                break;
            case "11":
                ch = "2E";
                break;
            case "12":
                ch = "2F";
                break;
            case "13":
                ch = "3A";
                break;
            case "14":
                ch = "3B";
                break;
            case "15":
                ch = "3C";
                break;
            case "16":
                ch = "3D";
                break;
            case "17":
                ch = "3E";
                break;
            case "18":
                ch = "3F";
                break;
        }
        return ch;
    }

    private void setTextAndTagAndSetVisibilityOfTheButtonsView(Button button, TextView textView, String source){

        String h = ch(FileHandler.readFileAsString(source, getApplicationContext()));
        button.setText(h);
        button.setTag(h);
        if (button.getText() == "" || button.getText() == null) {
            button.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        } else {
            button.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    private void resetOnBackCounter(){
        onBackCounter = 0;
    }


}



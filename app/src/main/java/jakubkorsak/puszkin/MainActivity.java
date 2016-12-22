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
                ch = "o1";
                break;
            case "1B":
                ch = "o2";
                break;
            case "1C":
                ch = "o3";
                break;
            case "1D":
                ch = "o4";
                break;
            case "1E":
                ch = "o5";
                break;
            case "1F":
                ch = "o6";
                break;
            case "2A":
                ch = "o7";
                break;
            case "2B":
                ch = "o8";
                break;
            case "2C":
                ch = "o9";
                break;
            case "2D":
                ch = "o10";
                break;
            case "2E":
                ch = "o11";
                break;
            case "2F":
                ch = "o12";
                break;
            case "3A":
                ch = "o13";
                break;
            case "3B":
                ch = "o14";
                break;
            case "3C":
                ch = "o15";
                break;
            case "3D":
                ch = "o16";
                break;
            case "3E":
                ch = "o17";
                break;
            case "3F":
                ch = "o18";
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


        planLekcji = (Button) findViewById(R.id.plan_lekcji);
        assert planLekcji != null;
        planLekcji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlanLekcji.class);
                startActivity(intent);
            }
        });

        zastepstwa = (Button) findViewById(R.id.zastepstwa);
        zastepstwa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlainView.class);
                Bundle b = new Bundle();
                b.putString("senderActivity", "zastepstwa");
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        harmonogram = (Button) findViewById(R.id.harmonogram);
        harmonogram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlainView.class);
                Bundle b = new Bundle();
                b.putString("senderActivity", "harmonogram");
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        dzienniczek = (Button) findViewById(R.id.dzienniczek);
        dzienniczek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlainView.class);
                Bundle b = new Bundle();
                b.putString("senderActivity", "dzienniczek");
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        planSaveText = (TextView) findViewById(R.id.text_ostatnia);
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
                b.putString("senderActivity", "plan");
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        twojaKlasa = (Button) findViewById(R.id.twoja_klasa);
        twojaKlasaText = (TextView) findViewById(R.id.text_twoja);
        setTextAndTagAndSetVisibilityOfTheButtonsView(twojaKlasa, twojaKlasaText, Settings.zrodla[1]);
        twojaKlasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlainView.class);
                Button button = (Button) v;
                String tag = button.getTag().toString();
                Bundle b = new Bundle();
                b.putString("tag", revCH(tag));
                b.putString("senderActivity", "plan");
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
            case "o1":
                ch = "1A";
                break;
            case "o2":
                ch = "1B";
                break;
            case "o3":
                ch = "1C";
                break;
            case "o4":
                ch = "1D";
                break;
            case "o5":
                ch = "1E";
                break;
            case "o6":
                ch = "1F";
                break;
            case "o7":
                ch = "2A";
                break;
            case "o8":
                ch = "2B";
                break;
            case "o9":
                ch = "2C";
                break;
            case "o10":
                ch = "2D";
                break;
            case "o11":
                ch = "2E";
                break;
            case "o12":
                ch = "2F";
                break;
            case "o13":
                ch = "3A";
                break;
            case "o14":
                ch = "3B";
                break;
            case "o15":
                ch = "3C";
                break;
            case "o16":
                ch = "3D";
                break;
            case "o17":
                ch = "3E";
                break;
            case "o18":
                ch = "3F";
                break;
        }
        return ch;
    }

    private void setTextAndTagAndSetVisibilityOfTheButtonsView(Button button, TextView textView, String source) {

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

    private void resetOnBackCounter() {
        onBackCounter = 0;
    }


}



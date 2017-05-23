package jakubkorsak.puszkin;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    Button planSaveButton;
    TextView planSaveText;
    Button zastepstwa;
    Button planLekcji;
    Button harmonogram;
    Button dzienniczek;
    Button settingsButton;
    Button facebookButtonILO;
    Button facebookButtonPuszkinowaPomoc;
    Button twojaKlasaButton;
    TextView twojaKlasaText;

    int onBackCounter = 0;

    Toolbar toolbar;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

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
                Intent intent = new Intent(MainActivity.this, HarmonogramZastepstwa.class);
                Bundle b = new Bundle();
                b.putString(Sources.SENDER_ACTIVITY, Sources.TYPE_OF_WEB_VIEW[2]);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        harmonogram = (Button) findViewById(R.id.harmonogram);
        harmonogram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HarmonogramZastepstwa.class);
                Bundle b = new Bundle();
                b.putString(Sources.SENDER_ACTIVITY, Sources.TYPE_OF_WEB_VIEW[1]);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        dzienniczek = (Button) findViewById(R.id.dzienniczek);
        dzienniczek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "dzienniczek_button");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "dzienniczek");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "button");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                Intent intent = new Intent(MainActivity.this, DzienniczekView.class);
                Bundle b = new Bundle();
                b.putString(Sources.SENDER_ACTIVITY, Sources.TYPE_OF_WEB_VIEW[3]);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        /**bierze szerokość ekranu, żeby przyciski planSaveButton i twojaKlasaButton zajmowały całą szerokość
         * kiedy widoczny jest tylko jeden z nich i dzieliły się po 50% kiedy widoczne są oba
         */
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;


        /**
         * przycisk wyświetlający ostatnio przeglądaną klasę (z wyłączeniem klasy wynikającej z naciśnięcia
         * twojaKlasaButton
         */
        planSaveText = (TextView) findViewById(R.id.text_ostatnia);
        planSaveButton = (Button) findViewById(R.id.plan_save);
        planSaveButton.setWidth(width);
        setTextAndTagAndSetVisibilityOfTheButtonsView(planSaveButton, planSaveText, Sources.zrodla[1]);
        planSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlanView.class);
                String tag = Sources.getID(FileHandling.readFileAsString(Sources.zrodla[0],
                        getApplicationContext()), "o", Sources.klasy);
                Bundle b = new Bundle();
                b.putString(Sources.TAG, tag);
                b.putString(Sources.SENDER_ACTIVITY, Sources.TYPE_OF_WEB_VIEW[0]);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        /**
        przycisk wyświetlający zapisaną w ustawieniach klasę.
         */
        twojaKlasaButton = (Button) findViewById(R.id.twoja_klasa);
        twojaKlasaText = (TextView) findViewById(R.id.text_twoja);
        twojaKlasaButton.setWidth(width);
        setTextAndTagAndSetVisibilityOfTheButtonsView(twojaKlasaButton, twojaKlasaText, Sources.zrodla[1]);
        twojaKlasaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlanView.class);
                String tag = Sources.getID(FileHandling.readFileAsString(Sources.zrodla[1],
                        getApplicationContext()), "o", Sources.klasy);
                Bundle b = new Bundle();
                b.putString(Sources.TAG, tag);
                b.putString(Sources.SENDER_ACTIVITY, Sources.TYPE_OF_WEB_VIEW[0]);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        settingsButton = (Button) findViewById(R.id.info);
        assert settingsButton != null;
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }
        });

        /**
         * deep-link do aplikacji Facebook do strony szkoły
         */
        facebookButtonILO = (Button) findViewById(R.id.facebook);
        assert facebookButtonILO != null;
        facebookButtonILO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/n/?puszkingorzow";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(url));
                startActivity(browserIntent);
            }
        });
        /**
         * deep-link do aplikacji Facebook do grupy uczniowskiej (~500-600 osób)
         */
        facebookButtonPuszkinowaPomoc = (Button) findViewById(R.id.facebook2);
        assert facebookButtonPuszkinowaPomoc != null;
        facebookButtonPuszkinowaPomoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/groups/puszkinowapomoc";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(url));
                startActivity(browserIntent);
            }
        });
    }

    /**
     * sprawdza czy planSaveButton i twojaKlasaButton nie wyprodukują NullPointerException
     */
    @Override
    protected void onResume() {
        super.onResume();
        resetOnBackCounter();
        setTextAndTagAndSetVisibilityOfTheButtonsView(planSaveButton, planSaveText, Sources.zrodla[0]);
        setTextAndTagAndSetVisibilityOfTheButtonsView(twojaKlasaButton, twojaKlasaText, Sources.zrodla[1]);
    }

    /**
     * dodaję ostrzeżenie przed omylnym wyjściem z aplikacji
     */
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

    /**
     * Sprawdza wartości nazw i tagów przycisków po czym odpowiednio ustawia ich widoczność
     *
     * @param button   przycisk który ma być sprawdzony
     * @param textView tekst który znajduje się webView jednym layoucie z przyciskiem
     * @param source   String który mówi funkcji o który przycisk chodzi
     */
    private void setTextAndTagAndSetVisibilityOfTheButtonsView(Button button, TextView textView, String source) {

        String h = FileHandling.readFileAsString(source, getApplicationContext());
        button.setText(h);
        if (button.getText() == "" || button.getText() == null) {
            button.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        } else {
            button.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * sprowadza onBackCounter do 0
     */
    private void resetOnBackCounter() {
        onBackCounter = 0;
    }


}



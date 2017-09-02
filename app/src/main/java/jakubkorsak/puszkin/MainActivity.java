package jakubkorsak.puszkin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {

    Button planSaveButton;
    TextView planSaveText;
    Button twojaKlasaButton;
    TextView twojaKlasaText;

    int onBackCounter = 0;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert toolbar != null;
        toolbar.setTitleTextColor(Color.WHITE);

        if (!checkInternet())
            Toast.makeText(this, "Nie mam dostępu do internetu", Toast.LENGTH_LONG).show();
        else {
            new getNauczyciele().execute();
        }

        setUpButtons();

    }

    /**
     * sprawdza czy planSaveButton i twojaKlasaButton nie wyprodukują NullPointerException
     */
    @Override
    protected void onResume() {
        super.onResume();
        resetOnBackCounter();
        buttonsVisibility(planSaveButton, planSaveText, Sources.zrodla[0]);
        buttonsVisibility(twojaKlasaButton, twojaKlasaText, Sources.zrodla[1]);
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
    private void buttonsVisibility(Button button, TextView textView, String source) {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, Settings.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpButtons() {

        findViewById(R.id.plan_lekcji).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlanLekcji.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.zastepstwa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HarmonogramZastepstwa.class);
                Bundle b = new Bundle();
                b.putString(Sources.SENDER_ACTIVITY, Sources.TYPE_OF_WEB_VIEW[2]);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        findViewById(R.id.harmonogram).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HarmonogramZastepstwa.class);
                Bundle b = new Bundle();
                b.putString(Sources.SENDER_ACTIVITY, Sources.TYPE_OF_WEB_VIEW[1]);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        findViewById(R.id.dzienniczek).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, DzienniczekView.class);
                Bundle b = new Bundle();
                b.putString(Sources.SENDER_ACTIVITY, Sources.TYPE_OF_WEB_VIEW[3]);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        /*
            bierze szerokość ekranu, żeby przyciski planSaveButton i twojaKlasaButton zajmowały całą szerokość
            kiedy widoczny jest tylko jeden z nich i dzieliły się po 50% kiedy widoczne są oba
         */
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;


        /*
            przycisk wyświetlający ostatnio przeglądaną klasę (z wyłączeniem klasy wynikającej z naciśnięcia
            twojaKlasaButton
         */
        planSaveText = (TextView) findViewById(R.id.text_ostatnia);
        planSaveButton = (Button) findViewById(R.id.plan_save);
        planSaveButton.setWidth(width);
        buttonsVisibility(planSaveButton, planSaveText, Sources.zrodla[1]);
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

        /*
            przycisk wyświetlający zapisaną w ustawieniach klasę.
         */
        twojaKlasaButton = (Button) findViewById(R.id.twoja_klasa);
        twojaKlasaText = (TextView) findViewById(R.id.text_twoja);
        twojaKlasaButton.setWidth(width);
        buttonsVisibility(twojaKlasaButton, twojaKlasaText, Sources.zrodla[1]);
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

        findViewById(R.id.info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }
        });

        /*
            deep-link do aplikacji Facebook do strony szkoły
         */
        findViewById(R.id.facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/n/?puszkingorzow";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(url));
                startActivity(browserIntent);
            }
        });
        /*
            deep-link do aplikacji Facebook do grupy uczniowskiej (~500-600 osób)
         */
        findViewById(R.id.facebook2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/groups/puszkinowapomoc";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(url));
                startActivity(browserIntent);
            }
        });
    }

    private boolean checkInternet() {
        return ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    private class getNauczyciele extends AsyncTask<Void, Void, Void> {

        String p = "http://plan.1lo.gorzow.pl/lista.html";
        boolean success;


        @Override
        protected Void doInBackground(Void... params) {
            //plik do którego będzie zapisywane
            String fileName = "nauczyciele";
            String output = "";

            //jeśli nie istnieje ściągnąć z JSoup
            Document doc;
            try {
                doc = Jsoup.connect(p).get();
                Elements nauczyciele = doc.select("a[href*=plany/n]");
                for (Element nauczyciel : nauczyciele) {
                    output = output.concat(nauczyciel.text() + " \n ");
                }
                FileHandling.writeStringAsFile(
                        output,
                        fileName,
                        getApplicationContext());

                success = true;
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Nie mam dostępu do internetu", Toast.LENGTH_SHORT).show();
                success = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (success) {
                Log.i("async task", "Pobrano listę nauczycieli");
            }

        }
    }

}



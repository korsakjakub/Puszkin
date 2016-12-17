package jakubkorsak.puszkin;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Ta klasa typu AppCompatActivity zawiera elementy GUI oraz ich metody onClick
 * Przy pierwszym uruchomieniu przycisk "planSave" będzie nie widoczny ze względu na brak
 * źródła to którego mógłby się odwołać
 *
 * PuszkinowaPomoc jest ZAMKNIĘTĄ uczniowską grupą na facebooku. Aby jej content dobrze się wyświetlał
 * trzeba być zalogowanym na konto do niej należące.
 */ 
public class MainActivity extends AppCompatActivity {

    Button planSave;
    Button zastepstwa;
    Button planLekcji;
    Button harmonogram;
    Button dzienniczek;
    Button settings;
    Button facebook;
    Button puszkinowaPomoc;

    /**
     * zastosowanie tego inta niżej.
     */
    int onBackCounter = 0;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Inicjuję toolbar i zmieniam kolor jego tekstu
         */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert toolbar != null;
        toolbar.setTitleTextColor(Color.WHITE);


        /**
         * przycisk służący do zmiany Activity MainActivity -> PlanLekcji
         */
        planLekcji= (Button) findViewById(R.id.plan_lekcji);
        assert planLekcji != null;
        planLekcji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlanLekcji.class);
                startActivity(intent);
            }
        });

        /**
         * przycisk służący do zmiany Activity MainActivity -> Zastepstwa
         */

        zastepstwa = (Button) findViewById(R.id.zastepstwa);
        assert zastepstwa != null;
        zastepstwa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Zastepstwa.class);
                startActivity(intent);
            }
        });

        /**
         * przycisk służący do zmiany Activity MainActivity -> Harmonogram
         */
        harmonogram = (Button) findViewById(R.id.harmonogram);
        assert harmonogram != null;
        harmonogram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Harmonogram.class);
                startActivity(intent);
            }
        });

        /**
         * przycisk służący do zmiany Activity MainActivity -> Dzienniczek
         */
        dzienniczek = (Button) findViewById(R.id.dzienniczek);
        assert dzienniczek != null;
        dzienniczek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Dzienniczek.class);
                startActivity(intent);
            }
        });

        /**
         * tu inicjowany jest przycisk planSave. Służy on do szybkiego powrotu do strony internetowej
         * z planem lekcji klasy, która była ostatnio odwiedzana. Jego Tekst i Tag są ściągane z pliku
         * znajdującego się w getFilesDir()+"/save". Aby sprawdzić content tego pliku proszę udać sie do
         * PlanLekcji.java.
         *
         */
        planSave = (Button) findViewById(R.id.plan_save);
        setTextAndTagAndSetVisibilityOfTheButtonsView();
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


        /**
         * przycisk służący do zmiany Activity MainActivity -> Settings
         */
        settings = (Button) findViewById(R.id.info);
        assert settings != null;
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }
        });

        /**
         * przycisk służący do deep-linkowania do aplikacji Facebook. W razie braku Facebooka na
         * urządzeniu, zostanie otwarta domyślna przeglądarka.
         */
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
        /**
         * przycisk służący do deep-linkowania do aplikacji Facebook. W razie braku Facebooka na
         * urządzeniu, zostanie otwarta domyślna przeglądarka.
         */
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

    /**
     * onResume zajmuje się przywróceniem onBackCounter do wartości 0 oraz aktualizowaniem
     * Textu i Tagu planSave.
     */
    @Override
    protected void onResume() {
        super.onResume();
        resetOnBackCounter();
        setTextAndTagAndSetVisibilityOfTheButtonsView();
    }

    /**
     * Tu zaimplementowałem prośbę o podwójne kliknięcie BackButton w celu zamknięcia aplikacji.
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
     *
     * @param ch następne 2 metody są prostymi switch-casami pomagającymi ujednolicenie Przesyłanych
     *           w intentach wartości Text i Tag
     * @return zwraca zmienioną wartość ch.
     */
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
    public String revCH(String ch) {
        switch (ch) {
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

    /**
     * nazwa metody jest self-explaining
     */
    private void setTextAndTagAndSetVisibilityOfTheButtonsView(){

        String h = ch(FileHandler.readFileAsString("save", getApplicationContext()));
        planSave.setText(h);
        planSave.setTag(h);
        if (planSave.getText() == "" || planSave.getText() == null) {
            planSave.setVisibility(View.GONE);
        } else {
            planSave.setVisibility(View.VISIBLE);
        }
    }

    /**
     * zmienia wartość onBackCounter do 0.
     */
    private void resetOnBackCounter(){
        onBackCounter = 0;
    }


}



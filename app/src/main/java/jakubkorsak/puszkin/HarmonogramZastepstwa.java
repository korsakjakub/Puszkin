package jakubkorsak.puszkin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public class HarmonogramZastepstwa extends AppCompatActivity {

    String path;
    TextView textView;
    String senderActivity;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harmonogram_zastepstwa);

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

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);
        textView = (TextView)findViewById(R.id.textView);
        textView.setVisibility(View.GONE);

        senderActivity = getIntent().getExtras().getString(Sources.SENDER_ACTIVITY);
        assert senderActivity != null;
        switch (senderActivity) {
            case "harmonogram":
                path = "http://www.zso1.edu.gorzow.pl/?lng=1&view=k&k=23";
                toolbar.setTitle("Harmonogram");
                new GetPlanInBackground().execute();
                break;
            case "zastepstwa":
                path = "http://www.zso1.edu.gorzow.pl/?lng=1&view=k&k=20";
                toolbar.setTitle("Zastępstwa");
                new GetPlanInBackground().execute();
                break;
        }
    }


    public class GetPlanInBackground extends AsyncTask<Void, Void, Void> {
        String taskOutput;
        String containerString;

        @Override
        protected Void doInBackground(Void... params) {

            try {
                //pobiera Obiekt docoment z adresu path
                Document document = Jsoup.connect(path).timeout(10000).get();
                //prettyPrint(false) pozostawia whitespaces
                document.outputSettings(new Document.OutputSettings().prettyPrint(false));
                //zaznacza klasy "br" i dodaje na końcu każdej "\\n"
                document.select("br");//.append("\\n");
                //to samo z klasami "path", ale dodaje na początku
                document.select("path");//.prepend("\\n\\n");


                switch (senderActivity) {
                    //jeżeli użytkownik nacisnął zastępstwa
                    case "zastepstwa":
                        //wyszukuje wszystkie obiekty zawierające frazę "Zastępstwa", zaznacza
                        //w tych obszarach klasy "tekst" i zamienia pierwszy przypadek "Zastępstwa"
                        //na pusty element <- na stronie internetowej napis jest zawsze 2 razy.
                        containerString = document.getElementsContainingText("Zastępstwa")
                                .select("div.tekst")
                                .html().replaceFirst("Zastępstwa", "");
                        break;

                    case "harmonogram":
                        //jak wyżej, ale zaznacza obiekty "p", ponieważ 2 bliźniacze strony
                        //internetowe jakimś cudem mogą mieć zupełnie różnie wprowadzane dane smh...
                        containerString = document.getElementsContainingText("Harmonogram")
                                .select("p")
                                .html();
                        //do sprawdzających: jeżeli nie działa to znaczy, że kod źródłowy strony znowu
                        //się zmienił...
                        break;
                }
                //Tutaj zarządzane są wszystkie whitespaces i usuwam napis "drukuj" bo nie pasuje do kontekstu
                taskOutput = Jsoup.clean(containerString
                                .replaceAll("\\\\n", "\n")
                                .replaceAll("\r", "")
                                .replaceAll("<sup>", ":")
                                .replaceAll("&nbsp;", " ")
                                .replaceAll("drukuj", "")
                                .replaceAll("\\n\\n", "\n")
                        , "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
            } catch (Exception e) {
                e.printStackTrace();
                //w razie errorów użytkownik przynajmniej dowie się co było nie tak
                taskOutput = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //jeśli wszystko się udało nasze textView dostaje nowy kontent w postaci taskOutput

            spinner.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            textView.setText(taskOutput);
        }
    }

}


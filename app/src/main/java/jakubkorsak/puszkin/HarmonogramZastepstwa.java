package jakubkorsak.puszkin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HarmonogramZastepstwa extends AppCompatActivity {

    String path;
    TextView textView;
    String senderActivity;
    private ProgressBar spinner;
    SwipeRefreshLayout swipeRefreshLayout;
    String SERVER_DOWN = "Bardzo serdecznie pozdrawiam szkolne serwery, które znowu nie działają.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harmonogram_zastepstwa);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        spinner = findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);
        textView = findViewById(R.id.textView);
        textView.setVisibility(View.GONE);
        swipeRefreshLayout = findViewById(R.id.swipe);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (textView.getText().equals(SERVER_DOWN)) {
                    new GetPlanInBackground().execute();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        senderActivity = getIntent().getExtras().getString(Sources.SENDER_ACTIVITY);
        assert senderActivity != null;
        switch (senderActivity) {
            case "harmonogram":
                //path = "http://www.zso1.edu.gorzow.pl/?lng=1&view=k&k=23";
                path = "http://www.zso1.edu.gorzow.pl/print.php5?view=k&lng=1&k=23&t=1559&short=1";
                toolbar.setTitle("Harmonogram");
                new GetPlanInBackground().execute();
                break;
            case "zastepstwa":
                //path = "http://www.zso1.edu.gorzow.pl/?lng=1&view=k&k=20";
                path = "http://www.zso1.edu.gorzow.pl/print.php5?view=k&lng=1&k=20&t=4&short=1";
                toolbar.setTitle("Zastępstwa");
                new GetPlanInBackground().execute();
                break;
        }
    }


    private class GetPlanInBackground extends AsyncTask<Void, Void, Void> {
        //TODO: Change that to HttpURLConnection https://developer.android.com/reference/java/net/HttpURLConnection
        String taskOutput;
        String containerString;


        @Override
        protected Void doInBackground(Void... params) {


            try {
                URL url = new URL(path);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                //taskOutput = IOUtils.toString(in.getInputStream(), "UTF-8");
                taskOutput = new String(ByteStreams.toByteArray(in), Charsets.UTF_8);
            } catch (Exception e) {
                taskOutput = SERVER_DOWN;
            }

            if (!taskOutput.equals(SERVER_DOWN)) {
                Document document = Jsoup.parse(taskOutput);


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
                        break;
                }

                //Tutaj zarządzane są wszystkie whitespaces i usuwam napis "drukuj" bo nie pasuje do kontekstu
                taskOutput = Jsoup.clean(containerString
                                .replaceAll("&nbsp;", "")
                                .replaceAll("\n", "\n\n")

                        // sounds good, but doesn't work
                        //.replaceAll("(((P|p)(oniedzia)(l|ł)ek))|(W|w)(torek)|(Ś|ś)(roda)|(C|c)(zwartek)|(P|p)(i)(a|ą)(tek)", "\n\n")
                        , "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));

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


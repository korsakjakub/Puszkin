package jakubkorsak.puszkin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public class HarmonogramZastepstwa extends AppCompatActivity {

    String p;
    TextView t;
    String senderActivity;

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



        t = (TextView)findViewById(R.id.textView);

        senderActivity = getIntent().getExtras().getString(Sources.SENDER_ACTIVITY);
        assert senderActivity != null;
        switch (senderActivity) {
            case "harmonogram":
                p = "http://www.zso1.edu.gorzow.pl/?lng=1&view=k&k=23";
                toolbar.setTitle("Harmonogram");
                new doIt().execute();
                break;
            case "zastepstwa":
                p = "http://www.zso1.edu.gorzow.pl/?lng=1&view=k&k=20";
                toolbar.setTitle("Zastępstwa");
                new doIt().execute();
                break;
        }
    }


    public class doIt extends AsyncTask<Void, Void, Void> {
        String words;
        String s;

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect(p).get();
                doc.outputSettings(new Document.OutputSettings().prettyPrint(false));
                doc.select("br").append("\\n");
                doc.select("p").prepend("\\n\\n");

                switch (senderActivity) {
                    case "zastepstwa":
                        s = doc.getElementsContainingText("Zastępstwa")
                                .select("div.tekst")
                                .html().replaceFirst("Zastępstwa", "");
                        break;

                    case "harmonogram":
                        s = doc.getElementsContainingText("Harmonogram")
                                .select("p")
                                .html();
                        break;
                }
                words = Jsoup.clean(s
                                .replaceAll("\\\\n", "\n")
                                .replaceAll("<sup>", ":")
                                .replaceAll("&nbsp;", " ")
                                .replaceAll("drukuj", "")
                                .replaceAll("\n\n\n", "\n")
                        , "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
            } catch (Exception e) {
                e.printStackTrace();
                words = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            t.setText(words);
        }
    }

}


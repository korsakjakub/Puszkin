package jakubkorsak.puszkin;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public class ParsedHTMLViewer extends AppCompatActivity {


    //WebView w;
    FloatingActionButton fab;
    String p;
    TextView t;
    String path;
    String h;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parserhtmlviewer);

        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.LTGRAY);
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        }

        t = (TextView) findViewById(R.id.t);
        String senderActivity = getIntent().getExtras().getString(Sources.SENDER_ACTIVITY);
        assert senderActivity != null;
        switch (senderActivity) {
            case "harmonogram":
                p = "http://www.zso1.edu.gorzow.pl/?lng=1&view=k&k=23";
                FileHandling.writeStringAsFile(Sources.TYPE_OF_WEB_VIEW[1], Sources.SENDER_ACTIVITY,
                    getApplicationContext());
                new doIt().execute();
                break;
            case "zastepstwa":
                p = "http://www.zso1.edu.gorzow.pl/?lng=1&view=k&k=20";
                FileHandling.writeStringAsFile(Sources.TYPE_OF_WEB_VIEW[2], Sources.SENDER_ACTIVITY,
                        getApplicationContext());
                new doIt().execute();
                break;

            case "plan":
                h = getIntent().getExtras().getString(Sources.TAG);
                p = "http://www.plan.1lo.gorzow.pl/plany/" + h + ".html";
                FileHandling.writeStringAsFile(Sources.TYPE_OF_WEB_VIEW[0], Sources.SENDER_ACTIVITY,
                        getApplicationContext());
                new doIt().execute();
                break;
        }
    }

    /**
     * Tu dzieje się praktycznie cała magia
     */
    public class doIt extends AsyncTask<Void, Void, Void> {
        String words;
        String s;

        @Override
        protected Void doInBackground(Void... params) {

            String senderActivity = FileHandling.readFileAsString
                    (Sources.SENDER_ACTIVITY, getApplicationContext());

            try {
                Document doc = Jsoup.connect(p).get();
                doc.outputSettings(new Document.OutputSettings().prettyPrint(false));
                doc.select("br").append("\\n");
                doc.select("p").prepend("\\n\\n");

                switch (senderActivity) {
                    case "zastepstwa":
                        s = doc.getElementsContainingText("Zastępstwa")
                                .select("div.tekst")
                                .html();
                        break;

                    case "harmonogram":
                        s = doc.getElementsContainingText("Harmonogram")
                                .select("p")
                                .html();
                        break;

                    case "plan":
                        s = doc.getElementsByClass("l").append("\\n\\ndalej")
                                .html();
                        break;
                }
                words = Jsoup.clean(s
                        .replaceAll("\\\\n", "\n")
                        .replaceAll("&nbsp;", " ")
                        .replaceAll("drukuj", "")
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


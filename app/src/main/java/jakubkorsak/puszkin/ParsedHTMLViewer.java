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
import org.jsoup.nodes.Element;

public class ParsedHTMLViewer extends AppCompatActivity {


    //WebView w;
    FloatingActionButton fab;
    String p;
    TextView t;


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
                new doIt().execute();
                break;
            case "zastepstwa":
                p = "http://www.zso1.edu.gorzow.pl/?lng=1&view=k&k=20";
                new doIt().execute();
                break;
        }
    }

    /**
     * Tu dzieje się praktycznie cała magia
     */
    public class doIt extends AsyncTask<Void, Void, Void> {
        String words;

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect(p).get();
                Element tekst = doc.select("div.tekst").first();
                words = tekst.html().replaceAll("<br>", "\n")
                        .replaceAll("&nbsp;", " ")
                        .replaceAll("<.*?>", " ")
                        .replaceAll("<td.*?>", "")
                        .replaceAll("drukuj", ""); //trochę łopatologiczne ale działa


            } catch (Exception e) {
                e.printStackTrace();
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


package jakubkorsak.puszkin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class Dzienniczek extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dzienniczek);
        WebView w = (WebView) findViewById(R.id.w);
        String p = "http://aplikacje.vulcan.pl/dziennik/00081/dzienniczek.aspx?view=Oceny";
        new WebRequestsHandler(w, p);
    }
}

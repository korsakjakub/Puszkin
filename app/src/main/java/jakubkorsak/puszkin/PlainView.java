package jakubkorsak.puszkin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class PlainView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plain_view);
        String h = getIntent().getExtras().getString("tag");
        String p = "http://plan.1lo.gorzow.pl/plany/" + h + ".html";
        WebView w = (WebView) findViewById(R.id.w);
        new WebRequestsHandler(w, p);
    }
}


package jakubkorsak.puszkin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class InnePlany extends AppCompatActivity {



    WebView w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inne_plany);
        w = (WebView)findViewById(R.id.w);
        String h = getIntent().getExtras().getString("nauczyciel");
        String p = "http://plan.1lo.gorzow.pl/plany/"+h+".html";
        new WebRequestsHandler(w, p);
    }
}

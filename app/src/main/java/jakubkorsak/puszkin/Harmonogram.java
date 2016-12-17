package jakubkorsak.puszkin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class Harmonogram extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harmonogram);
        WebView w = (WebView) findViewById(R.id.w);
        String path = "http://www.zso1.edu.gorzow.pl/print.php5?view=k&lng=1&k=23&t=1&short=1";
        new WebRequestsHandler(w, path, false, getApplicationContext(), null);
    }
}

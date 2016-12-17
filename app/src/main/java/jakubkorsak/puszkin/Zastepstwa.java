package jakubkorsak.puszkin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class Zastepstwa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zastepstwa);
        WebView w = (WebView) findViewById(R.id.w);
        String p = "http://www.zso1.edu.gorzow.pl/print.php5?view=k&lng=1&k=20&t=4&short=1";
        new WebRequestsHandler(w, p);
    }
}

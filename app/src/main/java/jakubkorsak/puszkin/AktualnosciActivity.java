package jakubkorsak.puszkin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AktualnosciActivity extends AppCompatActivity {

    WebView aktualnosciView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktualnosci);

        WebView aktualnosciView = (WebView) findViewById(R.id.aktualnosci_view);
        aktualnosciView.setWebViewClient(new WebViewClient());

        WebSettings webSettings = aktualnosciView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //To włącza funkcjonalność zoomu pinch-to-zoom
        webSettings.setBuiltInZoomControls(true);
        //To wyłącza brzydkie przyciski sterujące zoomem
        webSettings.setDisplayZoomControls(false);
        aktualnosciView.loadUrl("http://1lo.gorzow.pl");
    }

}

package jakubkorsak.puszkin;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DzienniczekView extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    WebView webView;
    String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#b53f3f")));
        }
        webView = (WebView) findViewById(R.id.w);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //To włącza funkcjonalność zoomu pinch-to-zoom
        webSettings.setBuiltInZoomControls(true);
        //To wyłącza brzydkie przyciski sterujące zoomem
        webSettings.setDisplayZoomControls(false);
        path = "http://dziennik.1lo.gorzow.pl/";
        webView.loadUrl(path);
    }
}

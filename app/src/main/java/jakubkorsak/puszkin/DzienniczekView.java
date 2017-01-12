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

    FloatingActionButton fab;
    WebView w;
    String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#b53f3f")));
        }
        w = (WebView) findViewById(R.id.w);
        w.setWebViewClient(new WebViewClient());
        WebSettings s = w.getSettings();
        s.setJavaScriptEnabled(true);
        s.setBuiltInZoomControls(true);
        s.setDisplayZoomControls(false);
            path = "http://aplikacje.vulcan.pl/dziennik/00081/dzienniczek.aspx?view=Oceny";

        w.loadUrl(path);
    }
}

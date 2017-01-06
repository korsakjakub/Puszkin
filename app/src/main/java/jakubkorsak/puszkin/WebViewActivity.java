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

public class WebViewActivity extends AppCompatActivity {

    FloatingActionButton fab;
    WebView w;
    String path;
    String h;


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
            getWindow().setStatusBarColor(Color.parseColor("#ffabab"));
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffabab")));
        }

        String senderActivity = getIntent().getExtras().getString(Sources.SENDER_ACTIVITY);
        w = (WebView) findViewById(R.id.w);
        w.setWebViewClient(new WebViewClient());
        WebSettings s = w.getSettings();
        s.setJavaScriptEnabled(true);
        s.setBuiltInZoomControls(true);
        s.setDisplayZoomControls(false);
        //ParsedHTMLViewer.changeGUIColor(getWindow(), fab, Color.parseColor("#ffabab") );

        if (senderActivity.equals(Sources.TYPE_OF_WEB_VIEW[0])) {
            h = getIntent().getExtras().getString(Sources.TAG);
            path = "http://plan.1lo.gorzow.pl/plany/" + h + ".html";
        } else if (senderActivity.equals(Sources.TYPE_OF_WEB_VIEW[3])) {
            path = "http://aplikacje.vulcan.pl/dziennik/00081/dzienniczek.aspx?view=Oceny";
        } else {
            path = "http://example.com";
        }

        w.loadUrl(path);
    }
}

package jakubkorsak.puszkin;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;

public class WebViewer extends AppCompatActivity {

    FloatingActionButton fab;

    private static void changeGUIColor(Window window, FloatingActionButton fab, int Color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color);
            fab.setBackgroundTintList(ColorStateList.valueOf(Color));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plain_view);

        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        WebView w = (WebView) findViewById(R.id.w);
        String p;
        String h;
        String senderActivity = getIntent().getExtras().getString(Sources.SENDER_ACTIVITY);
        assert senderActivity != null;
        switch (senderActivity) {
            case "plan":
                changeGUIColor(getWindow(), fab, Color.parseColor("#ffabab"));
                h = getIntent().getExtras().getString(Sources.TAG);
                p = "http://plan.1lo.gorzow.pl/plany/" + h + ".html";
                new WebRequestsHandler(w, p);
                break;

            case "harmonogram":
                changeGUIColor(getWindow(), fab, Color.WHITE);
                p = "http://www.zso1.edu.gorzow.pl/print.php5?view=k&lng=1&k=23&t=1557&short=1";
                new WebRequestsHandler(w, p);
                break;

            case "zastepstwa":
                changeGUIColor(getWindow(), fab, Color.WHITE);
                p = "http://www.zso1.edu.gorzow.pl/print.php5?view=k&lng=1&k=20&t=4&short=1";
                new WebRequestsHandler(w, p);
                break;

            case "dzienniczek":

                changeGUIColor(getWindow(), fab, Color.parseColor("#E1E2DC"));
                p = "http://aplikacje.vulcan.pl/dziennik/00081/dzienniczek.aspx?view=Oceny";
                new WebRequestsHandler(w, p);
                break;
        }
    }
}


package jakubkorsak.puszkin;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

class WebRequestsHandler {

    WebRequestsHandler(WebView web, String path) {
        try {

            WebSettings c = web.getSettings();
            c.setDefaultTextEncodingName("utf-8");
            c.setJavaScriptEnabled(true);
            c.setBuiltInZoomControls(true);
            c.setDisplayZoomControls(false);
            web.loadUrl(path);
            web.setWebViewClient(new WebViewClient());
        }
        catch(Exception e){
            web.loadDataWithBaseURL(null, e.getMessage(),"text/html","UTF-8",null);
        }
    }
}

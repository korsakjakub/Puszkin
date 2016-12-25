package jakubkorsak.puszkin;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

class WebRequestsHandler {

    /**
     * @param web WebView który ma wyświetlać
     * @param url ścieżka do strony internetowej
     */
    WebRequestsHandler(WebView web, String url) {
        try {
            WebSettings c = web.getSettings();
            c.setDefaultTextEncodingName("utf-8");
            c.setJavaScriptEnabled(true);
            c.setBuiltInZoomControls(true);
            c.setDisplayZoomControls(false);
            web.loadUrl(url);
            web.setWebViewClient(new WebViewClient());
        }
        catch(Exception e){
            web.loadDataWithBaseURL(null, e.getMessage(),"text/html","UTF-8",null);
        }
    }

}

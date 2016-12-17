package jakubkorsak.puszkin;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

class WebRequestsHandler {

    WebRequestsHandler(WebView web, String path, boolean whetherToSaveTheSite, Context context, String fileName) {
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
        if(whetherToSaveTheSite){
            FileHandler.getSourceFromUrl(context, path, fileName);
        }
    }
}

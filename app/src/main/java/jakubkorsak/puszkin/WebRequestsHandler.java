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
    /*
    String download(String url) throws Exception {
        String filename = "twojaKlasa.html";
        save(url, filename);

        List<String> imageLinks = getImageURLs(filename);
        for (String imageLink : imageLinks) {
            String imageFileName = getImageName(imageLink);
            save(imageLink, imageFileName);
        }

        convertImageURLs(filename);
        return filename;
    }

    void save(String url, String saveTo) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
        conn.connect();
        InputStream is = conn.getInputStream();
        save(is, saveTo);
    }

    void save(InputStream is, String saveTo) {
        // save actual content
    }

    List<String> getImageURLs(String localHtmlFile) {
        // parse localHtmlFile and get all URLs for the images
        return Collections.EMPTY_LIST;
    }

    String getImageName(String imageLink) {
        // get image name, from url
        return null;
    }

    void convertImageURLs(String localHtmlFile) {
        // convert all URLs of the images, something like:
        // <img src="original_url"> -> <img src="local_url">
    }
*/
}

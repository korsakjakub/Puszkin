package jakubkorsak.puszkin;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class FileHandler {
    FileHandler(){
    }

    static String readFileAsString(String fileName, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(new File(context.getFilesDir(), fileName)));
            while ((line = in.readLine()) != null) stringBuilder.append(line);

        } catch (IOException ignored) {
        }

        return stringBuilder.toString();
    }
    static void writeStringAsFile(final String fileContents, String fileName, Context context) {
        try {
            FileWriter out = new FileWriter(new File(context.getFilesDir(), fileName));
            out.write(fileContents);
            out.close();
        } catch (IOException ignored) {
        }
    }

    static void getSourceFromUrl(final Context context, String url, final String fileName){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("APp", e.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {

                if(response.isSuccessful()){
                    final String finalString = response.body().string();
                    writeStringAsFile(finalString, fileName, context);
                }
            }
        });

    }

}

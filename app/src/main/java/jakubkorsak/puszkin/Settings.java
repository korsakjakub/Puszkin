package jakubkorsak.puszkin;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class Settings extends AppCompatActivity {

    Toolbar toolbar;
    EditText editText;
    PackageInfo pInfo;
    Button info;
    Button zapisButton;
    ImageView ostatniaKlasaImageView;
    ImageView twojaKlasaImageView;
    Button deleteAll;
    String TextFromForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        assert toolbar != null;
        toolbar.setTitleTextColor(Color.WHITE);
        editText = (EditText) findViewById(R.id.editText);
        assert editText != null;
        editText.setHint("np. 1a");

        twojaKlasaImageView = (ImageView)findViewById(R.id.twoja_klasa_image);
        ostatniaKlasaImageView = (ImageView)findViewById(R.id.ostatnia_klasa_image);

        checkIfFilesNonNull();
        zapisButton = (Button)findViewById(R.id.zapis_button);
        findViewById(R.id.activity_settings);
        zapisButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TextFromForm = editText.getText().toString();
                if (Arrays.asList(Sources.klasy).contains(TextFromForm)) {
                    FileHandling.writeStringAsFile(TextFromForm, Sources.zrodla[1], getApplicationContext());
                    new downloadPageInBackground().execute();
                    recreate();
                }else{
                    Toast.makeText(Settings.this, "Nie ma takiej klasy. Pamiętaj o formacie \"1a\"", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteAll = (Button)findViewById(R.id.delete_all);
        deleteAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FileHandling.writeStringAsFile("", Sources.zrodla[0], getApplicationContext());
                FileHandling.writeStringAsFile("", Sources.zrodla[1], getApplicationContext());
                Toast.makeText(Settings.this, "Usunięto: " + Sources.zrodla[0] + ", " + Sources.zrodla[1], Toast.LENGTH_SHORT).show();
                recreate();
            }
        });

        pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        assert pInfo != null;
        final String version = pInfo.versionName;
        info = (Button) findViewById(R.id.info);
        assert info != null;
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Settings.this)
                        .setNeutralButton("OK", null)
                        .setMessage("Wersja: " + version + "\n\nFeedback: jakub.korsak@puszkin.eu")
                        .setTitle("Info")
                        .show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkIfFilesNonNull();
    }

    /**
     * sprawdza czy istnieją pliki z zapisanymi nazwami przycisków i odpowiednio dopasowuje źródła
     * ImageView
     */
    private void checkIfFilesNonNull(){
        File fileOstatniaKlasa = new File(getFilesDir() + "/" + Sources.zrodla[0]);
        File fileTwojaKlasa = new File(getFilesDir() + "/" + Sources.zrodla[1]);
        if (fileOstatniaKlasa.exists() && FileHandling.readFileAsString(Sources.zrodla[0], getApplicationContext()) != "") {
            ostatniaKlasaImageView.setImageDrawable(ContextCompat.getDrawable(Settings.this, R.drawable.presence_online));
        }else{
            ostatniaKlasaImageView.setImageDrawable(ContextCompat.getDrawable(Settings.this, R.drawable.presence_invisible));
        }
        if (fileTwojaKlasa.exists() && FileHandling.readFileAsString(Sources.zrodla[1], getApplicationContext()) != "") {
            twojaKlasaImageView.setImageDrawable(ContextCompat.getDrawable(Settings.this, R.drawable.presence_online));
        }else{
            twojaKlasaImageView.setImageDrawable(ContextCompat.getDrawable(Settings.this, R.drawable.presence_invisible));
        }
    }



    public class downloadPageInBackground extends AsyncTask<Void, Void, Void> {

        String p = "http://www.plan.1lo.gorzow.pl/plany/" +
                Sources.getID(TextFromForm, "o", Sources.klasy) +
                ".html";
        Elements s;

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect(p).get();
                doc.outputSettings(new Document.OutputSettings().prettyPrint(false));
                s = doc.getElementsByClass("l").append("\n\n");
                FileHandling.writeStringAsFile(s.text(), "zapisanaklasa", getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            /**
             * write arraylist to file
             */
            File file = new File(getFilesDir(), "t.tmp");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(s);
                oos.close();
                Toast.makeText(Settings.this, "Zapisano: " + TextFromForm, Toast.LENGTH_SHORT).show();
            }catch (IOException e){
                Toast.makeText(Settings.this, "Błąd: "+ e , Toast.LENGTH_SHORT).show();
            }
        }
    }
}

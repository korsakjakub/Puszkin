package jakubkorsak.puszkin;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.util.Arrays;

public class Settings extends AppCompatActivity {

    Toolbar toolbar;
    EditText editText;
    PackageInfo pInfo;
    Button info;
    Button zapisButton;
    Button pokazZapisane;
    Button deleteAll;
    String TextFromForm;
    private ProgressBar spinner;

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


        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        zapisButton = (Button)findViewById(R.id.zapis_button);
        findViewById(R.id.activity_settings);
        zapisButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TextFromForm = editText.getText().toString();
                FileHandling.writeStringAsFile(TextFromForm, Sources.TWOJA_KLASA_SAVED, getApplicationContext());
                if (Arrays.asList(Sources.klasy).contains(TextFromForm)) {
                    new downloadPageInBackground().execute();
                    Toast.makeText(Settings.this, "Zapisano: " + TextFromForm.toUpperCase(), Toast.LENGTH_SHORT).show();
                    FileHandling.writeStringAsFile(TextFromForm, Sources.zrodla[1], getApplicationContext());
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
                String twojaZapisana = "";
                try {
                    twojaZapisana = "ZAPIS_" + Sources.getID(FileHandling.readFileAsString(Sources.TWOJA_KLASA_SAVED,
                            getApplicationContext()), "o", Sources.klasy);
                }catch (IndexOutOfBoundsException ignored){
                }
                if((new File(getFilesDir(), twojaZapisana)).exists()){
                    deleteFile(twojaZapisana);
                }
                if((new File(getFilesDir(), Sources.zrodla[0])).exists()) {
                    deleteFile(Sources.zrodla[0]);
                }
                if((new File(getFilesDir(), Sources.zrodla[1])).exists()){
                    deleteFile(Sources.zrodla[1]);
                }
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
                        .setMessage("Wersja: " + version + "\nTwórca: Jakub Korsak" +
                                "\nFeedback: jakub.korsak@puszkin.eu")
                        .setTitle("Info")
                        .show();
            }
        });


        View bottomSheet = findViewById(R.id.design_bottom_sheet);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_SETTLING");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_COLLAPSED");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_HIDDEN");
                        break;
                }

                ((TextView)findViewById(R.id.bottomsheet_text)).setText("Lista plików");
            }


            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.i("BottomSheetCallback", "slideOffset: " + slideOffset);
            }
        });

        pokazZapisane = (Button)findViewById(R.id.show_saved_files);
        pokazZapisane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

            }
        });
    }


    //pobiera stronę wskazaną z EditText
    public class downloadPageInBackground extends AsyncTask<Void, Void, Void> {

        String p = "http://www.plan.1lo.gorzow.pl/plany/" +
                Sources.getID(TextFromForm, "o", Sources.klasy) +
                ".html";

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(p).get();
                FileHandling.writeStringAsFile(doc.html(), "ZAPIS_" + Sources.getID(TextFromForm, "o", Sources.klasy),
                        getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            spinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            spinner.setVisibility(View.GONE);
            Toast.makeText(Settings.this, "Zakończono", Toast.LENGTH_SHORT).show();
        }
    }
}

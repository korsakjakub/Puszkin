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
import java.util.ArrayList;
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

    ArrayList<String> lekcjeIndex = new ArrayList<>();
    ArrayList<String> nauczycieleIndex = new ArrayList<>();
    ArrayList<String> gabinetyIndex = new ArrayList<>();
    StringBuilder str = new StringBuilder("Pliki: \n\n");
    ArrayList<String> existingFiles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        /**
         * ładuję dane do ArrayList odpowiedniej nazwy
         */
        for(int i = 1; i <= 18; i++) {
            //wszystkie oddziały
            lekcjeIndex.add(0, "o" + i);
            Log.i("Zapisy array ", "Dodano " + lekcjeIndex.get(0));
        }
        for(int i = 1; i <= 48; i++){
            //wszyscy nauczyciele
            nauczycieleIndex.add(0, "n" + i);
            Log.i("Zapisy array ", "Dodano " + nauczycieleIndex.get(0));
        }
        for(int i = 1; i <= 32; i++){
            //wszystkie gabinety
            gabinetyIndex.add(0, "s" + i);
            Log.i("Zapisy array ", "Dodano " + gabinetyIndex.get(0));
        }

        /**
         * sprawdza czy znaleziono któryś z plików o nazwie tej komórki listy, po czym dopisuje do
         * StringBuildera nazwy tych które znalazł
         * + dopisuje do listy existingFiles znalezione. Trzyma ich spis żeby można było je
         * w razie czego usunąć.
         */
        try {
            for (int i = 0; i <= lekcjeIndex.size() - 1; i++) {
                //oddziały
                if (new File(getFilesDir(), lekcjeIndex.get(i)).exists()) {
                    str.append("Klasa: ")
                            .append(Sources.getIndex(lekcjeIndex.get(i), "o", Sources.index, Sources.klasy))
                            .append(", \n");
                    existingFiles.add(0, lekcjeIndex.get(i));
                    Log.i("Znaleziono", "klasa " + lekcjeIndex.get(0));
                }
            }
            for (int i = 0; i <= nauczycieleIndex.size() - 1; i++) {
                //nauczyciele
                if (new File(getFilesDir(), nauczycieleIndex.get(i)).exists()) {
                    str.append("Nauczyciel: ")
                            .append(Sources.getIndex(nauczycieleIndex.get(i), "n", Sources.index, Sources.Nauczyciele))
                            .append(", \n");
                    existingFiles.add(0, nauczycieleIndex.get(i));
                    Log.i("Znaleziono", "nauczyciel " + nauczycieleIndex.get(0));
                }
            }
            for (int i = 0; i <= gabinetyIndex.size() - 1; i++) {
                //gabinety
                if (new File(getFilesDir(), gabinetyIndex.get(i)).exists()) {
                    str.append("Sala: ")
                            .append(Sources.getIndex(gabinetyIndex.get(i), "s", Sources.index, Sources.Gabinety))
                            .append(", \n");
                    existingFiles.add(0, gabinetyIndex.get(i));
                    Log.i("Znaleziono", "sala " + gabinetyIndex.get(0));
                }
            }
        }catch (NullPointerException e){
            Log.e("Checking for files", "błędny rozmiar listy");
            e.printStackTrace();
        }

        toolbar = findViewById(R.id.toolbar);
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
        editText = findViewById(R.id.editText);
        assert editText != null;
        editText.setHint("np. 1a");

        spinner = findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        zapisButton = findViewById(R.id.zapis_button);
        findViewById(R.id.activity_settings);
        zapisButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TextFromForm = editText.getText().toString();
                //sprawdzamy czy to co wpisał użytkownik jest nazwą klasy
                if (Arrays.asList(Sources.klasy).contains(TextFromForm)) {
                    new downloadPageInBackground(new OnTaskCompleted() {
                        @Override
                        public void onTaskCompleted(boolean success) {
                            if(success)
                                Toast.makeText(Settings.this, "Pobrano", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(Settings.this, "Brak internetu", Toast.LENGTH_SHORT).show();
                        }
                    }).execute();
                }else{
                    Toast.makeText(Settings.this, "Nie ma takiej klasy. Pamiętaj o formacie \"1a\"", Toast.LENGTH_SHORT).show();
                }
            }
        });


        /**
         * tutaj usuwane są istniejące pliki klas
         */
        deleteAll = findViewById(R.id.delete_all);
        deleteAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                for(int i = 0; i <= existingFiles.size()-1; i++){
                    deleteFile(existingFiles.get(i));
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
        /**
         * prosty alert z informacjami o mnie i o wersji aplikacji
         */
        pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        assert pInfo != null;
        final String version = pInfo.versionName;
        info = findViewById(R.id.info);
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

        /**
        Cała logika dot. bottomSheet
         */
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
                    ((TextView)findViewById(R.id.bottomsheet_text)).setText(str);
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.i("BottomSheetCallback", "slideOffset: " + slideOffset);
            }
        });

        pokazZapisane = findViewById(R.id.show_saved_files);
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
    public interface OnTaskCompleted{
        void onTaskCompleted(boolean success);
    }

    //pobiera stronę wskazaną z EditText
    public class downloadPageInBackground extends AsyncTask<Void, Void, Void> {

        private OnTaskCompleted listener;

        downloadPageInBackground(OnTaskCompleted listener){
            this.listener = listener;
        }

        String p = "http://www.plan.1lo.gorzow.pl/plany/" +
                Sources.getID(TextFromForm, "o", Sources.klasy) +
                ".html";

        boolean success;

        @Override
        protected Void doInBackground(Void... params) {
            //sprawdzamy czy wcześniej ta klasa nie została już zapisana jako twojaKlasa
            try {
                String s = FileHandling.readFileAsString(Sources.zrodla[1], getApplicationContext());
                try {
                    s = Sources.getID(s, "o", Sources.klasy);
                    deleteFile(s);
                }catch (IndexOutOfBoundsException e){
                    Log.i("AsyncTask", "brak wcześniejszej \"twoja_klasa\"");
                }
                //pobieramy stronę do pamięci urządzenia
                Document doc = Jsoup.connect(p).get();
                FileHandling.writeStringAsFile(doc.html(), Sources.getID(TextFromForm, "o", Sources.klasy),
                        getApplicationContext());
                //informacja do MainActivity żeby mógł odczytać czy ma wyświetlać przycisk twojaKlasaButton
                FileHandling.writeStringAsFile(TextFromForm, Sources.zrodla[1], getApplicationContext());
                success = true; //udało się wykonać
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("AsyncTask", "Brak internetu");
                success = false; //nie udało sie wykonać
            }
            return null;
        }

        /**
         * włączamy spinner
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            spinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            spinner.setVisibility(View.GONE);
            recreate();
            listener.onTaskCompleted(success);
            Log.i("AsyncTask", "Koniec AsyncTasku");
        }
    }
}

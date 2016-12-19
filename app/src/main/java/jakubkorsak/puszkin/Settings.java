package jakubkorsak.puszkin;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
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

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Settings extends AppCompatActivity {


    Toolbar toolbar;
    EditText editText;
    PackageInfo pInfo;
    Button info;
    Button zapisButton;
    public static String zrodla[] = {
            "ostatnia_save", "twoja_save", "ostatnia_source", "twoja_source"
    };
    ImageView ostatniaKlasaImageView;
    ImageView twojaKlasaImageView;
    Button deleteAll;

    private static final Set<String> classNames = new HashSet<>(Arrays.asList(
            new String[]{
                    "1a", "1b", "1c", "1d", "1e", "1f",
                    "2a", "2b", "2c", "2d", "2e", "2f",
                    "3a", "3b", "3c", "3d", "3e", "3f",}
    ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert toolbar != null;
        toolbar.setTitleTextColor(Color.WHITE);
        editText = (EditText) findViewById(R.id.editText);
        assert editText != null;
        editText.setHint("np. 1a");

        twojaKlasaImageView = (ImageView)findViewById(R.id.twoja_klasa_image);
        ostatniaKlasaImageView = (ImageView)findViewById(R.id.ostatnia_klasa_image);

        checkIfFilesNonNull();
        zapisButton = (Button)findViewById(R.id.zapis_button);
        zapisButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String s = editText.getText().toString();
                if(classNames.contains(s)){
                    FileHandler.writeStringAsFile(s, zrodla[1], getApplicationContext());
                    //FileHandler.getSourceFromUrl(getApplicationContext(), "", zrodla[3]);//TODO "" zamienić na prawdziwy url
                    Toast.makeText(Settings.this, "Zapisano: "+s, Toast.LENGTH_SHORT).show();
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
                FileHandler.writeStringAsFile("", zrodla[0], getApplicationContext());
                Toast.makeText(Settings.this, "Deleted: "+zrodla[0], Toast.LENGTH_SHORT).show();
                FileHandler.writeStringAsFile("",zrodla[1],getApplicationContext());
                Toast.makeText(Settings.this, "Deleted: "+zrodla[1], Toast.LENGTH_SHORT).show();
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

    private void checkIfFilesNonNull(){
        File fileOstatniaKlasa = new File(getFilesDir()+"/"+zrodla[0]);
        File fileTwojaKlasa = new File(getFilesDir()+"/"+zrodla[1]);
        if(fileOstatniaKlasa.exists()&&FileHandler.readFileAsString(zrodla[0], getApplicationContext())!=""){
            ostatniaKlasaImageView.setImageDrawable(ContextCompat.getDrawable(Settings.this, R.drawable.presence_online));
        }else{
            ostatniaKlasaImageView.setImageDrawable(ContextCompat.getDrawable(Settings.this, R.drawable.presence_invisible));
        }
        if(fileTwojaKlasa.exists()&&FileHandler.readFileAsString(zrodla[1], getApplicationContext())!=""){
            twojaKlasaImageView.setImageDrawable(ContextCompat.getDrawable(Settings.this, R.drawable.presence_online));
        }else{
            twojaKlasaImageView.setImageDrawable(ContextCompat.getDrawable(Settings.this, R.drawable.presence_invisible));
        }
    }
}

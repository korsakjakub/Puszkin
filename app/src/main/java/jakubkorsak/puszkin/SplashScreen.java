package jakubkorsak.puszkin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.ads.MobileAds;


public class SplashScreen extends AppCompatActivity {

    /**
     * kiedy aplikacja będzie gotowa, przejdź do MainActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-6362398069925713~9827427093");
        Intent goToMainActivity = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(goToMainActivity);
        finish();
    }
}

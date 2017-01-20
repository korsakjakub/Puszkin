package jakubkorsak.puszkin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    /**
     * kiedy aplikacja będzie gotowa, przejdź do MainActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Intent goToMainActivity = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(goToMainActivity);
        finish();
    }
}

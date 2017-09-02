package jakubkorsak.puszkin;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class PlanView extends AppCompatActivity{

    String pathParameter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_view);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert toolbar!=null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolbar.setTitleTextColor(Color.WHITE);
        pathParameter = getIntent().getStringExtra(Sources.TAG);

        /*
        zmienia tytuł toolbaru i zmienia wg. o1 -> 1A
         lub n1 -> jakieś imię lub s1 -> jakiś gabinet
         */
        if(pathParameter.contains("o")) {
            toolbar.setTitle("Klasa " + Sources.getIndex
                    (pathParameter, "o", Sources.index, Sources.klasy).toUpperCase());
        }else if(pathParameter.contains("n")){
            toolbar.setTitle(Sources.getIndex
                    (pathParameter, "n", Sources.index, Sources.Nauczyciele).toUpperCase());
        }else if(pathParameter.contains("s")){
            toolbar.setTitle(Sources.getIndex
                    (pathParameter, "s", Sources.index, Sources.Gabinety).toUpperCase());
        }
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        /*
          biorę dzień tygodnia i wg. niego ustawiam domyślny tab
          więc kiedy użytkownik włączy plan otworzy mu się od razu
          plan na dzisiejszy dzień
         */
        int tab = 0;
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.MONDAY:
                //to i tak jest 0
                break;
            case Calendar.TUESDAY:
                tab = 1;
                break;
            case Calendar.WEDNESDAY:
                tab = 2;
                break;
            case Calendar.THURSDAY:
                tab = 3;
                break;
            case Calendar.FRIDAY:
                tab = 4;
                break;
            case Calendar.SATURDAY:
                break;
            case Calendar.SUNDAY:
                break;
        }

        mViewPager.setCurrentItem(tab);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_plan, menu);
        return true;
    }

    /**
     * sprawdza który item został wybrany i wg. tego przyporządkowuje onClickListener
     * @param item pozycja z menu
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent goToSettings = new Intent(PlanView.this, Settings.class);
            startActivity(goToSettings);
            return true;
        } else if(id == R.id.action_download_page){
            new downloadPageInBackground(new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(int success) {
                    if(success == 1)
                        Toast.makeText(PlanView.this, "Pobrano", Toast.LENGTH_SHORT).show();
                    else if(success == 0)
                        Toast.makeText(PlanView.this, "Brak internetu", Toast.LENGTH_SHORT).show();
                    else if(success == 2)
                        Toast.makeText(PlanView.this, "Ta klasa znajduje się już w pamięci", Toast.LENGTH_SHORT).show();
                }
            }).execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    interface OnTaskCompleted {
        void onTaskCompleted(int success);
    }

    /**klasa zajmująca się pobraniem strony do trybu offline
     * wywoływana przez onClickListener przycisku z menu
     */
    private class downloadPageInBackground extends AsyncTask<Void, Void, Void> {


        int success;
        String p = "http://www.plan.1lo.gorzow.pl/plany/" + pathParameter + ".html";
        private OnTaskCompleted listener;
        downloadPageInBackground(OnTaskCompleted listener){
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void... params) {
            //plik do którego będzie zapisywane
            String fileName = pathParameter;
            File file = new File(getFilesDir(), fileName);

            //jeśli nie istnieje ściągnąć z JSoup
            if(!file.exists()) {
                try {
                    Document doc = Jsoup.connect(p).get();
                    FileHandling.writeStringAsFile(
                            doc.html(),
                            fileName,
                            getApplicationContext());
                    success = 1;
                } catch (IOException e) {
                    e.printStackTrace();
                    success = 0;
                }
            }else{
                success = 2;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listener.onTaskCompleted(success);
        }
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         *
         * @param position zakładka z tabbedActivity
         * @return nowa instancja PlanViewFragment z indeksem wg. position lub null
         */
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return PlanViewFragment.newInstance(0);
                case 1:
                    return PlanViewFragment.newInstance(1);
                case 2:
                    return PlanViewFragment.newInstance(2);
                case 3:
                    return PlanViewFragment.newInstance(3);
                case 4:
                    return PlanViewFragment.newInstance(4);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Pon";
                case 1:
                    return "Wt";
                case 2:
                    return "Śr";
                case 3:
                    return "Czw";
                case 4:
                    return "Pt";
            }
            return null;
        }
    }
}


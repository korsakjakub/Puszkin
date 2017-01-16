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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class PlanView extends AppCompatActivity{

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    String title;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_view);


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
        toolbar.setTitleTextColor(Color.WHITE);
        title = getIntent().getStringExtra(Sources.TAG);
        /**
        zmienia tytuł toolbaru i zmienia wg. o1 -> 1A
         lub n1 -> jakieś imię lub s1 -> jakiś gabinet
         */
        if(title.contains("o")) {
            toolbar.setTitle("Klasa " + Sources.getIndex
                    (title, "o", Sources.index, Sources.klasy).toUpperCase());
        }else if(title.contains("n")){
            toolbar.setTitle(Sources.getIndex
                    (title, "n", Sources.index, Sources.Nauczyciele).toUpperCase());
        }else if(title.contains("s")){
            toolbar.setTitle(Sources.getIndex
                    (title, "s", Sources.index, Sources.Gabinety).toUpperCase());
        }
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        /**
         * biorę dzień tygodnia i wg. niego ustawiam domyślny tab
         * więc kiedy użytkownik włączy plan otworzy mu się od razu
         * plan na dzisiejszy dzień
         */

        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        mViewPager.setCurrentItem(dayOfWeek);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent goToSettings = new Intent(PlanView.this, Settings.class);
            startActivity(goToSettings);
            return true;
        } else if(id == R.id.action_download_page){
            new downloadPageInBackground().execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class downloadPageInBackground extends AsyncTask<Void, Void, Void>{

        String p = "http://www.plan.1lo.gorzow.pl/plany/" + title + ".html";

        @Override
        protected Void doInBackground(Void... params) {
            String fileName = "ZAPIS_" + Sources.getIndex(title, "o", Sources.index, Sources.klasy).toUpperCase();
            File file = new File(getFilesDir(), fileName);

            if(!file.exists()) {
                try {
                    Document doc = Jsoup.connect(p).get();
                    FileHandling.writeStringAsFile(
                            doc.html(),
                            fileName,
                            getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

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


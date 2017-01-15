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
import org.jsoup.select.Elements;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PlanView extends AppCompatActivity{

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_view);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

    int[] lekcje = {
            R.id.lekcja_0,
            R.id.lekcja_1,
            R.id.lekcja_2,
            R.id.lekcja_3,
            R.id.lekcja_4,
            R.id.lekcja_5,
            R.id.lekcja_6,
            R.id.lekcja_7,
            R.id.lekcja_8,
            R.id.lekcja_9
};

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

    public class downloadPageInBackground extends AsyncTask<Void, Void, Void>
            implements Serializable{

        String p = "http://www.plan.1lo.gorzow.pl/plany/" + title + ".html";
        Elements s;

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect(p).get();
                doc.outputSettings(new Document.OutputSettings().prettyPrint(false));
                s = doc.getElementsByClass("l").append("\n\n");
                //FileHandling.writeStringAsFile(s.text(), "zapisanaklasa", getApplicationContext());
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
            try {
                FileOutputStream fos = new FileOutputStream("t.tmp");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(s);
                oos.close();
            }catch (IOException ignored){}
            Toast.makeText(PlanView.this, "Zapisano", Toast.LENGTH_SHORT).show();

            /**
             * read arraylist from file
             */
            try {
                FileInputStream fis = new FileInputStream("t.tmp");
                ObjectInputStream ois = new ObjectInputStream(fis);
                ArrayList a = (ArrayList) ois.readObject();
                ois.close();
            }catch (IOException ignored){} catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
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


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
import org.jsoup.select.Elements;

import jakubkorsak.puszkin.PlanViewFragments.Czwartek;
import jakubkorsak.puszkin.PlanViewFragments.Piatek;
import jakubkorsak.puszkin.PlanViewFragments.Poniedzialek;
import jakubkorsak.puszkin.PlanViewFragments.Sroda;
import jakubkorsak.puszkin.PlanViewFragments.Wtorek;

public class PlanView extends AppCompatActivity {

    String p;
    String h;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;


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

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


                h = getIntent().getExtras().getString(Sources.TAG);
                p = "http://www.plan.1lo.gorzow.pl/plany/" + h + ".html";
                FileHandling.writeStringAsFile(Sources.TYPE_OF_WEB_VIEW[0], Sources.SENDER_ACTIVITY,
                        getApplicationContext());
                new doIt().execute();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Tu dzieje się praktycznie cała magia
     */
    public class doIt extends AsyncTask<Void, Void, Void> {
        String words;
        Intent sendArray;
        String [] lekcjeArray;

        @Override
        protected Void doInBackground(Void... params) {


            try {
                Document doc = Jsoup.connect(p).get();
                doc.outputSettings(new Document.OutputSettings().prettyPrint(false));
                        Elements s = doc.getElementsByClass("l");


                        lekcjeArray = new String[s.size()+1];
                        for(int i = 0; i< s.size(); i++){
                            lekcjeArray[i] = s.get(i).text();
                        }
                //sendArray = new Intent(getApplicationContext(), Poniedzialek.class);
            } catch (Exception e) {
                e.printStackTrace();
                words = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //sendArray.putExtra("lekcjeArray", lekcjeArray);

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
                    return Poniedzialek.newInstance(0);
                case 1:
                    return Wtorek.newInstance(1);
                case 2:
                    return Sroda.newInstance(2);
                case 3:
                    return Czwartek.newInstance(3);
                case 4:
                    return Piatek.newInstance(4);
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


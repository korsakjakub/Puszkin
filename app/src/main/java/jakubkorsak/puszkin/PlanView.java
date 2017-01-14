package jakubkorsak.puszkin;

import android.content.Intent;
import android.graphics.Color;
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

import java.util.Calendar;
import java.util.Date;

import jakubkorsak.puszkin.PlanViewFragments.Czwartek;
import jakubkorsak.puszkin.PlanViewFragments.Piatek;
import jakubkorsak.puszkin.PlanViewFragments.Poniedzialek;
import jakubkorsak.puszkin.PlanViewFragments.Sroda;
import jakubkorsak.puszkin.PlanViewFragments.Wtorek;

public class PlanView extends AppCompatActivity{

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
        String title = getIntent().getStringExtra(Sources.TAG);
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

    /*int[] lekcje = {
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
};*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent goToSettings = new Intent(PlanView.this, Settings.class);
            startActivity(goToSettings);
            return true;
        } else if(id == R.id.action_download_page){

          /*  View p = new Poniedzialek().getView();
            String pon[] = new String[10];
            if(p != null) {
                for (int i = 0; i <= pon.length; i++) {
                    pon[i] = ((TextView) p.findViewById(lekcje[i])).getText().toString();

                }
            }*/
            return true;
        }

        return super.onOptionsItemSelected(item);
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


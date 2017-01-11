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
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import jakubkorsak.puszkin.tabs.Czwartek;
import jakubkorsak.puszkin.tabs.Piatek;
import jakubkorsak.puszkin.tabs.Poniedzialek;
import jakubkorsak.puszkin.tabs.Sroda;
import jakubkorsak.puszkin.tabs.Wtorek;

public class ParsedHTMLViewer extends AppCompatActivity {

    String p;
    String h;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parserhtmlviewer);


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


        String senderActivity = getIntent().getExtras().getString(Sources.SENDER_ACTIVITY);
        assert senderActivity != null;
        switch (senderActivity) {
            case "harmonogram":
                p = "http://www.zso1.edu.gorzow.pl/?lng=1&view=k&k=23";
                FileHandling.writeStringAsFile(Sources.TYPE_OF_WEB_VIEW[1], Sources.SENDER_ACTIVITY,
                    getApplicationContext());
                new doIt().execute();
                break;
            case "zastepstwa":
                p = "http://www.zso1.edu.gorzow.pl/?lng=1&view=k&k=20";
                FileHandling.writeStringAsFile(Sources.TYPE_OF_WEB_VIEW[2], Sources.SENDER_ACTIVITY,
                        getApplicationContext());
                new doIt().execute();
                break;

            case "plan":
                h = getIntent().getExtras().getString(Sources.TAG);
                p = "http://www.plan.1lo.gorzow.pl/plany/" + h + ".html";
                FileHandling.writeStringAsFile(Sources.TYPE_OF_WEB_VIEW[0], Sources.SENDER_ACTIVITY,
                        getApplicationContext());
                new doIt().execute();
                break;
        }
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
        String s;

        @Override
        protected Void doInBackground(Void... params) {

            String senderActivity = FileHandling.readFileAsString
                    (Sources.SENDER_ACTIVITY, getApplicationContext());

            try {
                Document doc = Jsoup.connect(p).get();
                doc.outputSettings(new Document.OutputSettings().prettyPrint(false));
                doc.select("br").append("\\n");
                doc.select("p").prepend("\\n\\n");

                switch (senderActivity) {
                    case "zastepstwa":
                        s = doc.getElementsContainingText("Zastępstwa")
                                .select("div.tekst")
                                .html();
                        break;

                    case "harmonogram":
                        s = doc.getElementsContainingText("Harmonogram")
                                .select("p")
                                .html();
                        break;

                    case "plan":
                        Elements s = doc.getElementsByClass("l").append("\\n\\ndalej");
                                //.html();


                        String [] lekcjeArray = new String[s.size()+1];
                        for(int i = 0; i< s.size(); i++){
                            lekcjeArray[i] = s.get(i).text();
                        }
                        Intent sendArray = new Intent(getApplicationContext(), Poniedzialek.class);
                        sendArray.putExtra("lekcjeArray", lekcjeArray);
                        break;
                }
                words = Jsoup.clean(s
                        .replaceAll("\\\\n", "\n")
                        .replaceAll("&nbsp;", " ")
                        .replaceAll("drukuj", "")
                        , "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
            } catch (Exception e) {
                e.printStackTrace();
                words = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //t.setText(words);
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
                    Poniedzialek poniedzialek = new Poniedzialek();
                    return poniedzialek;
                case 1:
                    Wtorek wtorek = new Wtorek();
                    return wtorek;
                case 2:
                    Sroda sroda = new Sroda();
                    return sroda;
                case 3:
                    Czwartek czwartek = new Czwartek();
                    return czwartek;
                case 4:
                    Piatek piatek = new Piatek();
                    return piatek;
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


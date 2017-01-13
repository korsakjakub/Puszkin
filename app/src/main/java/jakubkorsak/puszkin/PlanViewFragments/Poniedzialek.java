package jakubkorsak.puszkin.PlanViewFragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import jakubkorsak.puszkin.R;
import jakubkorsak.puszkin.Sources;

public class Poniedzialek extends Fragment {

    String p;
    String h;
    TextView lekcja0;
    TextView lekcja1;
    TextView lekcja2;
    TextView lekcja3;
    TextView lekcja4;
    TextView lekcja5;
    TextView lekcja6;
    TextView lekcja7;
    TextView lekcja8;
    TextView lekcja9;


    public static Poniedzialek newInstance(int index){
        Poniedzialek p = new Poniedzialek();
        Bundle args = new Bundle();
        args.putInt("index", index);
        p.setArguments(args);
        return p;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_plan_view, container, false);

        lekcja0 = (TextView)view.findViewById(R.id.lekcja_0);
        lekcja1 = (TextView)view.findViewById(R.id.lekcja_1);
        lekcja2 = (TextView)view.findViewById(R.id.lekcja_2);
        lekcja3 = (TextView)view.findViewById(R.id.lekcja_3);
        lekcja4 = (TextView)view.findViewById(R.id.lekcja_4);
        lekcja5 = (TextView)view.findViewById(R.id.lekcja_5);
        lekcja6 = (TextView)view.findViewById(R.id.lekcja_6);
        lekcja7 = (TextView)view.findViewById(R.id.lekcja_7);
        lekcja8 = (TextView)view.findViewById(R.id.lekcja_8);
        lekcja9 = (TextView)view.findViewById(R.id.lekcja_9);

        h = getActivity().getIntent().getExtras().getString(Sources.TAG);
        p = "http://www.plan.1lo.gorzow.pl/plany/" + h + ".html";
        new doIt().execute();
        return view;
    }

    public class doIt extends AsyncTask<Void, Void, Void> {

        ArrayList<String> lekcjeArray;

        @Override
        protected Void doInBackground(Void... params) {


            try {
                Document doc = Jsoup.connect(p).get();
                doc.outputSettings(new Document.OutputSettings().prettyPrint(false));
                Elements s = doc.getElementsByClass("l");
                lekcjeArray= new ArrayList<>(s.size());
                for(int i = 0; i< s.size(); i++){
                    lekcjeArray.add(s.get(i).text());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            lekcja0.setText(lekcjeArray.get(0).replaceAll("-", ""));
            lekcja1.setText(lekcjeArray.get(5).replaceAll("-", ""));
            lekcja2.setText(lekcjeArray.get(10).replaceAll("-", ""));
            lekcja3.setText(lekcjeArray.get(15).replaceAll("-", ""));
            lekcja4.setText(lekcjeArray.get(20).replaceAll("-", ""));
            lekcja5.setText(lekcjeArray.get(25).replaceAll("-", ""));
            lekcja6.setText(lekcjeArray.get(30).replaceAll("-", ""));
            lekcja7.setText(lekcjeArray.get(35).replaceAll("-", ""));
            lekcja8.setText(lekcjeArray.get(40).replaceAll("-", ""));
            lekcja9.setText(lekcjeArray.get(45).replaceAll("-", ""));
        }
    }
}

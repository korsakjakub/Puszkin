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

/**
 * Created by jakub on 11.01.17.
 */

public class Piatek extends Fragment {


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

    public static Piatek newInstance(int index){
        Piatek p = new Piatek();
        Bundle args = new Bundle();
        args.putInt("index", index);
        p.setArguments(args);

        return p;
    }
    public int getShownIndex() {
        return getArguments().getInt("index", 0);
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
                lekcjeArray = new ArrayList<>(s.size());
                for (int i = 0; i < s.size(); i++) {
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
            if(lekcjeArray.size()>=5)
            lekcja0.setText(lekcjeArray.get(4).replaceAll("-", ""));
            if(lekcjeArray.size()>=10)
            lekcja1.setText(lekcjeArray.get(9).replaceAll("-", ""));
            if(lekcjeArray.size()>=15)
            lekcja2.setText(lekcjeArray.get(14).replaceAll("-", ""));
            if(lekcjeArray.size()>=20)
            lekcja3.setText(lekcjeArray.get(19).replaceAll("-", ""));
            if(lekcjeArray.size()>=25)
            lekcja4.setText(lekcjeArray.get(24).replaceAll("-", ""));
            if(lekcjeArray.size()>=30)
            lekcja5.setText(lekcjeArray.get(29).replaceAll("-", ""));
            if(lekcjeArray.size()>=35)
            lekcja6.setText(lekcjeArray.get(34).replaceAll("-", ""));
            if(lekcjeArray.size()>=40)
                lekcja7.setText(lekcjeArray.get(39).replaceAll("-", ""));
            if(lekcjeArray.size()>=45)
                lekcja8.setText(lekcjeArray.get(44).replaceAll("-", ""));

            if(lekcjeArray.size()>=50)
                lekcja9.setText(lekcjeArray.get(49).replaceAll("-", ""));

        }
    }
}

package jakubkorsak.puszkin;


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

import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanViewFragment extends Fragment {


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


    public static PlanViewFragment newInstance(int index){
        PlanViewFragment p = new PlanViewFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        p.setArguments(args);
        return p;
    }

    public int getIndex(){
        return getArguments().getInt("index");
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
        new GetPlanInBackground().execute();
        return view;
    }


    public class GetPlanInBackground extends AsyncTask<Void, Void, Void> {

        ArrayList<String> lekcjeArray;
        String fileName = "ZAPIS_" + Sources.getIndex(h, "o", Sources.index, Sources.klasy).toUpperCase();
        File saved = new File(getActivity().getFilesDir(), fileName);

        @Override
        protected Void doInBackground(Void... params) {
            Document doc;
            try {
                if(!saved.exists()) {
                    doc = Jsoup.connect(p).get();
                }
                else{
                    doc = Jsoup.parse(FileHandling.
                            readFileAsString(fileName, getActivity().getApplicationContext()));
                }
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
            int planOperator = 0;
            switch(getIndex()){
                case 0:
                    break;
                case 1:
                    planOperator+=1;
                    break;
                case 2:
                    planOperator+=2;
                    break;
                case 3:
                    planOperator+=3;
                    break;
                case 4:
                    planOperator+=4;
                    break;
            }
            try {
                if (lekcjeArray.size() >= planOperator + 1)
                    lekcja0.setText(lekcjeArray.get(planOperator).replaceAll("-", ""));
                if (lekcjeArray.size() >= planOperator + 6)
                    lekcja1.setText(lekcjeArray.get(planOperator + 5).replaceAll("-", ""));
                if (lekcjeArray.size() >= planOperator + 11)
                    lekcja2.setText(lekcjeArray.get(planOperator + 10).replaceAll("-", ""));
                if (lekcjeArray.size() >= planOperator + 16)
                    lekcja3.setText(lekcjeArray.get(planOperator + 15).replaceAll("-", ""));
                if (lekcjeArray.size() >= planOperator + 21)
                    lekcja4.setText(lekcjeArray.get(planOperator + 20).replaceAll("-", ""));
                if (lekcjeArray.size() >= planOperator + 26)
                    lekcja5.setText(lekcjeArray.get(planOperator + 25).replaceAll("-", ""));
                if (lekcjeArray.size() >= planOperator + 31)
                    lekcja6.setText(lekcjeArray.get(planOperator + 30).replaceAll("-", ""));
                if (lekcjeArray.size() >= planOperator + 36)
                    lekcja7.setText(lekcjeArray.get(planOperator + 35).replaceAll("-", ""));
                if (lekcjeArray.size() >= planOperator + 41)
                    lekcja8.setText(lekcjeArray.get(planOperator + 40).replaceAll("-", ""));
                if (lekcjeArray.size() >= planOperator + 46)
                    lekcja9.setText(lekcjeArray.get(planOperator + 45).replaceAll("-", ""));
            }catch (NullPointerException ignored){
                lekcja0.setText(R.string.brak_internetu);
            }
        }
    }
}

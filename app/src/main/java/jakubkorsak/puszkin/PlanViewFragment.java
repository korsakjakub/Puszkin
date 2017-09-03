package jakubkorsak.puszkin;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanViewFragment extends Fragment {


    private static final int[] ROW_IDS = {
            R.id.r0,
            R.id.r1,
            R.id.r2,
            R.id.r3,
            R.id.r4,
            R.id.r5,
            R.id.r6,
            R.id.r7,
            R.id.r8,
            R.id.r9,
    };
    String path;
    String pathParameter;
    List<LinearLayout> rows;
    private ProgressBar spinner;

    public static PlanViewFragment newInstance(int index){
        PlanViewFragment planViewFragment = new PlanViewFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        planViewFragment.setArguments(args);
        return planViewFragment;
    }

    /**
     * przyda się przy AsyncTasku
     * @return parametr dany przez PlanView przy new PlanViewFragment.newInstance(index);
     */
    public int getIndex(){
        return getArguments().getInt("index");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_plan_view, container, false);

        spinner = (ProgressBar)view.findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);

        rows = new ArrayList<>(ROW_IDS.length);
        for(int id : ROW_IDS){
            LinearLayout row = (LinearLayout)view.findViewById(id);
            row.setVisibility(View.INVISIBLE);
            rows.add(row);
        }

        //bierzemy pathParameter z intenta
        pathParameter = getActivity().getIntent().getExtras().getString(Sources.TAG);
        path = "http://www.plan.1lo.gorzow.pl/plany/" + pathParameter + ".html";

        //pobieramy plan z AsyncTasku
        new GetPlanInBackground().execute();

        return view;
    }


    private class GetPlanInBackground extends AsyncTask<Void, Void, Void> {

        //Lista do której zapiszemy lekcje
        ArrayList<String> lekcjeArray;
        ArrayList<String> godzinyArray;
        //plik z którego ewentualnie będą ładowane pliki
        String fileName;
        File saved;

        @Override
        protected Void doInBackground(Void... params) {
            Document document;

            fileName = pathParameter;
            saved = new File(getActivity().getFilesDir(), fileName);
            try {
                //jeżeli nie istnieje Jsoup weźmie dane z internetu
                if(!saved.exists()) {
                    document = Jsoup.connect(path).get();
                }
                //jeżeli istnieje spróbuje załadować z niego kontent
                else{
                    document = Jsoup.parse(FileHandling.
                            readFileAsString(fileName, getActivity().getApplicationContext()));
                }
                //prettyPrint(false) <- zostawia whitespaces
                document.outputSettings(new Document.OutputSettings().prettyPrint(false));
                //Zbiera do Elements wszystkie obiekty z klasą "l"
                Elements lessons = document.getElementsByClass("l");
                Elements hours = document.getElementsByClass("g");

                //ładuje lessons do lekcjeArray (ArrayList zdaje się lepiej reagować na castowanie do String)
                lekcjeArray = new ArrayList<>(lessons.size());
                for (int i = 0; i < lessons.size(); i++) {
                    lekcjeArray.add(lessons.get(i).text());
                }
                godzinyArray = new ArrayList<>(hours.size());
                for (int i = 0; i < hours.size(); i++){
                    godzinyArray.add(hours.get(i).text());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //sprawdzamy którą zakładkę tabelki chciał PlanView
            //ten planOperator zajmuje się zbieraniem odpowiednich danych z lekjeArray
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
                //sprawdza długość lekcjeArray wg. potrzeb i ładuje kontent z lekcjeArray do
                //lekcji$n
                //niektóre plany lekcji mogą mieć mniej niż 50 komórek, stąd bez warunków szybko
                //otrzymalibyśmy NullPointerException albo IndexOutOfBoundsException
                if (lekcjeArray.size() >= planOperator + 1) {
                    ((TextView)getView().findViewById(R.id.lekcja_0))
                            .setText(lekcjeArray.get(planOperator).replaceAll("-", ""));
                    ((TextView)getView().findViewById(R.id.godzina_0)).setText(godzinyArray.get(0));
                    rows.get(0).setVisibility(View.VISIBLE);
                }
                if (lekcjeArray.size() >= planOperator + 6) {
                    ((TextView)getView().findViewById(R.id.lekcja_1))
                            .setText(lekcjeArray.get(planOperator + 5).replaceAll("-", ""));
                    ((TextView)getView().findViewById(R.id.godzina_1)).setText(godzinyArray.get(1));
                    rows.get(1).setVisibility(View.VISIBLE);
                }
                if (lekcjeArray.size() >= planOperator + 11) {
                    ((TextView)getView().findViewById(R.id.lekcja_2))
                            .setText(lekcjeArray.get(planOperator + 10).replaceAll("-", ""));
                    ((TextView)getView().findViewById(R.id.godzina_2)).setText(godzinyArray.get(2));
                    rows.get(2).setVisibility(View.VISIBLE);
                }
                if (lekcjeArray.size() >= planOperator + 16) {
                    ((TextView)getView().findViewById(R.id.lekcja_3))
                            .setText(lekcjeArray.get(planOperator + 15).replaceAll("-", ""));
                    ((TextView)getView().findViewById(R.id.godzina_3)).setText(godzinyArray.get(3));
                    rows.get(3).setVisibility(View.VISIBLE);
                }
                if (lekcjeArray.size() >= planOperator + 21) {
                    ((TextView)getView().findViewById(R.id.lekcja_4))
                            .setText(lekcjeArray.get(planOperator + 20).replaceAll("-", ""));
                    ((TextView)getView().findViewById(R.id.godzina_4)).setText(godzinyArray.get(4));
                    rows.get(4).setVisibility(View.VISIBLE);
                }
                if (lekcjeArray.size() >= planOperator + 26) {
                    ((TextView)getView().findViewById(R.id.lekcja_5))
                            .setText(lekcjeArray.get(planOperator + 25).replaceAll("-", ""));
                    ((TextView)getView().findViewById(R.id.godzina_5)).setText(godzinyArray.get(5));
                    rows.get(5).setVisibility(View.VISIBLE);
                }
                if (lekcjeArray.size() >= planOperator + 31) {
                    ((TextView)getView().findViewById(R.id.lekcja_6))
                            .setText(lekcjeArray.get(planOperator +30).replaceAll("-", ""));
                    ((TextView)getView().findViewById(R.id.godzina_6)).setText(godzinyArray.get(6));
                    rows.get(6).setVisibility(View.VISIBLE);
                }
                if (lekcjeArray.size() >= planOperator + 36) {
                    ((TextView)getView().findViewById(R.id.lekcja_7))
                            .setText(lekcjeArray.get(planOperator + 35).replaceAll("-", ""));
                    ((TextView)getView().findViewById(R.id.godzina_7)).setText(godzinyArray.get(7));
                    rows.get(7).setVisibility(View.VISIBLE);
                }
                if (lekcjeArray.size() >= planOperator + 41) {
                    ((TextView)getView().findViewById(R.id.lekcja_8))
                            .setText(lekcjeArray.get(planOperator + 40).replaceAll("-", ""));
                    ((TextView)getView().findViewById(R.id.godzina_8)).setText(godzinyArray.get(8));
                    rows.get(8).setVisibility(View.VISIBLE);
                }
                if (lekcjeArray.size() >= planOperator + 46) {
                    ((TextView)getView().findViewById(R.id.lekcja_9))
                            .setText(lekcjeArray.get(planOperator + 45).replaceAll("-", ""));
                    ((TextView)getView().findViewById(R.id.godzina_9)).setText(godzinyArray.get(9));
                    rows.get(9).setVisibility(View.VISIBLE);
                }

                spinner.setVisibility(View.GONE);
            }catch (NullPointerException ignored){
                //NullPointerException -> brak internetu w tym przypadku
                ((TextView)getView().findViewById(R.id.lekcja_0)).setText(R.string.brak_internetu);
                spinner.setVisibility(View.GONE);
            }
        }
    }
}

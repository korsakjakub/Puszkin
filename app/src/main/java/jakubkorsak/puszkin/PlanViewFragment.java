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


    String path;
    String pathParameter;
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

        //inicjowanie elementów UI
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

        //bierzemy pathParameter z intenta
        pathParameter = getActivity().getIntent().getExtras().getString(Sources.TAG);
        path = "http://www.plan.1lo.gorzow.pl/plany/" + pathParameter + ".html";

        //pobieramy plan z AsyncTasku
        new GetPlanInBackground().execute();

        return view;
    }



    public class GetPlanInBackground extends AsyncTask<Void, Void, Void> {

        //Lista do której zapiszemy lekcje
        ArrayList<String> lekcjeArray;
        //plik z którego ewentualnie będą ładowane pliki
        String fileName;
        File saved;

        @Override
        protected Void doInBackground(Void... params) {
            Document document;

            fileName = "ZAPIS_" + pathParameter;
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

                //ładuje lessons do lekcjeArray (ArrayList zdaje się lepiej reagować na castowanie do String)
                lekcjeArray = new ArrayList<>(lessons.size());
                for (int i = 0; i < lessons.size(); i++) {
                    lekcjeArray.add(lessons.get(i).text());
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
                //NullPointerException -> brak internetu w tym przypadku
                lekcja0.setText(R.string.brak_internetu);
            }
        }
    }
}

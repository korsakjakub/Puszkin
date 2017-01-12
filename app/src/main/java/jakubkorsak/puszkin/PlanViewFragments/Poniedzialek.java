package jakubkorsak.puszkin.PlanViewFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jakubkorsak.puszkin.R;

/**
 * Created by jakub on 11.01.17.
 */

public class Poniedzialek extends Fragment {

    private static final int [] LEKCJE_IDS = {
            R.id.lekcja_0,
            R.id.lekcja_1,
            R.id.lekcja_2,
            R.id.lekcja_3,
            R.id.lekcja_4,
            R.id.lekcja_5,
            R.id.lekcja_6,
            R.id.lekcja_7,
            R.id.lekcja_8,
            R.id.lekcja_9,
    };
    public static Poniedzialek newInstance(ArrayList<String> lekcjeArray){
        Poniedzialek p = new Poniedzialek();
        Bundle args = new Bundle();
        args.putStringArrayList("lekcjeArray", lekcjeArray);
        p.setArguments(args);
        return p;
    }

    public ArrayList<String> getLekcjeArray(){ return getArguments().getStringArrayList("lekcjeArray");}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_plan_view, container, false);

        List<TextView> lekcje = new ArrayList<>(LEKCJE_IDS.length);
        for(int id : LEKCJE_IDS){
            TextView lekcjaTextView = (TextView)view.findViewById(id);
            lekcje.add(lekcjaTextView);
        }

        //    lekcje.get(0).setText(getLekcjeArray().get(0));
        //lekcje.get(1).setText();
        //lekcje.get(2).setText();

        return view;
    }
}

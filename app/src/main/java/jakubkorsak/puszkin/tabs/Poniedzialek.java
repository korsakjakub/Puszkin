package jakubkorsak.puszkin.tabs;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String lekcja[] = getActivity().getIntent().getStringArrayExtra("lekcjeArray");

        List<TextView> lekcje = new ArrayList<>(LEKCJE_IDS.length);
        for(int id : LEKCJE_IDS){
            TextView lekcjaTextView = (TextView)getActivity().findViewById(id);
            lekcje.add(lekcjaTextView);
        }

        return inflater.inflate(R.layout.fragment_htmlparsed, container, false);
    }
}
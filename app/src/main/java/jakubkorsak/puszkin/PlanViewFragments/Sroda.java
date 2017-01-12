package jakubkorsak.puszkin.PlanViewFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jakubkorsak.puszkin.R;

/**
 * Created by jakub on 11.01.17.
 */

public class Sroda extends Fragment {


    public static Sroda newInstance(int index){
        Sroda s = new Sroda();
        Bundle args = new Bundle();
        args.putInt("index", index);
        s.setArguments(args);

        return s;
    }
    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan_view, container, false);
    }
}

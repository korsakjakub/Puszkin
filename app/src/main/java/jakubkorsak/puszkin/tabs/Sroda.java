package jakubkorsak.puszkin.tabs;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_htmlparsed, container, false);
    }
}

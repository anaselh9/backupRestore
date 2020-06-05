package android.esisa.mybackup2.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.esisa.mybackup2.R;
import android.esisa.mybackup2.adapters.SmsAdapter;
import android.esisa.mybackup2.ui.MainActivity;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SmsFragment extends Fragment {
    private ListView listView;
    public SmsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_sms, container, false);

        listView =view.findViewById(R.id.listView2);
        SmsAdapter smsAdapter= new SmsAdapter(getContext());
        listView.setAdapter(smsAdapter);
        return view;
    }

}

package android.esisa.mybackup2.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.esisa.mybackup2.R;
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
    private MainActivity mainActivity;
    public SmsFragment() {
        Log.i("cycle","SMSFragment");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sms, container, false);
      /*  String[] menuitems={
                "Do  !",
                "Do s !",
                "Do y!"
        };
        ListView listView=view.findViewById(R.id.listView2);
        ArrayAdapter<String> listViewAdapter=new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                menuitems
        );
        listView.setAdapter(listViewAdapter);*/

        ListView listView=view.findViewById(R.id.listView1);
      /*  ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                mainActivity.getSmsList()
        );
        listView.setAdapter(adapter);*/
        return view;
    }

}

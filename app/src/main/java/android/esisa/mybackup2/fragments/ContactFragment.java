package android.esisa.mybackup2.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.esisa.mybackup2.R;
import android.esisa.mybackup2.adapters.ContactAdapter;
import android.esisa.mybackup2.ui.MainActivity;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {


    public ContactFragment() {
        Log.i("cycle","ContactFragment");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {





        Log.i("cycle","on createViewFragment");
       View view= inflater.inflate(R.layout.fragment_contact, container, false);
        String[] menuitems={
                "Do something !",
                "Do something else !",
                "Do yet another thing!"
        };
        ListView listView=view.findViewById(R.id.listView1);
       /* ArrayAdapter<String> listViewAdapter=new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                menuitems
        );*/

        ContactAdapter listViewAdapter=new ContactAdapter(getActivity());
        listView.setAdapter(listViewAdapter);

        return view;


    }

}

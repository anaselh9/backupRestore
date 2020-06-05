package android.esisa.mybackup2.fragments;


import android.esisa.mybackup2.Firebase.FirebaseHelper;
import android.esisa.mybackup2.R;
import android.esisa.mybackup2.adapters.ContactAdapter1;
import android.esisa.mybackup2.models.Contact;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


//    DatabaseReference db;
//    FirebaseHelper helper;
//    ContactAdapter1 adapter1;
//    private Button SaveBtn;
//
//
//    public HomeFragment() {
//        Log.i("cycle", "HomeFragment");
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//
//        Log.i("cycle", "on createViewFragment");
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        String[] menuitems = {
//                "Do something !",
//                "Do something else !",
//                "Do yet another thing!"
//        };
//
//        final ListView listView = view.findViewById(R.id.listView1);
//
//        db = FirebaseDatabase.getInstance().getReference();
//        helper = new FirebaseHelper(db);
//
//        adapter1 = new ContactAdapter1(getContext(), helper.retriveData());
//        listView.setAdapter(adapter1);
//
//
//        SaveBtn = view.findViewById(R.id.btnSave);
//        SaveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        String nameContact = ((TextView) view.findViewById(R.id.name)).getText().toString().trim();
//                        String phoneContact = ((TextView) view.findViewById(R.id.phone)).getText().toString().trim();
//                        String emailContact = ((TextView) view.findViewById(R.id.email)).getText().toString().trim();
//
//                        Contact contact = new Contact();
//
//                        contact.setName(nameContact);
//                        contact.setEmail(emailContact);
//                        contact.setPhone(phoneContact);
//
//                        if (nameContact != null && nameContact.length() > 0) {
//                            helper.save(contact);
//
//                            adapter1 = new ContactAdapter1(getActivity(), helper.retriveData());
//                            listView.setAdapter(adapter1);
//                        }
//                    }
//                });
//
//
//            }
//        });
//
//        return view;
//
//    }
}

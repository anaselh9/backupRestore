package android.esisa.mybackup2.fragments;


import android.Manifest;
import android.content.pm.PackageManager;

import android.esisa.mybackup2.R;
import android.esisa.mybackup2.adapters.ContactAdapter;
import android.esisa.mybackup2.adapters.ContactAdapter1;
import android.esisa.mybackup2.models.Contact;
import android.esisa.mybackup2.ui.MainActivity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment{

private  String nameContact, phoneContact, emailContact;
    long cp = 0;
    ContactAdapter adapter;

    private Button SaveBtn;
    FirebaseDatabase database;
    DatabaseReference ref;


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
        final ListView listView=view.findViewById(R.id.listView1);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Contacts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    cp = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        adapter = new ContactAdapter(getContext());
        listView.setAdapter(adapter);

        SaveBtn = view.findViewById(R.id.btnSave);
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                         nameContact = ((TextView) view.findViewById(R.id.name)).getText().toString().trim();
                         phoneContact = ((TextView) view.findViewById(R.id.phone)).getText().toString().trim();
                         emailContact = ((TextView) view.findViewById(R.id.email)).getText().toString().trim();

                   }
                });
                Contact contact = new Contact();

                contact.setName(nameContact);
                contact.setEmail(emailContact);
                contact.setPhone(phoneContact);

                if (nameContact != null && nameContact.length() > 0) {

                    Toast.makeText(getContext(), contact.getName(), Toast.LENGTH_LONG).show();
                    ref.child(String.valueOf(cp+1)).setValue(contact);

                }
           }
        });

       // ContactAdapter listViewAdapter=new ContactAdapter(getActivity());
      //  listView.setAdapter(listViewAdapter);



        return view;


    }


}

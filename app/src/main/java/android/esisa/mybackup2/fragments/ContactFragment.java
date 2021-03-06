package android.esisa.mybackup2.fragments;


import android.content.Context;
import android.esisa.mybackup2.R;
import android.esisa.mybackup2.adapters.ContactAdapter;
import android.esisa.mybackup2.dal.ContactDao;
import android.esisa.mybackup2.models.Contact;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment{

private  String nameContact, phoneContact, emailContact;
    long cp = 0;

    private Button SaveBtn;
    FirebaseDatabase database;
    DatabaseReference ref;


    private ContactAdapter adapter;
    private ArrayList<Contact> data= new ArrayList<>();

    public ContactFragment(ArrayList<Contact> data) {
            this.data = data;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_contact, container, false);
        final ListView listView=view.findViewById(R.id.listView1);

//      database = FirebaseDatabase.getInstance();
//        ref = database.getReference("Backup Restore");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    cp = (dataSnapshot.getChildrenCount());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//
//        SaveBtn = view.findViewById(R.id.btnSave);
//        SaveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                         nameContact = ((TextView) view.findViewById(R.id.name)).getText().toString().trim();
//                         phoneContact = ((TextView) view.findViewById(R.id.phone)).getText().toString().trim();
//                         emailContact = ((TextView) view.findViewById(R.id.email)).getText().toString().trim();
//
//                   }
//                });
//                Contact contact = new Contact();
//
//                contact.setName(nameContact);
//                contact.setEmail(emailContact);
//                contact.setPhone(phoneContact);
//
//                if (nameContact != null && nameContact.length() > 0) {
//
//                    Toast.makeText(getContext(), contact.getName(), Toast.LENGTH_LONG).show();
//                    ref.child(String.valueOf(cp+1)).setValue(contact);
//
//
//                }
//           }
//        });
//

        adapter = new ContactAdapter(getContext(),data);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);


        // test update

//        try{
//
//            if(data.size())
//
//
//
//        }catch(Exception ex){
//            Toast.makeText(getContext(), "error updating", Toast.LENGTH_SHORT).show();
//        }

        return view;


    }


}

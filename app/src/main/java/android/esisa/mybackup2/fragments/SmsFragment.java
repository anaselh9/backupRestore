package android.esisa.mybackup2.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.esisa.mybackup2.R;
import android.esisa.mybackup2.adapters.SmsAdapter;
import android.esisa.mybackup2.models.Sms;
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
import android.widget.ArrayAdapter;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class SmsFragment extends Fragment {
    private ListView listView;
    private String numberDestination, contentMessage;
    private long cp = 0;

    private Button btnSaveSms;
    FirebaseDatabase database;
    DatabaseReference ref;

    SmsAdapter smsAdapter;


    public SmsFragment() {
        Log.i("cycle", "Sms Fragment");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("cycle","onCreateView Fragment");

        View view= inflater.inflate(R.layout.fragment_sms, container, false);
        listView =view.findViewById(R.id.listView2);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Sms");
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

        btnSaveSms = view.findViewById(R.id.btnSms);
        btnSaveSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        numberDestination = ((TextView)view.findViewById(R.id.number)).getText().toString().trim();
                        contentMessage = ((TextView)view.findViewById(R.id.cont)).getText().toString().trim();
                    }
                });
                Sms mySms = new Sms();
                mySms.setNumero(numberDestination);
                mySms.setContenu(contentMessage);


                if (contentMessage != null && contentMessage.length() > 0) {

                    Toast.makeText(getContext(), mySms.getNumero(), Toast.LENGTH_LONG).show();
                    ref.child(String.valueOf(cp+1)).setValue(mySms);

                }
            }
        });
        SmsAdapter smsAdapter= new SmsAdapter(getContext());
        listView.setAdapter(smsAdapter);


        return view;


    }

}

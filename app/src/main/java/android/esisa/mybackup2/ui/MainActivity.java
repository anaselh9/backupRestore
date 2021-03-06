package android.esisa.mybackup2.ui;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;



import android.Manifest;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.esisa.mybackup2.GoogleInformations;
import android.esisa.mybackup2.adapters.SmsAdapter;
import android.esisa.mybackup2.dal.ContactDao;
import android.esisa.mybackup2.dal.SmsDao;
import android.esisa.mybackup2.fragments.ContactFragment;
import android.esisa.mybackup2.fragments.HomeFragment;
import android.esisa.mybackup2.R;
import android.esisa.mybackup2.fragments.SmsFragment;
import android.esisa.mybackup2.models.Contact;
import android.esisa.mybackup2.models.Sms;

import android.os.Bundle;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import android.widget.Toast;
import android.esisa.mybackup2.adapters.ContactAdapter;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;

import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ListView listView1;
    private ListView listView2;
    private SignInButton signInButton;
    private int RC_SIGN_IN = 0;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleInformations googleInformations;
    private String idEmail, emailCheck;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference testRef;
    private  String nameContact, phoneContact, emailContact;
    private String contentMessage, numberMessageS, numberMessageR;
    private ContactFragment contactFragment;
    private String nameTest;
    private ContactAdapter contactAdapter;
    private SmsAdapter smsAdapter;
    private int pos;
    private ArrayList<Contact> dataContact= new ArrayList<>();
    private ArrayList<Sms> dataSms= new ArrayList<>();
    private ArrayList<Contact> contacts= new ArrayList<>();
    private ArrayList<Sms> sms= new ArrayList<>();
    private ContactDao contactDao;
    private SmsDao smsDao;
   // ProgressBar progressBar;


    //Section Code : Retriving Data

    public ArrayList<Contact> listContactsR = new ArrayList<>();
    public ArrayList<Contact> listFromFBase = new ArrayList<>();
    public ArrayList<Sms> listSmsR = new ArrayList<>();
    public ArrayList<Sms> listSmsFromFBase = new ArrayList<>();

    public Contact contactFireBase;
    public Sms smsFireBase;
    Contact myContact;
    Sms mySms;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabLayout);
        listView1 = findViewById(R.id.listView1);
        listView2= findViewById(R.id.listView2);
        signInButton = findViewById(R.id.sign_in_button);
        //progressBar = findViewById(R.id.progressBar);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.sign_in_button:
                       signIn();
                        break;
                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        if (savedInstanceState == null) {
            pos=0;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.Frame, new HomeFragment())
                    .commitNow();
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.Frame, new HomeFragment())
                            .commitNow();
                }
              else if (tab.getPosition() == 1) {
                    pos=tab.getPosition();
                    if (checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED ) {
                        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
                        return;
                    } else  {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Frame, new ContactFragment(dataContact))
                                .commitNow();
                        Toast.makeText(MainActivity.this, "Selected Position" + tab.getPosition(), Toast.LENGTH_SHORT).show();

                    }

                } else if (tab.getPosition() == 2) {
                    pos=tab.getPosition();
                    if (checkSelfPermission(Manifest.permission.READ_SMS)== PackageManager.PERMISSION_DENIED) {
                        Log.i("cycle", "if :checkSelfPermission");
                        requestPermissions(new String[]{Manifest.permission.READ_SMS}, 1);
                        return;
                    } else {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Frame, new SmsFragment(dataSms))
                                .commitNow();
                        Toast.makeText(MainActivity.this, "Selected Position" + tab.getPosition(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Toast.makeText(MainActivity.this, "Unselected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Toast.makeText(MainActivity.this, "Re-Selected", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Intent homeIntent = new Intent(MainActivity.this, GoogleInformations.class);
              startActivity(homeIntent);
        } catch (ApiException e) {
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Backup:
                getEmailBack();
                if(checkEmailFirst() == true){
                    testFireBaseDatabase();
                }


                return true;
            case R.id.Restore:
                listFromFBase = getListContacts();
                listSmsFromFBase = getListSms();

               if(pos == 1){
                   getSupportFragmentManager().beginTransaction()
                           .replace(R.id.Frame, new ContactFragment(listFromFBase))
                           .commitNow();
                   Toast.makeText(MainActivity.this, "Selected Position" + pos, Toast.LENGTH_SHORT).show();

            }else if(pos ==2 ){
                   getSupportFragmentManager().beginTransaction()
                           .replace(R.id.Frame, new SmsFragment(listSmsFromFBase))
                           .commitNow();
                   Toast.makeText(MainActivity.this, "Selected Position" + pos, Toast.LENGTH_SHORT).show();
               }



             return true;
            case R.id.SignInEmail:
             Intent signInIntent = new Intent(MainActivity.this, GoogleInformations.class);
             startActivity(signInIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

     }
        public void getEmailBack(){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            idEmail = sharedPreferences.getString("Email", "no-id");
        }
        public boolean checkEmailFirst(){
            testRef = FirebaseDatabase.getInstance().getReference().child("email");
            testRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String emailCheckFromFireBase = dataSnapshot.getValue(String.class);
                    emailCheck = emailCheckFromFireBase;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            if(emailCheck != null){
                return true;
            }else
            {
                Toast.makeText(this,"Error, please try to login firt to save your data", Toast.LENGTH_LONG).show();
                return false;

            }


        }
        public ArrayList<Sms> getListSms(){
            testRef = FirebaseDatabase.getInstance().getReference().child("BACKUP RESTOR").child("Messages");
            testRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listSmsR.clear();
                    for (DataSnapshot smsDS : dataSnapshot.getChildren()){
                        mySms = smsDS.getValue(Sms.class);
                        smsFireBase = new Sms();

                        smsFireBase.setNumero(mySms.getNumero());
                        smsFireBase.setContenu(mySms.getContenu());

                        listSmsR.add(smsFireBase);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        return listSmsR;
        }
        public ArrayList<Contact> getListContacts(){
           testRef = FirebaseDatabase.getInstance().getReference().child("BACKUP RESTOR").child("Contacts");
            testRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listContactsR.clear();
                for(DataSnapshot contactDS : dataSnapshot.getChildren()){
                     myContact = contactDS.getValue(Contact.class);
                    contactFireBase = new Contact();

                    contactFireBase.setName(myContact.getName());
                    contactFireBase.setEmail(myContact.getEmail());
                    contactFireBase.setPhone(myContact.getPhone());

                    listContactsR.add(contactFireBase);
                }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            return listContactsR;
        }


        public void testFireBaseDatabase(){
            dataContact = AllContacts();
            dataSms = AllSms();
            firebaseDatabase = FirebaseDatabase.getInstance();
            testRef = firebaseDatabase.getReference().child("BACKUP RESTOR");
            testRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       // testRef.setValue("Test Firebase From Selected Item Menu");
                   // testRef.setValue(idEmail);

                    for (int i=0; i<dataContact.size(); i++){
                            nameContact = dataContact.get(i).getName();
                            phoneContact = dataContact.get(i).getPhone();
                            emailContact = dataContact.get(i).getEmail();

                        Contact contact = new Contact();
                        contact.setName(nameContact);
                        contact.setPhone(phoneContact);
                        contact.setEmail(emailContact);

                        testRef.child("Contacts").child(String.valueOf(i)).setValue(contact);
                    }
                    for (int j=0; j< dataSms.size(); j++){
                        contentMessage = dataSms.get(j).getContenu();
                        numberMessageS = dataSms.get(j).getNumero();

                        Sms ourSms = new Sms();
                        ourSms.setContenu(contentMessage);
                        ourSms.setNumero(numberMessageS);

                       // Toast.makeText(getBaseContext(), ourSms.getNumero(), Toast.LENGTH_LONG).show();

                       testRef.child("Messages").child(String.valueOf(j)).setValue(ourSms);

                    }
                 }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
            @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //Log.i("cycle", "grantResults[0]");
            if(pos==1) {
                Log.i("cycle", "onRequestPermissionsResult data "+dataContact.size());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Frame, new ContactFragment(dataContact))
                        .commitNow();
            }else if(pos==2) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Frame, new SmsFragment(dataSms))
                        .commitNow();
            }
        }
    }
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem=menu.findItem(R.id.search);
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                Log.i("cycle","onMenuItemActionExpand");
                return true;
            }
            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                Log.i("cycle","onMenuItemActionCollapse");
                return true;
            }
        });
        SearchView searchView=(SearchView)menuItem.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(pos==1){
                 if (newText.isEmpty()) {
                     dataContact = AllContacts();
                     getSupportFragmentManager().beginTransaction()
                             .replace(R.id.Frame, new ContactFragment(dataContact))
                             .commitNow();
                 }
                 else {
                     Log.i("cycle", "newText.isEmpty else");
                     dataContact=updateContact(newText);
                     if(dataContact.size()>0) {
                         getSupportFragmentManager().beginTransaction()
                                 .replace(R.id.Frame, new ContactFragment(dataContact))
                                 .commitNow();
                     }
                     else {
                         getSupportFragmentManager().beginTransaction()
                                 .replace(R.id.Frame, new HomeFragment())
                                 .commitNow();
                     }
                    }
                }
                else if(pos==2){
                    if (newText.isEmpty()) {
                        dataSms = AllSms();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Frame, new SmsFragment(dataSms))
                                .commitNow();

                    }
                    else {
                        dataSms = updateSms(newText);
                        if (dataSms.size() > 0) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.Frame, new SmsFragment(dataSms))
                                    .commitNow();
                        } else {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.Frame, new HomeFragment())
                                    .commitNow();
                        }
                    }
                }
                return true;
            }
        });

        return true;
    }
    public ArrayList<Contact> updateContact(String text) {
        contactDao =new ContactDao(this);
        contacts=contactDao.getData();

        ArrayList<Contact> filtredList=new ArrayList<>();
        for (Contact contact:contacts)
        {
            if (contact.getName().contains(text))
            {
                filtredList.add(contact);
            }
        }
        return filtredList;
    }
    public ArrayList<Contact> AllContacts() {
        contactDao =new ContactDao(this);
        contacts=contactDao.getData();

        return contacts;
    }
    public ArrayList<Sms> updateSms(String text) {
        smsDao=new SmsDao(this);
        sms=smsDao.getData();
        ArrayList<Sms> filtredList=new ArrayList<>();
        for (Sms s:sms)
        {
            if (s.getNumero().contains(text))
            {
                filtredList.add(s);
            }
        }
        return filtredList;
    }
    public ArrayList<Sms> AllSms() {
        smsDao=new SmsDao(this);
        sms=smsDao.getData();

        return sms;
    }

}

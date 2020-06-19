package android.esisa.mybackup2.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.esisa.mybackup2.adapters.ContactAdapter;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ListView listView1;
    private ListView listView2;
    private SignInButton signInButton;
    private int RC_SIGN_IN = 0;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleInformations googleInformations;
    private String idEmail;
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
    ProgressBar progressBar;


    //Section Code : Retriving Data

    private ArrayList<Contact> listContactsR;
    private ArrayList<Sms> listSmsR;
    private  String emailFromDB,nameFromDb,phoneFromDb,emailFromDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabLayout);
        listView1 = findViewById(R.id.listView1);
        listView2= findViewById(R.id.listView2);
        signInButton = findViewById(R.id.sign_in_button);
        progressBar = findViewById(R.id.progressBar);

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

                testFireBaseDatabase();

                return true;
            case R.id.Restore:
            //    getEmailBack(); pour afficher que retriving email marche

                if(pos==1) {
                    Log.i("cycle", "onRequestPermissionsResult data "+dataContact.size());
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.Frame, new ContactFragment(getListContacts())
                            .commitNow();
                }else if(pos==2) {
                    getSupportFragmentManager().beginTransaction()
                            //.replace(R.id.Frame, new SmsFragment(/*khas hena list dial sms jrab hadchu */)
                            .commitNow();
                }
               Toast.makeText(getBaseContext(), emailFromDb
                     , Toast.LENGTH_LONG).show();

             // Toast.makeText(this, idEmail, Toast.LENGTH_LONG).show();
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

        public /*Modif 1 */ArrayList<Contact> getListContacts(){
            ArrayList<Contact> data = new ArrayList<>();  //Modif 2
             testRef = FirebaseDatabase.getInstance().getReference().child("Backup Restore").child("Contacts");
            // testRef = firebaseDatabase.getReference("Backup Restore").child("Contacts").child("1");
            //testRef = firebaseDatabase.getReference("Backup Restore").child("Contacts").child("0");
            Contact contactFireBase = new Contact();//Modif 3 7iadt final
            testRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                      // Contact contactFireBase = dataSnapshot.getValue(Contact.class);
                     // listContactsR.add(contactFireBase);
                for(DataSnapshot contactDS : dataSnapshot.getChildren()){
                    nameFromDb = dataSnapshot.child("name").getValue().toString();
                    phoneFromDb = dataSnapshot.child("phone").getValue().toString();
                    emailFromDb = dataSnapshot.child("email").getValue().toString();
                }
                    contactFireBase.setName(nameFromDb);
                    contactFireBase.setEmail(emailFromDb);
                    contactFireBase.setPhone(phoneFromDb);
                    data.add(contactFireBase);//Modif 4 ajouter f data li declarite f modif 2
                       //listContactsR.add(contactFireBase);  //ContactFragment contactFragment = new ContactFragment(listContactsR);
                    // emailFromDB = dataSnapshot.child("Email").getValue().toString();
                   //Toast.makeText(getBaseContext(), listContactsR.get(0).getName(), Toast.LENGTH_LONG).show();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
                 return data; //Modif 5
        }
        public void testFireBaseDatabase(){
            dataContact = AllContacts();
            dataSms = AllSms();
            firebaseDatabase = FirebaseDatabase.getInstance();
            testRef = firebaseDatabase.getReference("Backup Restore");
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

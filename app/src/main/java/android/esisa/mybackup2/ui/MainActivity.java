package android.esisa.mybackup2.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.esisa.mybackup2.adapters.SmsAdapter;
import android.esisa.mybackup2.fragments.ContactFragment;
import android.esisa.mybackup2.fragments.HomeFragment;
import android.esisa.mybackup2.R;
import android.esisa.mybackup2.fragments.SmsFragment;
import android.esisa.mybackup2.models.Sms;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.esisa.mybackup2.adapters.ContactAdapter;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ListView listView1;
    private ListView listView2;



    private ContactAdapter contactAdapter;
    private SmsAdapter smsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabLayout);
        listView1 = findViewById(R.id.listView1);
        listView2= findViewById(R.id.listView2);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.Frame, new HomeFragment())
                    .commitNow();
        }



        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED && checkSelfPermission(Manifest.permission.READ_SMS)== PackageManager.PERMISSION_DENIED) {
            Log.i("cycle", "if :checkSelfPermission");
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.READ_SMS}, 1);
            return;
        } else {
            Log.i("cycle", "else :checkSelfPermission");
            //contactAdapter = new ContactAdapter(this);
            // smsAdapter= new SmsAdapter(this);
            //listView1.setAdapter(contactAdapter);
            //listView2.setAdapter(smsAdapter);
        }



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.Frame, new HomeFragment())
                            .commitNow();
                    Toast.makeText(MainActivity.this, "Selected Position" + tab.getPosition(), Toast.LENGTH_SHORT).show();
                } else if (tab.getPosition() == 1) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.Frame, new ContactFragment())
                            .commitNow();
                    Toast.makeText(MainActivity.this, "Selected Position" + tab.getPosition(), Toast.LENGTH_SHORT).show();
                } else if (tab.getPosition() == 2) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.Frame, new SmsFragment())
                            .commitNow();
                    Toast.makeText(MainActivity.this, "Selected Position" + tab.getPosition(), Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Backup:
                Toast.makeText(this, "Backup selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.Restore:
                Toast.makeText(this, "Restore selected", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.i("cycle", "grantResults[0]");
           // contactAdapter = new ContactAdapter(this);
           // smsAdapter= new SmsAdapter(this);
           //listView1.setAdapter(contactAdapter);
          // listView2.setAdapter(smsAdapter);
        }

    }


}

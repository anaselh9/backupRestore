package android.esisa.mybackup2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.esisa.mybackup2.fragments.HomeFragment;
import android.esisa.mybackup2.ui.MainActivity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GoogleInformations extends AppCompatActivity {

    private TextView emailView;
    private Button showEmail;


    FirebaseDatabase database;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_informations);

        emailView = findViewById(R.id.emailViewText);
        showEmail = findViewById(R.id.btnCheckemail);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            final String personEmail = acct.getEmail();
            emailView.setText(personEmail);
             showEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    database = FirebaseDatabase.getInstance();
                    ref = database.getReference();
                    ref.child("testEmailNoeud").setValue(personEmail);
                }
            });
        // Intent sendEmailValue = new Intent(this, MainActivity.class);
          // sendEmailValue.putExtra("Email", emailView.getText().toString());
           //startActivity(sendEmailValue);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Email", emailView.getText().toString());
            editor.apply();

        }
}




}

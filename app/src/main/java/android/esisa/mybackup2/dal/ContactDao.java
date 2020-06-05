package android.esisa.mybackup2.dal;

import android.content.Context;
import android.database.Cursor;
import android.esisa.mybackup2.models.Contact;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.Contacts;
import android.util.Log;

import androidx.loader.content.CursorLoader;

import java.util.ArrayList;
import java.util.List;

public class ContactDao {
    private ArrayList<Contact> data= new ArrayList<>();

    public ArrayList<Contact> getData() {
        return data;
    }

    public void setData(ArrayList<Contact> data) {
        this.data = data;
    }

    public ContactDao(Context context)
    {
        Log.i("cycle","contactDao");
        Cursor Cursorcontacts=new CursorLoader(context, Contacts.CONTENT_URI,null,null,null,null).loadInBackground();
        Contact contact;
        while (Cursorcontacts.moveToNext()){
            contact = new Contact();
            contact.setName(Cursorcontacts.getString(Cursorcontacts.getColumnIndex(Contacts.DISPLAY_NAME)));
            String Id =Cursorcontacts.getString(Cursorcontacts.getColumnIndex(Contacts._ID));

            //Recup les Teles

            String[] PhoneProjection = new String[]{CommonDataKinds.Phone.NUMBER};
            Cursor cursorPhone=context.getContentResolver().query(CommonDataKinds.Phone.CONTENT_URI,PhoneProjection,Id+"="+CommonDataKinds.Phone.CONTACT_ID,null,null);
            if(cursorPhone.getCount()>0){
                cursorPhone.moveToFirst();
                contact.setPhone(cursorPhone.getString(0));//Hit projection 3ndi ghi column w7da

            }
            //Recup Email
            String[] EmailProjection = new String[]{CommonDataKinds.Email.ADDRESS};
            Cursor cursorEmail=context.getContentResolver().query(CommonDataKinds.Email.CONTENT_URI,EmailProjection,Id+"="+CommonDataKinds.Email.CONTACT_ID,null,null);
            if(cursorEmail.getCount()>0){
                cursorEmail.moveToFirst();
                contact.setEmail(cursorEmail.getString(0));//Hit projection 3ndi ghi column w7da

            }
            Log.e("cycle",contact.toString());
            data.add(contact);

        }
    }
}

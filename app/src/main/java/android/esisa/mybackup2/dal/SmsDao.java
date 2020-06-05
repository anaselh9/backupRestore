package android.esisa.mybackup2.dal;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.esisa.mybackup2.models.Sms;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SmsDao {
    private List<Sms> data= new ArrayList<>();

    public List<Sms> getData() {
        return data;
    }

    public void setData(List<Sms> data) {
        this.data = data;
    }



    public SmsDao(Context context){
        Uri inboxUri = Uri.parse("content://sms/sent");

        ContentResolver contentResolver =context.getContentResolver();
        Cursor cursor = contentResolver.query(inboxUri,
                null, null, null, null
        );
        Sms sms;
        while (cursor.moveToNext()) {
            sms = new Sms();
            sms.setNumero(cursor.getString(cursor.getColumnIndexOrThrow("address")).toString());
            sms.setContenu(cursor.getString(cursor.getColumnIndexOrThrow("body")).toString());

            data.add(sms);
            Log.e("cycle", sms.toString());
        }
        cursor.close();

    }


}

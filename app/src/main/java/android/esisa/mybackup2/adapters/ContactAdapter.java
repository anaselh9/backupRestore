package android.esisa.mybackup2.adapters;

import android.content.Context;
import android.esisa.mybackup2.fragments.ContactFragment;
import android.esisa.mybackup2.models.Contact;
import android.esisa.mybackup2.dal.ContactDao;
import android.esisa.mybackup2.R;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends ArrayAdapter {
    private List<Contact> data=new ArrayList<>();
    private List<Contact> cache=new ArrayList<>();
    private ContactDao contactDao;

    public ContactAdapter(@NonNull Context context) {

        super(context,0);
        contactDao=new ContactDao(context);
        data=contactDao.getData();
    }
    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView name;
        TextView phone;
        TextView email;
        //View v;
        public Viewholder(View v){
            super(v);
            Log.i("cycle","constructeur ViewHolder");
            name=v.findViewById(R.id.name);
            phone=v.findViewById(R.id.phone);
            email=v.findViewById(R.id.email);

        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.i("cycle","getView");
        Viewholder viewHolder;
        if (convertView == null) {

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_contact, parent, false);
            viewHolder=new Viewholder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (Viewholder) convertView.getTag();
        }
        viewHolder.name.setText(data.get(position).getName());
        viewHolder.phone.setText(data.get(position).getPhone());
        viewHolder.email.setText(data.get(position).getEmail());

        return convertView;
    }
    @Override
    public int getCount() {
        return data.size();
    }

}

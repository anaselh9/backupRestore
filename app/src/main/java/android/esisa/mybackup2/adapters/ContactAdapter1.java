package android.esisa.mybackup2.adapters;

import android.content.Context;
import android.esisa.mybackup2.R;
import android.esisa.mybackup2.dal.ContactDao;
import android.esisa.mybackup2.models.Contact;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter1 extends BaseAdapter {

    Context context;
    ArrayList<Contact> Contacts ;
    private ContactDao daoContact;


    public ContactAdapter1(Context context, ArrayList<Contact> contacts) {
        this.context = context;
        Contacts = contacts;
        daoContact=new ContactDao(context);
        Contacts=daoContact.getData();
    }


    @Override
    public int getCount() {
        return Contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return Contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.items_contact, parent, false );
        }
        TextView nameContact = (TextView) convertView.findViewById(R.id.name);
        TextView emailConatct = (TextView) convertView.findViewById(R.id.email);
        TextView numberPhone = (TextView)convertView.findViewById(R.id.phone);

        Contact contact = (Contact) this.getItem(i);

        nameContact.setText(contact.getName());
        emailConatct.setText(contact.getEmail());
        numberPhone.setText(contact.getPhone());

        return convertView;
    }
}

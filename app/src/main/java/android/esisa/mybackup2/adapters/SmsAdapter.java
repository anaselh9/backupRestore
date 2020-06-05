package android.esisa.mybackup2.adapters;

import android.content.Context;
import android.esisa.mybackup2.R;
import android.esisa.mybackup2.dal.SmsDao;
import android.esisa.mybackup2.models.Sms;
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

public class SmsAdapter extends ArrayAdapter {
    private List<Sms> data=new ArrayList<>();
    private List<Sms> cache=new ArrayList<>();
    private SmsDao smsDao;

    public SmsAdapter(@NonNull Context context) {
        super(context,0);
        Log.i("cycle","constructeur SmsAdapter");
        smsDao=new SmsDao(context);
        data=smsDao.getData();

        Log.i("cycle",this.data.size()+" size of data");

    }
    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView number;
        TextView contenu;

        //View v;
        public Viewholder(View v){
            super(v);
            Log.i("cycle","constructeur ViewHolder");
            number=v.findViewById(R.id.number);
            contenu=v.findViewById(R.id.cont);


        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.i("cycle","getView");
        Viewholder viewHolder;
        if (convertView == null) {

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_sms, parent, false);
            viewHolder=new Viewholder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (Viewholder) convertView.getTag();
        }
        viewHolder.number.setText(data.get(position).getNumero());
        viewHolder.contenu.setText(data.get(position).getContenu());
        return convertView;
    }

    @Override
    public int getCount() {
        return data.size();
    }
}

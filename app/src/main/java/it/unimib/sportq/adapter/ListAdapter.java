package it.unimib.sportq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import it.unimib.sportq.R;
import it.unimib.sportq.model.ListData;

public class ListAdapter extends ArrayAdapter<ListData> {
    public ListAdapter(@NonNull Context context, ArrayList<ListData> dataArrayList) {
        super(context, R.layout.list_sports, dataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ListData listData = getItem(position);

        if( view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_sports, parent, false);
        }

        ImageView listImage = view.findViewById(R.id.listImage);
        TextView listName = view.findViewById(R.id.listName);
        TextView listPlayer = view.findViewById(R.id.listPlayer);

        listImage.setImageResource(listData.image);
        listName.setText(listData.name);
        listPlayer.setText(listData.player);

        return view;
    }
}

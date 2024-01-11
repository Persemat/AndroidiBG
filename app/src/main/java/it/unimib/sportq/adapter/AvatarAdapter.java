package it.unimib.sportq.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import it.unimib.sportq.R;

public class AvatarAdapter extends BaseAdapter {
    private Context mContext;

    // Array di immagini o dati che desideri visualizzare nella GridView
    private int[] mThumbIds = {
            R.drawable.albu,
            R.drawable.ale,
            R.drawable.michi,
            R.drawable.perse,
            R.drawable.wapi
    };

    public AvatarAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return mThumbIds[position];
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // Se la vista non è stata creata, creala
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200)); // Personalizza le dimensioni delle immagini
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        } else {
            // Se la vista esiste già, riutilizzala
            imageView = (ImageView) convertView;
        }

        imageView.setTag(mThumbIds[position]);
        imageView.setImageResource(mThumbIds[position]);

        return imageView;
    }
}

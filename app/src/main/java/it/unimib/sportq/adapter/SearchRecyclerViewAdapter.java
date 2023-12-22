package it.unimib.sportq.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.unimib.sportq.R;
import it.unimib.sportq.model.ListData;


public class SearchRecyclerViewAdapter extends
        RecyclerView.Adapter<SearchRecyclerViewAdapter.SearchViewHolder> {

    public interface OnItemClickListener{
        void onSearchItemClick(ListData dataList);
    }
    private final List<ListData> dataList;
    public final OnItemClickListener onItemClickListener;


    public SearchRecyclerViewAdapter(List<ListData> dataList, OnItemClickListener onItemClickListener){
        this.dataList = dataList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_sports, parent, false);
        return new SearchViewHolder(view);
    }

    //association between data and layout
    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        if (dataList != null){
            return dataList.size();
        }
        return 0;
    }

    //ViewHolder to bind data to the RecyclerView items
    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ImageView imageView;
        private final TextView textViewSport;
        private final TextView textViewPlayer;

        public SearchViewHolder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.view_image);
            textViewPlayer = itemView.findViewById(R.id.view_player);
            textViewSport = itemView.findViewById(R.id.view_sport);
            itemView.setOnClickListener(this);
        }

        //method bind for binding the cell with the layout
        public void bind(ListData listdata){
            //qui devo associare i valori che voglio mostrare
            textViewSport.setText(listdata.getSport());
            textViewPlayer.setText(listdata.getSport());
            imageView.setImageResource(listdata.getImage());
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onSearchItemClick(dataList.get(getAdapterPosition()));
        }
    }
}

package it.unimib.sportq.ui.main;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.unimib.sportq.model.ListData;
import it.unimib.sportq.adapter.SearchRecyclerViewAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.unimib.sportq.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSearch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSearch extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentSearch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSearch.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSearch newInstance(String param1, String param2) {
        FragmentSearch fragment = new FragmentSearch();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);

    }


    RecyclerView recyclerView = getView().findViewById(R.id.recycler_view);
    RecyclerView.LayoutManager layoutManager =
            new LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false);

   // List<ListData> dataList = getDataList();

    /*SearchRecyclerViewAdapter searchRecyclerViewAdapter = new SearchRecyclerViewAdapter( dataList,
            SearchRecyclerViewAdapter.OnItemClickListener(){
                @Override
                public void onSearchItemClick(ListData dataList) {
                   //da modificare, per ora come esempio
                    Snackbar.make(view, dataList.getSport(), Snackbar.LENGTH_SHORT.show());
                }
            });


    

    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(searchRecyclerViewAdapter);

    public List getDataList(){

        return data;
    }
    */
}
package com.example.demoapp.view.fragment.sales;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.demoapp.R;
import com.example.demoapp.adapter.sale.PriceListCyDomAdapter;
import com.example.demoapp.databinding.FragmentCyCyBinding;
import com.example.demoapp.model.DomCy;
import com.example.demoapp.utilities.Constants;
import com.example.demoapp.viewmodel.CommunicateViewModel;
import com.example.demoapp.viewmodel.DomCyViewModel;

import java.util.ArrayList;
import java.util.List;


public class CyCyFragment extends Fragment {

    private FragmentCyCyBinding binding;
    private DomCyViewModel mDomCyViewModel;
    private PriceListCyDomAdapter mCyDomAdapter;
    private SearchView searchView;

    private List<DomCy> mDomCyList = new ArrayList<>();

    private String month = "";
    private String continent = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCyCyBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        mCyDomAdapter = new PriceListCyDomAdapter(getContext());
        mDomCyViewModel = new ViewModelProvider(this).get(DomCyViewModel.class);

        CommunicateViewModel mCommunicateViewModel = new ViewModelProvider(requireActivity()).get(CommunicateViewModel.class);

        mCommunicateViewModel.needReloading.observe(getViewLifecycleOwner(), needLoading -> {
            if (needLoading) {
                onResume();
            }
        });

        setHasOptionsMenu(true);
        getAllData();
        setAutoComplete();

        return view;
    }

    public void setUpRecyclerView(String m, String c) {
        if (!m.isEmpty() && !c.isEmpty()) {
            mCyDomAdapter.setDomCy(filterData(m, c));
            binding.rcvDomCy.setAdapter(mCyDomAdapter);
            binding.rcvDomCy.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

    public List<DomCy> filterData(String m, String c) {
        List<DomCy> subList = new ArrayList<>();
        try {
            for (DomCy domCy : mDomCyList) {
                if (domCy.getMonth().equalsIgnoreCase(m) && domCy.getContinent().equalsIgnoreCase(c)) {
                    subList.add(domCy);
                }
            }
        } catch (NullPointerException nullPointerException) {
            Toast.makeText(getContext(), nullPointerException.toString(), Toast.LENGTH_LONG).show();
        }
        return subList;
    }

    public List<DomCy> filterDataResume(String m, String c, List<DomCy> list) {
        List<DomCy> subList = new ArrayList<>();
        try {
            for (DomCy domCy : list) {
                if (domCy.getMonth().equalsIgnoreCase(m) && domCy.getContinent().equalsIgnoreCase(c)) {
                    subList.add(domCy);
                }
            }
        } catch (NullPointerException nullPointerException) {
            Toast.makeText(getContext(), nullPointerException.toString(), Toast.LENGTH_LONG).show();
        }
        return subList;
    }

    public void setAutoComplete() {
        ArrayAdapter<String> adapterItemsMonth = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, Constants.ITEMS_MONTH);
        ArrayAdapter<String> adapterItemsContinent = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, Constants.ITEMS_CONTINENT);

        binding.autoDomMonth.setAdapter(adapterItemsMonth);
        binding.autoDomContinent.setAdapter(adapterItemsContinent);

        binding.autoDomMonth.setOnItemClickListener((adapterView, view, i, l) -> {
            month = adapterView.getItemAtPosition(i).toString();
            setUpRecyclerView(month, continent);
        });

        binding.autoDomContinent.setOnItemClickListener((adapterView, view, i, l) -> {
            continent = adapterView.getItemAtPosition(i).toString();
            setUpRecyclerView(month, continent);
        });
    }

    public void getAllData() {
        this.mDomCyList = new ArrayList<>();

        mDomCyViewModel.getAllData().observe(getViewLifecycleOwner(), domCy -> this.mDomCyList = domCy);
    }

    @Override
    public void onResume() {
        super.onResume();

        mDomCyViewModel.getAllData().observe(getViewLifecycleOwner(), domCy -> mCyDomAdapter.setDomCy(filterDataResume(month, continent, domCy)));

        binding.rcvDomCy.setAdapter(mCyDomAdapter);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });

    }
    private void filter(String text) {
        List<DomCy> filteredList = new ArrayList<>();
        for (DomCy domCy : filterData(month, continent)) {
            if (domCy.getStationGo().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(domCy);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            mCyDomAdapter.filterList(filteredList);
        }
    }


}
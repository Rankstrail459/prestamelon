package com.tomasdarioam.prestamelon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class HubOffersCatalogueFragment extends Fragment {

    private List<LoanOffer> mLoanOffersList;

    private RecyclerView mRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;

    public HubOffersCatalogueFragment(List<LoanOffer> loanOffers) {
        this.mLoanOffersList = loanOffers;
    }

    private void setRecyclerViewLayoutManager() {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    private List<OffersCatalogueAdapter.CatalogueOffer> getCatalogueOffersList() {
        List<OffersCatalogueAdapter.CatalogueOffer> catalogueOffersList = new ArrayList<>();

        /*
        for(LoanOffer loanOffer : mLoanOffersList)
            catalogueOfferList.add(new OffersCatalogueAdapter.CatalogueOffer(

            ));

         */

        return catalogueOffersList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hub_offers_catalogue, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        OffersCatalogueAdapter.CatalogueOffer[] catalogueOffers = new OffersCatalogueAdapter.CatalogueOffer[10];

        mRecyclerView = getView().findViewById(R.id.recycler_view_offers_catalogue);

        setRecyclerViewLayoutManager();

        mRecyclerView.setAdapter(new OffersCatalogueAdapter(catalogueOffers));
    }
}
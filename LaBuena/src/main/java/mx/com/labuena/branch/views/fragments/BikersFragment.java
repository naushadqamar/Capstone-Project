package mx.com.labuena.branch.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import javax.inject.Inject;

import mx.com.labuena.branch.R;
import mx.com.labuena.branch.models.Biker;
import mx.com.labuena.branch.presenters.BikersPresenter;
import mx.com.labuena.branch.setup.LaBuenaModules;
import mx.com.labuena.branch.views.adapters.BikersAdapter;

/**
 * Created by clerks on 8/22/16.
 */

public class BikersFragment extends BaseFragment implements BikersAdapter.BikerClickListener {
    public static final String DATA_USER_KEY = "Bikers";
    public static final String BIKERS_COLLECTION_KEY = "BikersCollection";
    private ArrayList<Biker> bikers;

    @Inject
    EventBus eventBus;

    @Inject
    BikersPresenter bikersPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.bikers_fragment;
    }

    public static BikersFragment newInstance(ArrayList<Biker> bikers) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(DATA_USER_KEY, bikers);
        BikersFragment fragment = new BikersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void injectDependencies(LaBuenaModules modules) {
        modules.inject(this);
    }

    @Override
    protected void loadFragmentArguments() {
        if (getArguments() != null) {
            this.bikers = getArguments().getParcelableArrayList(DATA_USER_KEY);
        }
    }

    @Override
    protected void initView(View rootView) {
        View newBiker = rootView.findViewById(R.id.newBikerActionButton);
        newBiker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bikersPresenter.navigateToBikerRegistration();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null)
            bikers = savedInstanceState.getParcelableArrayList(BIKERS_COLLECTION_KEY);

        displayBikers();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(BIKERS_COLLECTION_KEY, bikers);
        super.onSaveInstanceState(outState);
    }

    private void displayBikers() {
        BikersAdapter adapter = new BikersAdapter(getActivity(), bikers, this);

        final RecyclerView addonsRecyclerView = (RecyclerView) getView().findViewById(R.id.bikersRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setAutoMeasureEnabled(true);

        addonsRecyclerView.setAdapter(adapter);
        addonsRecyclerView.setLayoutManager(linearLayoutManager);
        addonsRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onBikerClick(Biker biker) {
        bikersPresenter.navigateToBikeUpdate(biker);
    }
}

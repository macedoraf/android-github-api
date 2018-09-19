package br.tbm.github.api.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.tbm.github.api.Constants;
import br.tbm.github.api.R;
import br.tbm.github.api.repository.BranchRepository;
import br.tbm.github.api.ui.adapters.BranchesTagsAdapter;
import br.tbm.github.api.interfaces.BranchMVP;
import br.tbm.github.api.presenter.BranchPresenter;
import br.tbm.github.api.network.entities.BranchesTagsResponse;

/**
 * Created by thalesbertolini on 26/08/2018
 **/
public class BranchFragment extends BaseFragment<BranchesTagsResponse> implements
        BranchMVP.View {

    private RecyclerView mRecyclerView;
    private TextView mTvListEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_branches, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String repositoryName = getArguments().getString(Constants.INTENT_REPOSITORY);
        String userName = getArguments().getString(Constants.INTENT_USERNAME);

        this.init();

        BranchPresenter presenter = new BranchPresenter(this, new BranchRepository());
        presenter.searchBranchesInServer(userName, repositoryName);
    }

    @Override
    protected void init() {
        getAppActivity().changeToolbarTitle(getString(R.string.branches_fragment_title));

        mRecyclerView = getAppActivity().findViewById(R.id.fragment_branches_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

        mTvListEmpty = getAppActivity().findViewById(R.id.fragment_branches_textview);
    }

    @Override
    public void branchesListEmpty() {
        dismissProgressDialog();
        mTvListEmpty.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void branchesList(ArrayList<BranchesTagsResponse> branches) {
        mTvListEmpty.setVisibility(View.GONE);
        mRecyclerView.setAdapter(new BranchesTagsAdapter(branches, true));
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}
package br.tbm.github.api.presenter;

import java.util.ArrayList;

import br.tbm.github.api.R;
import br.tbm.github.api.network.entities.BranchesTagsResponse;
import br.tbm.github.api.interfaces.BranchMVP;

/**
 * Created by thalesbertolini on 03/09/2018
 **/
public class BranchPresenter extends BasePresenter<BranchesTagsResponse> implements
        BranchMVP.Presenter {

    private BranchMVP.View mView;
    private BranchMVP.Model mModel;

    public BranchPresenter(BranchMVP.View view, BranchMVP.Model model) {
        super();
        this.mView = view;
        this.mModel = model;
        this.mModel.setCallback(this);
    }

    @Override
    void needsToCloseCurrentActivity() {
        closeActivity = true;
    }

    @Override
    public void searchBranchesInServer(String profileName, String repositoryName) {
        mView.updateProgressDialog(R.string.loading);
        mModel.searchBranchesInServer(profileName, repositoryName);
    }

    // ######################
    // CALLBACK DO REPOSITORY
    // ######################

    @Override
    public void success(ArrayList<BranchesTagsResponse> response) {
        super.success(response);

        if (response.isEmpty()) {
            mView.branchesListEmpty();
        } else {
            mView.branchesList(response);
        }
    }

    @Override
    public void networkIssue(int code) {
        super.networkIssue(code);
        mView.networkIssue(code, closeActivity);
    }

    @Override
    public void displayAlertDialog(int id) {
        super.displayAlertDialog(id);
        mView.displayAlertDialog(id, closeActivity);
    }
}

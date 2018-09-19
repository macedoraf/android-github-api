package br.tbm.github.api.repository;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import br.tbm.github.api.GithubApplication;
import br.tbm.github.api.interfaces.BranchMVP;
import br.tbm.github.api.network.entities.BranchesTagsResponse;
import br.tbm.github.api.network.rest.RestRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thalesbertolini on 15/09/2018
 **/
public class BranchRepository implements BranchMVP.Model {

    private BranchMVP.Presenter mPresenter;

    @Override
    public void searchBranchesInServer(String profileName, String repositoryName) {
        RestRepository service = GithubApplication.getRetrofitInstance().create(RestRepository.class);
        Call<ArrayList<BranchesTagsResponse>> responseCall = service.listBranches(profileName, repositoryName);
        responseCall.enqueue(new Callback<ArrayList<BranchesTagsResponse>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<BranchesTagsResponse>> call, @NonNull Response<ArrayList<BranchesTagsResponse>> response) {
                if (response.isSuccessful()) {
                    mPresenter.success(response.body());
                } else {
                    mPresenter.networkIssue(response.raw().code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<BranchesTagsResponse>> call, @NonNull Throwable t) {
                mPresenter.displayAlertDialog(t.getMessage());
            }
        });
    }

    /**
     * Metodo responsavel por adicionar a instancia do presenter no repository
     *
     * @param presenter BranchMVP.Presenter
     */
    @Override
    public void setCallback(BranchMVP.Presenter presenter) {
        this.mPresenter = presenter;
    }
}

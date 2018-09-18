package br.tbm.github.api.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.tbm.github.api.Constants;
import br.tbm.github.api.R;
import br.tbm.github.api.network.entities.CommitFilesResponse;
import br.tbm.github.api.repository.CommitDetailsRepository;
import br.tbm.github.api.ui.adapters.CommitDetailsAdapter;
import br.tbm.github.api.ui.components.CircleTransform;
import br.tbm.github.api.presenter.CommitsDetailsPresenter;
import br.tbm.github.api.network.entities.CommitsResponse;
import br.tbm.github.api.interfaces.CommitDetailsMVP;

/**
 * Created by thalesbertolini on 29/08/2018
 **/
public class CommitsDetailsActivity extends BaseActivity<CommitsResponse> implements
        CommitDetailsMVP.View {

    private RecyclerView mRecyclerView;
    private TextView mTvListEmpty, mTvCommitterName, mTvCommitterDate, mTvCommitDescription;
    private ImageView mIvCommitterProfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit_details);

        CommitsDetailsPresenter presenter = new CommitsDetailsPresenter(this, new CommitDetailsRepository());

        String repositoryName = getIntent().getExtras().getString(Constants.INTENT_REPOSITORY);
        String userName = getIntent().getExtras().getString(Constants.INTENT_USERNAME);
        String sha = getIntent().getExtras().getString(Constants.INTENT_SHA);

        this.init();

        presenter.search(userName, repositoryName, sha);
    }

    /**
     * Metodo responsavel por inicializar os componentes da tela
     */
    @Override
    protected void init() {
        setupToolbar(findViewById(R.id.toolbar));
        setToolbarProperties(getString(R.string.commit_details_activity_toolbar));

        mRecyclerView = findViewById(R.id.activity_commits_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

        mTvListEmpty = findViewById(R.id.activity_commits_empty_text_view);
        mTvCommitterName = findViewById(R.id.activity_commit_details_name_textview);
        mTvCommitterDate = findViewById(R.id.activity_commit_created_textview);
        mTvCommitDescription = findViewById(R.id.activity_commit_details_description_textview);

        mIvCommitterProfile = findViewById(R.id.activity_commit_details_imageview);
    }

    @Override
    public void setCommitterName(String login) {
        mTvCommitterName.setText(login);
    }

    @Override
    public void setCommitDescription(String message) {
        mTvCommitDescription.setText(message);
    }

    @Override
    public void setCommitterDate(String date) {
        mTvCommitterDate.setText(date);
    }

    @Override
    public void listCommitsEmpty() {
        mTvListEmpty.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void downloadProfileImage(String avatarUrl) {
        Picasso.with(this)
                .load(avatarUrl)
                .fit()
                .error(R.drawable.img_user_not_found)
                .transform(new CircleTransform())
                .into(mIvCommitterProfile);
    }

    @Override
    public void listCommits(List<CommitFilesResponse> commits) {
        mTvListEmpty.setVisibility(View.GONE);

        mRecyclerView.setAdapter(new CommitDetailsAdapter(commits));
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}
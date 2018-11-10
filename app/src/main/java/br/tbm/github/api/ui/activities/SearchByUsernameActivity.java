package br.tbm.github.api.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.tbm.github.api.R;
import br.tbm.github.api.repository.SearchByUsernameRepository;
import br.tbm.github.api.presenter.SearchByUsernamePresenter;
import br.tbm.github.api.interfaces.SearchByUsernameMVP;
import br.tbm.github.api.database.data.Profile;
import br.tbm.github.api.utils.RedirectUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thalesbertolini on 23/08/2018
 **/
public class SearchByUsernameActivity extends BaseActivity<Profile> implements
        SearchByUsernameMVP.View {

    private SearchByUsernamePresenter mPresenter;

    @BindView(R.id.search_activity_search_textlayout)
    TextInputLayout mTvProfile;

    @BindView(R.id.search_activity_search_edittext)
    EditText mEdProfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_username);

        ButterKnife.bind(this);

        this.mPresenter = new SearchByUsernamePresenter(this, new SearchByUsernameRepository(this));

        this.init();
    }

    /**
     * Metodo responsavel por inicializar os componentes da tela
     */
    @Override
    protected void init() {
        setupToolbar(findViewById(R.id.toolbar));
        setToolbarProperties(getString(R.string.search_activity_toolbar));

        Button btnSearch = findViewById(R.id.search_activity_button);
        btnSearch.setOnClickListener((View v) -> {
            mPresenter.validateFields(mEdProfile.getText().toString());
        });
    }

    @Override
    public void validateNotPassed(int message) {
        mTvProfile.setError(getString(message));
    }

    @Override
    public void validatePassed() {
        mTvProfile.setErrorEnabled(false);
    }

    // ######################
    // CALLBACK DO PRESENTER
    // ######################

    @Override
    public void success(Profile profile) {
        super.success(profile);
        RedirectUtils.redirectToProfileActivity(this, profile, true);
    }
}

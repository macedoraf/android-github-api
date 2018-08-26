package br.tbm.github.api.activities;

import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.tbm.github.api.R;
import br.tbm.github.api.adapters.ProfileAdapter;
import br.tbm.github.api.components.CustomActionMode;
import br.tbm.github.api.interfaces.AdaptersCallbacks;
import br.tbm.github.api.interfaces.TasksCallbacks;
import br.tbm.github.api.models.Profile;
import br.tbm.github.api.tasks.ListGithubUsersTask;
import br.tbm.github.api.tasks.RemoveUsersTask;
import br.tbm.github.api.utils.RedirectUtils;

/**
 * Created by thalesbertolini on 21/08/2018
 **/
public class ListProfilesActivity extends BaseActivity implements
        TasksCallbacks.RemoveUsersTaskCallback,
        TasksCallbacks.ListGithubUserTaskCallback,
        AdaptersCallbacks.ProfileAdapterCallback {

    private final String TAG = ListProfilesActivity.class.getSimpleName();

    private List<Profile> mProfiles;

    private RecyclerView mRecyclerView;
    private TextView mTvListEmpty;
    private ActionMode mCurrentActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_profiles);

        setupToolbar(findViewById(R.id.toolbar));
        changeToolbarTitle(getString(R.string.main_activity_toolbar));

        this.init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.listProfilesFromDatabase();
    }

    /**
     * Metodo responsavel por inicializar os componentes da tela
     */
    @Override
    protected void init() {
        mRecyclerView = findViewById(R.id.main_activity_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

        mTvListEmpty = findViewById(R.id.main_activity_list_description_text_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            RedirectUtils.redirectToSearchByUsernameActivity(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo responsavel por listar todos os perfils ja pesquisados na base de dados
     */
    private void listProfilesFromDatabase() {
        showProgressDialog(getString(R.string.loading));
        new ListGithubUsersTask(this).execute();
    }

    /**
     * Metodo responsavel por exibir na tela todos os usuarios ja pesquisados e salvos na base de dados
     *
     * @param profiles List<Profile>
     */
    private void listGithubUserSuccess(List<Profile> profiles) {
        this.mProfiles = profiles;
        dismissProgressDialog();
        if (profiles.isEmpty()) {
            mTvListEmpty.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mTvListEmpty.setVisibility(View.GONE);
            mRecyclerView.setAdapter(new ProfileAdapter(profiles, this));
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Verifica quantos items foram selecionado para atualizar o action mode title
     */
    private void checkNumberOfItemsHasBeenChecked() {
        int qtd = 0;
        for (Profile p : mProfiles) {
            if (p.hasSelected()) {
                qtd++;
            }
        }
        mCurrentActionMode.setTitle(String.valueOf(qtd));
    }

    /**
     * Metodo responsavel por carregar o menu na toolbar e a acao do onclick button
     */
    CustomActionMode customActionMode = new CustomActionMode() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mCurrentActionMode = mode;
            mode.setTitle("1");
            mode.getMenuInflater().inflate(R.menu.menu_list, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            menu.findItem(R.id.action_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete: {
                    removeItems();
                    mode.finish();
                    break;
                }
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mode.finish();
        }
    };

    /**
     * Metodo responsavel por selecionar apenas os itens que estao marcados para excluir, chamar a task
     * para remover os items e listar novamente
     */
    private void removeItems() {
        List<Profile> listToRemove = new ArrayList<>();
        for (Profile p : mProfiles) {
            if (p.hasSelected()) {
                listToRemove.add(p);
            }
        }

        new RemoveUsersTask(this, listToRemove).execute();
    }

    // ################
    // CALLBACK DA TASK
    // ################

    @Override
    public void removeUserTaskSuccess(List<Profile> profiles) {
        listGithubUserSuccess(profiles);
    }

    @Override
    public void removeUserTaskFailure() {
        displayGenericDatabaseIssue();
    }

    @Override
    public void listGithubUserTaskSuccess(List<Profile> profiles) {
        listGithubUserSuccess(profiles);
    }

    @Override
    public void listGithubUserTaskFailure() {
        displayGenericDatabaseIssue();
    }

    // ###################
    // CALLBACK DO ADAPTER
    // ###################

    @Override
    public void longClick(int position) {
        Log.d(TAG, "longClick(): " + position);
        mProfiles.get(position).setHasSelected(true);
        startSupportActionMode(customActionMode);
    }

    @Override
    public void onClick(int position) {
        RedirectUtils.redirectToProfileActivity(ListProfilesActivity.this, mProfiles.get(position), false);
    }

    @Override
    public void removeSelection(int position, boolean resetActionMode) {
        Log.d(TAG, "removeSelection(): " + position);
        mProfiles.get(position).setHasSelected(false);

        if (resetActionMode) {
            mCurrentActionMode.finish();
        } else {
            checkNumberOfItemsHasBeenChecked();
        }
    }

    @Override
    public void addSelection(int position) {
        Log.d(TAG, "addSelection(): " + position);
        mProfiles.get(position).setHasSelected(true);
        checkNumberOfItemsHasBeenChecked();
    }
}
package br.tbm.github.api.repository;

import java.util.List;

import br.tbm.github.api.R;
import br.tbm.github.api.database.data.Profile;
import br.tbm.github.api.interfaces.ListProfilesMVP;
import br.tbm.github.api.database.tasks.TasksCallbacks;
import br.tbm.github.api.database.tasks.ListGithubUsersTask;
import br.tbm.github.api.database.tasks.RemoveUsersTask;

/**
 * Created by thalesbertolini on 15/09/2018
 **/
public class ListProfilesRepository implements ListProfilesMVP.Model,
        TasksCallbacks.RemoveUsersTaskCallback,
        TasksCallbacks.ListGithubUserTaskCallback {

    private ListProfilesMVP.Presenter mPresenter;

    @Override
    public void listProfilesInDatabase() {
        new ListGithubUsersTask(this).execute();
    }

    @Override
    public void removeProfilesFromDatabase(List<Profile> profiles) {
        new RemoveUsersTask(this, profiles).execute();
    }

    // ################
    // CALLBACK DA TASK
    // ################

    @Override
    public void listGithubUserTaskSuccess(List<Profile> profiles) {
        mPresenter.success(profiles);
    }

    @Override
    public void listGithubUserTaskFailure() {
        mPresenter.displayAlertDialog(R.string.generic_database_issue);
    }

    @Override
    public void removeUserTaskSuccess(List<Profile> profiles) {
        mPresenter.success(profiles);
    }

    @Override
    public void removeUserTaskFailure() {
        mPresenter.displayAlertDialog(R.string.generic_database_issue);
    }

    /**
     * Metodo responsavel por adicionar a instancia do presenter no repository
     *
     * @param presenter ListProfilesMVP.Presenter
     */
    @Override
    public void setCallback(ListProfilesMVP.Presenter presenter) {
        this.mPresenter = presenter;
    }
}

package br.tbm.github.api.interfaces;

import java.util.List;

import br.tbm.github.api.interfaces.generic.BasePresenterCallbacks;
import br.tbm.github.api.interfaces.generic.BaseViewCallbacks;
import br.tbm.github.api.database.data.Profile;

/**
 * Created by thalesbertolini on 15/09/2018
 **/
public interface ListProfilesMVP {

    interface View extends BaseViewCallbacks<List<Profile>> {
        void listProfilesSuccess(List<Profile> profiles);
        void listProfilesEmpty();
    }

    interface Presenter extends BasePresenterCallbacks<List<Profile>> {
        void getProfilesInDatabase();
        void removeItems(List<Profile> profiles);

        String checkNumberOfItemsHasBeenChecked(List<Profile> profiles);
    }

    interface Model {
        void listProfilesInDatabase();
        void removeProfilesFromDatabase(List<Profile> profiles);
        void setCallback(ListProfilesMVP.Presenter presenter);
    }
}

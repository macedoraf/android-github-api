package br.tbm.github.api.components;

import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by thalesbertolini on 24/08/2018
 **/
public abstract class CustomActionMode implements ActionMode.Callback {

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}

package br.tbm.github.api.utils;

import android.app.Activity;
import android.content.Intent;

import br.tbm.github.api.Constants;
import br.tbm.github.api.ui.activities.CommitsDetailsActivity;
import br.tbm.github.api.ui.activities.EventDetailsActivity;
import br.tbm.github.api.ui.activities.GithubRateLimitActivity;
import br.tbm.github.api.ui.activities.ListProfilesActivity;
import br.tbm.github.api.ui.activities.ProfileActivity;
import br.tbm.github.api.ui.activities.RepositoryDetailsActivity;
import br.tbm.github.api.ui.activities.SearchByUsernameActivity;
import br.tbm.github.api.network.entities.EventPayloadResponse;
import br.tbm.github.api.database.data.Profile;

/**
 * Created by thalesbertolini on 23/08/2018
 **/
public class RedirectUtils {

    /**
     * Metodo responsavel por redirecionar para a tela principal e finalizar a atual tela do aplicativo
     *
     * @param activity Activity
     */
    public static void redirectToMainActivity(Activity activity) {
        Intent intent = new Intent(activity, ListProfilesActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * Metodo responsavel por redirecionar para a tela de perfil
     *
     * @param activity             Activity
     * @param profile              Profile
     * @param closeCurrentActivity Boolean
     */
    public static void redirectToProfileActivity(Activity activity, Profile profile, Boolean closeCurrentActivity) {
        Intent intent = new Intent(activity, ProfileActivity.class);
        intent.putExtra(Constants.INTENT_PROFILE, profile);
        activity.startActivity(intent);
        if (closeCurrentActivity) {
            activity.finish();
        }
    }

    /**
     * Metodo responsavel por abrir a tela de pesquisar novo usuario
     *
     * @param activity Activity
     */
    public static void redirectToSearchByUsernameActivity(Activity activity) {
        Intent intent = new Intent(activity, SearchByUsernameActivity.class);
        activity.startActivity(intent);
    }

    /**
     * Metodo responsavel por abrir a tela de mostrar os detalhes do repositorio selecionado
     *
     * @param activity       Activity
     * @param repositoryName String
     * @param username       String
     */
    public static void redirectToRepositoryDetailsActivity(Activity activity, String repositoryName, String username) {
        Intent intent = new Intent(activity, RepositoryDetailsActivity.class);
        intent.putExtra(Constants.INTENT_USERNAME, username);
        intent.putExtra(Constants.INTENT_REPOSITORY, repositoryName);
        activity.startActivity(intent);
    }

    /**
     * Metodo responsavel por abrir a tela de mostrar os detalhes do evento selecionado
     *
     * @param activity       Activity
     * @param repositoryName String
     * @param username       String
     * @param event          EventPayloadResponse
     */
    public static void redirectToEventsDetailsActivity(Activity activity, String repositoryName,
                                                       String username, EventPayloadResponse event) {
        Intent intent = new Intent(activity, EventDetailsActivity.class);
        intent.putExtra(Constants.INTENT_USERNAME, username);
        intent.putExtra(Constants.INTENT_REPOSITORY, repositoryName);
        intent.putExtra(Constants.INTENT_EVENT, event);
        activity.startActivity(intent);
    }

    /**
     * Metodo responsavel por abrir a tela de mostrar os detalhes do commit selecionado
     *
     * @param activity       Activity
     * @param repositoryName String
     * @param username       String
     * @param sha            String
     */
    public static void redirectToCommitsDetailsActivity(Activity activity, String repositoryName,
                                                        String username, String sha) {
        Intent intent = new Intent(activity, CommitsDetailsActivity.class);
        intent.putExtra(Constants.INTENT_USERNAME, username);
        intent.putExtra(Constants.INTENT_REPOSITORY, repositoryName);
        intent.putExtra(Constants.INTENT_SHA, sha);
        activity.startActivity(intent);
    }

    /**
     * Metodo responsavel por redirecionar para a tela de visualizar quantidade de requisicoes restantes
     *
     * @param activity Activity
     */
    public static void redirectToGithubRateLimitActivity(Activity activity) {
        Intent intent = new Intent(activity, GithubRateLimitActivity.class);
        activity.startActivity(intent);
    }
}

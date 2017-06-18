package com.sameera.duotest.presenter;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sameera on 6/18/17.
 */

public class ProfilePresenter extends BasePresenter {
    private static Map<String, ProfilePresenter> presenters = new HashMap<String, ProfilePresenter>();

    public static ProfilePresenter getPresenter(String id) {
        ProfilePresenter presenter = presenters.get(id);
        if (presenter != null) {
            return presenter;
        } else {
            presenter = new ProfilePresenter(id);
            presenters.put(id, presenter);
            return presenter;
        }
    }

    //private constructor to avoid instantiation
    private ProfilePresenter(String id) {
        Log.d("View Loaded", "Presenter created for" + id);
    }


    @Override public void destroy(String id) {
        presenters.remove(id);
    }
}

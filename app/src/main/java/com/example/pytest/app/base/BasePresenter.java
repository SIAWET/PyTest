package com.example.pytest.app.base;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * Presenter base
 */

public class BasePresenter {
    public BasePresenter() {
    }

    public BasePresenter(LifecycleProvider<ActivityEvent> provider) {
        this.provider = provider;
    }

    private LifecycleProvider<ActivityEvent> provider;


    public LifecycleProvider<ActivityEvent> getProvider() {
        return provider;
    }


}

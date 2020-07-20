package com.deivi.deivitask.domain.di;

import android.app.Application;

import com.deivi.deivitask.domain.di.modules.ApplicationModule;

public class App extends Application {
    private  ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}

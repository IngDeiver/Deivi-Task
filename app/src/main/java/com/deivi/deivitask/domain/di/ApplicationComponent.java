package com.deivi.deivitask.domain.di;

import com.deivi.deivitask.TaskViewModel;
import com.deivi.deivitask.domain.di.modules.ApplicationModule;
import com.deivi.deivitask.domain.di.modules.RoomModule;

import javax.inject.Singleton;

import dagger.Component;
@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class})
public interface ApplicationComponent {
    void inject(TaskViewModel taskViewModel);
}

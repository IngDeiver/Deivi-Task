package com.deivi.deivitask.domain.di.modules;

import android.app.Application;

import androidx.room.Room;

import com.deivi.deivitask.data.local.AppDatabase;
import com.deivi.deivitask.data.local.dao.TaskDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {


    @Singleton
    @Provides
    AppDatabase provideAppDatabase(Application application){
        return  Room.databaseBuilder(application, AppDatabase.class, "task-database").build();
    }

    @Singleton
    @Provides
    TaskDao providesTaskDao(AppDatabase  appDatabase){
        return  appDatabase.taskDao();
    }
}

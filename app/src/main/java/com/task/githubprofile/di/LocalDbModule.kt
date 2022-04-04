package com.task.githubprofile.di

import android.content.Context
import androidx.room.Room
import com.task.githubprofile.data.local.db.GitHubProfileAppDB
import com.task.githubprofile.data.local.localservice.GitLocalDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDbModule {
    @Provides
    fun provideTrendyDao(appDatabase: GitHubProfileAppDB): GitLocalDao {
        return appDatabase.GitLocalDao()
    }

    @Provides
    @Singleton
    fun provideAppDB(@ApplicationContext appContext: Context): GitHubProfileAppDB {
        return Room.databaseBuilder(
            appContext,
            GitHubProfileAppDB::class.java,
            "GitHubProfileAppDB"
        ).build()
    }

}
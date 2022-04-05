package com.task.githubprofile.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.task.githubprofile.data.dtos.responsedtos.Profile
import com.task.githubprofile.data.dtos.responsedtos.User
import com.task.githubprofile.data.local.converter.DataConverter
import com.task.githubprofile.data.local.localservice.GitLocalDao

@Database(entities = [Profile::class, User::class], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class GitHubProfileAppDB : RoomDatabase() {
    abstract fun GitLocalDao(): GitLocalDao
}
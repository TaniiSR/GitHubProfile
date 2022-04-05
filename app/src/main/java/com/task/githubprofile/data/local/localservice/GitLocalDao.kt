package com.task.githubprofile.data.local.localservice

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.githubprofile.data.dtos.responsedtos.Profile
import com.task.githubprofile.data.dtos.responsedtos.User

@Dao
interface GitLocalDao {
    @Query("SELECT * FROM git_profiles")
    suspend fun getAllTProfiles(): List<Profile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProfiles(profiles: List<Profile>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM git_user Where login == :userName")
    suspend fun getUser(userName: String): User?
}
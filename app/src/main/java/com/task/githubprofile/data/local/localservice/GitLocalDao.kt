package com.task.githubprofile.data.local.localservice

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.githubprofile.data.dtos.responsedtos.Profile

@Dao
interface GitLocalDao {
    @Query("SELECT * FROM git_profiles")
    suspend fun getAllTProfiles(): List<Profile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProfiles(profiles: List<Profile>)
}
package com.example.foxmember.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.foxmember.model.Member
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(member: Member)

    @Update
    suspend fun update(member: Member)

    @Delete
    suspend fun delete(member: Member)

    @Query("SELECT * FROM Member ORDER BY name ASC")
    fun getMembers(): Flow<List<Member>>

    @Query("SELECT * FROM Member WHERE id = :id")
    fun getMember(id: Int): Flow<Member>
}
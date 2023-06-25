package com.example.foxmember.model

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foxmember.R

@Entity
data class Member(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo()
    val name: String,
    val job: String,
    @DrawableRes val image: Int = R.drawable.member0
)
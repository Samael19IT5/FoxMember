package com.example.foxmember

import android.app.Application
import com.example.foxmember.data.MemberRoomDatabase

class MemberApplication : Application() {
    val database: MemberRoomDatabase by lazy { MemberRoomDatabase.getDatabase(this) }
}
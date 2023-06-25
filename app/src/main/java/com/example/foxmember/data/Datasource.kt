package com.example.foxmember.data

import android.content.Context
import com.example.foxmember.R
import com.example.foxmember.model.Member

class Datasource {
    fun loadMembers(): List<Member> {
        return listOf(
            Member(name = "Nguyễn Thảo Phương", job = "Human Resource", image = R.drawable.member1),
            Member(name = "Tam Le", job = "Mobile Developer", image = R.drawable.member2),
            Member(name = "Thanh Quang", job = "Mobile Developer", image = R.drawable.member3)
        )
    }
}
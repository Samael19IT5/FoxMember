package com.example.foxmember.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.foxmember.R
import com.example.foxmember.data.Datasource
import com.example.foxmember.data.MemberDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MemberViewModel(private val memberDao: MemberDao) : ViewModel() {
    val listMember: LiveData<List<Member>> = memberDao.getMembers().asLiveData()

    init {
        addStartMember()
    }

    private fun addStartMember() {
        viewModelScope.launch {
            if (memberDao.getMembers().first().isEmpty()) {
                val startMembers = Datasource().loadMembers()
                for (member in startMembers) {
                    addNewMember(member.name, member.job, member.image)
                }
            }
        }
    }

    private fun insertMember(member: Member) {
        viewModelScope.launch {
            memberDao.insert(member)
        }
    }

    private fun deleteMember(member: Member) {
        viewModelScope.launch {
            memberDao.delete(member)
        }
    }

    private fun updateMember(member: Member){
        viewModelScope.launch {
            memberDao.update(member)
        }
    }

    private fun getNewMemberEntry(
        name: String,
        job: String,
        image: Int = R.drawable.member0
    ): Member {
        return Member(
            name = name,
            job = job,
            image = image
        )
    }

    fun retrieveMember(id: Int): LiveData<Member> {
        return memberDao.getMember(id).asLiveData()
    }


    fun addNewMember(name: String, job: String, image: Int = R.drawable.member0) {
        val member = getNewMemberEntry(name, job, image)
        insertMember(member)
    }

    fun removeMember(member: Member){
        deleteMember(member)
    }

    fun editMember(member: Member){
        updateMember(member)
    }


    fun isEntryValid(name: String, job: String): Boolean {
        if (name.isBlank() || job.isBlank()) {
            return false
        }
        return true
    }
}

class MemberViewModelFactory(private val memberDao: MemberDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberViewModel(memberDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
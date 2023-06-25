package com.example.foxmember.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foxmember.R
import com.example.foxmember.model.Member


class MemberAdapter(private val onMemberClick: (Member) -> Unit) :
    ListAdapter<Member, MemberAdapter.MemberViewHolder>(DiffCallback) {

    class MemberViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val job: TextView = view.findViewById(R.id.job)
        val avatar: ImageView = view.findViewById(R.id.avatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.member_item, parent, false)
        return MemberViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val member = getItem(position)
        holder.name.text = member.name
        holder.job.text = member.job
        holder.avatar.setImageResource(member.image)
        holder.itemView.setOnClickListener {
            onMemberClick(member)
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Member>() {
            override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}
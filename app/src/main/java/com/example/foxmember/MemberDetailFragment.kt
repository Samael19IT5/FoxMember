package com.example.foxmember

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foxmember.databinding.FragmentMemberDetailBinding
import com.example.foxmember.model.Member
import com.example.foxmember.model.MemberViewModel
import com.example.foxmember.model.MemberViewModelFactory


class MemberDetailFragment : Fragment() {

    private var _binding: FragmentMemberDetailBinding? = null
    private val binding get() = _binding!!

    private val shareViewModel: MemberViewModel by activityViewModels {
        MemberViewModelFactory(
            (activity?.application as MemberApplication).database.memberDao()
        )
    }

    private val navigationArgs: MemberDetailFragmentArgs by navArgs()
    lateinit var member: Member

    private var image: Int = 0
    private lateinit var textName: String
    private lateinit var textJob: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            textName = it.getString(TEXT_NAME).toString()
            textJob = it.getString(TEXT_JOB).toString()
            image = it.getInt(AVATAR)
        }
    }

    private fun bind(member: Member) {
        binding.apply {
            binding.nameDetail.text = member.name
            binding.jobDetail.text = member.job
            binding.avatarDetail.setImageResource(member.image)
            binding.btnEdit.setOnClickListener {
                val action =
                    MemberDetailFragmentDirections.actionMemberDetailFragmentToMemberUpdateFragment(
                        member.id
                    )
                findNavController().navigate(action)
            }
            binding.btnDelete.setOnClickListener {
                shareViewModel.removeMember(member)
                findNavController().navigateUp()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMemberDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.memberId
        shareViewModel.retrieveMember(id).observe(this.viewLifecycleOwner) { selectedItem ->
            member = selectedItem
            bind(member)
        }
    }

    companion object {
        const val AVATAR = "image"
        const val TEXT_NAME = "textName"
        const val TEXT_JOB = "textJob"
    }
}
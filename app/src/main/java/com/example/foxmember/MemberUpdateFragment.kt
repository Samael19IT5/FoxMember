package com.example.foxmember

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foxmember.databinding.FragmentMemberUpdateBinding
import com.example.foxmember.model.Member
import com.example.foxmember.model.MemberViewModel
import com.example.foxmember.model.MemberViewModelFactory

class MemberUpdateFragment : Fragment() {

    private var _binding: FragmentMemberUpdateBinding? = null
    private val binding get() = _binding!!

    private lateinit var member: Member


    private val navigationArgs: MemberDetailFragmentArgs by navArgs()

    private val shareViewModel: MemberViewModel by activityViewModels {
        MemberViewModelFactory((activity?.application as MemberApplication).database.memberDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMemberUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun bind(member: Member) {
        binding.apply {
            binding.nameUpdate.setText(member.name)
            binding.jobUpdate.setText(member.job)
            binding.btnUpdate.setOnClickListener {
                if (binding.nameUpdate.text != null && binding.jobUpdate.text != null) {
                    shareViewModel.editMember(
                        Member(
                            member.id,
                            binding.nameUpdate.text.toString(),
                            binding.jobUpdate.text.toString(),
                            member.image
                        )
                    )
                    findNavController().navigateUp()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.memberId
        shareViewModel.retrieveMember(id).observe(this.viewLifecycleOwner) { selectedItem ->
            member = selectedItem
            bind(member)
        }
    }
}
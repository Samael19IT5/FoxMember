package com.example.foxmember

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.foxmember.adapter.MemberAdapter
import com.example.foxmember.databinding.FragmentMemberListBinding
import com.example.foxmember.model.MemberViewModel
import com.example.foxmember.model.MemberViewModelFactory


class MemberListFragment : Fragment() {

    private var _binding: FragmentMemberListBinding? = null
    private val binding get() = _binding!!


    private val shareViewModel: MemberViewModel by activityViewModels {
        MemberViewModelFactory(
            (activity?.application as MemberApplication).database.memberDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMemberListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val adapter = MemberAdapter {
            val action =
                MemberListFragmentDirections.actionMemberListFragmentToMemberDetailFragment(it.id)
            findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
        shareViewModel.listMember.observe(this.viewLifecycleOwner) { members ->
            members.let {
                adapter.submitList(it)
            }

        }
        binding.btnNew.setOnClickListener {
            val action = MemberListFragmentDirections.actionMemberListFragmentToMemberAddFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
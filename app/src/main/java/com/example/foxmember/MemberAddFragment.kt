package com.example.foxmember

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.foxmember.databinding.FragmentMemberAddBinding
import com.example.foxmember.model.MemberViewModel
import com.example.foxmember.model.MemberViewModelFactory


class MemberAddFragment : Fragment() {

    private var _binding: FragmentMemberAddBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentMemberAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnAdd.setOnClickListener {
            addNewMember()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun isEntryValid(): Boolean {
        return shareViewModel.isEntryValid(
            binding.nameInput.toString(),
            binding.jobInput.toString()
        )
    }

    private fun addNewMember() {
        if (isEntryValid()) {
            shareViewModel.addNewMember(binding.nameInput.text.toString(), binding.jobInput.text.toString())
        }
        findNavController().navigateUp()
    }

}
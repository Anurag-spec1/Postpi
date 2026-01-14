package com.anurag.postpi.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.anurag.postpi.R
import com.anurag.postpi.adapter.ParamsAdapter
import com.anurag.postpi.databinding.FragmentParamsBinding
import com.anurag.postpi.dataclass.ParamItem

class ParamsFragment : Fragment() {

    private var _binding: FragmentParamsBinding? = null
    private val binding get() = _binding!!

    private lateinit var paramsAdapter: ParamsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParamsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupClicks()
    }

    private fun setupRecyclerView() {
        paramsAdapter = ParamsAdapter(mutableListOf())

        binding.rvParams.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = paramsAdapter
        }
    }

    private fun setupClicks() {
        binding.fabAddParam.setOnClickListener {
            paramsAdapter.addParam()
        }

        binding.switchEnableParams.setOnCheckedChangeListener { _, isChecked ->
            binding.rvParams.alpha = if (isChecked) 1f else 0.4f
            binding.rvParams.isEnabled = isChecked
        }
    }

    fun getEnabledParams(): List<ParamItem> {
        return paramsAdapter.getEnabledParams()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

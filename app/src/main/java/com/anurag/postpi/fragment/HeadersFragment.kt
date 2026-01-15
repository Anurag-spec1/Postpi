package com.anurag.postpi.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.anurag.postpi.R
import com.anurag.postpi.adapter.KeyValueAdapter
import com.anurag.postpi.databinding.FragmentHeadersBinding
import com.anurag.postpi.dataclass.ParamItem

class HeadersFragment : Fragment() {

    private var _binding: FragmentHeadersBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: KeyValueAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeadersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = KeyValueAdapter(mutableListOf())

        binding.rvHeaders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HeadersFragment.adapter
        }

        binding.fabAddHeader.setOnClickListener {
            adapter.addItem()
        }

        binding.switchEnableHeaders.setOnCheckedChangeListener { _, enabled ->
            binding.rvHeaders.alpha = if (enabled) 1f else 0.4f
            binding.rvHeaders.isEnabled = enabled
        }
    }

    fun getEnabledHeaders(): List<ParamItem> =
        adapter.getEnabledItems()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

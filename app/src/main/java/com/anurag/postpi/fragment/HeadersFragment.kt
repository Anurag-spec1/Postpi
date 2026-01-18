package com.anurag.postpi.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.anurag.postpi.adapter.ParamsAdapter
import com.anurag.postpi.databinding.DialogAddParamBinding
import com.anurag.postpi.databinding.FragmentHeadersBinding
import com.anurag.postpi.dataclass.ParamItem

class HeadersFragment : Fragment() {

    private var _binding: FragmentHeadersBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ParamsAdapter

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

        adapter = ParamsAdapter(mutableListOf())

        binding.rvHeaders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = this@HeadersFragment.adapter
        }

        binding.fabAddHeader.setOnClickListener {
            showAddParamDialog()
        }
    }

    fun getEnabledHeaders(): List<ParamItem> {
        return if (this::adapter.isInitialized) adapter.getEnabledParams() else emptyList()
    }

    private fun showAddParamDialog() {

        val dialogBinding = DialogAddParamBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setCancelable(false)
            .create()

        dialogBinding.apply {

            btnAdd.setOnClickListener {
                val key = etKey.text.toString().trim()
                val value = etValue.text.toString().trim()

                if (key.isEmpty()) {
                    etKey.error = "Key required"
                    return@setOnClickListener
                }

                adapter.addParam(
                    ParamItem(
                        key = key,
                        value = value,
                        enabled = true
                    )
                )

                dialog.dismiss()
            }

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

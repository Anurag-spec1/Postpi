package com.anurag.postpi.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.anurag.postpi.R
import com.anurag.postpi.adapter.ParamsAdapter
import com.anurag.postpi.databinding.FragmentBodyBinding
import com.anurag.postpi.dataclass.BodyType

class BodyFragment : Fragment() {

    private var _binding: FragmentBodyBinding? = null
    private val binding get() = _binding!!

    private lateinit var formAdapter: ParamsAdapter
    private var bodyType = BodyType.NONE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBodyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFormData()
        setupBodyTypeSelector()
    }

    private fun setupFormData() {
        formAdapter = ParamsAdapter(mutableListOf())

        binding.rvFormData.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = formAdapter
        }

        binding.fabAddForm.setOnClickListener {
            formAdapter.addParam()
        }
    }

    private fun setupBodyTypeSelector() {

        selectNone()

        binding.btnNone.setOnClickListener {
            selectNone()
        }

        binding.btnRaw.setOnClickListener {
            selectRaw()
        }

        binding.btnForm.setOnClickListener {
            selectForm()
        }
    }

    private fun resetButtons() {
        binding.btnNone.setBackgroundResource(R.drawable.bg_body_type_unselected)
        binding.btnRaw.setBackgroundResource(R.drawable.bg_body_type_unselected)
        binding.btnForm.setBackgroundResource(R.drawable.bg_body_type_unselected)
    }

    private fun selectNone() {
        bodyType = BodyType.NONE
        resetButtons()
        binding.btnNone.setBackgroundResource(R.drawable.bg_body_type_selected)
        hideAll()
    }

    private fun selectRaw() {
        bodyType = BodyType.RAW
        resetButtons()
        binding.btnRaw.setBackgroundResource(R.drawable.bg_body_type_selected)
        showRaw()
    }

    private fun selectForm() {
        bodyType = BodyType.FORM_DATA
        resetButtons()
        binding.btnForm.setBackgroundResource(R.drawable.bg_body_type_selected)
        showForm()
    }


    private fun hideAll() {
        binding.etRawBody.visibility = View.GONE
        binding.rvFormData.visibility = View.GONE
        binding.fabAddForm.visibility = View.GONE
    }

    private fun showRaw() {
        binding.etRawBody.visibility = View.VISIBLE
        binding.rvFormData.visibility = View.GONE
        binding.fabAddForm.visibility = View.GONE
    }

    private fun showForm() {
        binding.etRawBody.visibility = View.GONE
        binding.rvFormData.visibility = View.VISIBLE
        binding.fabAddForm.visibility = View.VISIBLE
    }

    fun getBody(): Any? {
        return when (bodyType) {
            BodyType.NONE -> null
            BodyType.RAW -> binding.etRawBody.text.toString()
            BodyType.FORM_DATA -> formAdapter.getEnabledParams()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

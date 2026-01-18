package com.anurag.postpi.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.anurag.postpi.R
import com.anurag.postpi.adapter.ParamsAdapter
import com.anurag.postpi.databinding.DialogAddParamBinding
import com.anurag.postpi.databinding.FragmentBodyBinding
import com.anurag.postpi.dataclass.BodyType
import com.anurag.postpi.dataclass.ParamItem
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class BodyFragment : Fragment() {

    private var _binding: FragmentBodyBinding? = null
    private val binding get() = _binding!!

    private lateinit var formAdapter: ParamsAdapter
    private var bodyType: BodyType = BodyType.NONE

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
        selectNone()
    }

    private fun setupFormData() {
        formAdapter = ParamsAdapter(mutableListOf())

        binding.rvFormData.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = formAdapter
        }

        binding.fabAddForm.setOnClickListener {
            showAddParamDialog()
        }
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

                formAdapter.addParam(
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

    private fun setupBodyTypeSelector() {
        binding.btnNone.setOnClickListener { selectNone() }
        binding.btnRaw.setOnClickListener { selectRaw() }
        binding.btnForm.setOnClickListener { selectForm() }
    }


    private fun resetButtons() {
        binding.btnNone.setBackgroundResource(R.drawable.bg_body_type_unselected)
        binding.btnRaw.setBackgroundResource(R.drawable.bg_body_type_unselected)
        binding.btnForm.setBackgroundResource(R.drawable.bg_body_type_unselected)
    }

    private fun hideAll() {
        binding.etRawBody.visibility = View.GONE
        binding.rvFormData.visibility = View.GONE
        binding.fabAddForm.visibility = View.GONE
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
        hideAll()
        binding.etRawBody.visibility = View.VISIBLE
    }

    private fun selectForm() {
        bodyType = BodyType.FORM_DATA
        resetButtons()
        binding.btnForm.setBackgroundResource(R.drawable.bg_body_type_selected)
        hideAll()
        binding.rvFormData.visibility = View.VISIBLE
        binding.fabAddForm.visibility = View.VISIBLE
    }

    fun getRequestBody(method: String): RequestBody? {
        if (method == "GET" || method == "DELETE") return null

        return when (bodyType) {

            BodyType.NONE -> null

            BodyType.RAW -> {
                val json = binding.etRawBody.text.toString()
                if (json.isBlank()) return null
                json.toRequestBody("application/json; charset=utf-8".toMediaType())
            }

            BodyType.FORM_DATA -> {
                val builder = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)

                formAdapter.getEnabledParams().forEach {
                    builder.addFormDataPart(it.key, it.value)
                }

                builder.build()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


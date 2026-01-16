package com.anurag.postpi.fragment

import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.anurag.postpi.R
import com.anurag.postpi.databinding.FragmentAuthBinding
import com.anurag.postpi.dataclass.AuthType

class AuthorizationFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private var authType = AuthType.NONE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinner()
    }

    private fun setupSpinner() {
        val types = listOf("No Auth", "Bearer Token", "Basic Auth", "API Key")

        binding.spAuthType.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            types
        )

        binding.spAuthType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    hideAll()

                    when (position) {
                        0 -> authType = AuthType.NONE
                        1 -> {
                            authType = AuthType.BEARER
                            binding.etBearer.visibility = View.VISIBLE
                        }
                        2 -> {
                            authType = AuthType.BASIC
                            binding.layoutBasic.visibility = View.VISIBLE
                        }
                        3 -> {
                            authType = AuthType.API_KEY
                            binding.layoutApiKey.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun hideAll() {
        binding.etBearer.visibility = View.GONE
        binding.layoutBasic.visibility = View.GONE
        binding.layoutApiKey.visibility = View.GONE
    }

    fun getAuthHeaders(): Map<String, String> {
        return when (authType) {

            AuthType.NONE -> emptyMap()

            AuthType.BEARER -> {
                val token = binding.etBearer.text.toString()
                if (token.isBlank()) emptyMap()
                else mapOf("Authorization" to "Bearer $token")
            }

            AuthType.BASIC -> {
                val user = binding.etUsername.text.toString()
                val pass = binding.etPassword.text.toString()
                if (user.isBlank() || pass.isBlank()) emptyMap()
                else {
                    val creds = "$user:$pass"
                    val encoded = Base64.encodeToString(
                        creds.toByteArray(),
                        Base64.NO_WRAP
                    )
                    mapOf("Authorization" to "Basic $encoded")
                }
            }

            AuthType.API_KEY -> {
                val key = binding.etApiKeyName.text.toString()
                val value = binding.etApiKeyValue.text.toString()
                if (key.isBlank() || value.isBlank()) emptyMap()
                else mapOf(key to value)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

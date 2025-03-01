package com.example.quotationapp.fragment.permissionFragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.quotationapp.R
import com.example.quotationapp.databinding.FragmentPermissionBinding
import com.example.quotationapp.fragment.privacyPolicyFragment.PrivacyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PermissionFragment : Fragment() {

    private var _binding: FragmentPermissionBinding? = null
    private val binding get() = _binding
    private val privacyViewModel: PrivacyViewModel by activityViewModels()
    private lateinit var requestNotificationPermission: ActivityResultLauncher<String>
    private var notificationPermissionDeniedCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestNotificationPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            updateToggleUI(isGranted)
            if (!isGranted) {
                notificationPermissionDeniedCount++
                if (notificationPermissionDeniedCount >= 2) {
                    showPermissionDialog(requireContext())
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPermissionBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            clickListener()
        }
        checkPermissionStatus()  // Initial state check
    }

    override fun onResume() {
        super.onResume()
        checkPermissionStatus()  // Check again when returning from settings
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun FragmentPermissionBinding.clickListener() {
        icPermissionSwitch.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                requestNotificationPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

        btnNext.setOnClickListener {
            privacyViewModel.setNextButtonPermission(true)
            if (findNavController().currentDestination?.id == R.id.permissionFragment) {
                findNavController().navigate(R.id.action_permissionFragment_to_nameFragment)
            }
        }
    }

    private fun checkPermissionStatus() {
        val isGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) == PermissionChecker.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED
        }
        updateToggleUI(isGranted)
    }

    private fun updateToggleUI(isGranted: Boolean) {
        binding?.icPermissionSwitch?.setImageResource(if (isGranted) R.drawable.switch_on else R.drawable.switch_off)
    }

    private fun showPermissionDialog(context: Context) {
        val builder = AlertDialog.Builder(requireActivity())
        val dialog = builder.setTitle("Permission Required")
            .setMessage("Required permissions have been set to 'Don't ask again'. Please enable them in settings.")
            .setCancelable(true)
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .setPositiveButton("Settings") { dialogInterface, _ ->
                redirectToSystemSettings(context)
                dialogInterface.dismiss()
            }
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                redirectToSystemSettings(context)
                dialog.dismiss()
            }

            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun redirectToSystemSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }
}

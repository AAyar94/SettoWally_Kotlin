package com.settowally.settowally.ui.fragment.wallpaper_details_fragment.download_bottom_sheet

import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.settowally.settowally.R
import com.settowally.settowally.databinding.FragmentDownloadBottomSheetBinding

class DownloadBottomSheetFragment : BottomSheetDialogFragment() {

    private var mBinding: FragmentDownloadBottomSheetBinding? = null
    private val binding get() = mBinding!!
    private val srcArgs: DownloadBottomSheetFragmentArgs by navArgs()
    private val notificationManager by lazy { NotificationManagerCompat.from(requireContext()) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentDownloadBottomSheetBinding.inflate(layoutInflater, container, false)

        val peekHeight =
            resources.getDimensionPixelSize(com.google.android.material.R.dimen.design_bottom_sheet_peek_height_min)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, peekHeight)

        binding.downloadOriginalButton.setOnClickListener {
            startDownload(srcArgs.src.original)
            makeToastMessage(getString(R.string.wallpaper_downloaded_quality_original))
            findNavController().navigateUp()
        }
        binding.downloadLarge2xButton.setOnClickListener {
            startDownload(srcArgs.src.large2x)
            makeToastMessage(getString(R.string.wallpaper_downloaded_quality_large2x))
            findNavController().navigateUp()
        }
        binding.downloadLargeButton.setOnClickListener {
            startDownload(srcArgs.src.large)
            makeToastMessage(getString(R.string.wallpaper_downloaded_quality_large))
            findNavController().navigateUp()
        }
        binding.downloadMediumButton.setOnClickListener {
            startDownload(srcArgs.src.medium)
            makeToastMessage(getString(R.string.wallpaper_downloaded_quality_medium))
            findNavController().navigateUp()
        }
        binding.downloadSmallButton.setOnClickListener {
            startDownload(srcArgs.src.small)
            makeToastMessage(getString(R.string.wallpaper_downloaded_quality_small))
            findNavController().navigateUp()
        }
        binding.downloadTinyButton.setOnClickListener {
            startDownload(srcArgs.src.tiny)
            makeToastMessage(getString(R.string.wallpaper_downloaded_quality_tiny))
            findNavController().navigateUp()
        }
        binding.downloadLandscapeButton.setOnClickListener {
            startDownload(srcArgs.src.landscape)
            makeToastMessage(getString(R.string.wallpaper_downloaded_quality_landscape))
            findNavController().navigateUp()
        }
        binding.downloadPortraitButton.setOnClickListener {
            startDownload(srcArgs.src.portrait)
            makeToastMessage(getString(R.string.wallpaper_downloaded_quality_portrait))
            findNavController().navigateUp()
        }

        return binding.root
    }

    private fun startDownload(selectedQuality: String) {
        val imageUri: Uri = selectedQuality.toUri()

        val request = DownloadManager.Request(imageUri)
            .setTitle("Wallpaper Image")
            .setDescription("Downloading")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_PICTURES,
                "SettoWally/${System.currentTimeMillis()}.jpg"
            )

        val downloadManager =
            requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = downloadManager.enqueue(request)

        // Create and show a notification
        val notificationBuilder = NotificationCompat.Builder(requireContext(), "download_channel")
            .setContentTitle("Image Download")
            .setContentText("Downloading image...")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with your own icon
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true)

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(downloadId.toInt(), notificationBuilder.build())
    }

    private fun makeToastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

}
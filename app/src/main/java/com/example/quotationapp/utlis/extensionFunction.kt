package com.example.quotationapp.utlis

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quotationapp.R
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

private var toast: Toast? = null
fun Activity.toast(message: String) {
    try {
        if (this.isDestroyed || this.isFinishing) return
        if (toast != null) {
            toast?.cancel()
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        if (this.isDestroyed || this.isFinishing) return
        toast?.show()

    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Activity.shareApp(){
    try {
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.type = "text/plain"
        sendIntent.putExtra(
            Intent.EXTRA_SUBJECT,"ChargingAnimation")
        var shareMessage = "\n Let me recommend you this application\n\n"
        shareMessage = """
             ${shareMessage}https://play.google.com/store/apps/details?id= ${this.packageName}
        """.trimIndent()
        sendIntent.putExtra(Intent.EXTRA_TEXT,shareMessage)
        this.startActivity(Intent.createChooser(sendIntent, "Choose one"))
    }catch (e:java.lang.Exception){
        e.printStackTrace()
        this.toast("No Launcher")
    }
}

fun Context.feedBackWithEmail(title:String,message:String,emailId:String){
    try {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.flags  = Intent.FLAG_ACTIVITY_CLEAR_TASK
        emailIntent.data  = Uri.parse("mailto:")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailId))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, title)
        emailIntent.putExtra(Intent.EXTRA_TEXT, message)
        this.startActivity(emailIntent)

    }catch (e:java.lang.Exception){
        e.printStackTrace()
    }
}

fun Activity.privacyPolicyUrl(){
    try {
        this.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(this.getString(R.string.privacy_policy_link)))
        )
    }catch (e:Exception){
        e.printStackTrace()
        toast(this.getString(R.string.no_launcher))

    }
}

fun Activity.moreApps(){
    try {
        this.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(this.getString(R.string.more_app_link)))
        )
    }catch (e:Exception){
        e.printStackTrace()
        toast(this.getString(R.string.no_launcher))

    }
}

fun Activity.rateUs(){
    try {
        this.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=${this.packageName}")
            )
        )

    }catch (e:Exception){
        e.printStackTrace()
        toast("No Launcher")
    }
}

fun currentDateAndTime(context: Context, textView: TextView) {
    val currentDate = Date()
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("hh:mm,a", Locale.getDefault())
    val formattedDate = dateFormat.format(currentDate)
    val formattedTime = timeFormat.format(currentDate)
    val dateTimeString = "$formattedTime\n$formattedDate"
    textView.text = dateTimeString
}

@Suppress("DEPRECATION")
fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
    val runningServices = activityManager?.getRunningServices(Integer.MAX_VALUE)

    if (runningServices != null) {
        for (service in runningServices) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
    }
    return false
}


fun AppCompatImageView.setSelectedAlpha(selected: Boolean) {
    this.alpha = if (selected) 1f else 0.4f
}

fun List<AppCompatImageView>.setSelectedItem(selectedItem: AppCompatImageView) {
    for (item in this) {
        item.setSelectedAlpha(item == selectedItem)
    }
}

@SuppressLint("DefaultLocale")
fun getAudioDuration(audioPath: String): String {
    val retriever = MediaMetadataRetriever()
    try {
        retriever.setDataSource(audioPath)
        val durationString = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val durationMs = durationString?.toLongOrNull() ?: 0L

        val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMs)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(durationMs) % 60
        return String.format("%02d:%02d", minutes, seconds)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        retriever.release()
    }
    return "00:00"
}

fun Bitmap.saveToExternalStorage(context: Context, fileName: String): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "$fileName.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        put(MediaStore.Images.Media.IS_PENDING, 1)
    }

    val uri: Uri? = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    uri?.let {
        try {
            context.contentResolver.openOutputStream(it)?.use { outputStream ->
                this.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
            contentValues.clear()
            contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
            context.contentResolver.update(it, contentValues, null, null)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    return uri
}



fun String.deleteImageFile(tag: String): Boolean {
    val file = File(this)
    return if (file.exists()) {
        file.delete().also { deleted ->
            if (!deleted) {
                Log.e(tag, "Error occurred while deleting the file")
            }
        }
    } else {
        Log.e(tag, "File does not exist")
        false
    }
}

fun Context.deleteAudioFile(audioPath: String) {
    val file = File(audioPath)
    if (file.exists()) {
        file.delete()
    }
}

fun Context?.formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

fun Context?.monthlyFormatDate(timestamp: Long): String {
    // Format the date to match "3 August, 2024"
    val sdf = SimpleDateFormat("d MMMM, yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

fun ImageView.loadImageFromResources(resourceId: Int) {
    this.setImageResource(resourceId)
}




fun TextView.checkTextViewGravity():Int {
    val gravity = this.gravity

    return when {
        (gravity and Gravity.CENTER) == Gravity.CENTER -> {
            1
        }
        (gravity and Gravity.START) == Gravity.START -> {
            0
        }
        (gravity and Gravity.END) == Gravity.END -> {
            2
        }

        else -> {
            0
        }
    }
}


fun Context.saveImageToSpecificFolder(uri: Uri, folderName: String, fileName: String): String? {
    return try {
        val directory = File(filesDir, folderName)
        if (!directory.exists()) directory.mkdirs()
        val file = File(directory, fileName)
        val inputStream: InputStream = contentResolver.openInputStream(uri) ?: return null
        FileOutputStream(file).use { outputStream ->
            inputStream.use { input ->
                input.copyTo(outputStream)
            }
        }
        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Fragment.showDatePickerWithTime(
    calendar: Calendar,
    onDateTimeSelected: (String, String) -> Unit
) {
    DatePickerDialog(
        requireContext(),
        { _, year, monthOfYear, dayOfMonth ->
            calendar.set(year, monthOfYear, dayOfMonth)
            val formattedDate = calendar.time.toFormattedString("dd/MM/yyyy")
            showTimePicker(calendar) { formattedTime ->
                onDateTimeSelected(formattedDate, formattedTime)
            }
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

fun Fragment.showTimePicker(calendar: Calendar, onTimeSelected: (String) -> Unit) {
    TimePickerDialog(
        requireContext(),
        { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            val formattedTime = calendar.time.toFormattedString("hh:mm a")
            onTimeSelected(formattedTime)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    ).show()
}

fun Date.toFormattedString(pattern: String, locale: Locale = Locale.getDefault()): String {
    val dateFormat = SimpleDateFormat(pattern, locale)
    return dateFormat.format(this)
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun Fragment.navigateTo(destination: Int) {
    findNavController().navigate(destination)
}

fun showPermissionDialog(context: Context, fragment: Fragment) {
    val builder = AlertDialog.Builder(fragment.requireActivity())
    val dialog = builder.setTitle("Permission Required")
        .setMessage("Required permissions have been set to 'Don't ask again'. Please enable them in settings.")
        .setCancelable(true)
        .setNegativeButton("Cancel") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        .setPositiveButton("Settings") { dialogInterface, _ ->
            redirectToSystemSettings(context = context)
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



fun NumberPicker.setTextStyle(typeface: Typeface, textSize: Float, textColor: Int = Color.BLACK) {
    try {
        for (i in 0 until this.childCount) {
            val child = this.getChildAt(i)
            if (child is TextView) {
                child.typeface = typeface
                child.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
                child.setTextColor(textColor)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}





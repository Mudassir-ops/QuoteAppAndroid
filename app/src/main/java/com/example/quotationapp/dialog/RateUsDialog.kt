import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import com.example.quotationapp.databinding.RateUsDialogBinding
import com.example.quotationapp.utlis.feedBackWithEmail

class RateUsDialog(
    activity: Activity
) : Dialog(activity) {
    private val inflater = activity.getSystemService(
        Context.LAYOUT_INFLATER_SERVICE
    ) as LayoutInflater
    private val binding = RateUsDialogBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT
        )
        setCancelable(true)
        setCanceledOnTouchOutside(false)
        binding.apply {
            txtCancel.setOnClickListener { dismiss() }

            txtConfirm.setOnClickListener {
                val rating = binding.ratingBar.rating
                when {
                    rating <= 3 -> {
                        Toast.makeText(context, "Thank you for your feedback!", Toast.LENGTH_SHORT)
                            .show()
                            //   context.feedBackWithEmail(title = "Feedback", message = "Any Feedback", emailId = "saqibrehman503@gmail.com")
                        dismiss()
                    }

                    else -> {
                        val packageName = context.packageName
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=$packageName")
                        )
                        context.startActivity(intent)
                        dismiss()
                    }
                }
            }

        }
    }
}
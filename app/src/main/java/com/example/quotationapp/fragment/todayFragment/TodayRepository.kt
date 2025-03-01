package com.example.quotationapp.fragment.todayFragment

import com.example.quotationapp.json.Quotes
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject

class TodayRepository @Inject constructor(private val db: FirebaseFirestore) {

    fun getQuotesFromFirestore(callback: (List<Quotes>) -> Unit) {
        db.collection("categories").get().addOnSuccessListener { result ->
            val quotesList = mutableListOf<Quotes>()

            for (document in result) {
                document.reference.collection("quotes").get()
                    .addOnSuccessListener { quoteResult ->
                        for (quoteDoc in quoteResult) {
                            val quote = Quotes(
                                text = quoteDoc.getString("text") ?: "",
                                author = quoteDoc.getString("author") ?: ""
                            )
                            quotesList.add(quote)
                        }
                        callback(quotesList)
                    }
            }
        }
    }
}
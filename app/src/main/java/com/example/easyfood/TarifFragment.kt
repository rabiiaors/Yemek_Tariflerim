package com.example.easyfood

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

// TODO: Rename parameter arguments, choose names that match

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TarifFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // İlgili layout dosyasını şişir
        val view = inflater.inflate(R.layout.fragment_tarif, container, false)

        // Butonu bulma
        val buttonGoToWebsite: Button = view.findViewById(R.id.buttonGoToWebsite)

        // Butona tıklama dinleyicisi ekleme
        buttonGoToWebsite.setOnClickListener {
            // Web sitesine gitmek için bir Intent oluştur
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nefisyemektarifleri.com/"))

            // Intent'i başlat
            startActivity(intent)
        }

        return view
    }


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TarifFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
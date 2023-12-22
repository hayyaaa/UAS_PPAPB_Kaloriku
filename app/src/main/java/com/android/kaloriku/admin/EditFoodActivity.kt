package com.android.kaloriku.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.kaloriku.R
import com.android.kaloriku.data.DataMakanan
import com.android.kaloriku.databinding.ActivityEditFoodBinding
import com.google.firebase.firestore.FirebaseFirestore

class EditFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditFoodBinding
    private val firestore = FirebaseFirestore.getInstance() // digunakan untuk berinteraksi dengan database Firestore
    private val dataMakananCollectionRef  = firestore.collection("data_makanan") // merepresentasikan koleksi data makanan di Firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // mendapatkan data makanan yang akan diedit dari intent
        val id = intent.getStringExtra("id")
        val nama = intent.getStringExtra("namaMakanan")
        val kalori = intent.getStringExtra("kalori")
        val jumlah = intent.getStringExtra("jumlah")
        val satuan = intent.getStringExtra("satuan")

        with(binding){
            // menetapkan nilai dari data makanan ke tampilan
            edtNamaMakanan.setText(nama)
            edtKaloriMakanan.setText(kalori)
            edtJumlahMakanan.setText(jumlah)
            edtSatuanMakanan.setText(satuan)

            btnSimpan.setOnClickListener {
                // untuk mengambil data yang telah diubah, membuat objek DataMakanan, dan memanggil fungsi update untuk memperbarui data di Firestore
                val namaMakanan = edtNamaMakanan.text.toString()
                val kaloriMakanan = edtKaloriMakanan.text.toString()
                val jumlahMakanan = edtJumlahMakanan.text.toString()
                val satuanMakanan = edtSatuanMakanan.text.toString()

                val dataMakanan = DataMakanan(
                    id = id.toString(),
                    namaMakanan = namaMakanan,
                    kalori = kaloriMakanan.toFloat(),
                    jumlah = jumlahMakanan.toFloat(),
                    satuan = satuanMakanan)

                update(dataMakanan)

                val intent = Intent(this@EditFoodActivity, AdminActivity::class.java)
                startActivity(intent)
            }


        }

    }

    private fun update(dataMakanan: DataMakanan){
        //  mengambil objek DataMakanan dan menggunakan nilai ID dari objek tersebut untuk memperbarui data di Firestore
        val id = dataMakanan.id

        dataMakananCollectionRef.document(id).set(dataMakanan).addOnFailureListener{
            Log.d("MainActivity", "Error updating budget : ")
        }
    }
}
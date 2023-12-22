package com.android.kaloriku.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.kaloriku.data.DataMakanan
import com.android.kaloriku.databinding.MakananListBinding

// untuk mendefinisikan fungsi yang akan dipanggil saat terjadi klik pada item makanan, tombol edit, dan tombol delete
typealias OnClickFood = (DataMakanan) -> Unit
typealias OnClickEdit = (DataMakanan) -> Unit
typealias OnClickDelete = (DataMakanan) -> Unit

class FoodListAdapter(private var listFood: List<DataMakanan>,
                      private val onClickFood: OnClickFood,
                      private val onClickEdit: OnClickEdit,
                      private val onClickDelete: OnClickDelete
) : RecyclerView.Adapter<FoodListAdapter.ItemFoodViewHolder>() {

    inner class ItemFoodViewHolder(private val binding: MakananListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataMakanan) {
            with(binding) {
                txtNamaMakanan.text = data.namaMakanan
                txtJumlahKalori.text = data.kalori.toString()
                txtJumlah.text = data.jumlah.toString()
                txtSatuan.text = data.satuan

                itemView.setOnClickListener {
                    onClickFood(data)
                }

                btnEditMakanan.setOnClickListener {
                    onClickEdit(data)
                }

                btnDeleteMakanan.setOnClickListener {
                    onClickDelete(data)
                }
            }
        }
    }

    // Membuat dan menginisialisasi ViewHolder baru saat diperlukan
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemFoodViewHolder {
        val binding = MakananListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemFoodViewHolder(binding)
    }

    // Mengembalikan jumlah total item dalam daftar
    override fun getItemCount(): Int {
        return listFood.size
    }

    // Memasangkan data makanan ke ViewHolder pada posisi tertentu
    override fun onBindViewHolder(holder: ItemFoodViewHolder, position: Int) {
        holder.bind(listFood[position])
    }

    // Memperbarui daftar data makanan dan memberitahu adapter bahwa dataset telah berubah sehingga tampilan dapat diperbarui
    fun submitList(newList: List<DataMakanan>) {
        listFood = newList
        notifyDataSetChanged()
    }
}

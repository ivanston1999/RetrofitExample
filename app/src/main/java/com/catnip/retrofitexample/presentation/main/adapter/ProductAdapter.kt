package com.catnip.retrofitexample.presentation.main.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.catnip.retrofitexample.databinding.ItemProductBinding
import com.catnip.retrofitexample.model.Product

class ProductAdapter() : RecyclerView.Adapter<ProductViewHolder>() {


    private val dataDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    fun submitData(data: List<Product>) {
        dataDiffer.submitList(data)
        notifyItemChanged(0, data.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            binding = ItemProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(dataDiffer.currentList[position])

    }

    override fun getItemCount(): Int = dataDiffer.currentList.size
}




class ProductViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Product) {
        binding.tvProductTitle.text = item.title
        binding.tvProductDescription.text = item.desc
        binding.tvProductPrice.text = item.price.toString()
        if(item.images.size > 0) binding.imgProduct1.load(item.images[0])
        if(item.images.size > 1) binding.imgProduct2.load(item.images[1])
        if(item.images.size > 2) binding.imgProduct3.load(item.images[2])
        if(item.images.size > 3) binding.imgProduct4.load(item.images[3])
        if(item.images.size > 4) binding.imgProduct5.load(item.images[4])


    }


}
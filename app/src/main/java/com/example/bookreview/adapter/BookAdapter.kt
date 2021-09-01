package com.example.bookreview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookreview.databinding.ItemBookBinding
import com.example.bookreview.model.Book

// ListAdapter는 RecyclerAdapter와 비슷하지만 조금 다르다.
class BookAdapter(private val itemClickListener: (Book) -> Unit): ListAdapter<Book, BookAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bookModel: Book) {
            binding.titleTextView.text = bookModel.title
            binding.descriptionTextView.text = bookModel.description

            binding.root.setOnClickListener {
                itemClickListener(bookModel)
            }
            Glide
                .with(binding.coverImageView.context) // 인자로 context -> view에 있는 context할당
                .load(bookModel.coverSmallUrl) // 이미지 로딩
                .into(binding.coverImageView) // 이미지를 삽입할 view


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        // DiffUtil은 RecyclerView의 성능을 한층 더 개선할 수 있게 해주는 유틸리티 클래스다.
        // 기존의 데이터 리스트와 교체할 데이터 리스트를 비교해서 실질적으로 업데이트가 필요한 아이템들을 추려낸다.
        val diffUtil = object : DiffUtil.ItemCallback<Book>() {
            // 두 아이템이 동일한 내용물을 가지고 있는지 체크한다.
            override fun areContentsTheSame(oldItem: Book, newItem: Book) =
                oldItem == newItem
            // 두 아이템이 동일한 아이템인지 체크한다. Ex) item이 자신만의 고유한 id 같은걸 가지고 있다면 그걸 기준으로 삼으면 된다.
            override fun areItemsTheSame(oldItem: Book, newItem: Book) =
                oldItem.id == newItem.id
        }
    }

}
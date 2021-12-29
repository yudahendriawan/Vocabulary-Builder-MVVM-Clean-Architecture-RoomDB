package com.yudahendriawan.vocabularybuilder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yudahendriawan.vocabularybuilder.data.model.Vocabulary
import com.yudahendriawan.vocabularybuilder.databinding.ItemVocabularyBinding

class ListVocabularyAdapter : RecyclerView.Adapter<ListVocabularyAdapter.ViewHolder>() {

    private var dataList = emptyList<Vocabulary>()
    private lateinit var listener: IVocabularyClickListener

    inner class ViewHolder(private val binding: ItemVocabularyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(vocabulary: Vocabulary) {
            with(binding) {
                tvVocabularyWords.text = vocabulary.vocabulary

                layoutItem.setOnClickListener {
                    listener.onVocabClick(vocabulary)
                }


                layoutItem.setOnLongClickListener {
                    listener.onVocabLongClick(vocabulary.meaning)
                    true
                }
            }
        }
    }

    fun setOnVocabListener(listener: IVocabularyClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemVocabularyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    fun setData(vocabulary: List<Vocabulary>) {
        val vocabularyDiffUtil = VocabularyDiffUtil(dataList, vocabulary)
        val vocabularyDiffResult = DiffUtil.calculateDiff(vocabularyDiffUtil)
        this.dataList = vocabulary
        vocabularyDiffResult.dispatchUpdatesTo(this)
    }

    interface IVocabularyClickListener {
        fun onVocabClick(vocabulary: Vocabulary)
        fun onVocabLongClick(meaning: String)
    }

    class VocabularyDiffUtil(
        private val oldList: List<Vocabulary>,
        private val newList: List<Vocabulary>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id &&
                    oldList[oldItemPosition].vocabulary == newList[newItemPosition].vocabulary &&
                    oldList[oldItemPosition].meaning == newList[newItemPosition].meaning
        }

    }
}
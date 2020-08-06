package com.benoitthore.github.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.benoitthore.base.databinding.GithubRepositoryListItemBinding
import com.benoitthore.github.model.repository.GithubRepositoryModel

class GithubRepositoryAdapter : RecyclerView.Adapter<GithubRepositoryViewHolder>() {
    var repositories: List<GithubRepositoryModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubRepositoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GithubRepositoryListItemBinding.inflate(inflater)
        binding.root.layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        val vh = GithubRepositoryViewHolder(binding)

        return vh
    }

    override fun getItemCount(): Int = repositories.size

    override fun onBindViewHolder(holder: GithubRepositoryViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

}

class GithubRepositoryViewHolder(val binding: GithubRepositoryListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: GithubRepositoryModel) {
        binding.repositoryName.text = item.name.orEmpty()

        binding.repositoryDescription.text = item.description.orEmpty()
        binding.repositoryDescription.isVisible = binding.repositoryDescription.text.isNotEmpty()

        binding.repositoryNumForks.text = item.numberOfForks?.toString() ?: "0"
        binding.repositoryNumStars.text = item.numberOfForks?.toString() ?: "0"

        binding.repositoryLanguage.isVisible = item.language != null
        if (item.language != null) {
            binding.repositoryLanguage.text = item.language

            val colour = ContextCompat.getColor(binding.root.context, getProgrammingLanguageColor(item.language))
            binding.repositoryLanguage.compoundDrawablesRelative[0].setTint(colour)
        }
    }
}
package com.benoitthore.words.features.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.benoitthore.words.MainActivity
import com.benoitthore.words.data.WordsData
import com.benoitthore.words.features.add.AddWordFragment
import com.benoitthore.words.ui.HomeView
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ListWordsFragment : Fragment(), ListWordsPresenter.ListWordsView {

    val presenter: ListWordsPresenter by inject { parametersOf(lifecycleScope) }

    val ui by lazy {
        HomeView(requireActivity())
    }

    override var words: List<WordsData> = emptyList()
        set(value) {
            field = value
            ui.words = value
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = ui


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ui.onAddClicked {
            presenter.onAddClicked()
        }
        presenter.start(this)
    }

    override fun showAddWordUI() {
        (activity as MainActivity).loadFragment<AddWordFragment>()
    }

}
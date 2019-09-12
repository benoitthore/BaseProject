package com.benoitthore.words.features.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.benoitthore.words.ui.AddWordUI
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AddWordFragment : Fragment(), AddWordPresenter.AddWordView {
    val presenter: AddWordPresenter by inject { parametersOf(lifecycleScope) }

    val ui by lazy {
        AddWordUI(requireActivity(), AddWordUI.Data(
                arguments?.getString("1").orEmpty(),
                arguments?.getString("2").orEmpty()
        ))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            ui.view


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start(this)
        ui.onOkClicked {
            presenter.onOkClicked()
        }
    }


    override fun close() {
        requireActivity().supportFragmentManager.popBackStack()
    }


    override var wordA: String
        get() = ui.wordA
        set(value) {
            ui.wordA = value
        }
    override var wordB: String
        get() = ui.wordB
        set(value) {
            ui.wordB = value
        }
}
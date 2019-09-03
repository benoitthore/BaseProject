package com.benoitthore.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import splitties.views.dsl.core.view


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ui = AddWordUI(
                this,
                data = AddWordUI.Data("word1", "word2")
        )

        ui.apply { show() }


        val rv = view<RecyclerView> {
            this@view.adapter = adapter
        }

        setContentView(ui.view)

    }
}

class AddWordFragment : Fragment() {

    val presenter : AddWordPresenter by inject {  parametersOf(lifecycleScope) }

    val ui by lazy {
        AddWordUI(
                requireActivity(),
                data = AddWordUI.Data("word1", "word2")
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            ui.view
}
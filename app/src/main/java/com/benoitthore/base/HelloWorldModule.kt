package com.benoitthore.base

import android.app.Application
import androidx.room.Room
import com.benoitthore.base.helloworld.HelloWorldViewModel
import com.benoitthore.base.helloworld.data.HelloWorldRepo
import com.benoitthore.base.helloworld.data.db.AppDatabase
import com.benoitthore.base.helloworld.data.db.NoteMappers
import com.benoitthore.base.money.MoneyViewModel
import kotlinx.coroutines.GlobalScope
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

object HelloWorldModule {
    object Keys {
        val globalScope = StringQualifier("globalScope")
        val noteDtoToModel = StringQualifier("noteDtoToModel")
        val noteModelToDto = StringQualifier("noteModelToDto")
    }

    val module = module {
        single(Keys.globalScope) { GlobalScope }
        single { HelloWorldRepo() }
        single(Keys.noteDtoToModel) { NoteMappers.dtoToModel }
        single(Keys.noteModelToDto) { NoteMappers.modelToDto }
        single { Room.databaseBuilder(get(), AppDatabase::class.java, "myDB").build() }
        single { get<AppDatabase>().noteDao() }
        viewModel { HelloWorldViewModel() }
        viewModel { MoneyViewModel() }
    }
}

class HelloWorldApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@HelloWorldApplication)
            modules(listOf(HelloWorldModule.module))
        }
    }
}
package com.benoitthore.base

import android.app.Application
import com.benoitthore.base.helloworld.data.HelloWorldRepo
import com.benoitthore.base.helloworld.data.NoteDTO
import com.benoitthore.base.helloworld.data.NoteModel
import com.benoitthore.base.lib.repo.Mapper
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class HelloWorldApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        val module = module {

            single { HelloWorldRepo() }


        }
        startKoin {
            androidContext(this@HelloWorldApplication)
            modules(listOf(module))
        }
    }

}


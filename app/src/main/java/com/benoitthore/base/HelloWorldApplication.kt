package com.benoitthore.base

import android.app.Application
import androidx.room.Room
import com.benoitthore.base.helloworld.data.HelloWorldRepo
import com.benoitthore.base.helloworld.data.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class HelloWorldApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        val module = module {

            single { HelloWorldRepo() }
            single {  Room.databaseBuilder(get(), AppDatabase::class.java, "myDB").build() }
            single {  get<AppDatabase>().noteDao() }


        }
        startKoin {
            androidContext(this@HelloWorldApplication)
            modules(listOf(module))
        }
    }

}


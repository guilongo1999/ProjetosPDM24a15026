package pt.ipca.shopping_cart_app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShoppingApplication:Application() {

    override fun onCreate() {

        super.onCreate()
        //Graph.provide(this)
    }
}
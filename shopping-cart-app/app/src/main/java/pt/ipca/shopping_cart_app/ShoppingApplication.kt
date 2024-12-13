package pt.ipca.shopping_cart_app

import android.app.Application

class ShoppingApplication:Application() {

    override fun onCreate() {

        super.onCreate()
        Graph.provide(this)
    }
}
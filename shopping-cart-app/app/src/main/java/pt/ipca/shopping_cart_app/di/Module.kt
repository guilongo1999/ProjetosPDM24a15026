package pt.ipca.shopping_cart_app.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pt.ipca.shopping_cart_app.data.room.models.ItemDao
import pt.ipca.shopping_cart_app.data.room.models.ListDao
import pt.ipca.shopping_cart_app.data.room.models.SavedListsDao
import pt.ipca.shopping_cart_app.data.room.models.ShoppingListDatabase
import pt.ipca.shopping_cart_app.data.room.models.StoreDao
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ShoppingListDatabase {

        return Room.databaseBuilder(context, ShoppingListDatabase::class.java, "shopping_database")
            .fallbackToDestructiveMigration()
            .build()

    }

    @Provides
    @Singleton
    fun provideSavedListDaos(db:ShoppingListDatabase): SavedListsDao {

        return db.savedlistsDao()
    }

    @Provides
    @Singleton
    fun provideListDaos(db:ShoppingListDatabase): ListDao {

        return db.listDao()
    }

    @Provides
    @Singleton
    fun provideItemDao(db: ShoppingListDatabase): ItemDao {
        return db.itemDao()
    }

    @Provides
    @Singleton
    fun provideStoreDao(db: ShoppingListDatabase): StoreDao {
        return db.storeDao()
    }
}

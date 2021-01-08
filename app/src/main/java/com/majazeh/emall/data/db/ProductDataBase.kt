package com.majazeh.emall.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.majazeh.emall.data.db.doa.ProductDao
import com.majazeh.emall.data.db.entity.ProductDB

@Database(entities = [ProductDB::class], version = ProductDataBase.PRODUCT_VERSION, exportSchema = false)
abstract class ProductDataBase : RoomDatabase() {

    abstract fun getProductDao(): ProductDao

    companion object {
        const val PRODUCT_VERSION = 1
        private const val PRODUCT_DBNAME = "product.db"

        @Volatile
        private var INSTANCE: ProductDataBase? = null

        fun getInstance(context: Context): ProductDataBase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: build(context).also { INSTANCE = it }
        }

        private fun build(context: Context) =
            Room.databaseBuilder(context.applicationContext, ProductDataBase::class.java, PRODUCT_DBNAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}
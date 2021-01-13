package com.majazeh.emall.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.majazeh.emall.data.db.doa.InvoiceDao
import com.majazeh.emall.data.db.entity.InvoiceDB

@Database(
    entities = [InvoiceDB::class],
    version = InvoiceDataBase.INVOICE_VERSION,
    exportSchema = false
)
abstract class InvoiceDataBase : RoomDatabase() {

    abstract fun getInvoiceDao(): InvoiceDao

    companion object {
        const val INVOICE_VERSION = 1
        private const val INVOICE_DBNAME = "invoice.db"

        @Volatile
        private var INSTANCE: InvoiceDataBase? = null

        fun getInstance(context: Context): InvoiceDataBase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: build(context).also { INSTANCE = it }
        }

        private fun build(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                InvoiceDataBase::class.java,
                INVOICE_DBNAME
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}
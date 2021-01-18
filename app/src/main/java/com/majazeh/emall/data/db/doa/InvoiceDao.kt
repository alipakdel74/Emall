package com.majazeh.emall.data.db.doa

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.majazeh.emall.data.db.entity.InvoiceDB
import com.majazeh.emall.data.db.entity.ProductDB

@Dao
interface InvoiceDao {
    @Insert
    suspend fun setInvoice(invoiceDB: InvoiceDB)

    @Query("SELECT * FROM tbl_invoice ")
    suspend fun getAll(): MutableList<InvoiceDB>?

    @Query("DELETE FROM tbl_invoice WHERE id=:id")
    suspend fun deleteInvoice(id: String): Int

    @Query("DELETE FROM tbl_invoice")
    suspend fun deleteAll(): Int


    @Query("SELECT SUM(emall_price) FROM tbl_invoice")
    suspend fun getTotal(): Int?

    @Query("SELECT COUNT(id) FROM tbl_invoice")
    suspend fun getAmountAll(): Int?

    @Query("SELECT * FROM tbl_invoice WHERE id = :id ")
    suspend fun productData(id: String): InvoiceDB?

    @Query("UPDATE tbl_invoice SET count = count+:count  WHERE id = :id ")
    suspend fun update(id: String, count: Int): Int

    @Transaction
    suspend fun updateOrAdd(invoiceDB: InvoiceDB) {
        if (productData(invoiceDB.id) == null)
            setInvoice(invoiceDB)
        else update(invoiceDB.id, invoiceDB.count)
    }

}
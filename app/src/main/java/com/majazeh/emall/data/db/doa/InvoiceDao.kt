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

    @Query("SELECT SUM(emall_price * count) FROM tbl_invoice")
    suspend fun getEmallPrice(): Int?

    @Query("SELECT SUM(market_price * count) FROM tbl_invoice")
    suspend fun getMarketPrice(): Int?

    @Query("SELECT SUM(count) FROM tbl_invoice")
    suspend fun getAllNumber(): Int?

    @Query("SELECT SUM(count) FROM tbl_invoice WHERE id = :id")
    suspend fun getNumber(id: String): Int?

    @Query("SELECT * FROM tbl_invoice WHERE id = :id ")
    suspend fun productData(id: String): InvoiceDB?

    @Query("UPDATE tbl_invoice SET count = count+1  WHERE id = :id ")
    suspend fun updateCountList(id: String): Int

    @Query("UPDATE tbl_invoice SET count = :count  WHERE id = :id ")
    suspend fun update(id: String, count: Int): Int

    @Transaction
    suspend fun updateOrAdd(invoiceDB: InvoiceDB, isList: Boolean = false) {
        if (productData(invoiceDB.id) == null)
            setInvoice(invoiceDB)
        else {
            if (isList) updateCountList(invoiceDB.id)
            else update(invoiceDB.id, invoiceDB.count)
        }
    }

}
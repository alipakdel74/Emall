package com.majazeh.emall.data.db.doa

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.majazeh.emall.data.db.entity.ProductDB

@Dao
interface ProductDao {
    @Insert
    suspend fun setProduct(productDB: ProductDB)

    @Query("SELECT * FROM tbl_product ")
    suspend fun productData(): ProductDB?

    @Query("DELETE FROM tbl_product WHERE uid=:id")
    suspend fun deleteProduct(id: String): Int

    @Query("SELECT SUM(emall_price) FROM tbl_product")
    suspend fun getTotal(): Int?

    @Query("SELECT COUNT(id) FROM tbl_product")
    suspend fun getAmountAll(): Int?

}
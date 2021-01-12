package com.majazeh.emall.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.majazeh.emall.data.api.response.Product

@Entity(tableName = "tbl_product")
data class ProductDB(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "productId") val productId: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "brand") val brand: String,
    @ColumnInfo(name = "market_price") val market_price: Int,
    @ColumnInfo(name = "emall_price") val emall_price: Int,
    @ColumnInfo(name = "discount") val discount: Float,
    @ColumnInfo(name = "barcode") val barcode: String?,
    @ColumnInfo(name = "unit_type") val unit_type: String,
    @ColumnInfo(name = "unit") val unit: Float,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "quota") val quota: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "image_url") val imageUrl: String,
) {
    companion object {
        fun to(product: Product): ProductDB {
            return ProductDB(
                0,
                product.id,
                product.title,
                product.category.title,
                product.brand.title,
                product.market_price,
                product.emall_price,
                product.discount,
                product.barcode,
                product.unit_type,
                product.unit?:0f,
                product.status,
                product.quota,
                product.description,
                product.imageUrl.original,
            )
        }
    }

}

package com.maxshop.fatory

import com.maxshop.const.CategoryConst
import com.maxshop.exception.NoSuchCategoryException
import com.maxshop.model.category.Category
import java.util.Locale
import javax.inject.Inject

internal class CategoryFactory @Inject constructor() {
    fun get(categoryName: String): Category {
        return when (categoryName) {
            CategoryConst.ELECTRONICS -> Category(
                categoryName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                "https://upload.wikimedia.org/wikipedia/commons/d/d9/Arduino_ftdi_chip-1.jpg"
            )
            CategoryConst.JEWELERY -> Category(
                categoryName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                "https://cache.net-a-porter.com/content/images/story-head-content-1stFebruary2021-1611749733226.jpeg/w1900_q65.jpeg"
            )
            CategoryConst.MENS_CLOTHING -> Category(
                categoryName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                "https://www.farmers.co.nz/INTERSHOP/static/WFS/Farmers-Shop-Site/-/Farmers-Shop/en_NZ/2021/September/FTC4030-New-Season-Cat-Tiles-7-Oct/Mens-Cat-Tiles/02-Mens-Clothing/03-Mens-Clothing-Coats-Jackets.jpg"
            )
            CategoryConst.WOMENS_CLOTHING -> Category(
                categoryName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                "https://media.4rgos.it/i/Argos/4221-m007-25-01-women-sweatcoat?maxW=768&qlt=75&fmt.jpeg.interlaced=true"
            )
            else -> throw NoSuchCategoryException("Invalid category $categoryName")
        }
    }
}

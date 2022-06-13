package com.maxshop.fatory

import com.maxshop.const.ELECTRONICS
import com.maxshop.const.JEWELERY
import com.maxshop.const.MENS_CLOTHING
import com.maxshop.const.WOMENS_CLOTHING
import com.maxshop.exception.NoSuchCategoryException
import com.maxshop.model.category.Category
import java.util.Locale
import javax.inject.Inject

internal class CategoryFactory @Inject constructor() {
    fun get(categoryName: String): Category {
        return when (categoryName) {
            ELECTRONICS -> Category(
                categoryName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                "https://upload.wikimedia.org/wikipedia/commons/d/d9/Arduino_ftdi_chip-1.jpg"
            )
            JEWELERY -> Category(
                categoryName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                "https://cache.net-a-porter.com/content/images/story-head-content-1stFebruary2021-1611749733226.jpeg/w1900_q65.jpeg"
            )
            MENS_CLOTHING -> Category(
                categoryName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                "https://www.farmers.co.nz/INTERSHOP/static/WFS/Farmers-Shop-Site/-/Farmers-Shop/en_NZ/2021/September/FTC4030-New-Season-Cat-Tiles-7-Oct/Mens-Cat-Tiles/02-Mens-Clothing/03-Mens-Clothing-Coats-Jackets.jpg"
            )
            WOMENS_CLOTHING -> Category(
                categoryName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                "https://media.4rgos.it/i/Argos/4221-m007-25-01-women-sweatcoat?maxW=768&qlt=75&fmt.jpeg.interlaced=true"
            )
            else -> throw NoSuchCategoryException("Invalid category $categoryName")
        }
    }
}

package com.joyor.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull
import retrofit2.internal.EverythingIsNonNull

@Entity(primaryKeys = ["id", "color_code"])
class Product(
    @SerializedName("id") @Expose var id: Int = 0,
    @SerializedName("name") @Expose var name: String? = null,
    @SerializedName("short_description") @Expose var shortDescription: String? = null,
    @SerializedName("description") @Expose var description: String? = null,
    @SerializedName("url") @Expose var url: String? = null,
    @SerializedName("thumbnail") @Expose var thumbnail: String? = null,
    @SerializedName("images") @Expose var images: ArrayList<String>? = null,
    @SerializedName("price") @Expose var price: String? = null,
    @SerializedName("regular_price") @Expose var regularPrice: String? = null,
    @SerializedName("sale_price") @Expose var salePrice: String? = null,
    @SerializedName("type") @Expose var type: String? = null,
    @SerializedName("sku") @Expose var sku: String? = null,
    @SerializedName("in_stock") @Expose var inStock: Boolean? = null,
    @SerializedName("stock_quantity") @Expose var stockQuantity: String? = null,
    @SerializedName("categories") @Expose var categories: ArrayList<Category>? = null,
    @SerializedName("specs") @Expose var specs: ArrayList<Spec>? = null,
    @Embedded(prefix = "variations_")
    @SerializedName("variations") @Expose var variations: Variations? = null,
    var quantity: Int = 0,
    @Embedded(prefix = "color_")
    var colorSelect: Option = Option()
) : Parcelable {

    constructor(parcel: Parcel) : this(
        (parcel.readValue(Int::class.java.classLoader)!! as? Int)!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        parcel.createTypedArrayList(Category),
        parcel.createTypedArrayList(Spec),
        parcel.readParcelable(Variations::class.java.classLoader)
    ) {
    }

    data class Category(
        @SerializedName("id") @Expose var id: Int? = null,
        @SerializedName("name") @Expose var name: String? = null,
        @SerializedName("slug") @Expose var slug: String? = null
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeValue(id)
            parcel.writeString(name)
            parcel.writeString(slug)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Category> {
            override fun createFromParcel(parcel: Parcel): Category {
                return Category(parcel)
            }

            override fun newArray(size: Int): Array<Category?> {
                return arrayOfNulls(size)
            }
        }

    }

    data class Variations(
        @SerializedName("options") @Expose var options: ArrayList<Option>? = ArrayList(),
        @SerializedName("name") @Expose var name: String = ""
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.createTypedArrayList(Option),
            parcel.readString()!!
        ) {
        }



        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeTypedList(options)
            parcel.writeString(name)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Variations> {
            override fun createFromParcel(parcel: Parcel): Variations {
                return Variations(parcel)
            }

            override fun newArray(size: Int): Array<Variations?> {
                return arrayOfNulls(size)
            }
        }

    }
    data class Option(
        @NotNull
        @SerializedName("code") @Expose var code: String = "",
        @SerializedName("name") @Expose var name: String = ""
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(code)
            parcel.writeString(name)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Option> {
            override fun createFromParcel(parcel: Parcel): Option {
                return Option(parcel)
            }

            override fun newArray(size: Int): Array<Option?> {
                return arrayOfNulls(size)
            }
        }
    }
    data class Spec(
        @SerializedName("key") @Expose var key: String? = null,
        @SerializedName("value") @Expose var value: String? = null
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(key)
            parcel.writeString(value)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Spec> {
            override fun createFromParcel(parcel: Parcel): Spec {
                return Spec(parcel)
            }

            override fun newArray(size: Int): Array<Spec?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(shortDescription)
        parcel.writeString(description)
        parcel.writeString(url)
        parcel.writeString(thumbnail)
        parcel.writeStringList(images)
        parcel.writeString(price)
        parcel.writeString(regularPrice)
        parcel.writeString(salePrice)
        parcel.writeString(type)
        parcel.writeString(sku)
        parcel.writeValue(inStock)
        parcel.writeString(stockQuantity)
        parcel.writeTypedList(categories)
        parcel.writeTypedList(specs)
        parcel.writeParcelable(variations, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }


}
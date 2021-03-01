package com.joyor.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Product(
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("short_description")
    @Expose
    var shortDescription: String? = null,

    @SerializedName("description")
    @Expose
    var description: String? = null,

    @SerializedName("url")
    @Expose
    var url: String? = null,

    @SerializedName("images")
    @Expose
    var images: List<Image>? = null,

    @SerializedName("price")
    @Expose
    var price: String? = null,

    @SerializedName("regular_price")
    @Expose
    var regularPrice: String? = null,

    @SerializedName("sale_price")
    @Expose
    var salePrice: String? = null,

    @SerializedName("type")
    @Expose
    var type: String? = null,

    @SerializedName("sku")
    @Expose
    var sku: String? = null,

    @SerializedName("in_stock")
    @Expose
    var inStock: Boolean? = null,

    @SerializedName("stock_quantity")
    @Expose
    var stockQuantity: Any? = null,

    @SerializedName("categories")
    @Expose
    var categories: List<Image.Category>? = null,

    @SerializedName("variations")
    @Expose
    var variations: List<Int>? = null
) {
    class Image(
        @SerializedName("id")
        @Expose
        var id: Int? = null,

        @SerializedName("date_created")
        @Expose
        var dateCreated: String? = null,

        @SerializedName("date_created_gmt")
        @Expose
        var dateCreatedGmt: String? = null,

        @SerializedName("date_modified")
        @Expose
        var dateModified: String? = null,

        @SerializedName("date_modified_gmt")
        @Expose
        var dateModifiedGmt: String? = null,

        @SerializedName("src")
        @Expose
        var src: String? = null,

        @SerializedName("name")
        @Expose
        var name: String? = null,

        @SerializedName("alt")
        @Expose
        var alt: String? = null,

        @SerializedName("position")
        @Expose
        var position: Int? = null
    ) {
        class Category(
            @SerializedName("id")
            @Expose
            var id: Int? = null,

            @SerializedName("name")
            @Expose
            var name: String? = null,

            @SerializedName("slug")
            @Expose
            var slug: String? = null
        ) {}
    }
}
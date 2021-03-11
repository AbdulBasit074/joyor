package com.joyor.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class CouponDiscount(
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("code")
    @Expose
    var code: String? = null,

    @SerializedName("discount_type")
    @Expose
    var discountType: String? = null,

    @SerializedName("coupon_amount")
    @Expose
    var couponAmount: String? = null,

    @SerializedName("individual_use")
    @Expose
    var individualUse: String? = null,

    @SerializedName("apply_before_tax")
    @Expose
    var applyBeforeTax: String? = null,

    @SerializedName("exclude_sale_items")
    @Expose
    var excludeSaleItems: String? = null,

    @SerializedName("minimum_amount")
    @Expose
    var minimumAmount: String? = null,

    @SerializedName("maximum_amount")
    @Expose
    var maximumAmount: String? = null,

    @SerializedName("usage_limit")
    @Expose
    var usageLimit: String? = null,

    @SerializedName("usage_limit_per_user")
    @Expose
    var usageLimitPerUser: String? = null,

    @SerializedName("usage_count")
    @Expose
    var usageCount: String? = null,

    @SerializedName("expiry_date")
    @Expose
    var expiryDate: String? = null,

    @SerializedName("free_shipping")
    @Expose
    var freeShipping: String? = null,

    @SerializedName("product_ids")
    @Expose
    var productIds: String? = null,

    @SerializedName("exclude_product_ids")
    @Expose
    var excludeProductIds: String? = null,

    @SerializedName("customer_email")
    @Expose
    var customerEmail: String? = null,

    @SerializedName("product_categories")
    @Expose
    var productCategories: String? = null,

    @SerializedName("exclude_product_categories")
    @Expose
    var excludeProductCategories: String? = null,

    @SerializedName("_edit_lock")
    @Expose
    var editLock: String? = null
) {}
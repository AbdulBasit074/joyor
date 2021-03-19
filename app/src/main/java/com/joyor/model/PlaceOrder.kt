package com.joyor.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

import com.joyor.model.Address.Billing


class PlaceOrder(
    @SerializedName("payment_method") @Expose var paymentMethod: String? = null,
    @SerializedName("billing") @Expose var billing: Billing? = null,
    @SerializedName("line_items") @Expose var lineItems: ArrayList<LineItem>? = ArrayList(),
    @SerializedName("shipping_lines") @Expose var shippingLines: ShippingLines? = null,
    @SerializedName("additional_information") @Expose var additional_information: String? = null,
    @SerializedName("coupon_lines") @Expose var couponLine: ArrayList<CouponLines>? = ArrayList()


) {

    class LineItem(
        @SerializedName("product_id") @Expose var productId: Int? = null,
        @SerializedName("quantity") @Expose var quantity: Int? = null,
        @SerializedName("variation_id") @Expose var variationId: Int? = null
    ) {}

    class ShippingLines(
        @SerializedName("method_id") @Expose var methodId: String? = null,
        @SerializedName("method_title") @Expose var methodTitle: String? = null,
        @SerializedName("total") @Expose var total: String? = null
    ) {

    }

}

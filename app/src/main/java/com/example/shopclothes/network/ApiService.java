package com.example.shopclothes.network;

import com.example.shopclothes.view.activity.account.register.ResponseUser;
import com.example.shopclothes.view.activity.billDetail.ResponseBillDetail;
import com.example.shopclothes.view.activity.cart.ResponseCart;
import com.example.shopclothes.view.activity.order.response.ResponseAddress;
import com.example.shopclothes.view.activity.order.response.ResponseDiscount;
import com.example.shopclothes.view.activity.order.response.ResponseOrder;
import com.example.shopclothes.view.activity.product.response.ResponseComment;
import com.example.shopclothes.view.activity.product.response.ResponseProduct;
import com.example.shopclothes.view.activity.product.response.ResponseSize;
import com.example.shopclothes.view.fragment.billFragment.ResponseBill;
import com.example.shopclothes.view.fragment.homeFragment.response.ResponseBanner;
import com.example.shopclothes.view.fragment.homeFragment.response.ResponseTypeProduct;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    ApiService API_SERVICE = new Retrofit.Builder()
            .baseUrl(ManagerUrl.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);

    // user
    @POST(ManagerUrl.INSERT_USER)
    @FormUrlEncoded
    Call<ResponseUser> insertUser (@Field("id") String id);

    // banner
    @GET(ManagerUrl.READ_BANNER)
    Call<ResponseBanner> readBanner();

    // product
    @GET(ManagerUrl.READ_PRODUCT_NEW)
    Call<ResponseProduct> readProductNew();
    @GET(ManagerUrl.READ_PRODUCT_OUTSTANDING)
    Call<ResponseProduct> readProductOutstanding();
    @GET(ManagerUrl.READ_PRODUCT_ALL)
    Call<ResponseProduct> readProductAll();
    @POST(ManagerUrl.READ_PRODUCT_ID_CATEGORY)
    @FormUrlEncoded
    Call<ResponseProduct> readProductByIdCategory(@Field("id") int id);
    @POST(ManagerUrl.READ_PRODUCT_ID)
    @FormUrlEncoded
    Call<ResponseProduct> readProductById(@Field("id") int id);
    // type product
    @GET(ManagerUrl.READ_TYPE_PRODUCT)
    Call<ResponseTypeProduct> readTypeProduct();
    // comment
    @POST(ManagerUrl.READ_COMMENT_ID)
    @FormUrlEncoded
    Call<ResponseComment> readCommentById(@Field("id") int id);
    // size
    @POST(ManagerUrl.READ_SIZE_ID_PRODUCT)
    @FormUrlEncoded
    Call<ResponseSize> readSizeByIdProduct(@Field("id") int id);
    // cart
    @POST(ManagerUrl.READ_CART_BY_ID_USER)
    @FormUrlEncoded
    Call<ResponseCart> readCartById(@Field("id") String id);

    @GET(ManagerUrl.READ_CART)
    Call<ResponseCart> readCart();

    @POST(ManagerUrl.INSERT_CART)
    @FormUrlEncoded
    Call<ResponseCart> insertCart(@Field("quantity") int quantity,
                                  @Field("price") double price,
                                  @Field("size") String size,
                                  @Field("userId") String userId,
                                  @Field("productId") int productId);
    @POST(ManagerUrl.DELETE_CART)
    @FormUrlEncoded
    Call<ResponseCart> deleteCart(@Field("id") int id);
    @POST(ManagerUrl.UPDATE_CART)
    @FormUrlEncoded
    Call<ResponseCart> updateCart(@Field("id") int id,
                                  @Field("quantity") int quantity);

    // address
    @POST(ManagerUrl.READ_ADDRESS)
    @FormUrlEncoded
    Call<ResponseAddress> readAddress(@Field("userId") String userId);

    // discount
    @POST(ManagerUrl.READ_DISCOUNT_BY_ID)
    @FormUrlEncoded
    Call<ResponseDiscount> readDiscountById(@Field("code") String code);

    // order
    @POST(ManagerUrl.INSERT_ORDER)
    @FormUrlEncoded
    Call<ResponseOrder> insertOrder(@Field("id") String id,
                                    @Field("ghi_chu") String note,
                                    @Field("hinh_thuc_thanh_toan") String payments,
                                    @Field("tinh_trang_giao_hang") String deliveryStatus,
                                    @Field("ly_do_huy") String reasonCancel,
                                    @Field("thanh_tien") double price,
                                    @Field("giam_gia_id") String idDiscount,
                                    @Field("dia_chi_id") int idAddress,
                                    @Field("trang_thai_thanh_toan") String peacefulState,
                                    @Field("so_luong_don_hang") int quantityBill);

    @POST(ManagerUrl.INSERT_DETAIL_ORDER)
    @FormUrlEncoded
    Call<ResponseOrder> insertOrderDetail(@Field("kich_thuoc") String size,
                                    @Field("so_luong") int quantity,
                                    @Field("don_gia") double price,
                                    @Field("don_hang_id") String idDonHang,
                                    @Field("san_pham_id") int idProduct);

    //bill
    @POST(ManagerUrl.READ_BILL)
    @FormUrlEncoded
    Call<ResponseBill> readListBill(@Field("idUser") String idUser,
                                    @Field("deliveryStatus") String deliveryStatus);
    @POST(ManagerUrl.CANCEL_BILL)
    @FormUrlEncoded
    Call<ResponseBill> cancelBill(@Field("id") String id,
                                  @Field("reasonCancel") String reasonCancel);

    @POST(ManagerUrl.READ_DETAIL_BILL)
    @FormUrlEncoded
    Call<ResponseBillDetail> readBillDetail(@Field("id") String id);

}

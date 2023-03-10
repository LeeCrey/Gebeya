package com.example.online_ethio_gebeya.data.repositories;

import android.app.Application;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.online_ethio_gebeya.data.RetrofitConnectionUtil;
import com.example.online_ethio_gebeya.data.apis.CartItemApi;
import com.example.online_ethio_gebeya.models.Item;
import com.example.online_ethio_gebeya.models.responses.ItemResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartItemRepository {
    private static final String TAG = "CartItemRepository";
    private final CartItemApi api;
    private final MutableLiveData<List<Item>> mCartItemList;
    private Call<ItemResponse> cartItemResponseCall;
    private Call<List<Item>> listCall;
    private String authorizationToken;

    public CartItemRepository(@NonNull Application application) {
        mCartItemList = new MutableLiveData<>();
        api = RetrofitConnectionUtil.getRetrofitInstance(application).create(CartItemApi.class);
    }

    public LiveData<List<Item>> getCartItemList() {
        return mCartItemList;
    }

    public void cancelConnection() {
        if (cartItemResponseCall != null) {
            cartItemResponseCall.cancel();
        }
    }

    //    index
    public void getCartItems(long cartId, String authToken) {
        if (listCall != null) {
            listCall.cancel();
        }

        listCall = api.getCartList(authToken, cartId);
        listCall.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(@NonNull Call<List<Item>> call, @NonNull Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    mCartItemList.postValue(response.body());
                } else {
                    setEmptyToList();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Item>> call, @NonNull Throwable t) {
                // ignore
                setEmptyToList();
            }
        });
    }

    private void setEmptyToList() {
        mCartItemList.postValue(new ArrayList<>());
    }

    // add item to cart
    public void addItemToCart(long productId, Button addToCart, @NonNull Integer value) {
        cancelConnection();

        addToCart.setEnabled(false);
        cartItemResponseCall = api.addItem(authorizationToken, productId, value);
        cartItemResponseCall.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(@NonNull Call<ItemResponse> call, @NonNull Response<ItemResponse> response) {
                if (!response.isSuccessful()) {
                    // if it's bad request(item may be exist in cart. No need to enable button)
                    // no need to create duplicate item in cart(we should add quantity)
                    if (response.code() != 400) {
                        // unauthorized request
                        addToCart.setEnabled(true);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ItemResponse> call, @NonNull Throwable t) {
                // ignore
            }
        });
    }

    // delete item from cart
    public void removeItemFromCart(String authToken, int position) {
        if (cartItemResponseCall != null) {
            cartItemResponseCall.cancel();
        }

        List<Item> items = new ArrayList<>(Objects.requireNonNull(mCartItemList.getValue()));

        Item item = items.get(position);
        cartItemResponseCall = api.deleteItem(authToken, item.getId());
        cartItemResponseCall.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(@NonNull Call<ItemResponse> call, @NonNull Response<ItemResponse> response) {
                if (response.isSuccessful()) {
                    items.remove(item);
                    mCartItemList.postValue(items);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ItemResponse> call, @NonNull Throwable t) {
                // ignore
            }
        });
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }
}

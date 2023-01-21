package com.example.online_ethio_gebeya.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.online_ethio_gebeya.data.repositories.CartItemRepository;
import com.example.online_ethio_gebeya.models.CartItem;

import java.util.List;

public class CartItemViewModel extends AndroidViewModel {
    private final CartItemRepository repository;
    private final LiveData<List<CartItem>> oCartItemList;
    private String authToken = null;

    public CartItemViewModel(@NonNull Application application) {
        super(application);

        repository = new CartItemRepository(application);
        oCartItemList = repository.getCartItemList();
    }

    public void init(String authorizationToken) {
        authToken = authorizationToken;
    }

    public LiveData<List<CartItem>> getCartItemResponse() {
        return oCartItemList;
    }

    // get list
    public void getCartItems(int cartId) {
        repository.getCartItems(cartId, authToken);
    }

    public void removeItemFromCart(int position) {
        repository.removeItemFromCart(authToken, position);
    }
}

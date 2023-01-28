package com.example.online_ethio_gebeya.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.online_ethio_gebeya.data.repositories.ProductRepository;
import com.example.online_ethio_gebeya.models.responses.ProductShowResponse;

public class FragmentProductDetailViewModel extends ViewModel {
    private final LiveData<ProductShowResponse> showResponse;
    private final ProductRepository productRepository;
    private final long productId;

    public FragmentProductDetailViewModel(@NonNull ProductRepository repository, long productId) {
        productRepository = repository;
        showResponse = productRepository.getShowResponse();
        this.productId = productId;
    }

    public LiveData<ProductShowResponse> getShowResponse() {
        return showResponse;
    }

    public void getProductDetail() {
        productRepository.getProductDetail(productId);
    }
}
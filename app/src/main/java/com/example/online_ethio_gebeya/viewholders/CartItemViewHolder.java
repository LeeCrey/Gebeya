package com.example.online_ethio_gebeya.viewholders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_ethio_gebeya.R;
import com.example.online_ethio_gebeya.databinding.LayoutCartItemBinding;
import com.example.online_ethio_gebeya.models.CartItem;
import com.example.online_ethio_gebeya.models.Product;
import com.google.android.material.card.MaterialCardView;

public class CartItemViewHolder extends RecyclerView.ViewHolder {
    private final LayoutCartItemBinding binding;
    private final MaterialCardView materialCardView;

    public CartItemViewHolder(@NonNull LayoutCartItemBinding _binding) {
        super(_binding.getRoot());
        binding = _binding;

        materialCardView = (MaterialCardView) binding.getRoot();
    }

    public void bind(@NonNull Context context, @NonNull CartItem item) {
        binding.setCartItem(item);

        Product p = item.getProduct();
        double finalPrice = p.getPrice();
        if (p.getDiscount() != null) {
            finalPrice -= p.getDiscount();
        }

        String price = context.getString(R.string.price_in_ethio, finalPrice);
        binding.setPrice(price);

        materialCardView.setChecked(item.isChecked());
    }

    public MaterialCardView getMaterialCardView() {
        return materialCardView;
    }
}
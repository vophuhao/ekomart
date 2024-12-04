package vn.iotstar.service.user;

import vn.iotstar.entity.Cart;
import vn.iotstar.entity.CartItem;

public interface ICartService {

    Cart save(Cart cart);

    Cart findById(Long id);

    Cart findByUserId(Long userId);

    CartItem addItemToCart(Long cartId, CartItem item);

    CartItem updateItemQuantity(Long cartItemId, int quantity);

    void removeItemFromCart(Long cartItemId);
}

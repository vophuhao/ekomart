package vn.iotstar.service.user;

import vn.iotstar.entity.Cart;
import vn.iotstar.entity.CartItem;
import vn.iotstar.entity.UserInfo;

public interface ICartService {

    Cart save(Cart cart);

    Cart findByUser(UserInfo user);

    CartItem addItemToCart(UserInfo user, CartItem item);

    CartItem updateItemQuantity(Long cartItemId, int quantity);

    void removeItemFromCart(Long cartItemId);
}

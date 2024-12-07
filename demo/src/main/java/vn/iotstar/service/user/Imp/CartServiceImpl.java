package vn.iotstar.service.user.Imp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Cart;
import vn.iotstar.entity.CartItem;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.repository.CartItemRepository;
import vn.iotstar.repository.CartRepository;
import vn.iotstar.service.user.ICartService;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart findByUser(UserInfo user) {
        return cartRepository.findByUser(user)
        		.orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    @Override
    public CartItem addItemToCart(UserInfo user, CartItem item) {
    	Cart cart = cartRepository.findByUser(user)
    			.orElseThrow(() -> new RuntimeException("Cart not found"));
    	Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(item.getProduct().getId()))
                .findFirst();

        if (existingItem.isEmpty()) {
        	item.setCart(cart);
            cart.getItems().add(item);
            cartRepository.save(cart);
            return item;
        }
        return existingItem.get();
    }

    @Override
    public CartItem updateItemQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found with id: " + cartItemId));
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void removeItemFromCart(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found with id: " + cartItemId));
        cartItemRepository.delete(cartItem);
    }
}

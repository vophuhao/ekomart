package vn.iotstar.service.user.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Cart;
import vn.iotstar.entity.CartItem;
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
    public Cart findById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + id));
    }

    @Override
    public Cart findByUserId(Long userId) {
        return cartRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user with id: " + userId));
    }

    @Override
    public CartItem addItemToCart(Long cartId, CartItem item) {
        Cart cart = findById(cartId);
        item.setCart(cart);
        
        for (CartItem existingItem : cart.getItems()) {
            if (existingItem.getProduct().getId().equals(item.getProduct().getId())) {
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity()); // Cập nhật số lượng
                cartRepository.save(cart);
                return existingItem;
            }
        }
        
        cart.getItems().add(item);
        cartRepository.save(cart);
        return item; 
    }

    @Override
    public CartItem updateItemQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found with id: " + cartItemId));
        cartItem.setQuantity(quantity);
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void removeItemFromCart(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found with id: " + cartItemId));
        cartItemRepository.delete(cartItem);
    }
}

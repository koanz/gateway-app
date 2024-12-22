package com.example.gateway.order.services.impl;

import com.example.gateway.commons.dtos.MessageResponse;
import com.example.gateway.commons.dtos.OrderDto;
import com.example.gateway.commons.dtos.requests.OrderRequestDto;
import com.example.gateway.commons.entities.Item;
import com.example.gateway.commons.entities.Order;
import com.example.gateway.commons.entities.Product;
import com.example.gateway.commons.entities.User;
import com.example.gateway.commons.mappers.OrderMapper;
import com.example.gateway.order.exceptions.CustomException;
import com.example.gateway.order.exceptions.EntityNotFoundException;
import com.example.gateway.order.repositories.IOrderRepository;
import com.example.gateway.order.repositories.IProductRepository;
import com.example.gateway.order.repositories.IUserRepository;
import com.example.gateway.order.services.IOrderService;
import com.example.gateway.order.validations.ValidationOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService {
    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderMapper mapper;

    @Autowired
    private IOrderRepository repository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ValidationOrder validationOrder;

    @Override
    @Transactional
    public MessageResponse create(OrderRequestDto request) {
        Optional<User> user = userRepository.findById(request.getUserId());
        if(user.isEmpty()) {
            logger.error("User Not Found: " + request.getUserId());
            throw new EntityNotFoundException("Cannot create an Order. Cause: The User with ID " + request.getUserId() + " doesn't exist.");
        }

        final Order order = new Order();
        order.setUser(user.get());
        order.setTotal(request.getTotalPrice());

        // Crear los ítems y asociarlos a la orden
        List<Item> items = request.getItems().stream()
                .map(itemDto -> {
                    Optional<Product> product = productRepository.findById(itemDto.getProductId());
                    if(product.isEmpty()) {
                        logger.error("Product Not Found: " + itemDto.getProductId());
                        throw new EntityNotFoundException("Cannot create an Order. Cause: The Product with ID " + itemDto.getProductId() + " doesn't exist.");
                    }

                    Item item = new Item();
                    item.setQuantity(itemDto.getQuantity());
                    item.setProduct(product.get());
                    item.setOrder(order);
                    return item;
                }).toList();
        if(!validationOrder.isTotalValid(request.getTotalPrice(), items)) {
            throw new CustomException("The total value doesn't match.");
        }

        order.setItems(items);
        try {
            repository.save(order);
            logger.info("Order created: {}", order);
        } catch (Exception ex) {
            logger.error("Error al intentar registrar la Orden: " + ex.getMessage());
            return null;
        }

        return new MessageResponse(messageSource.getMessage("order.created", null, Locale.getDefault()));
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDto findById(Long id) {
        Optional<Order> order = repository.findById(id);

        if(order.isEmpty()) {
            logger.error("Order Not Found: " + id);
            throw new EntityNotFoundException(messageSource.getMessage("order.notfound", null, Locale.getDefault()) + " " + id);
        }

        return mapper.entityToResponse(order.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> findAll() {
        List<Order> orders = repository.findAll();

        if(orders.isEmpty()) {
            // throw new exception
            throw new EntityNotFoundException(messageSource.getMessage("order.notfound.list", null, Locale.getDefault()));
        }

        return orders.stream().map(mapper::entityToResponse).toList();
    }

}

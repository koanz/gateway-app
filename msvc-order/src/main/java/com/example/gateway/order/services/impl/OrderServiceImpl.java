package com.example.gateway.order.services.impl;

import com.example.gateway.commons.dtos.MessageResponse;
import com.example.gateway.commons.dtos.OrderDto;
import com.example.gateway.commons.dtos.requests.OrderRequestDto;
import com.example.gateway.commons.dtos.responses.UserResponseDto;
import com.example.gateway.commons.entities.Item;
import com.example.gateway.commons.entities.Order;
import com.example.gateway.commons.entities.Product;
import com.example.gateway.commons.entities.User;
import com.example.gateway.commons.mappers.OrderMapper;
import com.example.gateway.order.exceptions.CustomException;
import com.example.gateway.order.exceptions.EntityNotFoundException;
import com.example.gateway.order.repositories.IOrderRepository;
import com.example.gateway.order.services.IItemService;
import com.example.gateway.order.services.IOrderService;
import com.example.gateway.order.services.IProductService;
import com.example.gateway.order.services.IUserService;
import com.example.gateway.order.validations.ValidationOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper mapper;

    @Autowired
    private IOrderRepository repository;

    @Autowired
    private IItemService itemService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IProductService productService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ValidationOrder validationOrder;

    @Override
    @Transactional
    public MessageResponse create(OrderRequestDto request) {
        UserResponseDto userResponseDto = userService.findById(request.getUserId());
        User user = userService.findEntityById(userResponseDto.getId());

        final Order order = new Order();
        order.setUser(user);
        order.setTotal(request.getTotalPrice());

        // Crear los ítems y asociarlos a la orden
        List<Item> items = request.getItems().stream()
                .map(itemDto -> {
                    //Product product = productService.findEntityById(itemDto.getProductId());

                    /*Item item = new Item();
                    item.setQuantity(itemDto.getQuantity());
                    item.setProduct(product);
                    item.setOrder(order);*/


                    return new Item(null, itemDto.getQuantity(), productService.findEntityById(itemDto.getProductId()), order, new Date(), new Date());
                }).toList();

        if(!validationOrder.isTotalValid(request.getTotalPrice(), items)) {
            throw new CustomException("The total value doesn't match.");
        }

        try {
            order.setItems(items);

            repository.save(order);
        } catch (Exception ex) {
            log.error("Error al intentar registrar la Orden: " + ex.getMessage());
            return null;
        }

        return new MessageResponse(messageSource.getMessage("order.created", null, Locale.getDefault()));
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDto findById(Long id) {
        Optional<Order> order = repository.findById(id);

        if(order.isEmpty()) {
            log.error("Order Not Found: " + id);
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

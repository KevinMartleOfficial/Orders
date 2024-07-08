package be.vdab.orders.orders;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void voegOrderToe(Order order){
        orderRepository.voegOrderToe(order);
    }

    public List<OrdersPerWerknemer>findOrdersByWerknemerId(int id){
        return orderRepository.findOrdersByWerknemerId(id);
    }


    @Transactional
    public void updateGoedkeuringOrder(int id, Werknemer werknemer){
        Order orderMetIdBestaat = orderRepository.findOrderById(id).orElseThrow(()-> new OrderNietGevondenException(id));
        Order orderWerknemerVanChef = orderRepository.zoekOrderVanWerknemerVanChef(werknemer.getId(), orderMetIdBestaat.getId()).orElseThrow(()->
                new IllegalArgumentException("Order behoort niet tot een ondergeschikte van de chef"));

        if(orderWerknemerVanChef.getGoedgekeurd() != null){
            throw new IllegalArgumentException("Order is al goedgekeurd");
        }
        orderWerknemerVanChef.updateGoedgekeurd();
        orderRepository.update(orderWerknemerVanChef);

    }


}

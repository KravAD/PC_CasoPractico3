package com.uax.aop.service;

import com.uax.aop.orders.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Service
public class OrderSimulationService {

    private final OrderProcessorService orderProcessorService;

    public OrderSimulationService(OrderProcessorService orderProcessorService) {
        this.orderProcessorService = orderProcessorService;
    }

    public void runSimulation() {
        System.out.println("=== INICIO DE SIMULACIÓN DE PEDIDOS ===");

        List<Order> orders = createSampleOrders();

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        long globalStart = System.currentTimeMillis();

        // Simular llegada de pedidos simultáneos
        for (Order order : orders) {
            System.out.printf("[INFO] Pedido %d recibido para el cliente: %s%n",
                    order.getId(), order.getCustomerName());
            futures.add(orderProcessorService.processOrder(order));
        }

        int successCount = 0;
        int errorCount = 0;

        // Esperar a que terminen todos los hilos
        for (CompletableFuture<Void> future : futures) {
            try {
                future.join();
                successCount++;
            } catch (CompletionException ex) {
                errorCount++;
                // El detalle del error ya se registra en el aspecto (@AfterThrowing)
            }
        }

        long totalTime = System.currentTimeMillis() - globalStart;

        System.out.println();
        System.out.println("=== PROCESAMIENTO FINALIZADO ===");
        System.out.println("Pedidos completados exitosamente: " + successCount);
        System.out.println("Pedidos con error: " + errorCount);
        System.out.println("Tiempo total de simulación: " + totalTime + " ms aprox.");
    }

    private List<Order> createSampleOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1L, 120.50, "Ana López"));
        orders.add(new Order(2L, 85.99, "Carlos Gómez"));
        orders.add(new Order(3L, 210.00, "Marta Ruiz"));
        orders.add(new Order(4L, 45.30, "Diego Torres"));
        orders.add(new Order(5L, 300.10, "Laura Fernández"));
        orders.add(new Order(6L, 150.75, "Pedro Ramírez"));
        orders.add(new Order(7L, 90.00, "Sofía Medina"));
        orders.add(new Order(8L, 60.40, "Juan Pérez"));
        orders.add(new Order(9L, 180.90, "Lucía Vargas"));
        orders.add(new Order(10L, 99.99, "Jorge Castillo"));
        return orders;
    }
}

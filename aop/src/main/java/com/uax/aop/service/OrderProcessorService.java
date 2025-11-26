package com.uax.aop.service;

import com.uax.aop.annotations.Auditable;
import com.uax.aop.orders.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderProcessorService {

    private final Random random = new Random();

    @Async
    @Auditable
    public CompletableFuture<Void> processOrder(Order order) {
        // Cada pedido se procesa en un hilo independiente (@Async)
        simulateStep("Verificando stock", order);
        simulateStep("Procesando pago", order);
        simulateStep("Preparando envío", order);

        // Si no ha habido excepción, completamos normalmente
        return CompletableFuture.completedFuture(null);
    }

    private void simulateStep(String stepName, Order order) {
        try {
            // Pausa aleatoria entre 500 y 2500 ms para simular tiempos de red/cálculo
            int sleepTime = 500 + random.nextInt(2000);
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Hilo interrumpido durante " + stepName);
        }

        // Simulación de errores aleatorios (por ejemplo, 20% de probabilidad)
        if (random.nextDouble() < 0.2) {
            String message;
            if (random.nextBoolean()) {
                message = "Pago rechazado (Error simulado)";
            } else {
                message = "Error al verificar stock (Error simulado)";
            }
            throw new RuntimeException(message);
        }
    }
}

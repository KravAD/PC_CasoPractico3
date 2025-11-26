package com.uax.aop.aspects;

import com.uax.aop.orders.Order;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OrderAuditingAspect {

    @Around("@annotation(com.uax.aop.annotations.Auditable)")
    public Object aroundAuditableMethods(ProceedingJoinPoint pjp) throws Throwable {
        Order order = extractOrderFromArgs(pjp.getArgs());
        String orderIdText = order != null ? String.valueOf(order.getId()) : "?";

        System.out.printf("%n--- Auditoría: Inicio de proceso para Pedido %s ---%n", orderIdText);

        long start = System.currentTimeMillis();
        try {
            Object result = pjp.proceed();
            long time = System.currentTimeMillis() - start;

            System.out.printf("[PERFORMANCE] Pedido %s procesado en %d ms%n", orderIdText, time);
            System.out.printf("--- Auditoría: Fin de proceso para Pedido %s ---%n", orderIdText);

            return result;
        } catch (Throwable ex) {
            long time = System.currentTimeMillis() - start;
            // El error en sí se registra en @AfterThrowing, aquí podemos añadir performance si queremos
            System.out.printf("[PERFORMANCE] Pedido %s finalizó con error en %d ms%n", orderIdText, time);
            System.out.printf("--- Auditoría: Fin de proceso para Pedido %s ---%n", orderIdText);
            throw ex;
        }
    }

    @AfterThrowing(
            value = "@annotation(com.uax.aop.annotations.Auditable)",
            throwing = "ex"
    )
    public void afterThrowingAuditableMethods(JoinPoint jp, Throwable ex) {
        Order order = extractOrderFromArgs(jp.getArgs());
        String orderIdText = order != null ? String.valueOf(order.getId()) : "?";

        System.out.printf("[ERROR] Pedido %s falló: %s%n", orderIdText, ex.getMessage());
    }

    private Order extractOrderFromArgs(Object[] args) {
        if (args == null) {
            return null;
        }
        for (Object arg : args) {
            if (arg instanceof Order) {
                return (Order) arg;
            }
        }
        return null;
    }
}

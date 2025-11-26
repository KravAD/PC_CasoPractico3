# Caso Práctico 3 – Programación Concurrente y AOP

## Miembros del grupo
- Jose Carlos Zorrilla Garcia  
- Dmitry Kravets Osipov 
- Daniel de Alfonso Sánchez

---

## Descripción del proyecto
Este proyecto implementa una simulación concurrente del procesamiento de pedidos utilizando Spring Boot, multihilo y Programación Orientada a Aspectos (AOP).

Cada pedido se procesa en paralelo mediante servicios concurrentes. Los métodos clave están marcados con la anotación personalizada `@Auditable`, lo que permite aplicar aspectos AOP para:

- Registrar auditoría del inicio y fin del proceso.
- Medir el tiempo de ejecución.
- Simular y capturar errores durante la ejecución.

La lógica de negocio permanece limpia, mientras que las funcionalidades transversales se implementan de forma modular utilizando aspectos.

---

## Estructura del proyecto

El proyecto se encuentra bajo la carpeta `/aop/` y sigue la siguiente estructura:

```
src/main/java/com/uax/aop/
│
├── OrderSimulationApplication.java
│
├── annotations/
│   └── Auditable.java
│
├── aspects/
│   └── OrderAuditingAspect.java
│
├── orders/
│   └── Order.java
│
└── service/
    ├── OrderProcessorService.java
    └── OrderSimulationService.java
```

---

## Archivos relevantes y su propósito

### Raíz (`com.uax.aop`)
| Archivo | Descripción |
|--------|-------------|
| `OrderSimulationApplication.java` | Clase principal de Spring Boot que inicia la aplicación y la simulación. |

### Paquete `annotations`
| Archivo | Descripción |
|--------|-------------|
| `Auditable.java` | Anotación personalizada para marcar métodos interceptados por AOP. |

### Paquete `aspects`
| Archivo | Descripción |
|--------|-------------|
| `OrderAuditingAspect.java` | Aspecto AOP que registra inicio, fin, tiempo y errores en métodos anotados con `@Auditable`. |

### Paquete `orders`
| Archivo | Descripción |
|--------|-------------|
| `Order.java` | Modelo que representa un pedido con id, cliente y total. |

### Paquete `service`
| Archivo | Descripción |
|--------|-------------|
| `OrderProcessorService.java` | Lógica que procesa cada pedido, con esperas aleatorias y errores simulados. |
| `OrderSimulationService.java` | Genera múltiples pedidos y coordina su procesamiento concurrente mediante `ExecutorService`. |

---

## Lógica general de la solución

1. `OrderSimulationService` genera varios pedidos.
2. Cada pedido se envía a `OrderProcessorService`, que lo procesa en un hilo independiente.
3. Los métodos procesadores están anotados con `@Auditable`.
4. `OrderAuditingAspect` intercepta estos métodos:
   - Registra inicio del proceso.
   - Registra fin del proceso.
   - Mide su tiempo de ejecución.
   - Gestiona excepciones simuladas.
5. La consola muestra el flujo completo del procesamiento y un resumen final.

---

## Ejecución

1. Abrir el proyecto en un IDE compatible con Spring Boot.
2. Ejecutar `OrderSimulationApplication`.
3. La simulación comienza automáticamente.
4. La consola mostrará:
   - Pedidos generados.
   - Auditoría AOP.
   - Tiempos de ejecución.
   - Errores simulados.
   - Resumen final.

---

## Resultados esperados

Durante la ejecución se mostrarán mensajes como:

- Pedido recibido.
- Inicio y fin del proceso (AOP).
- Tiempo de procesamiento por pedido.
- Excepciones simuladas.
- Resumen final de pedidos correctos y fallidos.

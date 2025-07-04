# Sistema de Agencias de Viajes

## Autores
- **César David Sánchez Trejo**
- **Aaron de Jesús Santos Absalón**

---

## Descripción General

Este proyecto implementa un sistema de escritorio en Java para la gestión de una agencia de viajes, enfocado en dos módulos principales:

- Registro de turistas
- Reservación de hoteles

El sistema fue desarrollado utilizando **Java Swing** para la interfaz gráfica, y **ObjectDB** como motor de persistencia de datos, con implementación de **JPA (Java Persistence API)** para el acceso a la base de datos.

---

## Tecnologías Utilizadas

- **Java SE 8+**
- **Swing**: para la creación de interfaces gráficas.
- **ObjectDB**: base de datos embebida orientada a objetos.
- **JPA (javax.persistence)**: para la persistencia de entidades.
- **NetBeans o IntelliJ IDEA**: como entorno de desarrollo (IDE) recomendado.

---

## Módulo 1: Registro de Turistas

### Clases Principales

- `Turista.java`  
  Clase entidad que representa un turista. Se persiste automáticamente en ObjectDB mediante anotaciones JPA.  
  Campos: `id`, `codigoTurista`, `nombre`, `apellidos`, `direccion`, `telefono`.

- `TuristaService.java`  
  Clase de servicio encargada de operaciones CRUD con `Turista` usando JPA.

- `TuristaTableModel.java`  
  Modelo de tabla personalizado que muestra los datos de turistas en un `JTable`.

- `TuristaFrame.java`  
  Ventana principal del módulo. Permite registrar, actualizar, eliminar y listar turistas en una tabla.

### Funcionalidad

- Validación de campos obligatorios.
- Autoasignación de ID con `@GeneratedValue`.
- Refrescar datos desde la base de datos.
- Visualización ordenada por ID en la tabla.

---

## Módulo 2: Reservación de Hoteles

### Clases Principales

- `ReservaHotel.java`  
  Clase entidad que representa una reservación. Incluye relación con un `Turista`.  
  Campos: `id`, `codigoReserva`, `nombreHotel`, `fechaEntrada`, `fechaSalida`, `turista`.

- `ReservaHotelService.java`  
  Encargada de persistir, actualizar, eliminar y consultar reservaciones.

- `ReservaHotelTableModel.java`  
  Modelo de tabla que organiza las reservas mostrando nombre, fechas y turista.

- `ReservaHotelFrame.java`  
  Ventana principal del módulo. Permite gestionar reservas y asociarlas con turistas registrados.

### Funcionalidad

- Manejo de fechas con formato `yyyy-MM-dd`.
- Validación de que la fecha de salida no sea anterior a la de entrada.
- ComboBox dinámico con la lista de turistas disponibles.
- Carga y limpieza de datos.

---

## Organización del Proyecto


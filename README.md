# ingsoftware
Software Engineering - Final project - FCEFYN, UNC

# 💣 Bomberman en Java - Proyecto Final de Ingeniería de Software

Este proyecto es una mejora sustancial de un juego estilo **Bomberman**, utilizando buenas prácticas de desarrollo ágil, diseño orientado a objetos y arquitectura escalable. Fue realizado como **proyecto final de la materia Ingeniería de Software**.

---

## 🎮 ¿Qué hicimos?

Tomamos un proyecto open source de Bomberman y lo **mejoramos y expandimos significativamente**, agregando nuevas funcionalidades, aplicando patrones de diseño y automatizando el flujo de trabajo con integración continua.

---

## ✨ Funcionalidades implementadas

- 🎨 **Mejoras visuales y sonoras**  
  Nuevos sprites, animaciones, efectos y música.

- 👥 **Menú de selección de personajes**  
  Interfaz para elegir entre múltiples personajes jugables.

- 💥 **Power-ups agregados**  
  Nuevos tipos de bombas, explosiones y comportamiento de enemigos.

- 🧠 **Patrones de diseño aplicados**
    - `Strategy` para los diferentes tipos de explosiones (ExplosionStrategy).
    - `Observer` para el congelamiento de enemigos y fusionado con el Strategy para manejo de explosiones.
    - `Singleton` para centralizar la creación del Floor, manteniendo una única instancia durante el juego.

- 🧱 **Arquitectura limpia con MVC**
  Separación clara entre lógica del juego, vistas y controladores.

- 🧪 **Tests unitarios**
  Cobertura sobre lógica de juego, explosiones, movimientos y estados.

---

## 🧰 Tecnologías y herramientas usadas

- 🧑‍💻 **Java (con Swing)**
- 🧪 **JUnit para testing**
- 🔄 **GitHub Actions** (CI/CD)
- 📦 **Workflows automatizados**:
    - Para aprobar un `pull request`, era obligatorio:
        - ✅ Que todos los tests pasen.
        - ✅ Que al menos 3 miembros lo aprueben (code review).
- 🧭 **SCRUM aplicado**
    - Backlog, Sprints, Daily Meetings, Revisiones y Retrospectivas.

---

## 👨‍👩‍👧‍👦 Equipo de desarrollo

- 👨‍💻 **Saqib Daniel Mohammad Cabrejos** - [@CodeMadefromGod](https://github.com/CodeMadefromGod)
- 🧠 **Javier Alejandro Fatu** - [@JavierAlejandroFatu](https://github.com/JavierAlejandroFatu)
- 🔧 **José María** - [@JoseMGaloppo](https://github.com/JoseMGaloppo)
- 🎮 **Pablo-C97** - [@Pablo-C97](https://github.com/Pablo-C97)

---

## 📸 Capturas del juego

![img_1.png](img_1.png)



![img.png](img.png)


---

## Créditos

Este proyecto se basa en el trabajo original de [Gaspar Coding (Gaspared)](https://www.youtube.com/user/CbX397)

- Repositorio original: [https://github.com/Gaspared/Bomberman](https://github.com/Gaspared/Bomberman)

---

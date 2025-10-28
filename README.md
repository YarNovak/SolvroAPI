# 🍸 Solvro API — REST API do zarządzania koktajlami i składnikami

## 📘 Opis projektu

**Solvro API** to aplikacja REST stworzona w **Spring Boot**, umożliwiająca zarządzanie koktajlami i składnikami — tworzenie, edycję, usuwanie i wyszukiwanie danych.  
Projekt wspiera filtrowanie, sortowanie oraz paginację wyników.

---

## ⚙️ Technologie

| Technologia | Opis |
|--------------|------|
| ☕ **Java 17+** | Język programowania |
| 🌱 **Spring Boot 3** | Framework do tworzenia aplikacji webowych |
| 🗃️ **Spring Data JPA / Hibernate** | Obsługa bazy danych |
| 💾 **PostgreSQL / H2** | Relacyjna baza danych |
| 🧰 **Lombok** | Automatyzacja getterów i setterów |
| 🧱 **Maven** | Zarządzanie zależnościami |

---

## 🔌 Endpointy REST API

### 🍹 `/koktajls` — koktajle

| Metoda | Endpoint | Opis |
|---------|-----------|------|
| `GET` | `/koktajls` | Pobierz wszystkie koktajle |
| `GET` | `/koktajls/{id}` | Pobierz koktajl po ID |
| `POST` | `/koktajls` | Dodaj nowy koktajl |
| `PUT` | `/koktajls/{id}` | Zaktualizuj koktajl |
| `DELETE` | `/koktajls/{id}` | Usuń koktajl |
| `PUT` | `/koktajls/{k_id}/skladniki/{s_id}` | zmienić ilość składnika w koktajlu |
| `DELETE` | `/koktajls/{k_id}/skladniki/{s_id}` | Usuń składnik w koktajlu |

---

### 🍋 `/skladniki` — składniki

| Metoda | Endpoint | Opis |
|---------|-----------|------|
| `GET` | `/skladniki` | Pobierz wszystkie składniki |
| `GET` | `/skladniki/{id}` | Pobierz składnik po ID |
| `POST` | `/skladniki` | Dodaj nowy składnik |
| `PUT` | `/skladniki/{id}` | Zaktualizuj składnik |
| `DELETE` | `/skladniki/{id}` | Usuń składnik |

---

## 🚦 Obsługa błędów

Wszystkie błędy są obsługiwane globalnie przez `GlobalExceptionHandler`, zwracając czytelne komunikaty JSON.

---

## 🏁 Uruchomienie projektu

1. Utwórz bazę danych PostgreSQL lub użyj H2.  
2. Skonfiguruj `application.properties`:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/SolvroBar
   spring.datasource.username=postgres
   spring.datasource.password=twojehaslo
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true


   <img width="960" height="918" alt="image" src="https://github.com/user-attachments/assets/b2658625-3d08-4e3f-8d38-8c1f16866410" />


# Messanger
Это моя реализация веб-мессенджера, а также первое веб-приложение, написанное на Spring Boot. Целью было получить некоторый опыт в разработке rest-приложений, а также настройки авторизации методом JWT, а также кэширования. Также я впервые попробовал написать несколько Unit-тестов.  
### Используемые технологии 
- Spring Boot
- Spring MVC
- Spring Security
- Spring Cache
- СУБД PostgreSQL
- Spring Data и Hibernate
- JUnit и Mockito
### Использование
Для того, чтобы запустить проект, потребуется IDE (я использовал Intellige Idea Community) и кластер базы данных PostgreSQL.  
Также нужно создать пустую БД. Для этого подключитесь к уже существующей БД и выполните следующую команду
```sql
create database messanger;
```
Все параметры для подключения к БД находятся файле src/main/resources/application.properties
При желании их можно поменять под себя.

Для того, чтобы войти в приложение, можно использовать уже готовых тестовых пользователей testuser1 и testuser2 с паролями 1234 и 4321 соответственно.
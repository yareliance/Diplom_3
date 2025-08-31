# Diplom_3: Stellar Burgers UI Tests

## Запуск тестов в браузере Yandex

```bash 
mvn clean test -Dbrowser=yandex
```
## Запуск тестов в браузере Chrome
```bash 
mvn clean test -Dbrowser=chrome
```
## Запуск конкретного тестового класса:

mvn clean test -Dtest=MyTestClass -Dbrowser=yandex

## Запуск конкретного метода внутри тестового класса:

mvn clean test -Dtest=MyTestClass#testMethod -Dbrowser=yandex



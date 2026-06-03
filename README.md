# Сравнение: new внутри vs DI через конструктор

## BAD: new InMemoryLeadRepository() внутри класса

```java
public class LeadService {
    // Тесная связанность!
    private final LeadRepository repository = new InMemoryLeadRepository();
}
```

### Проблемы:
- Невозможно подставить mock в тестах — LeadService всегда использует реальный Repository
- Невозможно заменить реализацию — хотите PostgreSQL? Придётся менять код LeadService
- Скрытая зависимость — глядя на конструктор не видно, что нужно для работы класса

---

## GOOD: DI через конструктор

```java
public class LeadService {
    private final LeadRepository repository;

    public LeadService(LeadRepository repository) {
        this.repository = repository;
    }
}
```

### Преимущества:
- В тестах передаём `mock(LeadRepository.class)` — тестируем только бизнес-логику
- В production передаём `InMemoryLeadRepository`
- В будущем передаём `JpaLeadRepository` без изменения кода LeadService
- Зависимость явная — видно в конструкторе

---

## Сравнение подходов

| Критерий         | new внутри (BAD) | DI через конструктор (GOOD) |
|------------------|------------------|-----------------------------|
| Тестируемость    | ❌ mock невозможен | ✅ передаём mock            |
| Замена реализации| ❌ меняем LeadService | ✅ меняем только при создании |
| Явность          | ❌ зависимость скрыта | ✅ видно в конструкторе   |
| Immutability     | ✅ final поле    | ✅ final поле               |

---

## Как это связано со Spring

Spring автоматизирует DI. Вместо ручного `new LeadService(repository)`
Spring создаёт объекты и передаёт зависимости через `@Autowired`.
Принцип тот же: зависимости передаются извне, не создаются внутри.
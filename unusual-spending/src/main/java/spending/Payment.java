package spending;

import java.util.UUID;

public record Payment(UUID id, Amount amount, Category category) {
    static Payment of(UUID id, Amount amount, Category category) {
        return new Payment(id, amount, category);
    }

    static Payment of(Amount amount, Category category) {
        return of(UUID.randomUUID(), amount, category);
    }
}

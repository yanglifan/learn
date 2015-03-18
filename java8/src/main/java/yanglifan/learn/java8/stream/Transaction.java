package yanglifan.learn.java8.stream;

import java.math.BigDecimal;

public class Transaction {
    public enum TransactionType {
        GROCERY, SUPERMARKET, MALL
    }

    private int id;
    private TransactionType type;
    private BigDecimal value;

    public Transaction(int id, TransactionType type, BigDecimal value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getValue() {
        return value;
    }
}

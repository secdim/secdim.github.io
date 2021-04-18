package app;

import java.math.BigInteger;

public class Amount {
    private final BigInteger MIN_AMOUNT = new BigInteger(String.valueOf(0));
    private final BigInteger MAX_AMOUNT = new BigInteger(String.valueOf(1000000));
    private final BigInteger THRESHOLD = BigInteger.valueOf(1000);

    final BigInteger amount;

    Amount(String amount) {
        if (amount == null || amount.trim().length() == 0 || amount.isEmpty()) 
            throw new IllegalArgumentException("Invalid value");
        BigInteger convertedAmount = new BigInteger(amount);
        if (!validate(convertedAmount)) throw new IllegalArgumentException("Invalid value");
        this.amount = convertedAmount;
    }

    boolean requiresApproval() {
        return this.amount.compareTo(THRESHOLD) >= 0;
    }

    private boolean validate(BigInteger amount) {
        return amount.compareTo(MAX_AMOUNT) <= 0 && amount.compareTo(MIN_AMOUNT) >= 0;
    }
}

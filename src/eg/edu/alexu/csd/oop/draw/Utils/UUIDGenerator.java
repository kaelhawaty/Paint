package eg.edu.alexu.csd.oop.draw.Utils;

import java.util.concurrent.atomic.AtomicInteger;

public class UUIDGenerator {
    private final AtomicInteger generator;
    private static UUIDGenerator instance;
    private UUIDGenerator(){
        generator = new AtomicInteger(0);
    }

    public int generate(){
        return generator.incrementAndGet();
    }

    public synchronized static UUIDGenerator getInstance(){
        return instance == null? instance = new UUIDGenerator() : instance;
    }
}

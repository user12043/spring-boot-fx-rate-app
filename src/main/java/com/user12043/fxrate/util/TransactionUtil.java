package com.user12043.fxrate.util;

import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidFactory;

import java.util.random.RandomGenerator;

public class TransactionUtil {
    private static final UlidFactory FACTORY = UlidFactory.newMonotonicInstance(() -> RandomGenerator.getDefault().nextLong());

    public static Ulid next() {
        return FACTORY.create();
    }
}

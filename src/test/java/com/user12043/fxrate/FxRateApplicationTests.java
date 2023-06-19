package com.user12043.fxrate;

import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidCreator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FxRateApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void ulidBytesStringConversionTest() {
		final Ulid ulid = UlidCreator.getMonotonicUlid();
		final byte[] ulidBytes = ulid.toBytes();

		final Ulid ulidFromBytes = Ulid.from(ulidBytes);
		assert ulidFromBytes.equals(ulid);

		final String ulidString = ulidFromBytes.toString();
		final Ulid ulidFromString = Ulid.from(ulidString);
		assert ulidFromString.equals(ulid);
		assert ulidFromBytes.equals(ulid);
	}

}

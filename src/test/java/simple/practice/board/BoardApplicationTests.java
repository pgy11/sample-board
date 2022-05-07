package simple.practice.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardApplicationTests {

	@Test
	void hello() {
		Assertions.assertThat(Integer.parseInt("1") == 2).isFalse();
	}

}

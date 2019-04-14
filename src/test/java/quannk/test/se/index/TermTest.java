package quannk.test.se.index;

import org.junit.Assert;
import org.junit.Test;

public class TermTest {
	private static void testNotOKTerm(String rawTerm) {
		try {
			new Term(rawTerm);
			Assert.fail("Should not reach here, term=" + rawTerm);
		} catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void testTermOK() {
		new Term("123");
		new Term("123asv");
		new Term("12a34");
		new Term("abc34");
		new Term("vol1");
	}

	@Test
	public void testTermNotOK() {
		testNotOKTerm("123 ");
		testNotOKTerm("A1");
		testNotOKTerm("- ");
		testNotOKTerm("A-b");
		testNotOKTerm("vol.1");
	}

}
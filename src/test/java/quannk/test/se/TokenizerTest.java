package quannk.test.se;

import org.junit.Assert;
import org.junit.Test;
import quannk.test.se.index.Term;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TokenizerTest {

	@Test
	public void testEmptyTokens() {
		Tokenizer tokenizer = new Tokenizer();
		Assert.assertEquals(0, tokenizer.tokenize(" ").size());
		Assert.assertEquals(0, tokenizer.tokenize(" \t\n").size());
		Assert.assertEquals(0, tokenizer.tokenize(" -+").size());
		Assert.assertEquals(0, tokenizer.tokenize(" #$  @# ").size());
	}

	@Test
	public void testNormal() {
		Tokenizer tokenizer = new Tokenizer();
		test(tokenizer.tokenize("12 34"), new String[]{"12", "34"});
		test(tokenizer.tokenize("12_34"), new String[]{"12", "34"});
		test(tokenizer.tokenize("12 +34"), new String[]{"12", "34"});

		test(tokenizer.tokenize("12 "), new String[]{"12"});
		test(tokenizer.tokenize("12 +"), new String[]{"12"});
		test(tokenizer.tokenize("- 12 "), new String[]{"12"});
	}

	private void test(List<Term> result, String[] expected) {
		Set<String> expectedSet = new HashSet<>(Arrays.asList(expected));
		Assert.assertEquals(expected.length, expectedSet.size());
		Assert.assertEquals(expected.length, result.size());
		for (Term term : result) {
			Assert.assertTrue(expectedSet.contains(term.getRaw()));
		}
	}
}
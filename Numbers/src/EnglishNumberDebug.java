import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class EnglishNumberDebug {

	@Test
	/* Structural Basis, Nominal Case */
	public void initializeTest() {
		EnglishNumber englishNumber = new EnglishNumber();
		List<String> input = new ArrayList<String>();
		input.add("six");
		
		boolean expected = true;
		boolean actual = englishNumber.initialize(input);
		
		assertEquals(expected, actual);
	}
	
	@Test
	/* Structural Basis, Bad Data (No Tokens) */
	public void initializeNoTokensTest() {
		EnglishNumber englishNumber = new EnglishNumber();
		List<String> input = new ArrayList<String>();
		
		boolean expected = false;
		boolean actual = englishNumber.initialize(input);
		
		assertEquals(expected, actual);
	}
	
	@Test
	/* Structural Basis, Bad Data (Invalid Token) */
	public void initializeBadTokenTest() {
		EnglishNumber englishNumber = new EnglishNumber();
		List<String> input = new ArrayList<String>();
		input.add("asdf");
		
		boolean expected = false;
		boolean actual = englishNumber.initialize(input);
		
		assertEquals(expected, actual);
	}
	
	@Test
	/* Structural Basis, Nominal Case */
	public void toIntTest() {
		EnglishNumber englishNumber = new EnglishNumber();
		List<String> input = new ArrayList<String>();
		input.add("five");
		englishNumber.initialize(input);
		
		int expected = 5;
		int actual = englishNumber.toInt();
		
		assertEquals(expected, actual);
	}
	
	@Test
	/* Structural Basis, No Tokens */
	public void toIntMinValueTest() {
		EnglishNumber englishNumber = new EnglishNumber();
		List<String> input = new ArrayList<String>();
		englishNumber.initialize(input);
		
		int expected = Integer.MIN_VALUE;
		int actual = englishNumber.toInt();
		
		assertEquals(expected, actual);
	}
	
	@Test
	/* Structural Basis, Nominal Case */
	public void toStringTest() {
		EnglishNumber englishNumber = new EnglishNumber();
		List<String> input = new ArrayList<String>();
		input.add("five");
		englishNumber.initialize(input);
		
		String expected = "five";
		String actual = englishNumber.toString();
		
		assertEquals(expected, actual);
	}
	
	@Test
	/* Structural Basis, Uninitialized */
	public void toStringUninitializedTest() {
		EnglishNumber englishNumber = new EnglishNumber();
		List<String> input = new ArrayList<String>();
		englishNumber.initialize(input);
		
		String expected = "uninitialized";
		String actual = englishNumber.toString();
		
		assertEquals(expected, actual);
	}
	
	@Test
	/* Structural Basis, Positive Number */
	public void interpretAsIntPosNum() {
		EnglishNumber en = new EnglishNumber();
		EnglishNumber.Test test = en.new Test();
		List<String> input = new ArrayList<String>();
		input.add("one");
		input.add("hundred");
		input.add("twenty");
		input.add("three");
		input.add("million");
		input.add("four");
		input.add("hundred");
		input.add("fifty");
		input.add("six");
		input.add("thousand");
		input.add("seven");
		input.add("hundred");
		input.add("eighty");
		input.add("nine");
		en.initialize(input);
		
		int expected = 123456789;
		int actual = test.interpretAsInt();
		
		assertEquals(expected, actual);
	}
	
	@Test
	/* Structural Basis, Negative Number */
	public void interpretAsIntNegNum() {
		EnglishNumber en = new EnglishNumber();
		EnglishNumber.Test test = en.new Test();
		List<String> input = new ArrayList<String>();
		input.add("minus");
		input.add("one");
		input.add("hundred");
		input.add("twenty");
		input.add("three");
		input.add("million");
		input.add("four");
		input.add("hundred");
		input.add("fifty");
		input.add("six");
		input.add("thousand");
		input.add("seven");
		input.add("hundred");
		input.add("eighty");
		input.add("nine");
		en.initialize(input);
		
		int expected = -123456789;
		int actual = test.interpretAsInt();
		
		assertEquals(expected, actual);
	}
	
	@Test
	/* Structural Basis, Nominal Case */
	public void toTokens() {
		EnglishNumber en = new EnglishNumber();
		EnglishNumber.Test test = en.new Test();
		List<String> input = new ArrayList<String>();
		input.add("one");
		input.add("hundred");
		input.add("twenty");
		input.add("three");
		
		List<NumberToken> expected = new ArrayList<NumberToken>();
		expected.add(new NumberToken("one"));
		expected.add(new NumberToken("hundred"));
		expected.add(new NumberToken("twenty"));
		expected.add(new NumberToken("three"));
		List<NumberToken> actual = test.toTokens(input);
		
		assertEquals(expected, actual);
	}
	
	@Test
	/* Structural Basis, Null NumberToken  */
	public void toTokensNullNumberToken() {
		EnglishNumber en = new EnglishNumber();
		EnglishNumber.Test test = en.new Test();
		List<String> input = new ArrayList<String>();
		input.add("one hundred");
		input.add("twenty");
		input.add("three");
		
		List<NumberToken> expected = null;
		List<NumberToken> actual = test.toTokens(input);
		
		assertEquals(expected, actual);
	}
	
	@Test
	/* Structural Basis, Size = 0 */
	public void toTokensZeroSize() {
		EnglishNumber en = new EnglishNumber();
		EnglishNumber.Test test = en.new Test();
		List<String> input = new ArrayList<String>();
		
		List<NumberToken> expected = null;
		List<NumberToken> actual = test.toTokens(input);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void DigitsTest() {
		EnglishNumber en = new EnglishNumber();
		List<String> one = new ArrayList<String>();
		one.add("one");
		en.initialize(one);
		int expected = 1;
		int actual = en.toInt();
		assertEquals(expected, actual);
		
		EnglishNumber en2 = new EnglishNumber();
		List<String> two = new ArrayList<String>();
		two.add("two");
		en2.initialize(two);
		int expected2 = 2;
		int actual2 = en2.toInt();
		assertEquals(expected2, actual2);
		
		EnglishNumber en3 = new EnglishNumber();
		List<String> three = new ArrayList<String>();
		three.add("three");
		en3.initialize(three);
		int expected3 = 3;
		int actual3 = en3.toInt();
		assertEquals(expected3, actual3);
		
		EnglishNumber en4 = new EnglishNumber();
		List<String> four = new ArrayList<String>();
		four.add("four");
		en4.initialize(four);
		int expected4 = 4;
		int actual4 = en4.toInt();
		assertEquals(expected4, actual4);
		
		EnglishNumber en5 = new EnglishNumber();
		List<String> five = new ArrayList<String>();
		five.add("five");
		en5.initialize(five);
		int expected5 = 5;
		int actual5 = en5.toInt();
		assertEquals(expected5, actual5);
		
		EnglishNumber en6 = new EnglishNumber();
		List<String> six = new ArrayList<String>();
		six.add("six");
		en6.initialize(six);
		int expected6 = 6;
		int actual6 = en6.toInt();
		assertEquals(expected6, actual6);
		
		EnglishNumber en7 = new EnglishNumber();
		List<String> seven = new ArrayList<String>();
		seven.add("seven");
		en7.initialize(seven);
		int expected7 = 7;
		int actual7 = en7.toInt();
		assertEquals(expected7, actual7);
		
		EnglishNumber en8 = new EnglishNumber();
		List<String> eight = new ArrayList<String>();
		eight.add("eight");
		en8.initialize(eight);
		int expected8 = 8;
		int actual8 = en8.toInt();
		assertEquals(expected8, actual8);
		
		EnglishNumber en9 = new EnglishNumber();
		List<String> nine = new ArrayList<String>();
		nine.add("nine");
		en9.initialize(nine);
		int expected9 = 9;
		int actual9 = en9.toInt();
		assertEquals(expected9, actual9);
	}
	
	@Test
	public void ZeroTest() {
		EnglishNumber en = new EnglishNumber();
		List<String> zero = new ArrayList<String>();
		zero.add("zero");
		en.initialize(zero);
		
		int expected = 0;
		int actual = en.toInt();
		
		assertEquals(expected, actual);
		
		EnglishNumber en2 = new EnglishNumber();
		List<String> naught = new ArrayList<String>();
		naught.add("naught");
		en2.initialize(naught);
		
		int expected2 = 0;
		int actual2 = en2.toInt();
		
		assertEquals(expected2, actual2);
	}
	
	@Test
	public void MinusTest() {
		EnglishNumber en = new EnglishNumber();
		List<String> minusOne = new ArrayList<String>();
		minusOne.add("minus");
		minusOne.add("one");
		en.initialize(minusOne);
		
		int expected = -1;
		int actual = en.toInt();
		
		assertEquals(expected, actual);
		
		EnglishNumber en2 = new EnglishNumber();
		List<String> negFive = new ArrayList<String>();
		negFive.add("negative");
		negFive.add("five");
		en2.initialize(negFive);
		
		int expected2 = -5;
		int actual2 = en2.toInt();
		
		assertEquals(expected2, actual2);
	}
	
	@Test
	public void TenTest() {
		EnglishNumber en = new EnglishNumber();
		List<String> ten = new ArrayList<String>();
		ten.add("ten");
		en.initialize(ten);
		
		int expected = 10;
		int actual = en.toInt();
		assertEquals(expected, actual);
		
		EnglishNumber en2 = new EnglishNumber();
		List<String> tenThousand = new ArrayList<String>();
		tenThousand.add("ten");
		tenThousand.add("thousand");
		en2.initialize(tenThousand);
		
		int expected2 = 10000;
		int actual2 = en2.toInt();
		assertEquals(expected2, actual2);
		
		EnglishNumber en3 = new EnglishNumber();
		List<String> tenMillion = new ArrayList<String>();
		tenMillion.add("ten");
		tenMillion.add("million");
		en3.initialize(tenMillion);
		
		int expected3 = 10000000;
		int actual3 = en3.toInt();
		assertEquals(expected3, actual3);
		
		EnglishNumber en4 = new EnglishNumber();
		List<String> minusTen = new ArrayList<String>();
		minusTen.add("minus");
		minusTen.add("ten");
		en4.initialize(minusTen);
		
		int expected4 = -10;
		int actual4 = en4.toInt();
		assertEquals(expected4, actual4);
	}
	
	@Test
	public void TeenTest() {
		EnglishNumber en = new EnglishNumber();
		List<String> eleven = new ArrayList<String>();
		eleven.add("eleven");
		en.initialize(eleven);
		int expected = 11;
		int actual = en.toInt();
		assertEquals(expected, actual);
		
		EnglishNumber en2 = new EnglishNumber();
		List<String> twelve = new ArrayList<String>();
		twelve.add("twelve");
		en2.initialize(twelve);
		int expected2 = 12;
		int actual2 = en2.toInt();
		assertEquals(expected2, actual2);
		
		EnglishNumber en3 = new EnglishNumber();
		List<String> thirteen = new ArrayList<String>();
		thirteen.add("thirteen");
		en3.initialize(thirteen);
		int expected3 = 13;
		int actual3 = en3.toInt();
		assertEquals(expected3, actual3);
		
		EnglishNumber en4 = new EnglishNumber();
		List<String> fourteen = new ArrayList<String>();
		fourteen.add("fourteen");
		en4.initialize(fourteen);
		int expected4 = 14;
		int actual4 = en4.toInt();
		assertEquals(expected4, actual4);
		
		EnglishNumber en5 = new EnglishNumber();
		List<String> fifteen = new ArrayList<String>();
		fifteen.add("fifteen");
		en5.initialize(fifteen);
		int expected5 = 15;
		int actual5 = en5.toInt();
		assertEquals(expected5, actual5);
		
		EnglishNumber en6 = new EnglishNumber();
		List<String> sixteen = new ArrayList<String>();
		sixteen.add("sixteen");
		en6.initialize(sixteen);
		int expected6 = 16;
		int actual6 = en6.toInt();
		assertEquals(expected6, actual6);
		
		EnglishNumber en7 = new EnglishNumber();
		List<String> seventeen = new ArrayList<String>();
		seventeen.add("seventeen");
		en7.initialize(seventeen);
		int expected7 = 17;
		int actual7 = en7.toInt();
		assertEquals(expected7, actual7);
		
		EnglishNumber en8 = new EnglishNumber();
		List<String> eighteen = new ArrayList<String>();
		eighteen.add("eighteen");
		en8.initialize(eighteen);
		int expected8 = 18;
		int actual8 = en8.toInt();
		assertEquals(expected8, actual8);
		
		EnglishNumber en9 = new EnglishNumber();
		List<String> nineteen = new ArrayList<String>();
		nineteen.add("nineteen");
		en9.initialize(nineteen);
		int expected9 = 19;
		int actual9 = en9.toInt();
		assertEquals(expected9, actual9);
		
		EnglishNumber en10 = new EnglishNumber();
		List<String> minusEleven = new ArrayList<String>();
		minusEleven.add("minus");
		minusEleven.add("eleven");
		en10.initialize(minusEleven);
		int expected10 = -11;
		int actual10 = en10.toInt();
		assertEquals(expected10, actual10);
	}
	
	@Test
	public void NtyTest() {
		EnglishNumber en = new EnglishNumber();
		List<String> twenty = new ArrayList<String>();
		twenty.add("twenty");
		en.initialize(twenty);
		int expected = 20;
		int actual = en.toInt();
		assertEquals(expected, actual);
		
		EnglishNumber en2 = new EnglishNumber();
		List<String> thirty = new ArrayList<String>();
		thirty.add("thirty");
		en2.initialize(thirty);
		int expected2 = 30;
		int actual2 = en2.toInt();
		assertEquals(expected2, actual2);
		
		EnglishNumber en3 = new EnglishNumber();
		List<String> forty = new ArrayList<String>();
		forty.add("forty");
		en3.initialize(forty);
		int expected3 = 40;
		int actual3 = en3.toInt();
		assertEquals(expected3, actual3);
		
		EnglishNumber en4 = new EnglishNumber();
		List<String> fifty = new ArrayList<String>();
		fifty.add("fifty");
		en4.initialize(fifty);
		int expected4 = 50;
		int actual4 = en4.toInt();
		assertEquals(expected4, actual4);
		
		EnglishNumber en5 = new EnglishNumber();
		List<String> sixty = new ArrayList<String>();
		sixty.add("sixty");
		en5.initialize(sixty);
		int expected5 = 60;
		int actual5 = en5.toInt();
		assertEquals(expected5, actual5);
		
		EnglishNumber en6 = new EnglishNumber();
		List<String> seventy = new ArrayList<String>();
		seventy.add("seventy");
		en6.initialize(seventy);
		int expected6 = 70;
		int actual6 = en6.toInt();
		assertEquals(expected6, actual6);
		
		EnglishNumber en7 = new EnglishNumber();
		List<String> eighty = new ArrayList<String>();
		eighty.add("eighty");
		en7.initialize(eighty);
		int expected7 = 80;
		int actual7 = en7.toInt();
		assertEquals(expected7, actual7);
		
		EnglishNumber en8 = new EnglishNumber();
		List<String> ninety = new ArrayList<String>();
		ninety.add("ninety");
		en8.initialize(ninety);
		int expected8 = 90;
		int actual8 = en8.toInt();
		assertEquals(expected8, actual8);
		
		EnglishNumber en9 = new EnglishNumber();
		List<String> ninetynine = new ArrayList<String>();
		ninetynine.add("ninety");
		ninetynine.add("nine");
		en9.initialize(ninetynine);
		int expected9 = 99;
		int actual9 = en9.toInt();
		assertEquals(expected9, actual9);
		
		EnglishNumber en10 = new EnglishNumber();
		List<String> minusFifty = new ArrayList<String>();
		minusFifty.add("minus");
		minusFifty.add("fifty");
		en10.initialize(minusFifty);
		int expected10 = -50;
		int actual10 = en10.toInt();
		assertEquals(expected10, actual10);
	}
	
	@Test
	public void MinMaxTest() {
		EnglishNumber en = new EnglishNumber();
		List<String> min = new ArrayList<String>();
		min.add("minus");
		min.add("nine");
		min.add("hundred");
		min.add("ninety");
		min.add("nine");
		min.add("million");
		min.add("nine");
		min.add("hundred");
		min.add("ninety");
		min.add("nine");
		min.add("thousand");
		min.add("nine");
		min.add("hundred");
		min.add("ninety");
		min.add("nine");
		en.initialize(min);
		int expected = -999999999;
		int actual = en.toInt();
		assertEquals(expected, actual);
		
		EnglishNumber en2 = new EnglishNumber();
		List<String> max = new ArrayList<String>();
		max.add("nine");
		max.add("hundred");
		max.add("ninety");
		max.add("nine");
		max.add("million");
		max.add("nine");
		max.add("hundred");
		max.add("ninety");
		max.add("nine");
		max.add("thousand");
		max.add("nine");
		max.add("hundred");
		max.add("ninety");
		max.add("nine");
		en2.initialize(max);
		int expected2 = 999999999;
		int actual2 = en2.toInt();
		assertEquals(expected2, actual2);
	}
	
	@Test
	public void HundredsTest() {
		EnglishNumber en = new EnglishNumber();
		List<String> onehundred = new ArrayList<String>();
		onehundred.add("one");
		onehundred.add("hundred");
		en.initialize(onehundred);
		int expected = 100;
		int actual = en.toInt();
		assertEquals(expected, actual);
		
		EnglishNumber en2 = new EnglishNumber();
		List<String> randomHundred = new ArrayList<String>();
		randomHundred.add("two");
		randomHundred.add("hundred");
		randomHundred.add("fifty");
		randomHundred.add("eight");
		en2.initialize(randomHundred);
		int expected2 = 258;
		int actual2 = en2.toInt();
		assertEquals(expected2, actual2);
		
		EnglishNumber en3 = new EnglishNumber();
		List<String> maxHundred = new ArrayList<String>();
		maxHundred.add("nine");
		maxHundred.add("hundred");
		maxHundred.add("ninety");
		maxHundred.add("nine");
		en3.initialize(maxHundred);
		int expected3 = 999;
		int actual3 = en3.toInt();
		assertEquals(expected3, actual3);
	}
	
	@Test
	public void ThousandsTest() {
		EnglishNumber en = new EnglishNumber();
		List<String> oneThousand = new ArrayList<String>();
		oneThousand.add("one");
		oneThousand.add("thousand");
		en.initialize(oneThousand);
		int expected = 1000;
		int actual = en.toInt();
		assertEquals(expected, actual);
		
		EnglishNumber en2 = new EnglishNumber();
		List<String> randomThousand = new ArrayList<String>();
		randomThousand.add("three");
		randomThousand.add("thousand");
		randomThousand.add("four");
		randomThousand.add("hundred");
		randomThousand.add("sixty");
		randomThousand.add("nine");
		en2.initialize(randomThousand);
		int expected2 = 3469;
		int actual2 = en2.toInt();
		assertEquals(expected2, actual2);
		
		EnglishNumber en3 = new EnglishNumber();
		List<String> maxDigitThousand = new ArrayList<String>();
		maxDigitThousand.add("nine");
		maxDigitThousand.add("thousand");
		maxDigitThousand.add("nine");
		maxDigitThousand.add("hundred");
		maxDigitThousand.add("ninety");
		maxDigitThousand.add("nine");
		en3.initialize(maxDigitThousand);
		int expected3 = 9999;
		int actual3 = en3.toInt();
		assertEquals(expected3, actual3);
		
		EnglishNumber en4 = new EnglishNumber();
		List<String> ntyThousand = new ArrayList<String>();
		ntyThousand.add("forty");
		ntyThousand.add("thousand");
		en4.initialize(ntyThousand);
		int expected4 = 40000;
		int actual4 = en4.toInt();
		assertEquals(expected4, actual4);
		
		EnglishNumber en5 = new EnglishNumber();
		List<String> randomNtyThousand = new ArrayList<String>();
		randomNtyThousand.add("sixty");
		randomNtyThousand.add("one");
		randomNtyThousand.add("thousand");
		randomNtyThousand.add("two");
		randomNtyThousand.add("hundred");
		randomNtyThousand.add("seventy");
		randomNtyThousand.add("six");
		en5.initialize(randomNtyThousand);
		int expected5 = 61276;
		int actual5 = en5.toInt();
		assertEquals(expected5, actual5);
		
		EnglishNumber en6 = new EnglishNumber();
		List<String> maxNtyThousand = new ArrayList<String>();
		maxNtyThousand.add("ninety");
		maxNtyThousand.add("nine");
		maxNtyThousand.add("thousand");
		maxNtyThousand.add("nine");
		maxNtyThousand.add("hundred");
		maxNtyThousand.add("ninety");
		maxNtyThousand.add("nine");
		en6.initialize(maxNtyThousand);
		int expected6 = 99999;
		int actual6 = en6.toInt();
		assertEquals(expected6, actual6);
		
		EnglishNumber en7 = new EnglishNumber();
		List<String> oneHundredThousand = new ArrayList<String>();
		oneHundredThousand.add("one");
		oneHundredThousand.add("hundred");
		oneHundredThousand.add("thousand");
		en7.initialize(oneHundredThousand);
		int expected7 = 100000;
		int actual7 = en7.toInt();
		assertEquals(expected7, actual7);
		
		EnglishNumber en8 = new EnglishNumber();
		List<String> randomHundredThousand = new ArrayList<String>();
		randomHundredThousand.add("three");
		randomHundredThousand.add("hundred");
		randomHundredThousand.add("fifty");
		randomHundredThousand.add("seven");
		randomHundredThousand.add("thousand");
		randomHundredThousand.add("four");
		randomHundredThousand.add("hundred");
		randomHundredThousand.add("sixty");
		randomHundredThousand.add("eight");
		en8.initialize(randomHundredThousand);
		int expected8 = 357468;
		int actual8 = en8.toInt();
		assertEquals(expected8, actual8);
		
		EnglishNumber en9 = new EnglishNumber();
		List<String> maxHundredThousand = new ArrayList<String>();
		maxHundredThousand.add("nine");
		maxHundredThousand.add("hundred");
		maxHundredThousand.add("ninety");
		maxHundredThousand.add("nine");
		maxHundredThousand.add("thousand");
		maxHundredThousand.add("nine");
		maxHundredThousand.add("hundred");
		maxHundredThousand.add("ninety");
		maxHundredThousand.add("nine");
		en9.initialize(maxHundredThousand);
		int expected9 = 999999;
		int actual9 = en9.toInt();
		assertEquals(expected9, actual9);
		
		EnglishNumber en10 = new EnglishNumber();
		List<String> hundredThousandNoUnits = new ArrayList<String>();
		hundredThousandNoUnits.add("seven");
		hundredThousandNoUnits.add("hundred");
		hundredThousandNoUnits.add("fifty");
		hundredThousandNoUnits.add("three");
		hundredThousandNoUnits.add("thousand");
		en10.initialize(hundredThousandNoUnits);
		int expected10 = 753000;
		int actual10 = en10.toInt();
		assertEquals(expected10, actual10);
	}
	
	@Test
	public void MillionsTest() {
		EnglishNumber en = new EnglishNumber();
		List<String> oneMillion = new ArrayList<String>();
		oneMillion.add("one");
		oneMillion.add("million");
		en.initialize(oneMillion);
		int expected = 1000000;
		int actual = en.toInt();
		assertEquals(expected, actual);
		
		EnglishNumber en2 = new EnglishNumber();
		List<String> digitMillion = new ArrayList<String>();
		digitMillion.add("two");
		digitMillion.add("million");
		en2.initialize(digitMillion);
		int expected2 = 2000000;
		int actual2 = en2.toInt();
		assertEquals(expected2, actual2);
		
		EnglishNumber en3 = new EnglishNumber();
		List<String> randomMillion = new ArrayList<String>();
		randomMillion.add("five");
		randomMillion.add("million");
		randomMillion.add("one");
		randomMillion.add("hundred");
		randomMillion.add("twenty");
		randomMillion.add("three");
		randomMillion.add("thousand");
		randomMillion.add("nine");
		randomMillion.add("hundred");
		randomMillion.add("eighty");
		randomMillion.add("seven");
		en3.initialize(randomMillion);
		int expected3 = 5123987;
		int actual3 = en3.toInt();
		assertEquals(expected3, actual3);
		
		EnglishNumber en4 = new EnglishNumber();
		List<String> ntyMillion = new ArrayList<String>();
		ntyMillion.add("thirty");
		ntyMillion.add("million");
		en4.initialize(ntyMillion);
		int expected4 = 30000000;
		int actual4 = en4.toInt();
		assertEquals(expected4, actual4);
		
		EnglishNumber en5 = new EnglishNumber();
		List<String> randomNtyMillion = new ArrayList<String>();
		randomNtyMillion.add("eighty");
		randomNtyMillion.add("four");
		randomNtyMillion.add("million");
		en5.initialize(randomNtyMillion);
		int expected5 = 84000000;
		int actual5 = en5.toInt();
		assertEquals(expected5, actual5);
		
		EnglishNumber en6 = new EnglishNumber();
		List<String> oneHundredMillion = new ArrayList<String>();
		oneHundredMillion.add("one");
		oneHundredMillion.add("hundred");
		oneHundredMillion.add("million");
		en6.initialize(oneHundredMillion);
		int expected6 = 100000000;
		int actual6 = en6.toInt();
		assertEquals(expected6, actual6);
		
		EnglishNumber en7 = new EnglishNumber();
		List<String> randomHundredMillion = new ArrayList<String>();
		randomHundredMillion.add("nine");
		randomHundredMillion.add("hundred");
		randomHundredMillion.add("eighty");
		randomHundredMillion.add("seven");
		randomHundredMillion.add("million");
		randomHundredMillion.add("six");
		randomHundredMillion.add("hundred");
		randomHundredMillion.add("fifty");
		randomHundredMillion.add("four");
		randomHundredMillion.add("thousand");
		randomHundredMillion.add("three");
		randomHundredMillion.add("hundred");
		randomHundredMillion.add("twenty");
		randomHundredMillion.add("one");
		en7.initialize(randomHundredMillion);
		int expected7 = 987654321;
		int actual7 = en7.toInt();
		assertEquals(expected7, actual7);
		
		EnglishNumber en8 = new EnglishNumber();
		List<String> hundredMillionNoUnits = new ArrayList<String>();
		hundredMillionNoUnits.add("three");
		hundredMillionNoUnits.add("hundred");
		hundredMillionNoUnits.add("twenty");
		hundredMillionNoUnits.add("one");
		hundredMillionNoUnits.add("million");
		hundredMillionNoUnits.add("four");
		hundredMillionNoUnits.add("hundred");
		hundredMillionNoUnits.add("fifty");
		hundredMillionNoUnits.add("six");
		hundredMillionNoUnits.add("thousand");
		en8.initialize(hundredMillionNoUnits);
		int expected8 = 321456000;
		int actual8 = en8.toInt();
		assertEquals(expected8, actual8);
		
		EnglishNumber en9 = new EnglishNumber();
		List<String> hundredMillionNoThousands = new ArrayList<String>();
		hundredMillionNoThousands.add("seven");
		hundredMillionNoThousands.add("hundred");
		hundredMillionNoThousands.add("eighty");
		hundredMillionNoThousands.add("nine");
		hundredMillionNoThousands.add("million");
		hundredMillionNoThousands.add("one");
		hundredMillionNoThousands.add("hundred");
		hundredMillionNoThousands.add("twenty");
		hundredMillionNoThousands.add("three");
		en9.initialize(hundredMillionNoThousands);
		int expected9 = 789000123;
		int actual9 = en9.toInt();
		assertEquals(expected9, actual9);
	}
}

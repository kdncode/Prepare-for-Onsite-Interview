1. 首先需要丢弃字符串前面的空格；

2. 然后可能有正负号（注意只取一个，如果有多个正负号，那么说这个字符串是无法转换的，返回0。比如测试用例里就有个“+-2”）；

3. 字符串可以包含0~9以外的字符，如果遇到非数字字符，那么只取该字符之前的部分，如“-00123a66”返回为“-123”；

4. 如果超出int的范围，返回边界值（2147483647或-2147483648）。



import java.util.HashSet;
/**8. String to Integer (atoi)  QuestionEditorial Solution  My Submissions
Total Accepted: 115114
Total Submissions: 839893
Difficulty: Easy
Implement atoi to convert a string to an integer.

Hint: Carefully consider all possible input cases. If you want a challenge, please do not see below and ask yourself what are the possible input cases.

Notes: It is intended for this problem to be specified vaguely (ie, no given input specs). You are responsible to gather all the input requirements up front.*/
import java.util.Set;

/**
 * 8. String to Integer (atoi)
 *
 * Implement atoi to convert a string to an integer.
 *
 * Hint: Carefully consider all possible input cases. If you want a challenge, please do not see below and ask yourself what are the possible input cases.
 * Notes: It is intended for this problem to be specified vaguely (ie, no given input specs). You are responsible to gather all the input requirements up front.
 *
 * Requirements for atoi:
 * The function first discards as many whitespace characters as necessary until the first non-whitespace character is found.
 * Then, starting from this character, takes an optional initial plus or minus sign followed by as many numerical digits as possible, and interprets them as a numerical value.
 * The string can contain additional characters after those that form the integral number, which are ignored and have no effect on the behavior of this function.
 * If the first sequence of non-whitespace characters in str is not a valid integral number,
 * or if no such sequence exists because either str is empty or it contains only whitespace characters, no conversion is performed.
 * If no valid conversion could be performed, a zero value is returned. If the correct value is out of the range of representable values, INT_MAX (2147483647) or INT_MIN (-2147483648) is returned.*/



	public static int myAtoi(String str) {
	if (str == null || str.length() == 0)
		return 0;//
	str = str.trim();
	char firstChar = str.charAt(0);
	int sign = 1, start = 0, len = str.length();
	long sum = 0;
	if (firstChar == '+') {
		sign = 1;
		start++;
	} else if (firstChar == '-') {
		sign = -1;
		start++;
	}
	for (int i = start; i < len; i++) {
		if (!Character.isDigit(str.charAt(i)))
			return (int) sum * sign;
		sum = sum * 10 + str.charAt(i) - '0';
		if (sign == 1 && sum > Integer.MAX_VALUE)
			return Integer.MAX_VALUE;
		if (sign == -1 && (-1) * sum < Integer.MIN_VALUE)
			return Integer.MIN_VALUE;
	}

	return (int) sum * sign;
}





public class _8 {

	public static class Solution1 {
		/**Eventually, made it AC'ed, lots of corner cases, but now,
		 * really felt much easier and the though process is super clear than the first time I tried to solve it which was 3~4 years ago from now.
		 * - 8/9/2016
		 * */
		public int myAtoi(String str) {
			//case 1: str is greater than Integer.MAX_VALUE, return Integer.MAX_VALUE as the question states it

			//case 2: str is smaller than Integer.MIN_VALUE, return Integer.MIN_VALUE as the question states it

			//case 3: str contains non-numeric values

			//case 4: there're many leading whitespace characters which we'll have to ignore

			//case 5: when finding the first non-whitespace character, it could possibly be a '+' or '-' sign, after that, we parse all the consecutive numbers

			str = str.trim();//cut off its leading and trailing whitespace characters
			if (str == null || str.isEmpty()) {
				return 0;
			}
			Set<Character> numbers = new HashSet();
			for (int i = 0; i < 10; i++) {
				numbers.add(Character.forDigit(i, 10));
			}

			char[] chars = str.toCharArray();
			StringBuilder sb = new StringBuilder();
			boolean negative;
			int minuSignCount = 0;
			int plusSignCount = 0;
			int i = 0;
			while (i < chars.length) {
				if (chars[i] == '-') {
					minuSignCount++;
					i++;
				} else if (chars[i] == '+') {
					plusSignCount++;
					i++;
				} else {
					break;
				}
			}
			if ((plusSignCount > 0 && minuSignCount > 0) || minuSignCount > 1 || plusSignCount > 1) {
				return 0;
			}
			negative = minuSignCount % 2 != 0;
			if (i >= chars.length) {
				return 0;
			}

			//it might be a floating number, so consider '.'
			int period = 0;
			while (i < chars.length && numbers.contains(chars[i])) {
				if (chars[i] == '.') {
					period++;
				}
				if (period > 1) {
					break;
				}
				sb.append(chars[i++]);
			}

			if (sb == null || sb.length() == 0) {
				return 0;
			}

			int result = 0;
			if (period > 0) {
				//use Double to parse
				try {
					result = (int) Double.parseDouble(sb.toString());
				} catch (Exception e) {
					System.out.println(e);
				}
			} else {
				//use Long to parse to handle integer overflow case
				long temp = 0;
				if (sb.length() >= Long.toString(Long.MAX_VALUE).length() && negative) {
					return Integer.MIN_VALUE;
				} else if (sb.length() >= Long.toString(Long.MAX_VALUE).length() && !negative) {
					return Integer.MAX_VALUE;
				} else {
					try {
						temp = Long.parseLong(sb.toString());
					} catch (Exception e) {
						if (sb.length() >= Integer.MAX_VALUE) {
							result = Integer.MAX_VALUE;
						}
					}
					if (temp > (long) Integer.MAX_VALUE + 1) {
						if (!negative) {
							return Integer.MAX_VALUE;
						} else {
							return Integer.MIN_VALUE;
						}
					} else if (temp == (long) Integer.MAX_VALUE + 1 && negative) {
						return Integer.MIN_VALUE;
					} else if (temp == (long) Integer.MAX_VALUE + 1) {
						return Integer.MAX_VALUE;
					} else if (temp < Integer.MIN_VALUE) {
						result = Integer.MIN_VALUE;
					} else {
						result = (int) temp;
					}
				}
			}

			if (negative) {
				result = -result;
			}
			return result;
		}
	}

	public static class Solution2 {
		public int myAtoi(String str) {
			int p = 0;
			int result = 0;
			while (p < str.length() && Character.isWhitespace(str.charAt(p))) {
				p++;
			}
			if (p == str.length()) {
				return 0;
			}
			boolean negativeFlag = (str.charAt(p) == '-');
			if (str.charAt(p) == '+' || str.charAt(p) == '-') {
				p++;
			}
			for (; p < str.length(); p++) {
				if (str.charAt(p) > '9' || str.charAt(p) < '0') {
					break;
				} else {
					int digit = str.charAt(p) - '0';
					if (!negativeFlag && result > (Integer.MAX_VALUE - digit) / 10) {
						return Integer.MAX_VALUE;
					} else if (negativeFlag && result < (Integer.MIN_VALUE + digit) / 10) {
						return Integer.MIN_VALUE;
					}
					result = result * 10 + (negativeFlag ? -digit : digit);
				}
			}
			return result;
		}
	}

}

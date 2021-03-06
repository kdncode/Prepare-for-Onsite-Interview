只要遇到字符串的子序列或配准问题首先考虑动态规划DP，只要遇到需要求出所有可能情况首先考虑用递归
/**
 * 93. Restore IP Addresses
 *
 * Given a string containing only digits, restore it by returning all possible valid IP address
 * combinations.
 *
 * For example: Given "25525511135",
 *
 * return ["255.255.11.135", "255.255.111.35"]. (Order does not matter)
 */
public class _93 {

  public static class Solution1 {
    public List<String> restoreIpAddresses(String s) {
      List<String> allValidIpAddresses = new ArrayList<>();
      if (s == null || s.length() > 12 || s.length() < 4) {
        return allValidIpAddresses;
      }
      backtracking(s, new ArrayList<>(), allValidIpAddresses, 0);
      return allValidIpAddresses;
    }

    private void backtracking(String s, ArrayList<String> bytes, List<String> result, int pos) {
      if (bytes.size() == 4) {
        if (pos != s.length()) {
          return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
          stringBuilder.append(bytes.get(i));
          stringBuilder.append(".");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        result.add(stringBuilder.toString());
        return;
      }

      for (int i = pos; i < pos + 4 && i < s.length(); i++) {
        String oneByte = s.substring(pos, i + 1);
        if (!isValid(oneByte)) {
          continue;
        }
        bytes.add(oneByte);
        backtracking(s, bytes, result, i + 1);
        bytes.remove(bytes.size() - 1);
      }
    }

    private boolean isValid(String oneByte) {
      if (oneByte.charAt(0) == '0') {
        return oneByte.equals("0");
      }
      int num = Integer.valueOf(oneByte);
      return (num >= 0 && num < 256);
    }
  }
}


// 所以说IP地址总共有四段，每一段可能有一位，两位或者三位，范围是[0, 255]，题目明确指出输入字符串只含有数字，所以当某段是三位时，我们要判断其是否越界（>255)，还有一点很重要的是，当只有一位时，0可以成某一段，如果有两位或三位时，像 00， 01， 001， 011， 000等都是不合法的，所以我们还是需要有一个判定函数来判断某个字符串是否合法。这道题其实也可以看做是字符串的分段问题，在输入字符串中加入三个点，将字符串分为四段，每一段必须合法，求所有可能的情况。
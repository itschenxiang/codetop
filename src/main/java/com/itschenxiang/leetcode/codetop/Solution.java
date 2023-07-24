package com.itschenxiang.leetcode.codetop;

public class Solution {

    public static void main(String[] args) {
        new Solution().multiply("2", "3");
    }
    public String multiply(String num1, String num2) {
        String res = "0";
        String zeroSuffix = "";
        for(int i=num1.length()-1;i>=0;i--) {
            char c = num1.charAt(i);
            String tmp = multiply(num2, c);
            if(!"0".equals(tmp)) {
                res = add(res, tmp+zeroSuffix);
            }
            zeroSuffix += "0";
        }
        return res;
    }
    
    private String add(String num1, String num2) {
        StringBuilder res = new StringBuilder();
        int idx1 = num1.length()-1, idx2 = num2.length()-1;
        int carry = 0;
        while(idx1>=0 && idx2>=0) {
            int val1 = num1.charAt(idx1) - '0';
            int val2 = num2.charAt(idx2) - '0';
            res.append((val1 + val2 + carry) % 10);
            carry = (val1 + val2 + carry) / 10;
            idx1--;
            idx2--;
        }
        while(idx1>=0) {
            int val1 = num1.charAt(idx1) - '0';
            res.append((val1 + carry) % 10);
            carry = (val1 + carry) / 10;
            idx1--;
        }
        while(idx2>=0) {
            int val2 = num2.charAt(idx2) - '0';
            res.append((val2 + carry) % 10);
            carry = (val2 + carry) / 10;
            idx2--;
        }
        if(carry != 0) {
            res.append(carry);
        }
        return res.reverse().toString();
    }
    
    private String multiply(String num, char c) {
        StringBuilder res = new StringBuilder();
        if("0".equals(num) || c=='0') {
            return "0";
        }
        int carry = 0, val2 = c - '0';
        for(int i=num.length()-1;i>=0;i--) {
            int val1 = num.charAt(i) - '0';
            res.append((val1*val2 + carry) % 10);
            carry = (val1*val2 + carry) / 10;
        }
        if(carry != 0) {
            res.append(carry);
        }
        return res.reverse().toString();
    }
    
}

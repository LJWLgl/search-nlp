package cn.ganzhiqiang.searchnlp.util;

import cn.ganzhiqiang.searchnlp.DictAttribute;
import cn.ganzhiqiang.searchnlp.Token;
import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zq_gan
 * @since 2020/5/5
 **/

public class TokenUtil {

    public static Token convertToken(DictAttribute dictAttribute, int start, int end) {
        Token token = new Token();
        token.setStartOffset(start);
        token.setEndOffset(end);
        token.setToken(dictAttribute.getText());
        token.setTypes(convertTokenTypes(dictAttribute.getAttributes()));
        return token;
    }

    public static List<Token.TokenType> convertTokenTypes(List<DictAttribute.Attribute> attributes) {
        return attributes.stream().map(TokenUtil::convertTokenType).collect(Collectors.toList());
    }

    public static Token.TokenType convertTokenType(DictAttribute.Attribute attribute) {
        Token.TokenType tokenType = new Token.TokenType();
        tokenType.setBusinessId(attribute.getBusinessId());
        tokenType.setNature(attribute.getNature());
        tokenType.setType(attribute.getType());
        tokenType.setFrequency(attribute.getFrequency());
        return tokenType;
    }

    /**
     * 归并成多套分词
     * @param text
     * @param tokens
     * @return
     */
    public static List<List<Token>> mergeToMultipleSegments(String text, List<Token> tokens) {
        Map<Integer, List<Token>> tokenOffsetMap = new HashMap<>();
        List<Integer> offsets = new ArrayList<>();
        for (Token token : tokens) {
            if (tokenOffsetMap.get(token.getStartOffset()) == null) {
                tokenOffsetMap.put(token.getStartOffset(), Lists.newArrayList(token));
            } else {
                tokenOffsetMap.get(token.getStartOffset()).add(token);
            }
            offsets.add(token.getStartOffset());
        }
        offsets = offsets.stream().sorted().distinct().collect(Collectors.toList());
        List<List<Token>> multipleTokenList = multipleSegments(0, offsets, tokenOffsetMap);

        // 填充none
        List<List<Token>> resultTokens = new ArrayList<>();
        int[] flagArr = new int[text.length()];
        char[] textArr = text.toCharArray();
        for (List<Token> tokenList : multipleTokenList) {
            Arrays.fill(flagArr, 0);
            for (Token token : tokenList) {
                Arrays.fill(flagArr, token.getStartOffset(), token.getEndOffset(), 1);
            }
            List<Token> resultToken = new ArrayList<>(tokenList);
            for (int i = 0; i < flagArr.length; i++) {
                if (flagArr[i] == 0) {
                    resultToken.add(newNoneTypeToken(String.valueOf(textArr[i]), i, i + 1));
                }
            }
            resultTokens.add(resultToken.stream().sorted(Comparator.comparing(Token::getStartOffset)).collect(Collectors.toList()));
        }
        return resultTokens;
    }

    public static List<List<Token>> multipleSegments(int index, List<Integer> offsets, Map<Integer, List<Token>> tokenOffsetMap) {
        List<List<Token>> outResult = new ArrayList<>();
        List<Token> sameIndexTokens = tokenOffsetMap.get(offsets.get(index));
        for (Token token : sameIndexTokens) {
            int nexIndex = findNextIndex(offsets, token.getEndOffset());
            if (nexIndex > 0) {
                List<List<Token>> tempRes = multipleSegments(nexIndex, offsets, tokenOffsetMap);
                for (List<Token> tokenList : tempRes) {
                    tokenList.add(0, token);
                    outResult.add(tokenList);
                }
            } else {
                outResult.add(Lists.newArrayList(token));
            }

        }
        return outResult;
    }

    public static int findNextIndex(List<Integer> offsets, int nowOffset) {
        for (int i = 0; i < offsets.size(); i++) {
            if (offsets.get(i) >= nowOffset) {
                return i;
            }
        }
        return -1;
    }

    public static Token newNoneTypeToken(String text, int start, int end) {
        Token token = new Token();
        token.setToken(text);
        token.setStartOffset(start);
        token.setEndOffset(end);
        token.setTypes(Lists.newArrayList(new Token.TokenType("none")));
        return token;
    }
}

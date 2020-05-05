package cn.ganzhiqiang.searchnlp.segmentation;

import cn.ganzhiqiang.searchnlp.DictAttribute;
import cn.ganzhiqiang.searchnlp.Token;

import java.util.List;
import java.util.TreeMap;

/**
 * @author zq_gan
 * @since 2020/5/3
 **/

public interface ISegmentation {

    TreeMap<String, DictAttribute> loadDictionary();

    List<Token> segment(String text);

    /**
     * 全分词
     * @param text
     * @return
     */
    List<List<Token>> fullySegment(String text);

    /**
     * 正向最大分词
     * @param text 文本
     * @return tokens
     */
    List<Token> positiveMaximumSegment(String text);

    /**
     * 逆向最大分词
     * @param text 文本
     * @return tokens
     */
    List<Token> reverseMaximumSegment(String text);

    /**
     * 最优分词
     * @param text 文本
     * @return tokens
     */
    List<Token> optimalSegment(String text);

}

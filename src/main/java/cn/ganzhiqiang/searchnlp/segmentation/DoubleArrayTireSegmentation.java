package cn.ganzhiqiang.searchnlp.segmentation;

import cn.ganzhiqiang.searchnlp.*;
import cn.ganzhiqiang.searchnlp.tire.DoubleArrayTire;
import cn.ganzhiqiang.searchnlp.tire.IHit;
import cn.ganzhiqiang.searchnlp.util.IOUtil;
import cn.ganzhiqiang.searchnlp.util.TokenUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author zq_gan
 * @since 2020/5/3
 **/

@Component
public class DoubleArrayTireSegmentation implements ISegmentation {

    private DoubleArrayTire<DictAttribute> tire;

    @PostConstruct
    private void init() {
        tire = new DoubleArrayTire<>(loadDictionary());
    }

    @Override
    public TreeMap<String, DictAttribute> loadDictionary() {
        TreeMap<String, DictAttribute> dictionary = null;
        try {
            dictionary = IOUtil.loadDictionary("data/custom-dictionary.csv");
        } catch (IOException e) {
            Constants.logger.warning("DoubleArrayTireSegmentation loadDictionary error");
        }
        return dictionary;
    }

    @Override
    public List<Token> segment(String text) {
        List<Token> list = new ArrayList<>();
        tire.parseText(text, new IHit<DictAttribute>() {
            @Override
            public void hit(int begin, int end, DictAttribute value) {
                list.add(TokenUtil.convertToken(value, begin, end));
            }
        });
        return list;
    }

    @Override
    public List<List<Token>> fullySegment(String text) {
        return null;
    }

    @Override
    public List<Token> positiveMaximumSegment(String text) {
        return null;
    }

    @Override
    public List<Token> reverseMaximumSegment(String text) {
        return null;
    }

    @Override
    public List<Token> optimalSegment(String text) {
        return null;
    }
}

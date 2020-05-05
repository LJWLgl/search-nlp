package cn.ganzhiqiang.searchnlp.util;

import cn.ganzhiqiang.searchnlp.Token;
import cn.ganzhiqiang.searchnlp.segmentation.DoubleArrayTireSegmentation;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class TokenUtilTest {

    @Autowired
    private DoubleArrayTireSegmentation doubleArrayTireSegmentation;

    @Test
    public void text_mergeToMultipleSegments() {
        String text = "我住在上海松江区，在中国上海陆家嘴的东方明珠塔上看到了北京故宫和usa";
        List<Token> tokens =  doubleArrayTireSegmentation.segment(text);
        List<List<Token>> resultTokens = TokenUtil.mergeToMultipleSegments(text, tokens);
        Assert.assertNotNull(resultTokens);
    }
}
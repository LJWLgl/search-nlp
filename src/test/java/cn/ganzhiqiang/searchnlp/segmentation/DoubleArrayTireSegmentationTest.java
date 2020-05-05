package cn.ganzhiqiang.searchnlp.segmentation;

import cn.ganzhiqiang.searchnlp.Token;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
class DoubleArrayTireSegmentationTest {

    @Autowired
    private DoubleArrayTireSegmentation doubleArrayTireSegmentation;

    @Test
    public void test_segment() {
        String text = "我住在上海松江区，在中国上海陆家嘴的东方明珠塔上看到了北京故宫和usa";
        List<Token> tokens =  doubleArrayTireSegmentation.segment(text);
        System.out.println(text.substring(1, 3));
        System.out.println(text.substring(2, 4));;
        Assert.assertNotNull(tokens);
    }

}
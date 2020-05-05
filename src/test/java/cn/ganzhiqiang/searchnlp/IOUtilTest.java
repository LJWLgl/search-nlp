package cn.ganzhiqiang.searchnlp;

import cn.ganzhiqiang.searchnlp.util.IOUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.TreeMap;


class IOUtilTest {

    @Test
    public void text_loadDictionary() throws IOException {
        TreeMap<String, DictAttribute> dict  =  IOUtil.loadDictionary("data/custom-dictionary.csv");
        System.out.println(dict.size());
        Assert.assertNotNull(dict);
    }

}
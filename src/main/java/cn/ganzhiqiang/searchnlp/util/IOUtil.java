package cn.ganzhiqiang.searchnlp.util;

import cn.ganzhiqiang.searchnlp.DictAttribute;
import com.google.common.collect.Lists;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.TreeMap;

/**
 * @author zq_gan
 * @since 2020/5/3
 **/

public class IOUtil {

    public static TreeMap<String, DictAttribute> loadDictionary(String path) throws IOException {
        TreeMap<String, DictAttribute> map = new TreeMap<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
        String line;
        while ((line = reader.readLine()) != null){
            String[] params = line.split(",");
            if (map.get(params[0]) == null) {
                DictAttribute dictAttribute = new DictAttribute();
                dictAttribute.setText(params[0]);
                DictAttribute.Attribute attribute = new DictAttribute.Attribute();
                attribute.setType(params[1]);
                attribute.setNature(params[2]);
                attribute.setFrequency(Integer.parseInt(params[3]));
                attribute.setBusinessId(Long.parseLong(params[4]));
                dictAttribute.setAttributes(Lists.newArrayList(attribute));
                map.put(dictAttribute.getText(), dictAttribute);
            } else {
                List<DictAttribute.Attribute> attributes = map.get(params[0]).getAttributes();
                DictAttribute.Attribute attribute = new DictAttribute.Attribute();
                attribute.setType(params[1]);
                attribute.setNature(params[2]);
                attribute.setFrequency(Integer.parseInt(params[3]));
                attribute.setBusinessId(Long.parseLong(params[4]));
                attributes.add(attribute);
            }
        }
        return map;
    }

}

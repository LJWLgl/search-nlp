package cn.ganzhiqiang.searchnlp.tire;

import java.util.List;
import java.util.TreeMap;

/**
 * @author zq_gan
 * @since 2020/4/21
 **/

public interface ITire<V> {

    boolean build(TreeMap<String, V> dictionary);

    V get(String keyword);

    boolean containKey(String keyword);

    int size();

}

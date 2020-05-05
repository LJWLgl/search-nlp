package cn.ganzhiqiang.searchnlp.tire;

/**
 * @author zq_gan
 * @since 2020/5/3
 **/

public interface IHit<V> {

    void hit(int begin, int end, V value);
}

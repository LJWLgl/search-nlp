package cn.ganzhiqiang.searchnlp.tire;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author zq_gan
 * @since 2020/4/25
 **/

public class DoubleArrayTire<V> implements ITire<V> {

    private final int ARRAY_SIZE = 655350;
    private final int BASE_ROOT = 1;
    //根据状态转移公式：|base[start]|+code=end,可知当code>0时,|base[start]| < end
    //即： -end < base[start] < end，其中由于 base 数组中 0 作为根节点，则 end 最小值可取为 1
    //当 end 取 1 时则 base[start] = 0，则 code+1=1 得 code = 0，这和已知条件矛盾,因此可知
    //base数组中，闲置位置默认值可设置为: 0
    private final int BASE_NULL = 0;
    private final int CHECK_ROOT = -1;
    private final int CHECK_NULL = -2;
    private TrieNode[] base;
    private int check[];
    private V[] values;
    private int vl; // 标识值数组使用长度


    @Override
    public V get(String keyword) {
        return null;
    }

    @Override
    public boolean containKey(String keyword) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    public class TrieNode {
        private int transferRatio; //转移基数
        private boolean isLeaf = false; //是否为叶子节点
        private int vIndex; //当该节点为叶子节点时关联的字典表中对应词条的索引号

        public int getTransferRatio() {
            return transferRatio;
        }

        public void setTransferRatio(int transferRatio) {
            this.transferRatio = transferRatio;
        }

        public boolean isLeaf() {
            return isLeaf;
        }

        public void setLeaf(boolean leaf) {
            isLeaf = leaf;
        }

        public int getVIndex() {
            return vIndex;
        }

        public void setVIndex(int vIndex) {
            this.vIndex = vIndex;
        }
    }

    public DoubleArrayTire(TreeMap<String, V> dictionary) {
        build(dictionary);
    }

    @Override
    public boolean build(TreeMap<String, V> dictionary) {
        init(dictionary.size());

        List<String> words = new ArrayList<>(dictionary.keySet());
        int pos = 0;
        for (int c = 0; c < words.size(); c++) {
            for (int idx = 0; idx < words.size(); idx++) {
                char chars[] = words.get(idx).toCharArray();
                if (chars.length > pos) {
                    int startState = 0;
                    for (int i = 0; i <= pos-1; i++) {
                        startState = transfer(startState, getCode(chars[i]));
                    }
                    boolean isLeaf = chars.length == pos + 1;
                    V v = isLeaf ? dictionary.get(words.get(idx)) : null;
                    insert(startState, getCode(chars[pos]), isLeaf, v);
                }
            }
            pos++;
        }
        return true;
    }

    public void parseText(String text, IHit<V> processor) {
        char chars[] = text.toCharArray();
        int startState, endState;
        for (int i = 0; i < chars.length; i++) {
            startState = 0;
            for (int j = i + 1; j < chars.length; j++) {
                endState = transfer(startState, getCode(chars[j]));
                if (base[endState].getTransferRatio() != BASE_NULL && check[endState] == startState) {
                    if (base[endState].isLeaf()) {
                        processor.hit(i + 1, j + 1, values[base[endState].getVIndex()]);
                    }
                    startState = endState;
                } else {
                    break;
                }
            }
        }
    }

    /**
     * 根据起始状态和转移基数插入新节点并返回插入的节点
     *
     * @param startState 起始状态
     * @param offset  状态偏移量
     * @param isLeaf  是否为叶子节点
     * @return
     */
    private void insert(int startState, int offset, boolean isLeaf, V v) {
        int endState = transfer(startState, offset); //状态转移

        if (base[endState].getTransferRatio() != BASE_NULL && check[endState] != startState) { //已被占用
            do {
                endState += 1;
            } while (base[endState].getTransferRatio() != BASE_NULL);

            base[startState].setTransferRatio(endState - offset); //改变父节点转移基数
        }

        if (isLeaf) {
            base[endState].setTransferRatio(Math.abs(base[startState].getTransferRatio())*-1); //叶子节点转移基数标识为父节点转移基数的相反数
            base[endState].setLeaf(true);
            base[endState].setVIndex(vl); //为叶子节点时需要记录下该词在字典中的索引号
            values[vl++] = v;
        } else {
            if (base[endState].getTransferRatio() == BASE_NULL) { //未有节点经过
                base[endState].setTransferRatio(Math.abs(base[startState].getTransferRatio())); //非叶子节点的转移基数一定为正
            }
        }
        check[endState] = startState; //check中记录当前状态的父状态
    }

    /**
     * 根据起始状态和转移基数返回结束状态
     *
     * @param startState 起始状态
     * @param offset 转移基数
     * @return
     */
    private int transfer(int startState, int offset) {
        return Math.abs(base[startState].getTransferRatio())+offset; //状态转移
    }

    private int getCode(char c) {
        return (int)c;//这里必须大于0
    }

    private void init(int size) {
        base = new DoubleArrayTire.TrieNode[ARRAY_SIZE];
        check = new int[ARRAY_SIZE];

        for (int i = 0; i < ARRAY_SIZE; i++) {
            TrieNode node = new TrieNode();
            node.setTransferRatio(BASE_NULL);
            base[i] = node;
            check[i] = CHECK_NULL;
        }

        TrieNode root = new TrieNode();
        root.setTransferRatio(BASE_ROOT);
        base[0] = root;
        check[0] = CHECK_ROOT;
        values = (V[])new Object[size];
        vl = 0;
    }

}

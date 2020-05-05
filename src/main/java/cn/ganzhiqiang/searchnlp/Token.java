package cn.ganzhiqiang.searchnlp;

import java.util.List;

/**
 * @author zq_gan
 * @since 2020/5/3
 **/

public class Token {

    private String token;
    private int startOffset;
    private int endOffset;
    private List<TokenType> types;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(int startOffset) {
        this.startOffset = startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    public void setEndOffset(int endOffset) {
        this.endOffset = endOffset;
    }

    public List<TokenType> getTypes() {
        return types;
    }

    public void setTypes(List<TokenType> types) {
        this.types = types;
    }

    public static class TokenType {
        // 类别
        private String type;
        // 词性
        private String nature;
        private int frequency;
        private long businessId;


        public TokenType() {
        }

        public TokenType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNature() {
            return nature;
        }

        public void setNature(String nature) {
            this.nature = nature;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public long getBusinessId() {
            return businessId;
        }

        public void setBusinessId(long businessId) {
            this.businessId = businessId;
        }
    }

}

package cn.ganzhiqiang.searchnlp;

import java.util.List;

/**
 * @author zq_gan
 * @since 2020/5/3
 **/

public class DictAttribute {

    private String text;
    private List<Attribute> attributes;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public static class Attribute {
        private String type;
        private String nature;
        private Integer frequency;
        private Long businessId;


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

        public Integer getFrequency() {
            return frequency;
        }

        public void setFrequency(Integer frequency) {
            this.frequency = frequency;
        }

        public Long getBusinessId() {
            return businessId;
        }

        public void setBusinessId(Long businessId) {
            this.businessId = businessId;
        }
    }

}

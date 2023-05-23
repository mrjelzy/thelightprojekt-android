package com.thelightprojekt.model.data.order;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "prestashop")
public class OrderState {
    @Element(name = "order_state")
    private State orderState;

    public State getOrderState() {
        return orderState;
    }

    public void setOrderState(State orderState) {
        this.orderState = orderState;
    }

    @Root(name = "order_state")
    public static class State {
        @Element(name = "name")
        private Name name;

        public Name getName() {
            return name;
        }

        public void setName(Name name) {
            this.name = name;
        }
    }

    @Root(name = "name")
    public static class Name {
        @Element(name = "language")
        private Language language;

        public Language getLanguage() {
            return language;
        }

        public void setLanguage(Language language) {
            this.language = language;
        }
    }

    @Root(name = "language")
    public static class Language {
        @Attribute(name = "id")
        private int id;

        @Attribute(name = "href")
        private String href;

        @Text
        private String value;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getXlinkHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}


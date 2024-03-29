package com.sample.test.demo.utils;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class Pizza {

    private String item  = "";
    private String pizza = "";
    private List<String> toppings;

    public Pizza setItem(String item) {
        this.item = item;
        return this;
    }

    public String getItem() {
        return item;
    }

    public Pizza setPizza(String pizza) {
        this.pizza = pizza;
        return this;
    }

    public String getPizza() {
        return pizza;
    }

    public Pizza setToppings(List<String> toppings) {
        this.toppings = toppings;
        return this;
    }

    public List<String> getToppings() {
        return toppings;
    }

}

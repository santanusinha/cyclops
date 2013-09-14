package com.cyclops.common;

import java.util.ArrayList;
import java.util.Random;

/**
 * User: Santanu Sinha (santanu.sinha@flipkart.com)
 * Date: 14/09/13
 * Time: 5:09 PM
 */
public class RandomIndexSelector<T> {
    private ArrayList<T> elements;
    private int max;
    private Random randomGenerator;

    public RandomIndexSelector(ArrayList<T> elements) {
        this.elements = elements;
        this.max = elements.size();
        this.randomGenerator = new Random();
    }

    public T get() {
        return elements.get(randomGenerator.nextInt(max));
    }
}

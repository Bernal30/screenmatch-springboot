package com.company.screenmatch.Main;

import java.util.Arrays;
import java.util.List;

public class StreamExample {

    public void showExample() {
        List<String> names = Arrays.asList("Paco", "Jorge", "Maria", "Paola", "Ruben", "Eduardo");

        //el método stream permite hacer acciones encadenadas sobre un Array
        names.stream()
                .sorted()
                .filter(n -> n.startsWith("P"))
                .map(n -> n.toUpperCase())
                .limit(3)
                .forEach(System.out::println);
    }
}

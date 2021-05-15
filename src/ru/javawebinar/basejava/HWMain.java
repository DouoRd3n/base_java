package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class HWMain {
    public static void main(String[] args) {
        int[] i = {9,8,9,8,9,8};
        int i1 = minValue(i);
        System.out.println(i1);
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> list1 = oddOrEven(list);
        list1.forEach(System.out::println);
    }
    public static int minValue(int[] values){
        String result = "";

        String collect = Arrays.stream(values)
                .distinct()
                .sorted()
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
        return Integer.parseInt(collect);
    }

    public static List<Integer> oddOrEven(List<Integer> integers){
        AtomicInteger summ = new AtomicInteger();
        List<Integer> collect = integers.stream()
                .peek((i) -> summ.set(summ.get() + i))
                .sorted()
                .filter((i) -> (i % 2 == 0) == (summ.get() % 2 == 0))
                .collect(Collectors.toList());
        return collect;
    }
}

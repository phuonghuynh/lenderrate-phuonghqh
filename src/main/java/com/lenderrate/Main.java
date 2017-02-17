package com.lenderrate;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

  public static int nextInt(int[] array, int lastIndex) {
    if (lastIndex >= array.length) {
      return array[array.length - 1];
    }
    else if (lastIndex < 0) {
      return array[0];
    }
    return array[lastIndex];
  }

  public static int sum(List<Integer> ls) {
    return ls.stream().collect(Collectors.summingInt(Integer::intValue));
  }

  private static List<Integer> toInts(String s) {
    return Arrays.stream(s.split(" ")).map(Integer::parseInt).collect(Collectors.toList());
  }

  private static String toString(List<Integer> selectedList) {
    return selectedList.stream().map(v -> v.toString()).collect(Collectors.joining(" "));
  }

  public static void main(String[] args) {
    int[][] arrays = new int[][]{
      {5, 4, 3, 2, 1},
      {4, 1},
      {5, 0, 0},
      {6, 4, 2},
      {1},
    };
    int N = 5;

    Arrays.sort(arrays, (a1, a2) -> {
      int rs = a2[0] - a1[0];
      if (rs == 0) {
        rs = a2.length - a1.length;
      }
      return rs;
    });

    List<String> result = new ArrayList<>();
    for (int i = 0; i < N; i++) {//init
      result.add(null);
    }
    List<Integer> firstList = Arrays.stream(arrays).map(array -> array[0]).collect(Collectors.toList());
    result.set(0, toString(firstList));

    int[] lastIndexes = new int[arrays.length];
    for (int i = 0; i < lastIndexes.length; i++) { // init
      lastIndexes[i] = 0;
    }

    int Ni = 2;
    while (Ni <= N) {
      int sum = 0;
      int NiIndex = Ni - 1;

      for (int i = 0; i < arrays.length; i++) {
        int[] array = arrays[i];
        for (String lastResult : result) {
          if (lastResult == null) {
            break;
          }

          List<Integer> lastInts = toInts(lastResult);
          for (int j = 0; j <= NiIndex; j++) {
            int nextValue = nextInt(array, j);
            lastInts.set(i, nextValue);
            Integer newSum = lastInts.stream().collect(Collectors.summingInt(Integer::intValue));
            if (sum <= newSum) {
              String selectedArrayString = toString(lastInts);
              if (!result.contains(selectedArrayString)) {
                if (sum == newSum) {
                  Ni++;
                }
                result.set(Math.min(Ni - 1, N - 1), toString(lastInts));//yeah might be a result
                sum = newSum;
              }
            }
          }
        }
      }
      Ni++;
    }

    System.out.println("Combinations:");
    System.out.println(result);
    System.out.println("Sums:");
    result.forEach(s -> {
      String[] ints = s.split(" ");
      System.out.print(Arrays.stream(ints).mapToInt(Integer::parseInt).sum() + " ");
    });
  }
}

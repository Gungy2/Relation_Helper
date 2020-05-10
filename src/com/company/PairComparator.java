package com.company;

import java.util.Comparator;

public class PairComparator<T extends Comparable<T>> implements Comparator<Pair<T, T>> {

  @Override
  public int compare(Pair<T, T> pair1, Pair<T, T> pair2) {
    int firstCompare = pair1.getKey().compareTo(pair2.getKey());
    if (firstCompare != 0) {
      return firstCompare;
    }
    return pair1.getValue().compareTo(pair2.getValue());
  }

}

package com.company;

import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Relation<T extends Comparable<T>> {

  private Set<Pair<T, T>> elements;

  public Relation() {
    elements = new TreeSet<>(new PairComparator<>());
  }

  public Relation(Set<Pair<T, T>> elements) {
    this.elements = new TreeSet<>(new PairComparator<>());
    this.elements.addAll(elements);
  }

  public boolean addElements(T element1, T element2) {
    return elements.add(new Pair<>(element1, element2));
  }

  public Set<Pair<T, T>> getElements() {
    return elements;
  }

  public Relation<T> union(Relation<T> relation) {
    Set<Pair<T, T>> newElements = new HashSet<>();
    newElements.addAll(elements);
    newElements.addAll(relation.getElements());
    return new Relation<>(newElements);
  }

  public Relation<T> compose(Relation<T> relation) {
    Relation<T> newRelation = new Relation<>();
    for (Pair<T, T> firstPair : elements) {
      for (Pair<T, T> secondPair : relation.getElements()) {
        if (firstPair.getValue().equals(secondPair.getKey())) {
          newRelation.addElements(firstPair.getKey(), secondPair.getValue());
        }
      }
    }
    return newRelation;
  }

  public Relation<T> computeClosure() {
    Relation<T> closure = new Relation<>(elements);
    Relation<T> prev = this;
    for (int i = 0; i < elements.size() - 1; i++) {
      prev = compose(prev);
      closure = closure.union(prev);
    }
    return closure;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append('{');
    for (Pair<T, T> pair : elements) {
      sb.append('<');
      sb.append(pair.getKey());
      sb.append(", ");
      sb.append(pair.getValue());
      sb.append(">, ");
    }
    if (!elements.isEmpty()) {
      sb.delete(sb.length() - 2, sb.length());
    }
    sb.append('}');
    return sb.toString();
  }
}

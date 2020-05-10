package com.company;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Relation<T extends Comparable<T>> {

  private final Set<Pair<T, T>> elements;

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
    Set<Pair<T, T>> newElements = new TreeSet<>(new PairComparator<>());
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

  public Relation<T> power(int n) {
    Relation<T> prev = new Relation<>(elements);
    for (int i = 1; i < n; i++) {
      prev = compose(prev);
    }
    return prev;
  }

  public Relation<T> computeClosure() {
    Relation<T> closure = new Relation<>(elements);
    Relation<T> prev = new Relation<>(elements);
    for (int i = 0; i < elements.size() - 1; i++) {
      prev = compose(prev);
      closure = closure.union(prev);
    }
    return closure;
  }

  public Relation<T> inverse() {
    Relation<T> relation = new Relation<>();
    for (Pair<T, T> pair : elements) {
      relation.addElements(pair.getValue(), pair.getKey());
    }
    return relation;
  }

  public boolean isSymmetric() {
    return equals(inverse());
  }

  public boolean isTransitive() {
    return contains(compose(this));
  }

  public boolean isReflexive() {
    for (T element : makeSet()) {
      if (!elements.contains(new Pair<>(element, element))) {
        return false;
      }
    }
    return true;
  }

  public Set<T> makeSet() {
    Set<T> set = new TreeSet<>();
    for (Pair<T, T> pair : elements) {
      set.add(pair.getKey());
      set.add(pair.getValue());
    }
    return set;
  }

  private boolean contains(Relation<T> relation) {
    for (Pair<T, T> pair : relation.getElements()) {
      if (!elements.contains(pair)) {
        return false;
      }
    }
    return true;
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

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Relation)) return false;
    Relation<?> relation = (Relation<?>) o;
    return elements.equals(relation.getElements());
  }

  @Override
  public int hashCode() {
    return Objects.hash(elements);
  }
}

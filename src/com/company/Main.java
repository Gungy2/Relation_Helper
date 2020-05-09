package com.company;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    System.out.print("Introduce number of pairs: ");
    Relation<Integer> relation = new Relation<>();
    int n = in.nextInt();
    for (int i = 1; i <= n; i++) {
      String suffix;
      if (i % 100 >= 10 && i % 100 <= 20) {
        suffix = "th";
      } else {
        switch (i % 10) {
          case 1:
            suffix = "st";
            break;
          case 2:
            suffix = "nd";
            break;
          case 3:
            suffix = "rd";
            break;
          default:
            suffix = "th";
        }
      }
      System.out.print("Introduce the " + i + suffix + " pair: ");
      relation.addElements(in.nextInt(), in.nextInt());
    }
    System.out.println("Your relation: " + relation);
    System.out.println("Transitive Closure: " + relation.computeClosure());
  }
}

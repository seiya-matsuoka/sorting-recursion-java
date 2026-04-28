package io.github.seiya_matsuoka.sortingrecursion.algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * 再帰の基本を確認するための実装
 *
 * <p>このクラスでは、再帰そのものの流れを見やすくするために カウントダウン、階乗、1 から n までの合計を題材として扱う。
 */
public class RecursionBasics {

  /** カウントダウン結果 */
  public record CountDownResult(List<Integer> visitedValues, long callCount, long elapsedNanos) {}

  /** 階乗結果 */
  public record FactorialResult(long value, long callCount, long elapsedNanos) {}

  /** 再帰合計結果 */
  public record SumResult(long value, long callCount, long elapsedNanos) {}

  /**
   * 呼び出し回数を数えるための補助クラス
   *
   * <p>学習の本体ではなく、呼び出し回数表示のための補助用途。
   */
  private static final class CallCounter {
    private long count;

    void increment() {
      count++;
    }

    long count() {
      return count;
    }
  }

  /**
   * n から 1 までカウントダウンする。
   *
   * <p>自分自身を呼び出しながら、ベースケースに到達する流れを確認する題材。
   */
  public static CountDownResult countDown(int start, boolean trace) {
    List<Integer> visitedValues = new ArrayList<>();
    CallCounter counter = new CallCounter();

    long begin = System.nanoTime();
    countDownRecursive(start, visitedValues, counter, trace);
    long end = System.nanoTime();

    return new CountDownResult(visitedValues, counter.count(), end - begin);
  }

  /**
   * 階乗を再帰で求める。
   *
   * <p>戻りがけに値を組み立てる典型例として扱う。
   */
  public static FactorialResult factorial(int n, boolean trace) {
    CallCounter counter = new CallCounter();

    long begin = System.nanoTime();
    long value = factorialRecursive(n, counter, trace);
    long end = System.nanoTime();

    return new FactorialResult(value, counter.count(), end - begin);
  }

  /**
   * 1 から n までの合計を再帰で求める。
   *
   * <p>1 段ずつ問題を小さくし、戻りがけに足し合わせる流れを確認する題材。
   */
  public static SumResult sumFromOneToN(int n, boolean trace) {
    CallCounter counter = new CallCounter();

    long begin = System.nanoTime();
    long value = sumRecursive(n, counter, trace);
    long end = System.nanoTime();

    return new SumResult(value, counter.count(), end - begin);
  }

  /**
   * カウントダウンの本体処理
   *
   * <p>ここが再帰の学習で見るべき本体。
   *
   * <p>現在値を記録し、ベースケースで止まり、そうでなければ次の値で自分を呼ぶ。
   */
  private static void countDownRecursive(
      int current, List<Integer> visitedValues, CallCounter counter, boolean trace) {
    counter.increment();

    if (trace) {
      System.out.println("countDown に入る: current=" + current);
    }

    visitedValues.add(current);

    // ここがベースケース
    if (current <= 1) {
      if (trace) {
        System.out.println("ベースケースに到達したため、ここで呼び出しを終了する。");
      }
      return;
    }

    // ここが再帰呼び出し
    countDownRecursive(current - 1, visitedValues, counter, trace);

    if (trace) {
      System.out.println("countDown から戻る: current=" + current);
    }
  }

  /**
   * 階乗の本体処理
   *
   * <p>ここが再帰の学習で見るべき本体。
   *
   * <p>n * factorial(n - 1) という形で、自分より小さい問題の結果を使って値を作る。
   */
  private static long factorialRecursive(int n, CallCounter counter, boolean trace) {
    counter.increment();

    if (trace) {
      System.out.println("factorial に入る: n=" + n);
    }

    // ここがベースケース
    if (n <= 1) {
      if (trace) {
        System.out.println("ベースケースに到達したため、1 を返す。");
      }
      return 1;
    }

    // ここが再帰呼び出し
    long subResult = factorialRecursive(n - 1, counter, trace);
    long result = n * subResult;

    if (trace) {
      System.out.println("戻りがけに計算する: " + n + " * " + subResult + " = " + result);
    }

    return result;
  }

  /**
   * 1 から n までの合計の本体処理
   *
   * <p>factorial と同様に、今の値と小さい問題の結果を組み合わせる。
   */
  private static long sumRecursive(int n, CallCounter counter, boolean trace) {
    counter.increment();

    if (trace) {
      System.out.println("sum に入る: n=" + n);
    }

    // ここがベースケース
    if (n <= 1) {
      if (trace) {
        System.out.println("ベースケースに到達したため、1 を返す。");
      }
      return 1;
    }

    // ここが再帰呼び出し
    long subResult = sumRecursive(n - 1, counter, trace);
    long result = n + subResult;

    if (trace) {
      System.out.println("戻りがけに計算する: " + n + " + " + subResult + " = " + result);
    }

    return result;
  }

  private RecursionBasics() {}
}

package io.github.seiya_matsuoka.sortingrecursion.algorithms;

import java.util.Arrays;

/**
 * 分割統治の基本を確認するための実装
 *
 * <p>配列を半分ずつ分け、小さい問題を解いてから結果を統合する流れを扱う。
 */
public class DivideAndConquerBasics {

  /** 合計結果 */
  public record SumResult(int[] sourceArray, long value, long callCount, long elapsedNanos) {}

  /** 最大値結果 */
  public record MaxResult(int[] sourceArray, int value, long callCount, long elapsedNanos) {}

  /** 呼び出し回数を数えるための補助クラス */
  private static final class CallCounter {
    private long count;

    void increment() {
      count++;
    }

    long count() {
      return count;
    }
  }

  /** 配列合計を分割統治で求める。 */
  public static SumResult sum(int[] source, boolean trace) {
    int[] values = Arrays.copyOf(source, source.length);
    CallCounter counter = new CallCounter();

    long begin = System.nanoTime();
    long value = sumRecursive(values, 0, values.length - 1, counter, trace);
    long end = System.nanoTime();

    return new SumResult(values, value, counter.count(), end - begin);
  }

  /** 配列最大値を分割統治で求める。 */
  public static MaxResult max(int[] source, boolean trace) {
    int[] values = Arrays.copyOf(source, source.length);
    CallCounter counter = new CallCounter();

    long begin = System.nanoTime();
    int value = maxRecursive(values, 0, values.length - 1, counter, trace);
    long end = System.nanoTime();

    return new MaxResult(values, value, counter.count(), end - begin);
  }

  /**
   * 合計計算の本体処理
   *
   * <p>ここが分割統治の学習で見るべき本体。
   *
   * <p>区間を半分に分け、左右の部分問題を解いてから足し合わせる。
   */
  private static long sumRecursive(
      int[] values, int left, int right, CallCounter counter, boolean trace) {
    counter.increment();

    if (trace) {
      System.out.println("sum 区間に入る: [" + left + ", " + right + "]");
    }

    // ここがベースケース
    if (left == right) {
      if (trace) {
        System.out.println("要素が 1 つになったため、その値を返す: " + values[left]);
      }
      return values[left];
    }

    int mid = left + (right - left) / 2;

    if (trace) {
      System.out.println(
          "区間を分割する: [" + left + ", " + mid + "] と [" + (mid + 1) + ", " + right + "]");
    }

    // ここが分割統治のコア処理
    long leftValue = sumRecursive(values, left, mid, counter, trace);
    long rightValue = sumRecursive(values, mid + 1, right, counter, trace);
    long result = leftValue + rightValue;

    if (trace) {
      System.out.println("左右の結果を統合する: " + leftValue + " + " + rightValue + " = " + result);
    }

    return result;
  }

  /**
   * 最大値探索の本体処理
   *
   * <p>左右の部分問題を解き、最後に大きい方を採用する。
   */
  private static int maxRecursive(
      int[] values, int left, int right, CallCounter counter, boolean trace) {
    counter.increment();

    if (trace) {
      System.out.println("max 区間に入る: [" + left + ", " + right + "]");
    }

    // ここがベースケース
    if (left == right) {
      if (trace) {
        System.out.println("要素が 1 つになったため、その値を返す: " + values[left]);
      }
      return values[left];
    }

    int mid = left + (right - left) / 2;

    if (trace) {
      System.out.println(
          "区間を分割する: [" + left + ", " + mid + "] と [" + (mid + 1) + ", " + right + "]");
    }

    // ここが分割統治のコア処理
    int leftValue = maxRecursive(values, left, mid, counter, trace);
    int rightValue = maxRecursive(values, mid + 1, right, counter, trace);
    int result = Math.max(leftValue, rightValue);

    if (trace) {
      System.out.println("左右の結果を統合する: max(" + leftValue + ", " + rightValue + ") = " + result);
    }

    return result;
  }

  private DivideAndConquerBasics() {}
}

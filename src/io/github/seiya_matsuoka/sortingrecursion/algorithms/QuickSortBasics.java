package io.github.seiya_matsuoka.sortingrecursion.algorithms;

import java.util.Arrays;

/** クイックソートの基本実装 */
public class QuickSortBasics {

  /** クイックソートの結果をまとめて返すためのレコード */
  public record SortResult(
      int[] sortedArray, long comparisonCount, long swapCount, long elapsedNanos) {}

  /**
   * クイックソートを行う。
   *
   * <p>学習の主題は、pivot を 1 つ決め、その値以下とそれより大きい値へ区切りながら 配列を並べ替えていく流れにある。
   *
   * @param source 元の配列
   * @param trace 途中経過を表示するかどうか
   * @return ソート結果
   */
  public static SortResult sort(int[] source, boolean trace) {
    // 元配列をそのまま壊さずに学習できるよう、作業用配列を複製する。
    int[] values = Arrays.copyOf(source, source.length);

    Counter counter = new Counter();
    long start = System.nanoTime();

    if (values.length > 0) {
      quickSort(values, 0, values.length - 1, trace, 0, counter);
    }

    long end = System.nanoTime();
    return new SortResult(values, counter.comparisonCount, counter.swapCount, end - start);
  }

  /**
   * pivot を確定させながら左右の区間を再帰的に整列する。
   *
   * <p>ここがクイックソートの本体の前半。
   *
   * <p>現在の区間を partition で左右へ分け、pivot より左と右を再帰的に整列する。
   *
   * @param values 整列対象の配列
   * @param leftStart 現在見ている区間の左端 index
   * @param rightEnd 現在見ている区間の右端 index
   * @param trace 途中経過を表示するかどうか
   * @param depth trace 表示時のインデント深さ
   * @param counter 比較回数と交換回数を数えるための補助
   */
  private static void quickSort(
      int[] values, int leftStart, int rightEnd, boolean trace, int depth, Counter counter) {
    // 区間の長さが 1 以下なら、それ以上分割せずに戻る。
    if (leftStart >= rightEnd) {
      if (trace) {
        printTrace(depth, "分割終了: [" + leftStart + ", " + rightEnd + "]");
      }
      return;
    }

    if (trace) {
      printTrace(
          depth,
          "partition する区間: "
              + Arrays.toString(Arrays.copyOfRange(values, leftStart, rightEnd + 1)));
    }

    // ここがクイックソートの本体の後半へつながる処理。
    // partition によって pivot の確定位置を求める。
    int finalPivotIndex = partition(values, leftStart, rightEnd, trace, depth, counter);

    // pivot より左側と右側を、それぞれ再帰的に整列する。
    quickSort(values, leftStart, finalPivotIndex - 1, trace, depth + 1, counter);
    quickSort(values, finalPivotIndex + 1, rightEnd, trace, depth + 1, counter);
  }

  /**
   * pivot を基準に区間を組み替える。
   *
   * <p>ここがクイックソートのコア処理。
   *
   * <p>今回は右端の値を pivot とし、pivot 以下の値を左側へ集める方式を採用する。
   *
   * @param values 整列対象の配列
   * @param leftStart 現在見ている区間の左端 index
   * @param rightEnd 現在見ている区間の右端 index。ここの値を pivot とする
   * @param trace 途中経過を表示するかどうか
   * @param depth trace 表示時のインデント深さ
   * @param counter 比較回数と交換回数を数えるための補助
   * @return pivot が最終的に確定した index
   */
  private static int partition(
      int[] values, int leftStart, int rightEnd, boolean trace, int depth, Counter counter) {
    // 右端の値を今回の基準値として使う。
    int pivotValue = values[rightEnd];

    // pivot 以下の値を次に置く位置
    int nextSmallValueIndex = leftStart;

    if (trace) {
      printTrace(depth, "pivot の値: " + pivotValue + " (index=" + rightEnd + ")");
    }

    // leftStart から rightEnd - 1 まで順に見ていき、
    // pivot 以下なら左側グループへ入れる。
    for (int scanIndex = leftStart; scanIndex < rightEnd; scanIndex++) {
      counter.comparisonCount++;

      if (trace) {
        printTrace(
            depth,
            "  比較する値: values[" + scanIndex + "]=" + values[scanIndex] + " / pivot=" + pivotValue);
      }

      if (values[scanIndex] <= pivotValue) {
        swap(values, nextSmallValueIndex, scanIndex, counter);

        if (trace) {
          printTrace(depth, "  pivot 以下なので左側グループへ移動する");
          printTrace(
              depth,
              "  現在の区間: " + Arrays.toString(Arrays.copyOfRange(values, leftStart, rightEnd + 1)));
        }

        nextSmallValueIndex++;
      }
    }

    // 最後に pivot を境界位置へ移動し、左右の区間を確定させる。
    swap(values, nextSmallValueIndex, rightEnd, counter);

    if (trace) {
      printTrace(depth, "pivot を確定位置へ移動する");
      printTrace(
          depth,
          "pivot 確定後の区間: " + Arrays.toString(Arrays.copyOfRange(values, leftStart, rightEnd + 1)));
      printTrace(depth, "pivot の確定位置: index=" + nextSmallValueIndex);
    }

    return nextSmallValueIndex;
  }

  /**
   * 2 つの要素を交換する補助処理
   *
   * <p>アルゴリズム本体ではなく、交換を共通化するための補助用途。
   */
  private static void swap(int[] values, int leftIndex, int rightIndex, Counter counter) {
    if (leftIndex == rightIndex) {
      return;
    }

    int temp = values[leftIndex];
    values[leftIndex] = values[rightIndex];
    values[rightIndex] = temp;
    counter.swapCount++;
  }

  /** trace 用の表示処理 */
  private static void printTrace(int depth, String message) {
    System.out.println("  ".repeat(depth) + message);
  }

  /** 比較回数と交換回数を数えるための補助クラス */
  private static class Counter {
    private long comparisonCount;
    private long swapCount;
  }

  private QuickSortBasics() {}
}

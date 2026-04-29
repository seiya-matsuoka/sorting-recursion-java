package io.github.seiya_matsuoka.sortingrecursion.algorithms;

import java.util.Arrays;

/** マージソートの基本実装 */
public class MergeSortBasics {

  /** マージソートの結果をまとめて返すためのレコード */
  public record SortResult(
      int[] sortedArray, long comparisonCount, long writeCount, long elapsedNanos) {}

  /**
   * マージソートを行う。
   *
   * <p>学習の主題は、配列を半分へ分け、十分小さい区間まで分割した後、 ソート済み区間同士をマージして全体を整列させる流れにある。
   *
   * @param source 元の配列
   * @param trace 途中経過を表示するかどうか
   * @return ソート結果
   */
  public static SortResult sort(int[] source, boolean trace) {
    // 元配列をそのまま壊さずに学習できるよう、作業用配列を複製する。
    int[] values = Arrays.copyOf(source, source.length);

    // マージ結果を一時的に受ける作業用配列
    int[] temp = new int[values.length];

    Counter counter = new Counter();
    long start = System.nanoTime();

    if (values.length > 0) {
      mergeSort(values, temp, 0, values.length - 1, trace, 0, counter);
    }

    long end = System.nanoTime();
    return new SortResult(values, counter.comparisonCount, counter.writeCount, end - start);
  }

  /**
   * 区間を半分へ分けながら再帰的に整列する。
   *
   * <p>ここがマージソートの本体の前半。
   *
   * <p>現在見ている区間を左半分と右半分へ分割し、 それぞれを再帰的に整列した後で merge を呼び出す。
   *
   * @param values 整列対象の配列
   * @param temp マージ時の一時配列
   * @param leftStart 現在見ている区間の左端 index
   * @param rightEnd 現在見ている区間の右端 index
   * @param trace 途中経過を表示するかどうか
   * @param depth trace 表示時のインデント深さ
   * @param counter 比較回数と書き込み回数を数えるための補助
   */
  private static void mergeSort(
      int[] values,
      int[] temp,
      int leftStart,
      int rightEnd,
      boolean trace,
      int depth,
      Counter counter) {
    // 区間の長さが 1 以下なら、それ以上分割せずに戻る。
    if (leftStart >= rightEnd) {
      if (trace) {
        printTrace(depth, "分割終了: [" + leftStart + ", " + rightEnd + "]");
      }
      return;
    }

    // 現在の区間を左右へ分ける境界 index
    int middleIndex = (leftStart + rightEnd) / 2;

    if (trace) {
      printTrace(
          depth, "分割する区間: " + Arrays.toString(Arrays.copyOfRange(values, leftStart, rightEnd + 1)));
      printTrace(depth, "左区間: [" + leftStart + ", " + middleIndex + "]");
      printTrace(depth, "右区間: [" + (middleIndex + 1) + ", " + rightEnd + "]");
    }

    // 左右の区間をそれぞれ整列する。
    mergeSort(values, temp, leftStart, middleIndex, trace, depth + 1, counter);
    mergeSort(values, temp, middleIndex + 1, rightEnd, trace, depth + 1, counter);

    // ここがマージソートの本体の後半。
    // 整列済みになった左右区間を 1 つへ統合する。
    merge(values, temp, leftStart, middleIndex, rightEnd, trace, depth, counter);
  }

  /**
   * 左右 2 つのソート済み区間を 1 つへ統合する。
   *
   * <p>ここがマージソートのコア処理。 左区間と右区間の先頭値を比較し、小さい方を一時配列へ書き込む。
   *
   * @param values 整列対象の配列
   * @param temp マージ時の一時配列
   * @param leftStart 左区間の先頭 index
   * @param middleIndex 左区間の末尾 index / 右区間の直前
   * @param rightEnd 右区間の末尾 index
   * @param trace 途中経過を表示するかどうか
   * @param depth trace 表示時のインデント深さ
   * @param counter 比較回数と書き込み回数を数えるための補助
   */
  private static void merge(
      int[] values,
      int[] temp,
      int leftStart,
      int middleIndex,
      int rightEnd,
      boolean trace,
      int depth,
      Counter counter) {
    int leftIndex = leftStart;
    int rightIndex = middleIndex + 1;
    int tempWriteIndex = leftStart;

    if (trace) {
      printTrace(depth, "マージ開始");
      printTrace(
          depth,
          "  左区間 : " + Arrays.toString(Arrays.copyOfRange(values, leftStart, middleIndex + 1)));
      printTrace(
          depth,
          "  右区間 : " + Arrays.toString(Arrays.copyOfRange(values, middleIndex + 1, rightEnd + 1)));
    }

    // 左右の先頭同士を比較し、小さい方を temp へ入れていく。
    while (leftIndex <= middleIndex && rightIndex <= rightEnd) {
      counter.comparisonCount++;

      if (trace) {
        printTrace(depth, "  比較する値: left=" + values[leftIndex] + " / right=" + values[rightIndex]);
      }

      if (values[leftIndex] <= values[rightIndex]) {
        temp[tempWriteIndex] = values[leftIndex];
        counter.writeCount++;

        if (trace) {
          printTrace(
              depth, "  左区間の値を採用: " + values[leftIndex] + " -> temp[" + tempWriteIndex + "]");
        }

        leftIndex++;
      } else {
        temp[tempWriteIndex] = values[rightIndex];
        counter.writeCount++;

        if (trace) {
          printTrace(
              depth, "  右区間の値を採用: " + values[rightIndex] + " -> temp[" + tempWriteIndex + "]");
        }

        rightIndex++;
      }

      tempWriteIndex++;
    }

    // 左区間に残りがあれば、そのまま後ろへ続けて入れる。
    while (leftIndex <= middleIndex) {
      temp[tempWriteIndex] = values[leftIndex];
      counter.writeCount++;

      if (trace) {
        printTrace(
            depth, "  左区間の残りをコピー: " + values[leftIndex] + " -> temp[" + tempWriteIndex + "]");
      }

      leftIndex++;
      tempWriteIndex++;
    }

    // 右区間に残りがあれば、そのまま後ろへ続けて入れる。
    while (rightIndex <= rightEnd) {
      temp[tempWriteIndex] = values[rightIndex];
      counter.writeCount++;

      if (trace) {
        printTrace(
            depth, "  右区間の残りをコピー: " + values[rightIndex] + " -> temp[" + tempWriteIndex + "]");
      }

      rightIndex++;
      tempWriteIndex++;
    }

    // temp に作ったマージ結果を元配列へ戻す。
    for (int index = leftStart; index <= rightEnd; index++) {
      values[index] = temp[index];
      counter.writeCount++;
    }

    if (trace) {
      printTrace(
          depth,
          "マージ後の区間: " + Arrays.toString(Arrays.copyOfRange(values, leftStart, rightEnd + 1)));
    }
  }

  /** trace 用の表示処理 */
  private static void printTrace(int depth, String message) {
    System.out.println("  ".repeat(depth) + message);
  }

  /** 比較回数と書き込み回数を数えるための補助クラス */
  private static class Counter {
    private long comparisonCount;
    private long writeCount;
  }

  private MergeSortBasics() {}
}

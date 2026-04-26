package io.github.seiya_matsuoka.sortingrecursion.algorithms;

import java.util.Arrays;

/** 挿入ソートの基本実装 */
public class InsertionSortBasics {

  /** 挿入ソートの結果をまとめて返すためのレコード */
  public record SortResult(
      int[] sortedArray, long comparisonCount, long swapCount, long elapsedNanos) {}

  /**
   * 挿入ソートを行う。
   *
   * <p>学習の主題は、左側の整列済み区間に対して 現在の値を適切な位置へ挿し込む流れにある。
   *
   * @param source 元の配列
   * @param trace 途中経過を表示するかどうか
   * @return ソート結果
   */
  public static SortResult sort(int[] source, boolean trace) {
    int[] values = Arrays.copyOf(source, source.length);

    long comparisonCount = 0;
    long shiftCount = 0;

    long start = System.nanoTime();

    // i より左側を整列済み区間として扱う
    for (int i = 1; i < values.length; i++) {
      int current = values[i];
      int j = i - 1;

      if (trace) {
        System.out.println("[外側ループ] index=" + i + ", 挿入対象=" + current);
      }

      // ここが挿入ソートのコア処理
      // current より大きい値を右へずらし、空いた位置へ current を入れる
      while (j >= 0) {
        comparisonCount++;

        if (trace) {
          System.out.println("  比較: values[" + j + "]=" + values[j] + " と current=" + current);
        }

        if (values[j] > current) {
          values[j + 1] = values[j];
          shiftCount++;

          if (trace) {
            System.out.println("  右へシフト: " + Arrays.toString(values));
          }

          j--;
        } else {
          break;
        }
      }

      values[j + 1] = current;

      if (trace) {
        System.out.println("  挿入後: " + Arrays.toString(values));
      }
    }

    long end = System.nanoTime();

    return new SortResult(values, comparisonCount, shiftCount, end - start);
  }

  private InsertionSortBasics() {}
}

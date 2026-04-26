package io.github.seiya_matsuoka.sortingrecursion.algorithms;

import java.util.Arrays;

/** 選択ソートの基本実装 */
public class SelectionSortBasics {

  /** 選択ソートの結果をまとめて返すためのレコード */
  public record SortResult(
      int[] sortedArray, long comparisonCount, long swapCount, long elapsedNanos) {}

  /**
   * 選択ソートを行う。
   *
   * <p>学習の主題は、未整列区間から最小値を探し、 先頭側へ確定させていく流れにある。
   *
   * @param source 元の配列
   * @param trace 途中経過を表示するかどうか
   * @return ソート結果
   */
  public static SortResult sort(int[] source, boolean trace) {
    int[] values = Arrays.copyOf(source, source.length);

    long comparisonCount = 0;
    long swapCount = 0;

    long start = System.nanoTime();

    // left より左側は整列済み、left 以降が未整列区間
    for (int left = 0; left < values.length - 1; left++) {
      int minIndex = left;

      if (trace) {
        System.out.println("[外側ループ] left=" + left + " から最小値を探す");
      }

      // ここが選択ソートのコア処理
      // 未整列区間を走査して最小値の位置を見つける
      for (int i = left + 1; i < values.length; i++) {
        comparisonCount++;

        if (trace) {
          System.out.println(
              "  比較: values[" + i + "]=" + values[i] + " と currentMin=" + values[minIndex]);
        }

        if (values[i] < values[minIndex]) {
          minIndex = i;

          if (trace) {
            System.out.println("  最小値候補を更新: index=" + minIndex + ", value=" + values[minIndex]);
          }
        }
      }

      // 最小値が先頭にない場合だけ交換する
      if (minIndex != left) {
        int temp = values[left];
        values[left] = values[minIndex];
        values[minIndex] = temp;
        swapCount++;

        if (trace) {
          System.out.println("  交換後: " + Arrays.toString(values));
        }
      } else if (trace) {
        System.out.println("  交換不要: 先頭位置がすでに最小値");
      }
    }

    long end = System.nanoTime();

    return new SortResult(values, comparisonCount, swapCount, end - start);
  }

  private SelectionSortBasics() {}
}

package io.github.seiya_matsuoka.sortingrecursion.algorithms;

import java.util.Arrays;

/** バブルソートの基本実装 */
public class BubbleSortBasics {

  /** バブルソートの結果をまとめて返すためのレコード */
  public record SortResult(
      int[] sortedArray, long comparisonCount, long swapCount, long elapsedNanos) {}

  /**
   * バブルソートを行う。
   *
   * <p>学習の主題は、隣り合う要素を比較し、必要なら交換しながら 大きい値を右端へ押し出していく流れにある。
   *
   * @param source 元の配列
   * @param trace 途中経過を表示するかどうか
   * @return ソート結果
   */
  public static SortResult sort(int[] source, boolean trace) {
    // 元配列をそのまま壊さずに学習できるよう、作業用配列を複製する。
    int[] values = Arrays.copyOf(source, source.length);

    long comparisonCount = 0;
    long swapCount = 0;

    long start = System.nanoTime();

    // 外側のループは「未確定区間」を少しずつ縮める役割
    for (int end = values.length - 1; end > 0; end--) {
      boolean swapped = false;

      if (trace) {
        System.out.println("[外側ループ] end=" + end + " の範囲まで比較する");
      }

      // ここがバブルソートのコア処理
      // 左から順に隣接要素を比較し、逆順なら交換する
      for (int i = 0; i < end; i++) {
        comparisonCount++;

        if (trace) {
          System.out.println(
              "  比較: values["
                  + i
                  + "]="
                  + values[i]
                  + " と values["
                  + (i + 1)
                  + "]="
                  + values[i + 1]);
        }

        if (values[i] > values[i + 1]) {
          int temp = values[i];
          values[i] = values[i + 1];
          values[i + 1] = temp;
          swapCount++;
          swapped = true;

          if (trace) {
            System.out.println("  交換後: " + Arrays.toString(values));
          }
        }
      }

      // 1回も交換が起きなければ、以降も整列済みと判断できる
      if (!swapped) {
        if (trace) {
          System.out.println("交換が発生しなかったため、この時点で処理を終了する。");
        }
        break;
      }
    }

    long end = System.nanoTime();

    return new SortResult(values, comparisonCount, swapCount, end - start);
  }

  private BubbleSortBasics() {}
}

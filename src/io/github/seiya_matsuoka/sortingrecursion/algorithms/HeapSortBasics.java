package io.github.seiya_matsuoka.sortingrecursion.algorithms;

import java.util.Arrays;

/** ヒープソートの基本実装 */
public class HeapSortBasics {

  /** ヒープソートの結果をまとめて返すためのレコード */
  public record SortResult(
      int[] sortedArray, long comparisonCount, long swapCount, long elapsedNanos) {}

  /**
   * ヒープソートを行う。
   *
   * <p>学習の主題は、配列上で最大ヒープを作り、 先頭の最大値を末尾へ移しながら整列させる流れにある。
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

    // ここがヒープソートの前半。
    // 配列全体を最大ヒープへ作り替える。
    buildMaxHeap(values, trace, counter);

    // ここがヒープソートの後半。
    // 先頭の最大値を末尾へ移し、残り区間を再度ヒープ化する。
    for (int heapSize = values.length; heapSize > 1; heapSize--) {
      if (trace) {
        System.out.println("[最大値を末尾へ移動] heapSize=" + heapSize);
        System.out.println("  交換前: root=" + values[0] + ", last=" + values[heapSize - 1]);
      }

      swap(values, 0, heapSize - 1, counter);

      if (trace) {
        System.out.println("  交換後: " + Arrays.toString(values));
        System.out.println("  残り区間を再ヒープ化する範囲: [0, " + (heapSize - 2) + "]");
      }

      heapify(values, 0, heapSize - 1, trace, 1, counter);
    }

    long end = System.nanoTime();
    return new SortResult(values, counter.comparisonCount, counter.swapCount, end - start);
  }

  /**
   * 配列全体を最大ヒープへ作り替える。
   *
   * <p>親子関係は配列 index で管理する。
   *
   * <p>子の index は次のように求まる。 - 左の子: 2 * parentIndex + 1 - 右の子: 2 * parentIndex + 2
   *
   * @param values ヒープ化する配列
   * @param trace 途中経過を表示するかどうか
   * @param counter 比較回数と交換回数を数えるための補助
   */
  private static void buildMaxHeap(int[] values, boolean trace, Counter counter) {
    if (trace) {
      System.out.println("[最大ヒープを構築する]");
    }

    // 葉ノードより右側はそれ以上子を持たない。
    // そのため、最後の親ノードから順に heapify していく。
    for (int parentIndex = values.length / 2 - 1; parentIndex >= 0; parentIndex--) {
      if (trace) {
        System.out.println(
            "  heapify 開始: parentIndex=" + parentIndex + ", value=" + values[parentIndex]);
      }
      heapify(values, parentIndex, values.length, trace, 1, counter);
    }

    if (trace) {
      System.out.println("構築後のヒープ: " + Arrays.toString(values));
      System.out.println();
    }
  }

  /**
   * 指定した親ノードを根とする部分木を最大ヒープへ整える。
   *
   * <p>ここがヒープソートのコア処理。
   *
   * <p>親、左の子、右の子を比較し、最大値が親でなければ交換して下へ沈める。
   *
   * @param values 整列対象の配列
   * @param rootIndex 今回ヒープ条件を整える根ノードの index
   * @param heapSize 現在ヒープとして扱う要素数
   * @param trace 途中経過を表示するかどうか
   * @param depth trace 表示時のインデント深さ
   * @param counter 比較回数と交換回数を数えるための補助
   */
  private static void heapify(
      int[] values, int rootIndex, int heapSize, boolean trace, int depth, Counter counter) {
    int largestIndex = rootIndex;
    int leftChildIndex = 2 * rootIndex + 1;
    int rightChildIndex = 2 * rootIndex + 2;

    if (trace) {
      printTrace(
          depth,
          "heapify 対象 rootIndex="
              + rootIndex
              + ", value="
              + values[rootIndex]
              + ", heapSize="
              + heapSize);
    }

    // 左の子がヒープ範囲内なら、現在の最大候補と比較する。
    if (leftChildIndex < heapSize) {
      counter.comparisonCount++;

      if (trace) {
        printTrace(
            depth, "左の子と比較: parent=" + values[largestIndex] + " / left=" + values[leftChildIndex]);
      }

      if (values[leftChildIndex] > values[largestIndex]) {
        largestIndex = leftChildIndex;

        if (trace) {
          printTrace(
              depth, "最大候補を左の子へ更新: index=" + largestIndex + ", value=" + values[largestIndex]);
        }
      }
    }

    // 右の子がヒープ範囲内なら、現在の最大候補と比較する。
    if (rightChildIndex < heapSize) {
      counter.comparisonCount++;

      if (trace) {
        printTrace(
            depth,
            "右の子と比較: currentMax=" + values[largestIndex] + " / right=" + values[rightChildIndex]);
      }

      if (values[rightChildIndex] > values[largestIndex]) {
        largestIndex = rightChildIndex;

        if (trace) {
          printTrace(
              depth, "最大候補を右の子へ更新: index=" + largestIndex + ", value=" + values[largestIndex]);
        }
      }
    }

    // 親が最大でない場合は交換し、下の部分木も再度ヒープ条件を整える。
    if (largestIndex != rootIndex) {
      if (trace) {
        printTrace(depth, "交換する: rootIndex=" + rootIndex + " と largestIndex=" + largestIndex);
      }

      swap(values, rootIndex, largestIndex, counter);

      if (trace) {
        printTrace(depth, "交換後: " + Arrays.toString(Arrays.copyOf(values, heapSize)));
      }

      heapify(values, largestIndex, heapSize, trace, depth + 1, counter);
    } else if (trace) {
      printTrace(depth, "交換不要: 親ノードが最大値");
    }
  }

  /** 要素を交換する。 */
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

  private HeapSortBasics() {}
}

package io.github.seiya_matsuoka.sortingrecursion.runner;

import io.github.seiya_matsuoka.sortingrecursion.RunnerOptions;
import io.github.seiya_matsuoka.sortingrecursion.algorithms.InsertionSortBasics;
import java.util.Arrays;

/** 挿入ソート実行用 runner */
public class InsertionSortRunner implements TopicRunner {

  private static final int MAX_GENERATED_SIZE = 3000;

  @Override
  public void run(RunnerOptions options) {
    if (options.target() != null && !options.target().isBlank()) {
      System.out.println("この topic では --target を使用しないため、指定値は無視する。");
      System.out.println("指定値: " + options.target());
      System.out.println();
    }

    // 入力データを決定する処理
    int[] input = resolveInput(options);

    System.out.println("挿入ソート");
    System.out.println();
    System.out.println("入力配列: " + Arrays.toString(input));
    System.out.println();

    // 学習対象の本体処理を呼び出す部分
    InsertionSortBasics.SortResult result = InsertionSortBasics.sort(input, options.trace());

    System.out.println();
    System.out.println("ソート後配列: " + Arrays.toString(result.sortedArray()));
    System.out.println("比較回数: " + result.comparisonCount());
    System.out.println("シフト回数: " + result.swapCount());
    System.out.println("参考実行時間(ns): " + result.elapsedNanos());
  }

  /**
   * 入力配列を決定する。
   *
   * <p>優先順位は次の通り。 1. --input 2. --size 3. 小さいデフォルト配列
   */
  private int[] resolveInput(RunnerOptions options) {
    if (options.input() != null && !options.input().isBlank()) {
      return parseInput(options.input());
    }

    if (options.size() != null) {
      int requested = options.size();
      int actual = Math.min(requested, MAX_GENERATED_SIZE);

      if (requested > MAX_GENERATED_SIZE) {
        System.out.println("入力サイズが大きすぎるため、学習用として先頭 " + MAX_GENERATED_SIZE + " 件に制限する。");
        System.out.println();
      }

      return createGeneratedInput(actual);
    }

    return defaultInput();
  }

  /** カンマ区切り文字列を int 配列に変換する。 */
  private int[] parseInput(String raw) {
    String[] parts = raw.split(",");
    int[] values = new int[parts.length];

    for (int i = 0; i < parts.length; i++) {
      values[i] = Integer.parseInt(parts[i].trim());
    }

    return values;
  }

  /**
   * 何も指定がない場合の小さい入力
   *
   * <p>trace と相性がよいサイズにしている。
   */
  private int[] defaultInput() {
    return new int[] {5, 3, 8, 1, 4, 7, 2, 6};
  }

  /**
   * 大きい入力を自動生成する。
   *
   * <p>基本ソートの差が見えやすいよう、逆順に近い並びを使う。
   */
  private int[] createGeneratedInput(int size) {
    int[] values = new int[size];

    for (int i = 0; i < size; i++) {
      values[i] = size - i;
    }

    return values;
  }
}

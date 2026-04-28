package io.github.seiya_matsuoka.sortingrecursion.runner;

import io.github.seiya_matsuoka.sortingrecursion.RunnerOptions;
import io.github.seiya_matsuoka.sortingrecursion.algorithms.DivideAndConquerBasics;
import java.util.Arrays;

/** 分割統治実行用 runner */
public class DivideAndConquerRunner implements TopicRunner {

  private static final int MAX_GENERATED_SIZE = 4096;

  @Override
  public void run(RunnerOptions options) {
    if (options.target() != null && !options.target().isBlank()) {
      System.out.println("この topic では --target を使用しないため、指定値は無視する。");
      System.out.println("指定値: " + options.target());
      System.out.println();
    }

    // 分割統治の流れを確認しやすいよう、配列入力を使う。
    int[] input = resolveInput(options);

    System.out.println("分割統治");
    System.out.println();
    System.out.println("入力配列: " + Arrays.toString(input));
    System.out.println();

    // 配列合計は「分けて解き、最後に足し合わせる」流れを見る題材
    DivideAndConquerBasics.SumResult sumResult = DivideAndConquerBasics.sum(input, options.trace());

    System.out.println();
    System.out.println("分割統治による合計値: " + sumResult.value());
    System.out.println("合計計算の呼び出し回数: " + sumResult.callCount());
    System.out.println("合計計算の参考実行時間(ns): " + sumResult.elapsedNanos());
    System.out.println();

    // 最大値探索は「左右の部分問題の結果を比較して統合する」流れを見る題材
    DivideAndConquerBasics.MaxResult maxResult = DivideAndConquerBasics.max(input, options.trace());

    System.out.println("分割統治による最大値: " + maxResult.value());
    System.out.println("最大値探索の呼び出し回数: " + maxResult.callCount());
    System.out.println("最大値探索の参考実行時間(ns): " + maxResult.elapsedNanos());
  }

  /**
   * 分割統治トピック用の入力配列を決定する。
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

  /** trace と相性がよい小さい入力 */
  private int[] defaultInput() {
    return new int[] {8, 3, 7, 4, 9, 2, 6, 5};
  }

  /**
   * 大きい入力を自動生成する。
   *
   * <p>分割後の左右差が出すぎないよう、規則的な値を作る。
   */
  private int[] createGeneratedInput(int size) {
    int[] values = new int[size];

    for (int i = 0; i < size; i++) {
      values[i] = (i * 7 + 3) % (size + 11);
    }

    return values;
  }
}

package io.github.seiya_matsuoka.sortingrecursion.runner;

import io.github.seiya_matsuoka.sortingrecursion.RunnerOptions;
import io.github.seiya_matsuoka.sortingrecursion.algorithms.RecursionBasics;

/** 再帰実行用 runner */
public class RecursionRunner implements TopicRunner {

  private static final int DEFAULT_NUMBER = 5;
  private static final int MAX_GENERATED_SIZE = 12;

  @Override
  public void run(RunnerOptions options) {
    if (options.target() != null && !options.target().isBlank()) {
      System.out.println("この topic では --target を使用しないため、指定値は無視する。");
      System.out.println("指定値: " + options.target());
      System.out.println();
    }

    // 再帰の基本を確認しやすいよう、単一の整数入力を使う。
    int number = resolveNumber(options);

    System.out.println("再帰");
    System.out.println();
    System.out.println("入力値: " + number);
    System.out.println();

    // カウントダウンは「自分を呼ぶ流れ」と「ベースケース」を見るための題材
    RecursionBasics.CountDownResult countDownResult =
        RecursionBasics.countDown(number, options.trace());

    System.out.println();
    System.out.println("カウントダウン結果: " + countDownResult.visitedValues());
    System.out.println("カウントダウンの呼び出し回数: " + countDownResult.callCount());
    System.out.println("カウントダウンの参考実行時間(ns): " + countDownResult.elapsedNanos());
    System.out.println();

    // factorial は「戻りがけに結果を組み立てる再帰」を確認する題材
    RecursionBasics.FactorialResult factorialResult =
        RecursionBasics.factorial(number, options.trace());

    System.out.println("階乗の結果: " + factorialResult.value());
    System.out.println("階乗の呼び出し回数: " + factorialResult.callCount());
    System.out.println("階乗の参考実行時間(ns): " + factorialResult.elapsedNanos());
    System.out.println();

    // 1 から n までの合計は、再帰で値を足し戻す流れを確認する題材
    RecursionBasics.SumResult sumResult = RecursionBasics.sumFromOneToN(number, options.trace());

    System.out.println("1 から " + number + " までの再帰合計: " + sumResult.value());
    System.out.println("再帰合計の呼び出し回数: " + sumResult.callCount());
    System.out.println("再帰合計の参考実行時間(ns): " + sumResult.elapsedNanos());
  }

  /**
   * 再帰トピック用の整数入力を決定する。
   *
   * <p>優先順位は次の通り。 1. --input 2. --size 3. 小さいデフォルト値
   */
  private int resolveNumber(RunnerOptions options) {
    if (options.input() != null && !options.input().isBlank()) {
      return parsePositiveNumber(options.input());
    }

    if (options.size() != null) {
      int requested = options.size();
      int actual = Math.min(requested, MAX_GENERATED_SIZE);

      if (requested > MAX_GENERATED_SIZE) {
        System.out.println("入力値が大きすぎるため、学習用として " + MAX_GENERATED_SIZE + " に制限する。");
        System.out.println();
      }

      return actual;
    }

    return DEFAULT_NUMBER;
  }

  /**
   * 正の整数を受け取る。
   *
   * <p>再帰の題材として 1 未満は扱いづらいため、1 未満は 1 に補正する。
   */
  private int parsePositiveNumber(String raw) {
    int parsed = Integer.parseInt(raw.trim());

    if (parsed < 1) {
      System.out.println("再帰の学習では 1 以上を扱うため、入力値を 1 に補正する。");
      System.out.println();
      return 1;
    }

    return parsed;
  }
}

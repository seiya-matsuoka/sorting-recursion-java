package io.github.seiya_matsuoka.sortingrecursion;

import io.github.seiya_matsuoka.sortingrecursion.runner.BubbleSortRunner;
import io.github.seiya_matsuoka.sortingrecursion.runner.DivideAndConquerRunner;
import io.github.seiya_matsuoka.sortingrecursion.runner.InsertionSortRunner;
import io.github.seiya_matsuoka.sortingrecursion.runner.MergeSortRunner;
import io.github.seiya_matsuoka.sortingrecursion.runner.QuickSortRunner;
import io.github.seiya_matsuoka.sortingrecursion.runner.RecursionRunner;
import io.github.seiya_matsuoka.sortingrecursion.runner.SelectionSortRunner;
import io.github.seiya_matsuoka.sortingrecursion.runner.TopicRunner;

/**
 * 共通エントリーポイント
 *
 * <p>コマンドライン引数を受け取り、指定された topic に応じて 各 runner に処理を振り分ける。
 */
public class App {

  public static void main(String[] args) {
    RunnerOptions options = parseOptions(args);

    if (options.topic() == null || options.topic().isBlank()) {
      printUsage();
      return;
    }

    TopicRunner runner =
        switch (options.topic()) {
          case "bubble-sort" -> new BubbleSortRunner();
          case "selection-sort" -> new SelectionSortRunner();
          case "insertion-sort" -> new InsertionSortRunner();
          case "recursion" -> new RecursionRunner();
          case "divide-and-conquer" -> new DivideAndConquerRunner();
          case "merge-sort" -> new MergeSortRunner();
          case "quick-sort" -> new QuickSortRunner();
          default -> null;
        };

    if (runner == null) {
      System.out.println("未対応の topic が指定されたため、実行を終了する。");
      System.out.println("指定値: " + options.topic());
      System.out.println();
      printUsage();
      return;
    }

    runner.run(options);
  }

  /**
   * String[] args を簡易的に解析する。
   *
   * <p>外部ライブラリは使わず、今回の学習で必要なオプションだけを扱う。
   */
  private static RunnerOptions parseOptions(String[] args) {
    String topic = null;
    String input = null;
    boolean trace = false;
    String target = null;
    Integer size = null;

    for (int i = 0; i < args.length; i++) {
      String current = args[i];

      switch (current) {
        case "--topic" -> {
          if (i + 1 < args.length) {
            topic = args[++i];
          }
        }
        case "--input" -> {
          if (i + 1 < args.length) {
            input = args[++i];
          }
        }
        case "--trace" -> trace = true;
        case "--target" -> {
          if (i + 1 < args.length) {
            target = args[++i];
          }
        }
        case "--size" -> {
          if (i + 1 < args.length) {
            String raw = args[++i];
            try {
              size = Integer.parseInt(raw);
            } catch (NumberFormatException e) {
              System.out.println("--size には整数を指定する必要があるため、この値は無視する。");
              System.out.println("指定値: " + raw);
            }
          }
        }
        default -> {
          // 今回の学習では未対応オプションは無視する。
        }
      }
    }

    return new RunnerOptions(topic, input, trace, target, size);
  }

  /** 実行方法を簡潔に表示する。 */
  private static void printUsage() {
    System.out.println("sorting-recursion-java 実行方法");
    System.out.println();
    System.out.println("基本形:");
    System.out.println(
        "  java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic <topic> [options]");
    System.out.println();
    System.out.println("指定できる topic:");
    System.out.println("  bubble-sort");
    System.out.println("  selection-sort");
    System.out.println("  insertion-sort");
    System.out.println("  recursion");
    System.out.println("  divide-and-conquer");
    System.out.println("  merge-sort");
    System.out.println("  quick-sort");
    System.out.println();
    System.out.println("指定できる共通オプション:");
    System.out.println("  --input  例: 5,3,8,1,4 / 6");
    System.out.println("  --trace  途中経過を表示する");
    System.out.println("  --target この回では使用しない");
    System.out.println("  --size   大きい入力を自動生成する");
  }
}

package io.github.seiya_matsuoka.sortingrecursion.runner;

import io.github.seiya_matsuoka.sortingrecursion.RunnerOptions;

/** topic ごとの実行入口インターフェース */
public interface TopicRunner {

  /**
   * topic を実行する
   *
   * @param options 実行時オプション
   */
  void run(RunnerOptions options);
}

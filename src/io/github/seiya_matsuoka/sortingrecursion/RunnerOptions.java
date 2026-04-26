package io.github.seiya_matsuoka.sortingrecursion;

/** 実行時オプションをまとめて保持するクラス */
public class RunnerOptions {

  private final String topic;
  private final String input;
  private final boolean trace;
  private final String target;
  private final Integer size;

  public RunnerOptions(String topic, String input, boolean trace, String target, Integer size) {
    this.topic = topic;
    this.input = input;
    this.trace = trace;
    this.target = target;
    this.size = size;
  }

  public String topic() {
    return topic;
  }

  public String input() {
    return input;
  }

  public boolean trace() {
    return trace;
  }

  public String target() {
    return target;
  }

  public Integer size() {
    return size;
  }
}

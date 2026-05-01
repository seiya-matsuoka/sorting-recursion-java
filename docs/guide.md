# sorting-recursion-java ガイド

## 1. このリポジトリで学ぶこと

このリポジトリでは、ソートアルゴリズムの基礎と、それを支える再帰・分割統治の考え方を扱う。

対象トピックは次の8つ。

- バブルソート
- 選択ソート
- 挿入ソート
- 再帰
- 分割統治
- マージソート
- クイックソート
- ヒープソート

ここでの目的は、複数のソートアルゴリズムを比較しながら、**配列をどのように並び替えるか・再帰で問題をどのように小さくするか・分割統治で問題をどのように分けて解くか** を、Java のコードを読みながら確認すること。

---

## 2. 学習対象トピック一覧

### 2-1. バブルソート

- 隣り合う要素を比較する
- 順序が逆なら交換する
- 1周ごとに最大値が右端へ寄る流れを確認する
- 比較回数と交換回数の見方を確認する

対応ドキュメント:

- `docs/topics/bubble-sort.md`

### 2-2. 選択ソート

- 未整列区間から最小値を探す
- 見つけた最小値を先頭へ移す
- 先頭から順に確定していく流れを確認する
- 比較回数と交換回数の見方を確認する

対応ドキュメント:

- `docs/topics/selection-sort.md`

### 2-3. 挿入ソート

- 左側の整列済み区間へ現在値を挿し込む
- 大きい値を右へずらす
- 少しずつ整列済み区間を広げる流れを確認する
- 比較回数とシフト回数の見方を確認する

対応ドキュメント:

- `docs/topics/insertion-sort.md`

### 2-4. 再帰

- 自分自身を呼び出す処理の形を確認する
- ベースケースの役割を確認する
- 再帰呼び出しと戻りの流れを確認する
- ソートアルゴリズムに再帰がどのように使われるかの土台を確認する

対応ドキュメント:

- `docs/topics/recursion.md`

### 2-5. 分割統治

- 問題を小さな問題へ分ける
- 分けた問題をそれぞれ解く
- 最後に結果を統合する
- マージソートやクイックソートの前提となる考え方を確認する

対応ドキュメント:

- `docs/topics/divide-and-conquer.md`

### 2-6. マージソート

- 配列を半分ずつに分ける
- 再帰的に小区間を整列する
- ソート済み区間同士をマージする
- 分割統治と追加配列を使った整列の流れを確認する

対応ドキュメント:

- `docs/topics/merge-sort.md`

### 2-7. クイックソート

- pivot を基準に区間を分ける
- pivot より小さい値と大きい値を左右へ寄せる
- 左右の区間を再帰的に整列する
- partition の流れと平均的な速さを確認する

対応ドキュメント:

- `docs/topics/quick-sort.md`

### 2-8. ヒープソート

- 配列全体を最大ヒープへ作り替える
- 先頭の最大値を末尾へ移す
- 残り区間を再ヒープ化する
- ヒープ構造を使った整列の流れを確認する

対応ドキュメント:

- `docs/topics/heap-sort.md`

---

## 3. 推奨する学習順

このリポジトリ内では、次の順で進めるのがおすすめ。

1. バブルソート
2. 選択ソート
3. 挿入ソート
4. 再帰
5. 分割統治
6. マージソート
7. クイックソート
8. ヒープソート

### 理由

- まず O(n^2) の基本ソート3種で、並び替え方の違いを押さえる
- 次に再帰と分割統治を先に確認し、マージソートとクイックソートの土台を作る
- その後で O(n log n) 系のマージソートとクイックソートを比較する
- 最後にヒープソートで、ヒープ構造を使った別系統の整列方法を確認する

---

## 4. ディレクトリ構成

```text
sorting-recursion-java/
├─ src/
│  └─ io/
│     └─ github/
│        └─ seiya_matsuoka/
│           └─ sortingrecursion/
│              ├─ App.java
│              ├─ RunnerOptions.java
│              ├─ runner/
│              │  ├─ TopicRunner.java
│              │  ├─ BubbleSortRunner.java
│              │  ├─ SelectionSortRunner.java
│              │  ├─ InsertionSortRunner.java
│              │  ├─ RecursionRunner.java
│              │  ├─ DivideAndConquerRunner.java
│              │  ├─ MergeSortRunner.java
│              │  ├─ QuickSortRunner.java
│              │  └─ HeapSortRunner.java
│              └─ algorithms/
│                 ├─ BubbleSortBasics.java
│                 ├─ SelectionSortBasics.java
│                 ├─ InsertionSortBasics.java
│                 ├─ RecursionBasics.java
│                 ├─ DivideAndConquerBasics.java
│                 ├─ MergeSortBasics.java
│                 ├─ QuickSortBasics.java
│                 └─ HeapSortBasics.java
├─ docs/
│  ├─ guide.md
│  └─ topics/
│     ├─ bubble-sort.md
│     ├─ selection-sort.md
│     ├─ insertion-sort.md
│     ├─ recursion.md
│     ├─ divide-and-conquer.md
│     ├─ merge-sort.md
│     ├─ quick-sort.md
│     └─ heap-sort.md
└─ .gitignore
```

### 4-1. 各ファイル・ディレクトリの役割

#### `App.java`

共通エントリーポイント。  
コマンドライン引数を受け取り、指定された topic に応じて対応する runner に処理を振り分ける。

#### `RunnerOptions.java`

実行時オプション保持用クラス。  
`--topic`、`--input`、`--trace`、`--target`、`--size` の値をまとめて runner に渡す。

#### `runner/`

実行用クラス群。  
入力データの決定、trace 表示、表示用メッセージの整形など、学習用デモとして動かすための周辺処理を担当する。

#### `algorithms/`

学習テーマのコアとなる実装本体。  
実際のソート処理、再帰処理、分割統治の処理はここに置く。

#### `docs/topics/`

各学習トピックの説明ドキュメント。  
コードと対応づけながら読むことを前提とする。

---

## 5. 実行方法

このリポジトリは Gradle などのビルドツールを使わず、素の Java でコンパイル・実行する。

### 5-1. 前提

- Java 21 を使用
- VS Code では、フォルダを開いてターミナルからコンパイル・実行すればよい

### 5-2. bash / zsh でのコンパイル

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
```

### 5-3. PowerShell でのコンパイル

```powershell
$files = Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d out $files
```

### 5-4. 実行コマンドの基本形

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic <topic> [options]
```

---

## 6. 共通オプション

このリポジトリでは、次の5つの共通オプションを使う。

### 6-1. `--topic`

実行する学習トピックを指定する。

指定できる値:

- `bubble-sort`
- `selection-sort`
- `insertion-sort`
- `recursion`
- `divide-and-conquer`
- `merge-sort`
- `quick-sort`
- `heap-sort`

### 6-2. `--input`

入力値を直接指定する。

例:

- `--input 5,3,8,1,4`
- `--input 6`

### 6-3. `--trace`

処理途中の流れを表示する。

使いどころ:

- ソート中の比較と交換の流れ確認
- 再帰呼び出しの流れ確認
- 分割統治で区間を分ける流れ確認
- マージや partition の流れ確認
- ヒープ化と再ヒープ化の流れ確認

### 6-4. `--target`

このリポジトリでは基本的に使用しない。

不要なトピックでは、指定されていても使わずに無視する。

### 6-5. `--size`

大きい入力データを生成したい場合のサイズを指定する。

使いどころ:

- 基本ソート3種で入力サイズ差を確認する
- マージソート、クイックソート、ヒープソートで大きめ配列を使う
- 再帰や分割統治の入力サイズを変える

---

## 7. 入力データの決まり方

各トピックで使う入力データは、次の優先順位で決定する。

1. `--input` が指定されていればそれを使う
2. `--input` がなく、`--size` が指定されていれば、そのサイズで入力を生成する
3. どちらもなければ、学習用の小さいデフォルト値を使う

### 7-1. この順にしている理由

- まずは自分で試したい入力を最優先にできる
- 入力サイズだけ変えて差を見たい場合にも対応できる
- 何も指定しなくてもすぐ実行できる

---

## 8. topic ごとの実行例

### 8-1. バブルソート

#### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic bubble-sort --trace
```

#### 入力指定

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic bubble-sort --input 5,3,8,1,4 --trace
```

#### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic bubble-sort --size 1000
```

### 8-2. 選択ソート

#### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic selection-sort --trace
```

#### 入力指定

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic selection-sort --input 5,3,8,1,4 --trace
```

#### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic selection-sort --size 1000
```

### 8-3. 挿入ソート

#### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic insertion-sort --trace
```

#### 入力指定

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic insertion-sort --input 5,3,8,1,4 --trace
```

#### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic insertion-sort --size 1000
```

### 8-4. 再帰

#### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic recursion --trace
```

#### 入力指定

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic recursion --input 6 --trace
```

#### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic recursion --size 10
```

### 8-5. 分割統治

#### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic divide-and-conquer --trace
```

#### 入力指定

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic divide-and-conquer --input 8,3,7,4,9,2,6,5 --trace
```

#### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic divide-and-conquer --size 1000
```

### 8-6. マージソート

#### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic merge-sort --trace
```

#### 入力指定

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic merge-sort --input 5,3,8,1,4 --trace
```

#### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic merge-sort --size 1000
```

### 8-7. クイックソート

#### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic quick-sort --trace
```

#### 入力指定

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic quick-sort --input 5,3,8,1,4 --trace
```

#### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic quick-sort --size 1000
```

### 8-8. ヒープソート

#### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic heap-sort --trace
```

#### 入力指定

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic heap-sort --input 5,3,8,1,4 --trace
```

#### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic heap-sort --size 1000
```

---

## 9. 学習時の見方

### 9-1. まず見るべき場所

まずは次の順で見るのがおすすめ。

1. `docs/topics/*.md` で概要と処理の流れを読む
2. `runner/` で入力決定や表示の流れを確認する
3. `algorithms/` でコアとなる処理を読む
4. 実際に `--trace` 付きで実行して出力を見る

### 9-2. 特に見るべきポイント

- どこが学習テーマの本体処理か
- どこが入力準備や表示のための補助処理か
- O(n^2) のソート3種で、何を比較し、いつ交換または移動しているか
- 再帰で、どこがベースケースでどこが再帰呼び出しか
- 分割統治で、どこで問題を分けてどこで統合しているか
- マージソートで、どのように区間を分けてどのようにマージしているか
- クイックソートで、pivot と partition がどう働くか
- ヒープソートで、ヒープ化と再ヒープ化がどう進むか

### 9-3. 実行時間の見方

実行時間は参考値として表示することがあるが、学習の中心は次の順。

1. 理論計算量
2. 比較回数や交換回数、移動回数
3. 参考実行時間

特にソートでは、秒数そのものより「入力サイズが増えたときにどう増えるか」「どの種類の操作が多いか」を見る。

---

## 10. このリポジトリを終えた時点で目指す状態

このリポジトリを終えた時点で、次の状態を目指す。

- バブルソート、選択ソート、挿入ソートの違いを説明できる
- 再帰と分割統治の基本的な考え方を説明できる
- マージソートとクイックソートの流れを説明できる
- ヒープソートの基本的な流れを説明できる
- O(n^2) と O(n log n) の代表的な違いを説明できる
- コードを読んだときに、学習本体処理と補助処理を見分けられる

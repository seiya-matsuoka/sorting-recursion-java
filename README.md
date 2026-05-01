# Sorting / Recursion - Java

<p>
  <img alt="Java" src="https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=ffffff">
  <img alt="Algorithm" src="https://img.shields.io/badge/Algorithm-Study-1F6FEB">
  <img alt="Data Structure" src="https://img.shields.io/badge/Data%20Structure-Study-7C3AED">
</p>

ソートと再帰・分割統治の基礎のうち、**バブルソート / 選択ソート / 挿入ソート / 再帰 / 分割統治 / マージソート / クイックソート / ヒープソート** を Java で学習するためのリポジトリ。  
コードを読み、実行し、出力を確認しながら、並び替えアルゴリズムの違いと、再帰的に問題を分けて解く考え方を段階的に理解することを目的とする。  
各トピックごとにドキュメントを用意し、実装と対応づけながら見返せる形で整理している。

---

## 学習目的

このリポジトリでは、主に次の内容を目的として学習を行う。

- バブルソート / 選択ソート / 挿入ソートの基本動作と違いを理解する
- 再帰の基本的な考え方と、ベースケースの役割を理解する
- 分割統治の考え方を理解する
- マージソートとクイックソートの流れと違いを理解する
- ヒープソートの流れと、ヒープを使った整列の考え方を理解する
- Java のコードを読みながら、処理の流れを追えるようにする
- 実行結果や途中経過を見ながら、挙動を確認できるようにする

---

## 学習範囲

このリポジトリで扱うトピックは次の通り。

- バブルソート
- 選択ソート
- 挿入ソート
- 再帰
- 分割統治
- マージソート
- クイックソート
- ヒープソート

### 各トピックの位置づけ

- **バブルソート**  
  隣り合う要素を比較しながら大きい値を右端へ押し出す流れを確認する

- **選択ソート**  
  未整列区間から最小値を探して先頭へ確定する流れを確認する

- **挿入ソート**  
  左側の整列済み区間へ現在値を挿し込む流れを確認する

- **再帰**  
  ベースケースと再帰呼び出しを通して、自分自身を呼ぶ処理の流れを確認する

- **分割統治**  
  問題を小さな部分問題へ分割し、それぞれを解いて最後に統合する考え方を確認する

- **マージソート**  
  配列を半分ずつに分け、戻りながらマージする流れを確認する

- **クイックソート**  
  pivot を基準に配列を分割し、左右を再帰的に整列する流れを確認する

- **ヒープソート**  
  最大ヒープを作って最大値を末尾へ移しながら整列する流れを確認する

---

## 学習の進め方

基本的な進め方は次の通り。

1. `docs/guide.md` を読み、このリポジトリ全体の構成と実行方法を把握する
2. `docs/topics/` 配下の対象トピックのドキュメントを読む
3. `App.java` から `--topic` を指定して実行する
4. 必要に応じて `--input`、`--trace`、`--target`、`--size` を使って挙動を変える
5. `runner/` と `algorithms/` 配下のコードを読み、コメントと出力を対応させながら理解する

---

## 前提環境

- Java 21
- VS Code などの Java を扱えるエディタ
- ビルドツールは使用しない
- `javac` と `java` でコンパイル・実行を行う

---

## 実行方法

### 1. コンパイル

#### bash / Git Bash

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
```

#### PowerShell

```powershell
$files = Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d out $files
```

### 2. 実行

基本形は次の通り。

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic <topic>
```

例:

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic bubble-sort
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic selection-sort
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic insertion-sort
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic recursion
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic divide-and-conquer
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic merge-sort
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic quick-sort
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic heap-sort
```

---

## 共通オプション

このリポジトリでは、共通で次のオプションを使う。

- `--topic`  
  実行するトピックを指定する

- `--input`  
  任意の入力値を直接指定する  
  例: `--input 5,3,8,1,4` / `--input 6`

- `--trace`  
  処理途中の流れを表示する

- `--target`  
  探索対象値など、別途指定したい値を渡す  
  必要なトピックのみで使用する

- `--size`  
  大きい入力データを自動生成したい場合のサイズ指定に使う  
  比較確認や差分把握で使用する

---

## 実行例

### バブルソート

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic bubble-sort --input 5,3,8,1,4 --trace
```

### 選択ソート

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic selection-sort --input 5,3,8,1,4 --trace
```

### 挿入ソート

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic insertion-sort --input 5,3,8,1,4 --trace
```

### 再帰

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic recursion --input 6 --trace
```

### 分割統治

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic divide-and-conquer --input 8,3,7,4,9,2,6,5 --trace
```

### マージソート

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic merge-sort --input 5,3,8,1,4 --trace
```

### クイックソート

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic quick-sort --input 5,3,8,1,4 --trace
```

### ヒープソート

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic heap-sort --input 5,3,8,1,4 --trace
```

---

## リポジトリ構成

```text
.
├─ src/
│  └─ io/
│     └─ github/
│        └─ seiya_matsuoka/
│           └─ sortingrecursion/
│              ├─ App.java
│              ├─ RunnerOptions.java
│              ├─ runner/
│              └─ algorithms/
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
└─ README.md
```

### 各ディレクトリ・ファイルの役割

- `App.java`  
  共通エントリーポイント  
  引数を読み取り、対象の runner に振り分ける

- `RunnerOptions.java`  
  実行オプションを保持する

- `runner/`  
  入力の決定、実装呼び出し、出力表示を担当する

- `algorithms/`  
  学習対象となるソート・再帰・分割統治の実装本体を置く

- `docs/guide.md`  
  リポジトリ全体の案内と実行方法をまとめる

- `docs/topics/`  
  各トピックの個別ドキュメントを置く

---

## ドキュメント

- ガイド: [`docs/guide.md`](docs/guide.md)
- バブルソート: [`docs/topics/bubble-sort.md`](docs/topics/bubble-sort.md)
- 選択ソート: [`docs/topics/selection-sort.md`](docs/topics/selection-sort.md)
- 挿入ソート: [`docs/topics/insertion-sort.md`](docs/topics/insertion-sort.md)
- 再帰: [`docs/topics/recursion.md`](docs/topics/recursion.md)
- 分割統治: [`docs/topics/divide-and-conquer.md`](docs/topics/divide-and-conquer.md)
- マージソート: [`docs/topics/merge-sort.md`](docs/topics/merge-sort.md)
- クイックソート: [`docs/topics/quick-sort.md`](docs/topics/quick-sort.md)
- ヒープソート: [`docs/topics/heap-sort.md`](docs/topics/heap-sort.md)

---

## このリポジトリで確認できること

このリポジトリを一通り進めることで、次の状態を目指す。

- バブルソート / 選択ソート / 挿入ソートの違いを説明できる
- 再帰とベースケースの役割を理解できる
- 分割統治の考え方を説明できる
- マージソートとクイックソートの流れの違いを説明できる
- ヒープソートの流れと、ヒープを使う意味を理解できる
- Java の実装と出力を対応させながら読める
- `--topic` や各オプションを使って、自分で入力を変えながら確認できる

# ヒープソート

## 1. 概要

ヒープソートは、配列を最大ヒープへ作り替えた後、先頭の最大値を末尾へ移しながら並べ替えるソートアルゴリズム。

配列上で親子関係を扱う点が特徴で、ヒープ構造とソート処理を一緒に確認できる。

---

## 2. 基本の動き

ヒープソートでは、次の流れで並び替えを行う。

- 配列全体を最大ヒープへ作り替える
- 先頭には常に最大値が来る
- 先頭の最大値を末尾と交換する
- 末尾の確定済み要素を除いた残り区間を再ヒープ化する
- これを繰り返して、右側から順に値を確定していく

最大ヒープを使うことで、未確定区間の最大値を先頭から取り出せる。

---

## 3. 処理の流れ

処理の流れは次の通り。

1. 配列の後ろ側にある親ノードから順に heapify を行い、最大ヒープを構築する
2. 先頭の最大値と未確定区間の末尾を交換する
3. 末尾の要素を確定済みとし、ヒープ範囲を 1 つ縮める
4. 先頭ノードに対して再度 heapify を行う
5. 未確定区間が 1 要素になるまで、交換と再ヒープ化を繰り返す

trace を有効にすると、どの親ノードを heapify しているか、どの子ノードと比較しているか、どこを交換したかを確認できる。

---

## 4. 計算量

- 時間計算量
  - 最悪: O(n log n)
  - 平均: O(n log n)
  - 最良: O(n log n)

- 空間計算量
  - O(1)

### 補足

- 最大ヒープ構築は O(n)
- 最大値の取り出しと再ヒープ化を n 回近く繰り返すため、全体では O(n log n)
- 追加の作業配列を使わず、配列内で並べ替えられる

---

## 5. メリット / デメリット

### メリット

- O(n log n) で安定して動く
- 追加配列なしで並べ替えできる
- 配列とヒープ構造の対応を確認しやすい

### デメリット

- 基本ソート 3 種より実装が複雑
- マージソートやクイックソートより、処理の流れを直感的に追いにくい
- trace を見ないと heapify の流れがつかみにくい

---

## 6. Java実装のポイント

- 元配列をそのまま壊さないよう、最初に `Arrays.copyOf` で複製している
- `buildMaxHeap(...)` で配列全体を最大ヒープへ作り替えている
- `heapify(...)` で、親ノードを根とする部分木のヒープ条件を整えている
- `rootIndex` が今回見ている親ノードの位置になる
- `leftChildIndex` と `rightChildIndex` で、左右の子ノード位置を表している
- 比較回数、交換回数、参考実行時間を結果として返している

---

## 7. コアとなる処理・重要なコードの見どころ

最も重要なのは、親ノードと子ノードを比較して、必要なら下へ沈める `heapify(...)` の処理。

```java
if (leftChildIndex < heapSize && values[leftChildIndex] > values[largestIndex]) {
    largestIndex = leftChildIndex;
}

if (rightChildIndex < heapSize && values[rightChildIndex] > values[largestIndex]) {
    largestIndex = rightChildIndex;
}

if (largestIndex != rootIndex) {
    swap(values, rootIndex, largestIndex, counter);
    heapify(values, largestIndex, heapSize, trace, depth + 1, counter);
}
```

ここで、

- `rootIndex` は今回ヒープ条件を整える親ノードの位置
- `leftChildIndex` と `rightChildIndex` は左右の子ノード位置
- `largestIndex` は親と子の中で最大値を持つ位置

を表している。  
親が最大でなければ、最大の子と交換し、その子の位置からさらに heapify を続けることで、ヒープソートの本体処理を実現している。

---

## 8. 実行例

### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic heap-sort --trace
```

### 入力指定

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic heap-sort --input 5,3,8,1,4 --trace
```

### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic heap-sort --size 1000
```

実行時は、入力配列、ヒープ構築の流れ、ソート後配列、比較回数、交換回数、参考実行時間を確認できる。

---

## 9. 関連トピック

- バブルソート
- 選択ソート
- 挿入ソート
- マージソート
- クイックソート
- 再帰
- 分割統治

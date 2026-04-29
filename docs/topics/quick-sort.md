# クイックソート

## 1. 概要

クイックソートは、pivot を 1 つ決めて、その値より小さい要素と大きい要素に区切りながら並び替えるソートアルゴリズム。

再帰と分割統治を使う代表的なソートであり、平均的には高速に動作する。

---

## 2. 基本の動き

クイックソートでは、次の流れで並び替えを行う。

- 区間の中から pivot を 1 つ選ぶ
- pivot 以下の要素を左側へ集める
- pivot より大きい要素を右側へ集める
- pivot の位置を確定する
- pivot より左側と右側を再帰的にソートする

今回の実装では、右端の要素を pivot として使っている。

---

## 3. 処理の流れ

処理の流れは次の通り。

1. 現在見ている区間の左端と右端を確認する
2. 区間の長さが 1 以下なら、それ以上分割せずに終了する
3. 右端の値を pivot として選ぶ
4. 左から順に値を見ていき、pivot 以下なら左側グループへ移動する
5. 走査が終わったら、pivot を境界位置へ移動して確定する
6. pivot より左側を再帰的にソートする
7. pivot より右側を再帰的にソートする

trace を有効にすると、どの値を pivot にしたか、どの値を左側へ移動したか、pivot がどこへ確定したかを確認できる。

---

## 4. 計算量

- 時間計算量
  - 最悪: O(n^2)
  - 平均: O(n log n)
  - 最良に近いケース: O(n log n)

- 空間計算量
  - O(log n) 程度

### 補足

- pivot の選び方によって分割の偏りが変わる
- 毎回偏った分割になると O(n^2) に近づく
- 平均的には O(n log n) で高速に動く

---

## 5. メリット / デメリット

### メリット

- 平均的には高速
- 追加領域が少なくて済む
- partition の考え方を確認しやすい

### デメリット

- 最悪計算量が O(n^2)
- pivot の選び方に影響を受ける
- trace を追わないと内部の流れが見えにくい

---

## 6. Java実装のポイント

- 元配列をそのまま壊さないよう、最初に `Arrays.copyOf` で複製している
- `quickSort(...)` で区間を再帰的に処理している
- `leftStart` と `rightEnd` が、現在見ている区間の両端 index になる
- `pivotValue` が、今回の区間で基準にする値になる
- `nextSmallValueIndex` が、pivot 以下の値を次に置く位置になる
- 比較回数と交換回数を計測している

---

## 7. コアとなる処理・重要なコードの見どころ

最も重要なのは、pivot を基準に区間を組み替える `partition(...)` の処理。

```java
for (int scanIndex = leftStart; scanIndex < rightEnd; scanIndex++) {
    counter.comparisonCount++;

    if (values[scanIndex] <= pivotValue) {
        swap(values, nextSmallValueIndex, scanIndex, counter);
        nextSmallValueIndex++;
    }
}

swap(values, nextSmallValueIndex, rightEnd, counter);
```

ここで、

- `pivotValue` は今回の基準値
- `scanIndex` は左から順に確認している位置
- `nextSmallValueIndex` は pivot 以下の値を次に置く位置

を表している。  
現在値が pivot 以下なら左側グループへ移し、最後に pivot を境界位置へ移動することで、クイックソートの本体処理を実現している。

---

## 8. 実行例

### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic quick-sort --trace
```

### 任意入力

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic quick-sort --input 5,3,8,1,4 --trace
```

### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic quick-sort --size 1000
```

実行時は、入力配列、ソート後配列、比較回数、交換回数、参考実行時間を確認できる。  
trace を付けると、pivot の選択、partition、pivot の確定位置も確認できる。

---

## 9. 関連トピック

- 再帰
- 分割統治
- バブルソート
- 選択ソート
- 挿入ソート
- マージソート

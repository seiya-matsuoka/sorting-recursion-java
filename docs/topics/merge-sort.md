# マージソート

## 1. 概要

マージソートは、配列を半分ずつに分けて十分小さくした後、ソート済みの区間同士をマージしながら全体を整列させるソートアルゴリズム。

再帰と分割統治の考え方が、そのまま並び替えへつながる代表的な例のひとつ。

---

## 2. 基本の動き

マージソートでは、次の流れで並び替えを行う。

- 配列を左半分と右半分へ分ける
- 左半分を再帰的にソートする
- 右半分を再帰的にソートする
- ソート済みになった左右の区間を 1 つにマージする

分割を続けると、最終的には長さ 1 の区間にたどり着く。  
その後は戻りながら、小さい区間同士を順に統合していく。

---

## 3. 処理の流れ

処理の流れは次の通り。

1. 現在見ている区間の左端と右端を確認する
2. 区間の長さが 1 以下なら、それ以上分割せずに終了する
3. 区間の中央を求めて、左区間と右区間へ分ける
4. 左区間を再帰的にソートする
5. 右区間を再帰的にソートする
6. 左右の区間の先頭同士を比較し、小さい値を一時配列へ入れる
7. どちらかの区間が尽きたら、残りを続けて入れる
8. 一時配列の内容を元配列へ戻す

trace を有効にすると、どの区間を分割しているか、どの値を採用してマージしているかを確認できる。

---

## 4. 計算量

- 時間計算量
  - 最悪: O(n log n)
  - 平均: O(n log n)
  - 最良: O(n log n)

- 空間計算量
  - O(n)

### 補足

- 分割の深さはおおよそ log n
- 各段階で全体をマージするため O(n)
- 一時配列を使うため、追加領域が必要になる

---

## 5. メリット / デメリット

### メリット

- 計算量が安定して O(n log n)
- 分割統治の流れを確認しやすい
- trace で分割とマージの過程を追いやすい

### デメリット

- 一時配列が必要になる
- 基本ソート 3 種より実装が複雑
- 小さい入力では利点が見えにくいことがある

---

## 6. Java実装のポイント

- 元配列をそのまま壊さないよう、最初に `Arrays.copyOf` で複製している
- `mergeSort(...)` で区間を再帰的に分割している
- `leftStart` と `rightEnd` が、現在見ている区間の両端 index になる
- `middleIndex` が、区間を左右へ分ける境界 index になる
- `temp` を、一時的なマージ結果を受ける作業用配列として使っている
- 比較回数と配列への書き込み回数を計測している

---

## 7. コアとなる処理・重要なコードの見どころ

最も重要なのは、左右のソート済み区間を 1 つへ統合する `merge(...)` の処理。

```java
while (leftIndex <= middleIndex && rightIndex <= rightEnd) {
    counter.comparisonCount++;

    if (values[leftIndex] <= values[rightIndex]) {
        temp[tempWriteIndex] = values[leftIndex];
        counter.writeCount++;
        leftIndex++;
    } else {
        temp[tempWriteIndex] = values[rightIndex];
        counter.writeCount++;
        rightIndex++;
    }

    tempWriteIndex++;
}
```

ここで、

- `leftIndex` は左区間の現在位置
- `rightIndex` は右区間の現在位置
- `tempWriteIndex` は一時配列へ次に書き込む位置

を表している。  
左右の先頭値を比較し、小さい方を一時配列へ順に入れていくことで、マージソートの本体処理を実現している。

---

## 8. 実行例

### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic merge-sort --trace
```

### 任意入力

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic merge-sort --input 5,3,8,1,4 --trace
```

### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.sortingrecursion.App --topic merge-sort --size 1000
```

実行時は、入力配列、ソート後配列、比較回数、書き込み回数、参考実行時間を確認できる。  
trace を付けると、区間の分割とマージの流れも確認できる。

---

## 9. 関連トピック

- 再帰
- 分割統治
- バブルソート
- 選択ソート
- 挿入ソート
- クイックソート

# ie03project-introduction_of_java

# 実行コマンドリスト

# TestCase Generator
```java TestCaseGenerator```

|オプション|使用方法|
|---|---|
|-v|-v n でPhaseを指定|

# プログラム実行
```java Phase3_9```

|オプション|使用方法|
|---|---|
|-v|-v n でPhaseを指定|
|-a|全てのPhaseを実行|
|-d|デフォルトのデータファイルを使用する|


# プロット
```python3 graph_plot.py < testcase.txt```


# 検証用
```java Phase3_9 -a -d```


※データが大きすぎると重くなるのでrand.nextInt(100000)>rand.nextInt(1000)ぐらいにする。
```java TestCaseGenerator -v 2 | python3 graph_plot.py```


```java OutputDataFor1_3 < data1_2.txt| python3 graph_plot.py```


```java TestCaseGenerator -v 2 | java OutputDataFor1_3 | python3 graph_plot.py```


```java TestCaseGenerator -v 2 | java Phase3_9 -v 2```

```cat testcase.txt | java Phase3_9 -v 2 > out.txt```


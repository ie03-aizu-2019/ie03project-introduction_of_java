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


#検証用
```java Phase3_9 -a -d```
```java TestCaseGenerator -v 2 | python3 graph_plot.py```
```java OutputDataFor1_3 < data1_2.txt| python3 graph_plot.py```
```java TestCaseGenerator -v 2 | java OutputDataFor1_3 | python3 graph_plot.py```
```java TestCaseGenerator -v 2 | java Phase3_9 -v 2```



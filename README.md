# ie03project-introduction_of_java

## 実行コマンドリスト
# Phase1-1
Python3 ./plot_and_test/graph_plot.py < ./plot_and_test/data1_1.txt

java -classpath ./Phase_1/ DetectCrossingPoint < ./plot_and_test/data1_1.txt


# Phase1-2
Python3 ./plot_and_test/graph_plot.py < ./plot_and_test/data1_2.txt 

java -classpath ./Phase_1/ ListCrossingPoint < ./plot_and_test/data1_2.txt


# Phase1-3
java -classpath ./plot_and_test/ OutputDataFor1_3 < ./plot_and_test/data1_3.txt

Python3 ./plot_and_test/graph_plot.py < ./plot_and_test/after_added_crossing

java -classpath ./Phase_1/ DistanceOfShortestPath < ./plot_and_test/data1_3.txt


# Phase1-4
Python3 ./plot_and_test/graph_plot.py < ./plot_and_test/after_added_crossing

java -classpath  ./Phase_1/ EnumerationOfShortestPath < ./plot_and_test/data1_4.txt


# Phase2_5
Python3 ./plot_and_test/graph_plot.py < ./plot_and_test/after_added_crossing

java -classpath ./Phase_2/ DistanceOfShortestPath < ./plot_and_test/data2_5.txt


# Phase2_6
java -classpath ./Phase_2XXXXX < ../plot_and_test/data2_6.txt


# Phase2_7
java -classpath ./Phase_2/ DistanceOfShortestPath7 < ./plot_and_test/data2_7.txt


# Phase2_8
Python3 ./plot_and_test/graph_plot.py < ./plot_and_test/data2_8_1.txt

java -classpath ./Phase_2/ Phase1_8 < ./plot_and_test/data2_8_1.txt


Python3 ./plot_and_test/graph_plot.py < ./plot_and_test/data2_8_2.txt

java -classpath ./Phase_2/ Phase1_8 < ./plot_and_test/data2_8_2.txt


Python3 ./plot_and_test/graph_plot.py < ./plot_and_test/data2_8_3.txt

java -classpath ./Phase_2/ Phase1_8 < ./plot_and_test/data2_8_3.txt


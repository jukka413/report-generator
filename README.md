# report-generator
Запустить проект можно так:

java -jar report-generator.jar settings.xml source-data.tsv example-report.txt

Для чтения .tsv используется библиотека univocity-parsers
Для повторения символов используется библиотека commons-lang3


Для чтения xml используется StAX


Такж использован maven-compiler-plugin, так как java 11 не смогла компилировать сама.

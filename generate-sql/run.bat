set des=.\des
set conf=.\config
java -Dconf=%conf% -Ddes=%des% -jar .\generate-ui-sql-1.0-SNAPSHOT.jar
pause
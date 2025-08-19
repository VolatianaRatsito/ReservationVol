:: Répertoires de sortie
set OUTPUT_DIR="D:\Students\S5\Mr Naina\Ticketing\Vols\classes"
set LIB= "D:\Students\S5\Mr Naina\Ticketing\Vols\lib"
set FRAMEWORK_JAR=reservation.jar

:: Compilation des fichiers Java
javac -d "D:\Students\S5\Mr Naina\Ticketing\Vols\classes" -cp "D:\Students\S5\Mr Naina\Ticketing\Vols\classes;lib\*" src\models\*.java src\connex\*.java
javac -d "D:\Students\S5\Mr Naina\Ticketing\Vols\classes" -cp "D:\Students\S5\Mr Naina\Ticketing\Vols\classes;lib\*" src\utilDAO\*.java
javac -d "D:\Students\S5\Mr Naina\Ticketing\Vols\classes" -cp "D:\Students\S5\Mr Naina\Ticketing\Vols\classes;lib\*" src\controller\*.java

:: Création du fichier JAR dans le dossier lib
jar -cvf "D:\Students\S5\Mr Naina\Ticketing\Vols\lib\reservation.jar" -C "D:\Students\S5\Mr Naina\Ticketing\Vols\classes" .

echo Compilation et création du JAR réussies

endlocal
pause

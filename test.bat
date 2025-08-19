set url_webapps=C:\apache-tomcat-10.1.7\webapps
set nom_projet=ReservationVol
set url_temp=C:\apache-tomcat-10.1.7\webapps\ReservationVol
set url_lib=lib
set url_src=D:\Students\S5\Mr Naina\Ticketing\Vols\src\controller
set url_xml=D:\Students\S5\Mr Naina\Ticketing\Vols
set url_view=D:\Students\S5\Mr Naina\Ticketing\Vols\web

rmdir "%url_temp%"
mkdir "%url_temp%"
mkdir "%url_temp%\WEB-INF"
mkdir "%url_temp%\WEB-INF\lib"
mkdir "%url_temp%\WEB-INF\classes"
mkdir "%url_temp%\views"

copy "%url_lib%\*.*" "%url_temp%\WEB-INF\lib"
copy "%url_xml%\*.xml" "%url_temp%\WEB-INF"
copy "%url_view%\*.jsp" "%url_temp%\views"

copy "%url_temp%\WEB-INF\lib" "D:\Students\S5\Mr Naina\Ticketing\Vols\lib\*"
javac -parameters -d "%url_temp%\WEB-INF\classes" -cp lib/* src/controller/*.java


set namefilewar=%nom_projet%
jar -cvf "%url_webapps%\%namefilewar%.war" -C "%url_temp%" .

pause








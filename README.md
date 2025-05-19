# ComProgProj

Pang-connect natin sa MariaDB:
  ./MySQL -u reconnect_user -pAVNS_DB3f2_oo-klHmzd1Nxk -h db-mysql-sgp1-information-management-do-user-9437339-0.l.db.ondigitalocean.com -P 25060 -D reconnect

To run Database.java
  javac -cp ".;mysql-connector-j-9.3.0.jar" Database.java && java -cp ".;mysql-connector-j-9.3.0.jar" Database

credentials to use when connecting from vscode:
  url = "jdbc:mysql://db-mysql-sgp1-information-management-do-user-9437339-0.l.db.ondigitalocean.com:25060/reconnect?useSSL=true&requireSSL=true"

  user = "reconnect_user"

  password = "AVNS_DB3f2_oo-klHmzd1Nxk";



FROM SIR VISAYA
  MySQLTest
    For Windows
    javac -cp ".;mysql-connector-j-9.3.0.jar" MySQLTest.java && java -cp ".;mysql-connector-j-9.3.0.jar" MySQLTest

    For Mac
    javac -cp ".:mysql-connector-j-9.3.0.jar" MySQLTest.java && java -cp ".:mysql-connector-j-9.3.0.jar" MySQLTest

  MiniGame
    For Windows
    javac -cp ".;mysql-connector-j-9.3.0.jar" MiniGame.java && java -cp ".;mysql-connector-j-9.3.0.jar" MiniGame

    For Mac
    javac -cp ".:mysql-connector-j-9.3.0.jar" MiniGame.java && java -cp ".:mysql-connector-j-9.3.0.jar" MiniGame

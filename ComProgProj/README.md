## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).



To run the reconnect package: javac -cp ".;mysql-connector-j-9.3.0.jar" src\Reconnect*.java && java -cp ".;mysql-connector-j-9.3.0.jar;src" Reconnect.Main

Pang-connect natin sa MariaDB: ./MySQL -u reconnect_user -pAVNS_DB3f2_oo-klHmzd1Nxk -h db-mysql-sgp1-information-management-do-user-9437339-0.l.db.ondigitalocean.com -P 25060 -D reconnect

To run Database.java: javac -cp ".;mysql-connector-j-9.3.0.jar" Database.java && java -cp ".;mysql-connector-j-9.3.0.jar" Database

credentials to use when connecting from vscode: url = "jdbc:mysql://db-mysql-sgp1-information-management-do-user-9437339-0.l.db.ondigitalocean.com:25060/reconnect?useSSL=true&requireSSL=true"

user = "reconnect_user"

password = "AVNS_DB3f2_oo-klHmzd1Nxk";

FROM SIR VISAYA MySQLTest For Windows javac -cp ".;mysql-connector-j-9.3.0.jar" MySQLTest.java && java -cp ".;mysql-connector-j-9.3.0.jar" MySQLTest

For Mac
javac -cp ".:mysql-connector-j-9.3.0.jar" MySQLTest.java && java -cp ".:mysql-connector-j-9.3.0.jar" MySQLTest
MiniGame For Windows javac -cp ".;mysql-connector-j-9.3.0.jar" MiniGame.java && java -cp ".;mysql-connector-j-9.3.0.jar" MiniGame

For Mac
javac -cp ".:mysql-connector-j-9.3.0.jar" MiniGame.java && java -cp ".:mysql-connector-j-9.3.0.jar" MiniGame

ECHO OFF
ECHO make sure JAVA_HOME is set to JDK folder
ECHO make sure maven is on the system PATH
mvn %1 -f ../ dependency:copy-dependencies package
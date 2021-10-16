REM set the class path,
REM assumes the build was executed with maven copy-dependencies
SET BASE_CP=../helpdesk.daemon.motorFluxos\target\helpdesk.daemon.motorFluxos-1.3.0-SNAPSHOT.jar;../helpdesk.daemon.motorFluxos\target\dependency\*;

REM call the java VM, e.g, 
java -cp %BASE_CP% helpdesk.motorFluxos.MotorFluxoAtividade

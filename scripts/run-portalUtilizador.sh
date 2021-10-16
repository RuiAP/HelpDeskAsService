#!/usr/bin/env bash

#REM set the class path,
#REM assumes the build was executed with maven copy-dependencies
export BASE_CP=../helpdesk.app.portalUtilizador.console/target/helpdesk.app.portalUtilizador.console-1.3.0-SNAPSHOT.jar:../helpdesk.app.portalUtilizador.console/target/dependency/*;

#REM call the java VM, e.g,
java -cp $BASE_CP helpdesk.app.portalUtilizador.console.AppPortalUtilizador

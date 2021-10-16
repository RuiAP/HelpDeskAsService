#!/usr/bin/env bash

#REM set the class path,
#REM assumes the build was executed with maven copy-dependencies
export BASE_CP=../helpdesk.app.servicosERH.console/target/helpdesk.app.servicosERH.console-1.3.0-SNAPSHOT.jar:../helpdesk.app.servicosERH.console/target/dependency/*;

#REM call the java VM, e.g,
java -cp $BASE_CP helpdesk.app.servicosERH.console.AppServicosERH

#!/usr/bin/env bash

#REM set the class path,
#REM assumes the build was executed with maven copy-dependencies
export BASE_CP=../helpdesk.app.bootstrap/target/helpdesk.app.bootstrap-1.3.0-SNAPSHOT.jar:../helpdesk.app.bootstrap/target/dependency/*;

#REM call the java VM, e.g,
java -cp $BASE_CP helpdesk.app.bootstrap.BaseBootstrap

#!/usr/bin/env bash

#REM set the class path,
#REM assumes the build was executed with maven copy-dependencies
export BASE_CP=../helpdesk.daemon.executorTarefas/target/helpdesk.daemon.executorTarefas-1.3.0-SNAPSHOT.jar:../helpdesk.daemon.executorTarefas/target/dependency/*;

#REM call the java VM, e.g,
java -cp $BASE_CP helpdesk.executorTarefas.ExecutorTarefasAutomaticas $1

#!/bin/sh
cd ..

COMMAND=$1
shift

if [ "$JAVA_HOME" = "" ]; then
    echo "Error: JAVA_HOME is not set."
    exit 1
fi


JAVA=$JAVA_HOME/bin/java

CLASSPATH=${CLASSPATH}:$JAVA_HOME/lib/tools.jar
CLASSPATH=${CLASSPATH}:"target/mail-plus-1.0-SNAPSHOT-jar-with-dependencies.jar"


CLASS=edu.sysu.lhfcws.mailplus.client.cli.MailPlusCli

"$JAVA" -classpath "$CLASSPATH" $CLASS

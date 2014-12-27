#!/bin/sh
cd ..

print_usage ()
{
    echo "Usage: sh run.sh COMMAND"
    echo "where COMMAND is one of the follows:"
    exit 1
}

if [ $# = 0 ] || [ $1 = "help" ]; then
    print_usage
fi

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

#!/bin/bash

if [ "$JAVA_HOME" != "" ]; then
    JAVA="$JAVA_HOME/bin/java"
else
    JAVA=java
fi

KILL=kill

WARIOPIDFILE='Wario_Daemon.pid'
WARIOMAIN='com.renren.Wario.Main'
_WARIO_DAEMON="Wario.out"
CLASSPATH=$CLASSPATH:target/Wario-0.1.0-SNAPSHOT-jar-with-dependencies.jar

case $1 in
start)
    echo -n "Starting wario process in background."
    if [ -f $WARIOPIDFILE ]; then
      if kill -0 `cat $WARIOPIDFILE` > /dev/null 2>&1; then
         echo $command already running as process `cat $WARIOPIDFILE`.
         exit 0
      fi
    fi
    nohup $JAVA -cp "$CLASSPATH" $WARIOMAIN > "$_WARIO_DAEMON" 2>&1 < /dev/null &
    if [ $? -eq 0 ]
    then
      if /bin/echo -n $! > "$WARIOPIDFILE"
      then
        sleep 1
        echo STARTED
      else
        echo FAILED TO WRITE PID
        exit 1
      fi
    else
      echo SERVER DID NOT START
      exit 1
    fi
    
    ;;
restart)
    shift
    "$0" stop ${@}
    sleep 3
    "$0" start ${@}
    ;;
stop)

    echo -n "Stopping wario ... "
    if [ ! -f "$WARIOPIDFILE" ]
    then
        echo "no wario to stop (could not find file $WARIOPIDFILE)"
    else
        $KILL -9 $(cat "$WARIOPIDFILE")
        rm "$WARIOPIDFILE"
        echo STOPPED
    fi
    ;;

start-foreground)

    $JAVA -cp "$CLASSPATH" $WARIOMAIN
    ;;
*)
    echo "Usage: $0 {start|start-foreground|stop|restart}" >&2
esac



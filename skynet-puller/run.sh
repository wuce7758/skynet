nowpath=$PWD
java -server  -cp "$nowpath/conf:$nowpath/lib/*"  io.github.fengya90.skynet.SkynetPuller >server.log 2>&1 &     echo $! > skynet-puller.pid

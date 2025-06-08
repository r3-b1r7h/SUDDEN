#! /bin/sh

svn update ..

##svn log -v --xml -r {2007-02-19}:{2007-02-12} > log.xml
svn log -v --xml  .. > logfile.log

java -jar ./statsvn.jar ./logfile.log .. -output-dir SUDDENstatistics -verbose -include "src/**/*.*:WebContent/**/*" -exclude "**/lib/*.*:WebContent/images/*.*:WebContent/css-images/*.*:WebContent/WEB-INF/classes/**/*.*:D5_1Branch/**/*.*"



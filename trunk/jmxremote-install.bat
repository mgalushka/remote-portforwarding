call mvn install:install-file -Dfile=jmxremote.jar -DgroupId=javax.management -DartifactId=jmxremote -Dversion=1.0.1_04 -Dpackaging=jar
call mvn install:install-file -Dfile=jmxremote_optional.jar -DgroupId=javax.management -DartifactId=jmxremote_optional -Dversion=1.0.1_04 -Dpackaging=jar
call mvn install:install-file -Dfile=rmissl.jar -DgroupId=javax.management -DartifactId=jmxremote_ssl -Dversion=1.0.1_04 -Dpackaging=jar

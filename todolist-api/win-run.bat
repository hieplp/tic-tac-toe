CALL ./gradlew clean
CALL ./gradlew shadowJar
CALL java -Djava.net.useSystemProxies=true -jar build/libs/todolist-1.0.jar -conf build/resources/main/config.json


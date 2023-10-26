FROM maven:3.8.5-openjdk-17

WORKDIR /

COPY . .

RUN mvn -DskipTests clean install

CMD mvn -DskipTests spring-boot:run
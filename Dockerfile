# copie lo que se ve en video
#STAGE 1
FROM gradle:jdk21 as builder


# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos de dependencias y los instala
COPY ./build.gradle .
COPY ./settings.gradle .
COPY src ./src

RUN gradle build --no-daemon



#STAGE 2
FROM openjdk:21-jdk-slim
# Copia el resto del código de la API al contenedor
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar discografia.jar

# Expone el puerto que la  API escucha 
EXPOSE 8080  
# Comando para ejecutar tu API
CMD ["java","-jar", "discografia.jar"]

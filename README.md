# redirect
Java Spring service - TEST

# Antes de ejecutar el microservicio
Crear una base MySQL y ejecutar el script contenido en el archivo /start.sql
   - Crea las tablas necesarias
   - Crea los procedures utilizados
   
# Correr el microservicio
Con el comando:
   - java -jar redirect.jar
  
# Indicaciones
   - En la base MySQL se encuentra la tabla 'endpoint', la cual contiene las url que se redireccionarán según el link que se abra.
   - Todas las URL que tengan el siguiente formato:
      - http://{server}:8080/link/{value}
   - La tabla también contiene la cantidad de veces que se puede redigirir a esa url.
   - Si llega al máximo, la siguiente ejecución enviará un error 404.
   - Los datos de las peticiones se guardan en /var/log/JCM001/clicks.json

# Ejemplo
   - Ingresa la URL => http://localhost:8080/link/google
   - El servicio lo redirige a https://www.google.com/

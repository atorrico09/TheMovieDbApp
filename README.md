# TheMovieDbApp
Ejercicio técnico

## Funcionalidades realizadas:
* Listado principal
* Visualizacion del Detalle con animacion al scroll + color de fondo segun predominante de imagen
* Obtencion del genero de cada pelicula
* Suscripcion (local)

## Tecnologias y librerias utilizadas:
* Arquitectura MVVM (Repository Pattern, LiveData, ViewModel, Coroutines)
* Hilt para Inyeccion de Dependencias
* Retrofit para obtencion de datos de la API 
* Room para persistencia local
* Componente Navigation
* Splash con Motion Layout
* RecyclerView para el listado principal


#### Configuracion de API KEY:
* A fin de no publicar la API key de manera publica, esta misma es referenciada a traves del BuildConfig en tiempo de compilacion. Para esto se debe agregar en el root del proyecto un archivo llamado "apikey.properties" y dentro la clave: MOVIE_DB_API_KEY="XXXXXXXXXXXXXXXXXXXXXXX"

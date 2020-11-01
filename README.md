# TheMovieDbApp
Ejercicio técnico

## Funcionalidades realizadas:
* Listado principal
* Visualizacion del Detalle con animacion al scroll + color de fondo segun predominante de imagen
* Obtencion del genero de cada pelicula
* Suscripcion (local)

## Tecnologias y librerias utilizadas:
* Arquitectura MVVM (Repository Pattern, LiveData, ViewModel, Coroutines)
* Kotlin
* Android Jetpack
* Hilt para Inyeccion de Dependencias
* Retrofit2 para obtencion de datos de la API 
* Room para persistencia local
* Componente Navigation
* Databinding
* Glide para manejo de imagenes
* RecyclerView
* Splash con Motion Layout
* Material Design



#### Configuracion de API KEY:
* A fin de no publicar la API key de manera publica, esta misma es referenciada a traves del BuildConfig en tiempo de compilacion. Para esto se debe agregar en el root del proyecto un archivo llamado "apikey.properties" y dentro la clave: MOVIE_DB_API_KEY="XXXXXXXXXXXXXXXXXXXXXXX"

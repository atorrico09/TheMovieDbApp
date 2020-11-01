# TheMovieDbApp
Ejercicio técnico

## Funcionalidades realizadas:
* Listado principal
* Detalle con animacion al scroll y color de fondo segun imagen
* Obtencion del genero de cada pelicula y visualizacion en cada item
* Suscripcion (local)

## Tecnologias y librerias utilizadas:
* Arquitectura MVVM (Repository Pattern, LiveData, ViewModel, Coroutines)
* Hilt para Inyeccion de Dependencias
* Room para persistencia local
* Componente Navigation
* Splash con Motion Layout
* RecyclerView para el listado principal


#### Configuracion de API KEY:
* A fin de no publicar la API key de manera publica, esta misma es referenciada a traves del BuildConfig. Para esto se debe agregar en el root del proyecto un archivo llamado "apikey.properties" y dentro la clave: MOVIE_DB_API_KEY="XXXXXXXXXXXXXXXXXXXXXXX"

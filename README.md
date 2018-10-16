# TestBusquedaEdson

Prueba realizada con los requerimientos solicitados; para ello se utilizó la siguiente arquitectura.

  - Se implemento patron MVP para el manejo de vista, su interacción y lógica
  - Se implemento Retrofit para el consumo de servicios de la api proporcionada
  - Se implemento SQLite para manejo de base de datos y funcionamiento offline
  - Se implemento recycleview para la grilla de resultado y fuera más eficiente el renderizado
  - Se implemento Glide para cargar imagenes desde la url
  - Se implemento CircleImageView para mostrar las imagenes en circulo
  - Se manejo los principios SOLID
  - Se implmento progressdialog el cual se muestra al momento de realizar el cargue de datos
  - En la grilla se puede seleccionar cualquiera de los hipervinculos para que sea redirigido al navegador y muestre información adicional. 
  - Se utilizó GSON para el serializado de objetos al momento de obtener el response del servicio.

# Link acceso a código fuente

  - https://github.com/jorelk19/TestBusquedaEdson.git
  

# Funcionamiento offline

A medida que el usuario vaya relizando búsquedas online, estos resultados se van almacenando para cuando se ejecute la aplicación offline pueda mostrar la información.

Para éste funcionamiento, se crearon las siguientes tablas:
  - Categoria
  - Track
  - Album
  - Artist
  - Image

Estas tablas se encargan de almacenar la información que se menciono anteriormente al momento de realizar una búsqueda online.

# Funcionamiento online

Cuando el usuario posea acceso a internet, la aplicación consumirá los servicios expuestos en la api https://www.last.fm/api, para ello utilice los siguientes métodos:

- Album.search
- Artist.getTopAlbums
- Track.search

Para poderlos utilizar, realice el registro en la pagina https://www.last.fm para que se me asignara un API_KEY para el consumo respectivo.

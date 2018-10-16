# TestBusquedaEdson

Prueba realizada con los requerimientos solicitados; para ello se utiliz� la siguiente arquitectura.

  - Se implemento patron MVP para el manejo de vista, su interacci�n y l�gica
  - Se implemento Retrofit para el consumo de servicios de la api proporcionada
  - Se implemento SQLite para manejo de base de datos y funcionamiento offline
  - Se implemento recycleview para la grilla de resultado y fuera m�s eficiente el renderizado
  - Se implemento Glide para cargar imagenes desde la url
  - Se implemento CircleImageView para mostrar las imagenes en circulo
  - Se manejo los principios SOLID
  - Se implmento progressdialog el cual se muestra al momento de realizar el cargue de datos
  - En la grilla se puede seleccionar cualquiera de los hipervinculos para que sea redirigido al navegador y muestre informaci�n adicional. 
  - Se utiliz� GSON para el serializado de objetos al momento de obtener el response del servicio.

# Link acceso a c�digo fuente

  - https://github.com/jorelk19/TestBusquedaEdson.git
  

# Funcionamiento offline

A medida que el usuario vaya relizando b�squedas online, estos resultados se van almacenando para cuando se ejecute la aplicaci�n offline pueda mostrar la informaci�n.

Para �ste funcionamiento, se crearon las siguientes tablas:
  - Categoria
  - Track
  - Album
  - Artist
  - Image

Estas tablas se encargan de almacenar la informaci�n que se menciono anteriormente al momento de realizar una b�squeda online.

# Funcionamiento online

Cuando el usuario posea acceso a internet, la aplicaci�n consumir� los servicios expuestos en la api https://www.last.fm/api, para ello utilice los siguientes m�todos:

- Album.search
- Artist.getTopAlbums
- Track.search

Para poderlos utilizar, realice el registro en la pagina https://www.last.fm para que se me asignara un API_KEY para el consumo respectivo.

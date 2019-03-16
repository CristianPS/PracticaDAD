# Fiesta Paga-Na'

## Descripción

La web es una plataforma de encuentro entre lugares de ocio y gente que quiere pasarlo bien. En la web, los bares, pubs, discotecas... Podrán poner sus ofertas, y los usuarios podrán beneficiarse de ellas. 
La web tendrá dos zonas bien diferenciadas, la correspondiente a los dueños o representantes de los establecimientos y la de los consumidores.
La parte de los usuarios será publica, aunque tendrán también opción a loguearse para poder tener ciertas ventaja, como poder guardar sus sitios favoritos, recibir publicidad con las mejores ofertas, obtener puntos, etc.
La parte de los establecimientos será completamente privada. En ella podrán añadir ofertas, dar los datos del bar, usar las diferentes opciones de promoción, etc.

## Entidades

  * Usuario Cliente
  * Usuario Empresa
  * Comercio
  * Valoracion
  * Oferta
  
 ## Servicios internos
 
 * Mandar ofertas por email. 
 * Generar PDF con oferta (para imprimir o guardar en el equipo)
 * Acceder a las diferentes bases de datos.

 ## Alumnos
 
   Cristian Posada Santos
  * c.posada@alumnos.urjc.es
  * https://github.com/CristianPS
  
   Carlos Gil Sabrido
  * c.gilsab@alumnos.urjc.es
  * https://github.com/carls52
  
   Jose Ignacio Díaz Errejón
  * ji.diaze@alumnos.urjc.es
  * https://github.com/sito22

 ## Diagrama Modelo de Datos
![UML](https://github.com/CristianPS/PracticaDAD/blob/master/PracticaDAD/src/screenshots/uml.png)

## Diagrama de clases y templates

![UML](https://github.com/CristianPS/PracticaDAD/blob/master/PracticaDAD/src/screenshots/uml.png)

 ## Diagrama Entidad Relación
 ![Entidad Relación](https://github.com/CristianPS/PracticaDAD/blob/master/PracticaDAD/src/screenshots/modeloEntidadRelacion.JPG)
 
 ## Diagrama de Navegación
  ![Navegación](https://github.com/CristianPS/PracticaDAD/blob/master/PracticaDAD/src/screenshots/diagramaNavegacion.PNG)
  
 ## Screenshots
 * Index
![Index](https://github.com/CristianPS/PracticaDAD/blob/master/PracticaDAD/src/screenshots/index.png)
   En esta pestaña cualquier usuario que no este loggeado podrá ver únicamente las mejores ofertas que se encuentran en la base de datos. Para poder verlas, deberá iniciar sesión como usuario cliente. Esto lo podrá hacer accediendo a cualquiera de las ofertas, haciendo click en el menú ofertas, o en el menú login.

 * Login
![Login](https://github.com/CristianPS/PracticaDAD/blob/master/PracticaDAD/src/screenshots/login.png)
En esta pestaña se procederá al inicio de sesión en el caso de que la persona que está accediendo tenga un usuario ya creado. Para ello deberá introducir su nombre de usuario y su contraseña. Además, podrá elegir mediante un "switch" si desea iniciar sesión como usuario cliente o como empresario. En caso de que no tenga un usuario ya creado, podrá registrarse haciendo click en el botón de registro.

 * Registro de Usuario
![Registro de Usuario](https://github.com/CristianPS/PracticaDAD/blob/master/PracticaDAD/src/screenshots/registro.png)
En esta pestaña se procederá al registro de un usuario cliente. Para ello deberá introducir todos los datos requeridos y tras completar el registro, el usuario se guardará en la base de datos.

 * Registro de Empresa
![Registro de Empresa](https://github.com/CristianPS/PracticaDAD/blob/master/PracticaDAD/src/screenshots/registroEmpresa.png)
En esta pestaña se procederá al registro de un empresario. Para ello deberá introducir todos los datos requeridos y tras completar el registro, el empresario se guardará en la base de datos.

 * Index Usuario
![Index Usuario](https://github.com/CristianPS/PracticaDAD/blob/master/PracticaDAD/src/screenshots/indexUsuario.png)
En esta pestaña el usuario ya se ha loggeado y por tanto puede acceder a las mejores ofertas de la base de datos, podrá visualizar todas las ofertas en la pestaña "ofertas", podrá acceder a su perfil en la pestaña en la cual pone su nombre y podrá cerrar sesión.

 * Ofertas
![Ofertas](https://github.com/CristianPS/PracticaDAD/blob/master/PracticaDAD/src/screenshots/ofertas.png)
En esta pestaña el usuario ya se ha loggeado y por tanto puede visualizar todas las ofertas guardadas en la base de datos, pudiendo acceder a cada una de ellas mediante el botón acceder. También podrá volver a la página de inicio, acceder a su perfil y cerrar sesión.

 * Oferta Particular
![Oferta Particular](https://github.com/CristianPS/PracticaDAD/blob/master/PracticaDAD/src/screenshots/ofertaParticular.png)
En esta pestaña el usuario ya se ha loggeado y está visualizando una oferta en particular de la base de datos. Cada oferta tiene una serie de campos, entre los cuales se encuentran valoracion y comentarios. Estos dos campos podrán ser modificados por los usuarios clientes, es decir, un cliente podrá añadir un comentario a la oferta y borrarlo, y valorar la oferta.

 * Perfil Usuario
![Perfil Usuario](https://github.com/CristianPS/PracticaDAD/blob/master/PracticaDAD/src/screenshots/perfil.png)
En esta pestaña el usuario ya se ha loggeado y está visualizando su perfil, en el cual encuentra todos los datos que ha introducido previamente en el registro. Algunos de estos datos son modificables y otros no. Si el usuario quiere editar algún campo editable, deberá hacer click en el botón "editar" y posteriormente editar dicho o dichos campos. Finalmente dando al botón "guardar" podrá guardar los cambios realizados, lo que quiere decir que se modificarán los datos en la base de datos.

 * Mis Comercios
 ![Mis Comercios](https://github.com/CristianPS/PracticaDAD/blob/master/PracticaDAD/src/screenshots/misComercios.png)
En esta página se muestra al empresario sus comercios con la información de cada uno. Desde aquí podremos acceder a un comercio en particular, o añadir una oferta a ese comercio.

 * Crear Comercio
 ![Crear Comercio](https://github.com/CristianPS/PracticaDAD/blob/master/PracticaDAD/src/screenshots/crearComercio.png)
 Esta página sirve al empresario para añadir un nuevo comercio dando los datos de este, y una vez ahí, poder usarlo como el resto.
 
 * Comercio Particular
 ![Comercio Particular](https://github.com/CristianPS/PracticaDAD/blob/master/PracticaDAD/src/screenshots/comercioParticular.png)
 Desde aquí podemos ver la información de un comercio en particular, y editarla.
 
 * Mis Ofertas (Index Empresa)
![Mis Ofertas](https://github.com/CristianPS/PracticaDAD/blob/master/PracticaDAD/src/screenshots/misOfertas.png)
En esta pestaña el empresario ya se ha loggeado y está visualizando todas las ofertas que ha subido a la base de datos. Pudiendo acceder a cada una de ellas mediante el botón acceder para poder visualizar los datos de la oferta y modificarla o borrarla.

 * Crear Oferta
![Crear Oferta](https://github.com/CristianPS/PracticaDAD/blob/master/PracticaDAD/src/screenshots/crearOferta.png)
En esta pestaña el empresario ya se ha loggeado y está procediendo a añadir una nueva oferta. Para ello deberá rellenar todos los campos requeridos y tras terminar, la nueva oferta se guardará en la base de datos.

 * Oferta Particular Empresa
![Oferta Particular Empresa](https://github.com/CristianPS/PracticaDAD/blob/master/PracticaDAD/src/screenshots/ofertaEmpresa.png)
En esta pestaña el empresario ya se ha loggeado y está visualizando una oferta en particular. Así podrá modificar todos los datos que sean editables, y también podrá eliminar la oferta.

 * Perfil Empresa
![Perfil Empresa](https://github.com/CristianPS/PracticaDAD/blob/master/PracticaDAD/src/screenshots/perfilEmpresa.png)
En esta pestaña el empresario ya se ha loggeado y está visualizando los datos de uno de los comercios que regenta, para poder modificar aquellos campos que sean editables.

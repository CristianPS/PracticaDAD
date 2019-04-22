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

![UML](https://github.com/CristianPS/PracticaDAD/blob/Security/PracticaDAD/src/screenshots/imagenDC2.png)

* Color marrón clarito: representa las templates del proyecto.
* Color azul: representa los controladores anotados con @Controller.
* Color morado: representa los repositorios.
* Color verde: representa las clases de objetos.
* Color rosa: representa el servicio interno anotado con @Service.
* Color amarillo: representa la clase que se encarga de proporcionar la seguridad en las páginas. Indica a qué páginas se puede acceder sin haber iniciado sesión y a cuales no, e incorpora la funcionalidad del formulario de login.
* Color gris: representa la clase que se encarga de permitir el inicio de sesión cuando un usuario se va a autenticar.

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
![Ofertas](https://github.com/CristianPS/PracticaDAD/blob/Security/PracticaDAD/src/screenshots/Ofertas2.PNG)
En esta pestaña el usuario ya se ha loggeado y por tanto puede visualizar todas las ofertas guardadas en la base de datos, pudiendo acceder a cada una de ellas mediante el botón acceder. También podrá volver a la página de inicio, acceder a su perfil y cerrar sesión.

 * Oferta Particular
![Oferta Particular](https://github.com/CristianPS/PracticaDAD/blob/Security/PracticaDAD/src/screenshots/ofertaParticular2.PNG)
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

 * Contraseña olvidada
 ![Contraseña olvidada](https://github.com/CristianPS/PracticaDAD/blob/Security/PracticaDAD/src/screenshots/nuevaPass.PNG)
 En el caso de que un usuario olvide su contraseña si escribe su nombre de usuario y presiona aceptar recibirá en correo electrónico con   su nueva contraseña.
 * Error de login
 ![Error de login](https://github.com/CristianPS/PracticaDAD/blob/Security/PracticaDAD/src/screenshots/loginError.PNG)
Si iniciando sesión hubiera algún error o la información introducida no sea correcta se mostrará esta pantalla.

* Error de registro
 ![Error de registro](https://github.com/CristianPS/PracticaDAD/blob/Security/PracticaDAD/src/screenshots/registroError.PNG)
Si hubiera algún error a la hora de registrarse o lya exista un usuario con ese nombre se mostrará esta pantalla.

## Instrucciones para desplegar la aplicacion en una maquina virtual

1. Generamos los archivos jar de cada proyecto de la siguiente manera:
   > Click derecho en el proyecto -> Run as... -> Maven build... -> Goals -> clean package -> Run

     
     
2. Instalamos Vagrant y VirtualBox.

3. Creamos una carpeta en Windows que se llame "vagrant" y dentro de ella creamos otra carpeta llamada "spring".

4. > Ejecutamos CMD -> cd ~/vagrant/spring -> vagrant init ubuntu/trusty64 

     Con este comando descargamos una máquina virtual de Ubuntu.

5. Dentro de Vagrantfile descomentamos la siguiente línea y añadimos las dos siguientes:
   > config.vm.network "private_network", ip: "192.168.33.10"
   
   > config.vm.network "forwarded_port", guest: 7777, host: 7777, host_ip: "127.0.0.1"
   
   > config.vm.network "forwarded_port", guest: 8443, host: 8443, host_ip: "127.0.0.1"
   
   Con esto hacemos que nuestra VM se pueda ver en la red privada, y que se redireccionen los puertos 7777 (Puerto del servicio Interno) y 8443 (puerto del Cliente) hacia la VM.

6. > cmd -> cd ~/vagrant/spring -> vagrant up -> vagrant ssh

     Con esto iniciamos nuestra VM y nos conectamos a ella a través de SSH

7. Instalamos java dentro de la maquina virtual
   > sudo add-apt-repository ppa:webupd8team/java -y 
   
   > sudo apt-get update 
   
   > sudo apt-get install oracle-java8-installer

8. Instalamos MySQL dentro de la máquina virtual 
   > sudo apt-get install mysql-server 

   > sudo apt-get install mysql-workbench 

     Cuando nos pidan introducir una contraseña introducimos la contrasela "pass". Posteriormente tenemos que crear la base de datos, para ello ejecutamos el siguiente comando: 
   > mysql -u root -p 

     Introducimos la contraseña "pass". Con esto nos encontraremos dentro de mysql, para crear la base de datos debemos introducir 
   > CREATE DATABASE _nombre de la BBDD_;

9. Nos metemos en la carpeta que comparte la VM con el host con el siguiente comando:
   > cd /vagrant 
    
   Dentro de la carpeta spring de Windows debemos añadir los jar generados previamente para poder ejecutarlos. Para ejecutar cualquiera de los dos jar debemos poner el siguiente comando:
   > java -jar _nombre del fichero jar_

## Formato de los mensajes entre el Cliente y el Servicio Interno

El cliente va a pasar mensajes al Servicio Interno en dos ocasiones: Cuando quiera obtener una oferta y cuando haya olvidado su contraseña. Para esto, tenemos que distinguir primero entre los dos tipos de mensajes. En el mensaje que se envía, lo primero que aparecerá será un 0 en el caso de obtener oferta o un 1 en el caso de haber olvidado la contraseña, y en cualquiera de los dos casos va un punto después.
Una vez que ya hemos diferenciado entre los dos tipos de mensajes, vamos a pasar a describir cada uno por separado:
 * Obtener Oferta:
   > 0_*idOferta*-_username_
   
   _idOferta_ es el ID que corresponde a la oferta que queramos obtener, y _username_ el nombre del usuario que quiere obtener la oferta, al que se le enviará un email con un PDF con la oferta y un código QR.
   
 * Contraseña Olvidada:
   > 1_*username*
   
   _username_ es el nombre del usuario que ha olvidado su contraseña, al que se le enviará un correo con la nueva contraseña.

## Formación de los docker

Tenemos 5 dockers, dos para las dos instancias de la aplicacion web, uno para el servicio interno, otro para la base de datos mysql y otro para el balanceador de carga haproxy.
Todos los dockers están dentro del mismo docker-machine que tiene la ip 192.168.99.100.

* Docker cliente1: 
 Expone el puerto 8082 para que el balanceador de carga pueda verlo y comunicarse con él.
 
* Docker cliente2: 
 Expone el puerto 8083 para que el balanceador de carga pueda verlo y comunicarse con él.
 
* Docker servicioInterno: 
 Expone el puerto 7777 que es el usado para la comunicacion entre el servicio interno y los clientes a través de los websockets.
 
* Docker mysql: 
 Expone el puerto 3306 correspondiente con el puerto en el que se ejecuta la base de datos. Así se pueden comunicar con esta tanto los dos clientes como el servicio interno.
 
* Docker haproxy: 
 Expone el puerto 8443 que es el unico puerto al que puede acceder el usuario para ver la pagina web.

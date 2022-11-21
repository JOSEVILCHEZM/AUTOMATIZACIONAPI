# language: es
Característica: Ejecucion de una api


  @GET_LISTAR_USUARIOS
  Escenario: Listar usuarios de un servicio
    Dado que configuro las cabeceras
      | cabeceras    | value            |
      | Content-Type | application/json |
    Y configuro los query params
      | key  | value |
      | page | 2     |
    Cuando ejecuto el api
      | method | url                         |
      | GET    | https://reqres.in/api/users |

    Entonces valido el status code sea "200"

    Y valido la respuesta del servicio
      | nodo               | value   |
      | page               | 2       |
      | data[0].id         | 7       |
      | data[0].first_name | Michael |
      | data[0].last_name  | Lawson  |

  @GET_OBTENER_USUARIO_ID
  Escenario: Obtener un usuario por id
    Dado que configuro las cabeceras
      | cabeceras    | value            |
      | Content-Type | application/json |
    Y configuro los path variables
      | key | value |
      | id  | 2     |
    Cuando ejecuto el api
      | method | url                              |
      | GET    | https://reqres.in/api/users/{id} |
    Entonces valido el status code sea "200"


    Y valido la respuesta del servicio

      | nodo           | value  |
      | data.last_name | Weaver |

  @POST_CREAR_USUARIO
  Escenario: Crear un nuevo usuario

    Dado que configuro las cabeceras
      | cabeceras    | value            |
      | Content-Type | application/json |
    Y configuro el body request "crear-usuario.json"
      | key  | value           |
      | name | Test 0001       |
      | job  | Automation Apis |
    Cuando ejecuto el api
      | method | url                         |
      | POST   | https://reqres.in/api/users |
    Entonces valido el status code sea "201"
    Y valido la respuesta del servicio
      | nodo | value           |
      | job  | Automation Apis |
    Y valido el esquema de respuesta "crear-usuario-schema.json"

  @DELETE_ELIMINAR_USUARIO
  Escenario: Eliminar un usuario por id
    Dado que configuro las cabeceras
      | cabeceras    | value            |
      | Content-Type | application/json |
    Y configuro los path variables
      | key       | value |
      | idUsuario | 2     |
    Cuando ejecuto el api
      | method | url                                     |
      | DELETE | https://reqres.in/api/users/{idUsuario} |
    Entonces valido el status code sea "204"















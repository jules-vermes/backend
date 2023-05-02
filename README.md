
# Backend projet Dev web avancé

Ce repo héberge mon code Backend realisé avec Java Spring Boot

## API Reference

#### Get all items

```http
  GET /allHouses
```

Retourne toutes les maisons contenues dans la BDD

#### Get house by Id

```http
  GET /HouseId${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Requis** Id de l'item a récupérer |


#### Post house

```http
  POST /house
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `house`      | `house` | **Tous les champs sont requis** Maison que l'on souhaite créer|


#### Delete house

```http
  DELETE  /house
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Requis** Id de l'item a supprimer |




#### Delete house

```http
  DELETE  /deleteAll

```

Supprime toutes les maisons de la BDD.

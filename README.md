```shell script
docker-compose --file compose.yaml up --detach
```

```
curl -L -X POST 'http://localhost:8080/realms/quarkus-exam/protocol/openid-connect/token' \                                                                                                                                                            ─╯
-H 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=quarkus-exam-client' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'client_secret=**********' \
--data-urlencode 'scope=openid' \
--data-urlencode 'username=user' \
--data-urlencode 'password=password'
```

[Kafka](http://localhost:29092)
[Infinispan](http://localhost:11222/console)
[Postgres](postgresql::/localhost:5432)
[Keycloak](http://localhost:8080/)
[Keycloak Realm Configuration](http://localhost:8080/realms/quarkus-exam/.well-known/openid-configuration)
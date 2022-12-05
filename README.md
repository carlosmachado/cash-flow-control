# cash-flow-control

criar o container postgres:
docker run --name postgres-db -e POSTGRES_PASSWORD=docker -p 5432:5432 -d postgres

criar uma database para o app:
docker exec -it postgres-db psql -U postgres -c "create database cash_flow"

criar uma database para os testes integrados:
docker exec -it postgres-db psql -U postgres -c "create database cash_flow_test"

criar o container rabbitmq:
docker run -d -p 5672:5672 -p 15672:15672 --name my-rabbit rabbitmq:3-management


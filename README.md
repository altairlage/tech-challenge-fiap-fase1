# tech-challenge-fiap-fase1

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
\
&nbsp;
\
&nbsp;

Projeto de sistema de gestão de restaurantes - SGR, desenvolvido em grupo para a conclusão da primeira fase do curso de pós-graduação de desenvolvimento e arquitetura Java da FIAP.

O projeto é desenvolvido em Java, com o framework Spring MVC e Spring Boot, utilizando banco de dados MySQL. A aplicação será encapsulada em containers Docker, permitindo assim, provisionar todos seus recursos com docker-compose.

O banco utilizado foi o MySQL, utilizando container mysql:8.0. Seu container é criado a partir da imagem base mysql:8.0, e os scripts do diretorio `sql_scripts` são executados em sua inicialização.
- schema.sql -> Cria a estrutura de tabelas do banco.
- data.sql -> Cria alguns usuarios de teste na tabela `usuarios` para teste.
\
&nbsp;
\
&nbsp;

# Compilando a aplicação
Neste momento, a aplicação precisa ser compilada antes de poder ser executada em seus containers Docker.

Para isso, abra o projeto da pasta `sgr` em uma IDE Java e rode a rotina de `install` do maven. No Intellij:
![intellij](./doc_resources/maven_install.png)

Pelo console execute o comando `mvn clean install`.

Isso irá compilar a aplicação e criar o arquivo `sgr/target/sgr-0.0.1.jar` necessario para build do container da aplicação.
\
&nbsp;
\
&nbsp;

# Comandos para rodar a aplicação
Para iniciar todos os containers e acessar a aplicação: `docker-compose up`.
Por default o arquivo docker-compose.yaml será utilizado. Que faz:

1. Baixa o container padrão do MySQL.
    1. Configura o DB MySQL (usuarios, senhas, etc.).
    1. Cria o banco de dados 'sgr' (tabelas, etc.).
    1. Cria dados de teste na tabela 'usuarios'.
1. Constroi o container da aplicação.
    1. Baixa o container padrao openjdk.
    1. Copia o arquivo da aplicação para dentro do container.
    1. Cria um entrypoint com o comando de execução da aplicação.
\
&nbsp;
\
&nbsp;

## Exemplos:
Execução do comando de inicialização:
![docker_compose_up1](./doc_resources/docker_compose_up1.png)

Criação e configuração do DB:
![docker_compose_up2](./doc_resources/docker_compose_up2.png)

Teste da aplicação:
![teste1](./doc_resources/teste1.png)
![teste2](./doc_resources/teste2.png)
\
&nbsp;
\
&nbsp;

# Comandos para parar a aplicação
Normalmente todos os containers provisionados são encerrados sem problemas com execução do comando `docker-compose down` no diretório em que o arquivo `docker-compose.yaml` está. 

Existem situações em que os containers podem ficar rodando devido a problemas de execução ou falhas de inicialização.

## Comandos de cleanup
Em caso de problemas para provisionar os containers, execute os comandos abaixo:

- `docker-compose down -v`: Remove os serviços do Docker Compose junto com seus volumes.
    - `down`: Parar e remover contêineres, redes e imagens criados pelo arquivo docker-compose.yml.
    - `-v`: Remove também os volumes associados aos serviços.
- `docker system prune -af`: Remove todos os recursos não utilizados (contêineres, imagens, redes e volumes).
    - `system prune`: Limpa todos os recursos não utilizados (contêineres, redes, imagens, e volumes).
    - `-a`: Remove todas as imagens não utilizadas, não apenas as "dangling" (aquelas sem tags ou sem contêineres associados).
    - `-f`: Força a execução do comando sem solicitar confirmação.
- `docker volume prune -f`: Remove somente volumes órfãos (não utilizados por nenhum contêiner).
    - `volume prune`: Exclui volumes que não estão atualmente em uso.
    - `-f`: Força a execução do comando sem pedir confirmação.

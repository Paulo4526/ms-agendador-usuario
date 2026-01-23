#Iniciando o primeiro estage que onde será salvo os arquivos da pasta o diretório WORKDIR, lembrando que essa etapa é feita para salvar o projeto que será a base para a instalação do segundo estágio mantendo o arquivo integro.
#Baixando via maven a versão do java 21 que será compilado
FROM gradle:9.2-jdk21 as build
#Criando o diretório na raiz do projeto /opt/app
RUN mkdir /opt/app
#Copiando tdo o projeto da pasta raiz onde está o docker file para o diretório criado acima
#O ponto informa o diretório raiz, sendo assim tudo que está no diretório será copiado
COPY . /opt/app
#Definindo o ditetório padrão de trabalho sendo a pasta app
WORKDIR /opt/app
#Rodando o comando MVN clean para limpeza da pasta build ou target ou fica a copia da aplicação, e package em mvn ou build em gradle para criar o artefato snapshot
#O -x test ignora a fase de testes que ainda não está pronta
RUN gradle clean build -x test --no-daemon


#Iniciando o segundo stage, onde será feito a inicialização da aplicação baseado no primeiro estage, onde estão salvos os arquivos.
FROM eclipse-temurin:21-jre-alpine
#Criando o diretório na raiz do projeto /opt/app par a acopia do artefato e criação da imagem no docker
RUN mkdir /opt/app
#Copiando o artefato snapshot.jar dentro da aplicação para a pasta opt/app dentro do docker, além disso alterando o nome do artefato snapshoot terminado em jar para app.jar
COPY --from=build /opt/app/build/libs/*.jar /opt/app/app.jar
#Definindo o diretório padrão dentro da imagem docker
WORKDIR /opt/app
#Porta de entrada da aplicação
EXPOSE 8080
#Comando de entrada para inicio da aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

#Comando para criar a imagem docker: docker build -t nome-do-projeto .
#Comando para criar o contaniner a partir de uma imagem no projeto docker: docker run -dti -p 8080:8080 --name bff-projeto-agendador nome-do-projeto
#Explicação de comandos:
#docker run -> comando que cria o container a partir da imagem
#-dti -> comando para que ele tenha diretiva, e iteração
# -p 8086:8080 -> a porta 8080 do container será exposta para fora como porta 8080
# nome-do-projeto -> nome da imagem criada no primeiro comando onde será utilizada como base para criação do nosso container

#Parametros adicionais:
# --name -> parametro para setar o nome do container a ser criado
# -e -> parametro para indicar uma variavel de ambiente caso setada
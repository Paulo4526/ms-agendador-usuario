<h1 align="center">👤 ms-projeto-agendador-usuario</h1>

<p align="center">
  <strong>Microserviço principal de gestão de usuários e autenticação</strong><br>
  Serviço <strong>central</strong> do <strong>Projeto Agendador</strong>
</p>

<p align="center">
  Java • Spring Boot • JPA • Banco Relacional • JWT • Segurança • Microsserviços • Docker • CI/CD
</p>

<hr>

<hr>

<h2>🧩 Arquitetura do Projeto Agendador</h2>

<p>
  O <strong>Projeto Agendador</strong> é composto por <strong>4 microsserviços</strong>,
  que trabalham de forma integrada e devem ser iniciados em uma
  <strong>ordem específica</strong> para garantir o funcionamento correto do sistema.
</p>

<h3>📌 Ordem de Execução dos Microsserviços</h3>

<ol>
  <li>
    <strong>ms-projeto-agendador-usuario</strong><br>
    Microsserviço <strong>principal</strong> e <strong>obrigatório</strong>, responsável
    por autenticação, autorização e gestão de usuários.<br>
    <em>Deve estar em execução antes de todos os outros serviços.</em>
  </li>
  <br>

  <li>
    <strong>ms-projeto-agendador-tarefas</strong><br>
    Responsável pelo agendamento e gerenciamento de tarefas, consumindo
    autenticação do microsserviço de usuários.<br>
    🔗 Repositório:
    <a href="https://github.com/Paulo4526/ms-projeto-agendador-tarefas" target="_blank">
      https://github.com/Paulo4526/ms-projeto-agendador-tarefas
    </a>
  </li>
  <br>

  <li>
    <strong>ms-projeto-agendador-notificacao</strong><br>
    Microsserviço responsável pelo envio de notificações (e-mails),
    acionado a partir dos eventos de tarefas.<br>
    🔗 Repositório:
    <a href="https://github.com/Paulo4526/ms-projeto-agendador-notificacao" target="_blank">
      https://github.com/Paulo4526/ms-projeto-agendador-notificacao
    </a>
  </li>
  <br>

  <li>
    <strong>ms-projeto-agendador-bff</strong><br>
    Backend for Frontend responsável por centralizar, orquestrar e expor
    as APIs para o frontend, consumindo os demais microsserviços.<br>
    🔗 Repositório:
    <a href="https://github.com/Paulo4526/ms-projeto-agendador-bff" target="_blank">
      https://github.com/Paulo4526/ms-projeto-agendador-bff
    </a>
  </li>
</ol>

<p>
  ⚠️ <strong>Importante:</strong> A aplicação deve ser executada exatamente
  na ordem acima, pois cada microsserviço depende dos anteriores
  para autenticação, comunicação e processamento correto.
</p>

<hr>

<h2>🚀 Tecnologias Utilizadas</h2>

<h3>🧠 Linguagem & Framework</h3>
<ul>
  <li><strong>Java 21</strong></li>
  <li><strong>Spring Boot</strong></li>
  <li>Spring Web</li>
  <li>Spring Data JPA</li>
  <li>Spring Security</li>
</ul>

<h3>🔐 Segurança</h3>
<ul>
  <li>JWT (JSON Web Token)</li>
  <li>Autenticação stateless</li>
  <li>Filtros de segurança customizados</li>
  <li>Controle de acesso por roles</li>
</ul>

<h3>🗄️ Persistência</h3>
<ul>
  <li>Banco de dados relacional Postgres</li>
  <li>JPA / Hibernate</li>
</ul>

<h3>🔗 Integrações</h3>
<ul>
  <li>Serviço central de autenticação</li>
  <li>Validação de token JWT entre microsserviços</li>
</ul>

<h3>🛠 Build & Infraestrutura</h3>
<ul>
  <li>Gradle</li>
  <li>Docker</li>
  <li>Docker Compose</li>
  <li>GitHub Actions (CI)</li>
</ul>

<hr>

<h2>🐳 Execução com Docker</h2>

<h3>📦 Criar a imagem Docker</h3>

<pre><code>docker build -t ms-projeto-agendador-usuario .</code></pre>

<h3>🚀 Criando a imagem com compose e subindo a aplicação com Docker Compose</h3>

<pre><code>docker compose build api .</code></pre>
<pre><code>docker compose up -d</code></pre>

<p>
  Após a inicialização, o microsserviço de usuários estará disponível
  e pronto para ser consumido pelos demais serviços do ecossistema.
</p>

<hr>

<h2>🏗️ Conceitos Arquiteturais</h2>

<ul>
  <li>Arquitetura de <strong>Microsserviços</strong></li>
  <li>Serviço central de identidade (User Core Service)</li>
  <li>Separação de responsabilidades (Controller, Service, Repository)</li>
  <li>DTO para transporte de dados</li>
  <li>Mapeamento DTO ↔ Entity</li>
  <li>Injeção de Dependência (IoC / DI)</li>
  <li>Autenticação e autorização via JWT</li>
  <li>Segurança stateless</li>
  <li>Tratamento de exceções personalizadas</li>
  <li>Configuração por variáveis de ambiente</li>
  <li>Containerização com Docker</li>
</ul>

<hr>

<h2>📦 Benefícios da Solução</h2>

<ul>
  <li>Centralização da autenticação e identidade</li>
  <li>Segurança consistente entre microsserviços</li>
  <li>Escalabilidade independente</li>
  <li>Alto nível de reutilização</li>
  <li>Facilidade de manutenção e evolução</li>
  <li>Preparado para ambientes cloud</li>
</ul>

<hr>

<p align="center">
  <strong>Projeto Agendador</strong><br>
  Microsserviços • Java • Spring Boot • Segurança
</p>

<p align="center">
  Desenvolvido por <strong>Paulo Bueno</strong>
</p>

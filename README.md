<h1 align="center">Projeto Final #ElasTech 2024</h1>

![image](https://github.com/JessicaArf/help-delas/assets/106780748/9ce15472-3445-4e8f-8ad3-89a0ff434811)

# Índice
<!--ts-->

   * [Sobre](#sistema-de-chamados-técnicos-em-informática)
   * [Requisitos](#requisitos)
   * [Instalação](#instalação)
   * [Telas](#telas)
   * [Modelo de Entidade e Relacionamento](#modelo-de-entidade-e-relacionamento)
   * [Tecnologias](#tecnologias)
   * [Grupo](#grupo)
 
<!--te-->

## Sistema de Chamados Técnicos em Informática

Desenvolvemos uma aplicação web utilizando Java Spring Boot para atendimento de chamados técnicos em informática. Nosso sistema permite que os usuários abram chamados para assistência, enquanto os técnicos podem assumir esses chamados, realizar o atendimento e modificar as informações do chamado conforme necessário. Isso inclui atualizar o status, prioridades e fazer anotações sobre as atividades realizadas durante o atendimento.

Para garantir a segurança e a disponibilidade adequada das funcionalidades, implementamos o Spring Security para gerenciar a autenticação do login e controlar o acesso às rotas com base nos perfis de usuário. Além disso, utilizamos o Thymeleaf para integrar o back-end com o front-end, permitindo uma renderização dinâmica das páginas HTML. Com o JavaMailSender, possibilitamos o envio de e-mails automáticos conforme as interações dos usuários e técnicos com o sistema.

## Requisitos

Essa aplicação teve os seguintes requisitos:

- [x] Tela Inicial contendo uma breve descrição sobre o sistema para introduzir o usuário.<br>
- [x] Tela de Login contendo a possibilidade de logar no sistema e também realizar o cadastro de um usuário.<br>
- [x] Tela do Usuário contendo a possibilidade de cadastrar novos chamados, visualizar seus dados e editá-los.<br>
- [x] Tela do Técnico, contendo a possibilidade de visualizar os chamados disponíveis para serem atendidos, os chamados que já estão atribuídos a ele e a chance de alterar o status dos chamados.<br>
- [x] Tela do Administrador, disponibilizando uma visão abrangente do uso do sistema, fornecendo dados essenciais, como números de chamados em aberto, em execução e aguardando. Além disso, nessa tela permite o gerenciamento de elementos-chaves do sistema, como o cadastramento de informações vitais, garantido flexibilidade e adaptabilidade ao ambiente corporativo.<br>

## Instalação

1. Clonar o repositório:

```bash
git@github.com:JessicaArf/help-delas.git
```

2. Instalar as dependências com Maven.

3. Configure o arquivo application.properties com seus dados pessoais do Banco de dados MySql.

4. Execute o projeto.

A aplicação pode ser acessada em: `http://localhost:8080`.

## Telas

<strong>Tela Inicial</strong>
<ul>
  <li>Uma breve introdução sobre o sistema.</li>
</ul>

<strong>Tela Login</strong>
<ul>
  <li>Funcionalidade de login.</li>
  <li>Cadastro como usuário.</li>
  <li>Funcionalidade do esqueceu senha.</li>
</ul>

<strong>Tela Usuário</strong>
<ul>
  <li>Visualização de todos os seus chamados.</li>
  <li>Criação de um novo chamado.</li>
  <li>Edição de um chamado.</li>
  <li>Deletar um chamado.</li>
  <li>Visualização de seus dados.</li>
  <li>Edição de seus dados.</li>
</ul>

<strong>Tela Técnico</strong>
<ul>
  <li>Visualização dos chamados sem um técnico atribuído.</li>
  <li>Visualização dos chamados atribuídos ao técnico que está logado.</li>
  <li>Edição de um chamado.</li>
  <li>Visualização de seus dados.</li>
</ul>

<strong>Tela Admin</strong>
<ul>
  <li>Visualização de todos os chamados do sistema.</li>
  <li>Visualização dos chamados sem um técnico atribuído.</li>
  <li>Visualização dos chamados com um técnico atribuído.</li>
  <li>Cadastro de um técnico.</li>
  <li>Edição de um técnico.</li>
  <li>Visualização de todos os usuários do sistema.</li>
  <li>Funcionalidade de ativar um usuário.</li>
  <li>Funcionalidade de desativar um usuário.</li>
  <li>Cadastro de setor.</li>
  <li>Edição de um setor.</li>
  <li>Deletar um setor.</li>
  <li>Visualização de todos os setores.</li>
  <li>Cadastro de prioridade.</li>
  <li>Edição de uma prioridade.</li>
  <li>Deletar uma prioridade.</li>
  <li>Visualização de todas as prioridades.</li>
</ul>

## Modelo de Entidade e Relacionamento

![image](https://github.com/JessicaArf/help-delas/assets/106780748/b83b2e7e-30b7-4049-94c5-af924fb0f910)


## Tecnologias
<div align="left">
<img src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/Java-Dark.svg" width=80"/>
<img src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/Maven-Dark.svg" width="80"/>
  <img src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/Spring-Dark.svg" width="80"/>
<img src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/MySQL-Dark.svg" width="80"/>
<img src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/CSS.svg" width="80"/>
<img src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/HTML.svg" width="80"/> 
<img src="https://raw.githubusercontent.com/tandpfun/skill-icons/main/icons/Bootstrap.svg" width="80"/> 
</div>

## Grupo
<table align="left">
  <tr>
   <td align="center"> <img src="https://avatars.githubusercontent.com/u/91222725?v=4" width=175/></br><a href="https://github.com/GiselleKSS">Giselle Santos</a>
   </td>
   <td align="center"> <img src="https://avatars.githubusercontent.com/u/106780748?v=4" width=175/></br><a href="https://github.com/JessicaArf">Jéssica Arf</a>
   </td>
   <td align="center"> <img src="https://avatars.githubusercontent.com/u/10958007?v=4" width=175/></br><a href="https://github.com/slrocha">Stephanie Lima</a>
   </td>
   <td align="center"> <img src="https://avatars.githubusercontent.com/u/161541448?v=4" width=175/></br><a href="https://github.com/DevSuellen">Suellen Abreu</a>
   </td>
</table>

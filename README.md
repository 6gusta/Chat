# 📱 Chat Multi-Instância - WhatsApp API Backend

Este projeto é uma API backend desenvolvida em Java com Spring Boot para gerenciamento de múltiplas instâncias de chat integradas ao WhatsApp. O sistema foi projetado para automação de mensagens, escalabilidade e integração com sistemas externos.

---

## 📌 Visão Geral

A aplicação permite criar e gerenciar múltiplas sessões independentes de WhatsApp, possibilitando automação de atendimento, integração com chatbots e sistemas de terceiros.

O sistema simula um ambiente real de comunicação em escala, sendo ideal para suporte, vendas e automação de mensagens.

---

## ⚙️ Funcionalidades

- 🔄 Criação e gerenciamento de múltiplas instâncias de chat  
- 📲 Geração de QR Code para autenticação no WhatsApp  
- 💬 Envio e recebimento de mensagens via API REST  
- 📡 Webhook para comunicação em tempo real  
- 🔐 Autenticação e segurança com Spring Security + JWT  
- 🧩 Integração com sistemas externos (Typebot, n8n, etc.)  
- 🟢 Monitoramento de status das instâncias  
- ❌ Logout e remoção de sessões ativas  

---

## 🛠️ Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- REST API
- WebSockets (se aplicável)
- whatsapp-web.js (integração externa)
- Node.js (serviço auxiliar, se usado)
- Maven

---

## 🧠 Arquitetura do Sistema

- **Controller** → Endpoints REST da API  
- **Service** → Regras de negócio e gerenciamento de instâncias  
- **Repository** → Persistência de dados  
- **Security** → Autenticação e autorização com JWT  
- **Integration Layer** → Comunicação com WhatsApp e serviços externos  
- **Webhook Handler** → Recebimento de mensagens em tempo real  

---

## 🔐 Segurança

O sistema utiliza autenticação baseada em JWT para proteger endpoints sensíveis, garantindo controle de acesso às instâncias e mensagens.

---

## 🚀 Como executar o projeto

### Pré-requisitos
- Java 17+
- Maven
- Node.js (caso use whatsapp-web.js)
- Banco de dados (opcional, dependendo da versão)

### Passos

```bash
git clone https://github.com/6gusta/Chat_frontend.git
cd Chat_frontend

# backend (caso separado)
cd backend

mvn spring-boot:run

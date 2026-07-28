# Fundoo Application Backend

Fundoo is a high-performance, collaborative note-taking web application backend modeled after Google Keep. It provides a RESTful API for managing user profiles, stateless JWT-based authentication, rich-text notes (with pins, archivals, trashing, and custom colors), labels, and real-time collaboration features supported by background schedulers and cache layers.

The messaging architecture has been refactored to support **loose coupling**. The application interfaces exclusively with a generic `EventPublisher` abstraction and can dynamically publish to either **RabbitMQ** (default) or **Apache Kafka** by modifying a simple property value.

---

## Pre-Running Checklist (Must Check Before Running!)

Before you compile and run the application, make sure the following checklist is completed:

### 1. Database Configuration
* **MySQL Server**: Ensure your MySQL server is running.
* **Database Setup**: You must create a database named `fundoo_db` before launching the app:
  ```sql
  CREATE DATABASE fundoo_db;
  ```
* **Credentials**: The application uses profile-specific settings. Check the configurations in [application-dev.properties](file:///d:/BridgeLabz/MagicSoftware/fundoo/src/main/resources/application-dev.properties):
  * **Default Username**: `root`
  * **Default Password**: `Passwd@123`
  * **URL**: `jdbc:mysql://localhost:3306/fundoo_db`
  *(If your local MySQL credentials differ, please update this file or define the corresponding environment variables).*

### 2. Caching Configuration
* **Redis Server**: The application uses Redis for cache management. Ensure Redis is running on port `6379`.
  * Command to verify Redis: `redis-cli ping` (should respond with `PONG`).

### 3. Event Broker & Messaging
* **Active Broker (RabbitMQ)**: By default, the application uses **RabbitMQ**. Ensure your local RabbitMQ broker is running on port `5672` (management dashboard on port `15672`).
* **Optional Broker (Apache Kafka)**: To switch the messaging system to Kafka:
  * Set `messaging.provider=kafka` in properties.
  * Ensure ZooKeeper/Kafka Broker are running locally at `localhost:9092`.

### 4. Java SDK Version
* **JDK Version**: Make sure your local JDK version is set to **Java 21**.
  * Verify in terminal: `java -version`
  * Verify `JAVA_HOME` environment variable points to your Java 21 path.

### 5. Seeded Admin Account
* On startup, the application checks if an admin user exists. If not, it automatically seeds:
  * **Email**: `admin@fundoo.com`
  * **Password**: `AdminPassword@123`
  * **Role**: `ROLE_ADMIN`

---

## 🛠️ Technology Stack & Architecture

* **Framework**: Spring Boot 3.5.x
* **Language**: Java 21
* **Security**: Stateless Spring Security via JWT
* **Persistence**: Spring Data JPA & Hibernate
* **Database**: MySQL 8.x
* **Cache**: Spring Data Redis
* **Message Brokers**: RabbitMQ (active default) and Apache Kafka (future/standby)
* **Scheduler**: Spring Boot Scheduler (handles active reminder checking)

---

## 🚀 Running the Application

### Option A: Pull & Run Pre-Built Image from Docker Hub (Easiest)

We build and push a production-grade Docker image to Docker Hub under the user account `mugilanjagadeesan`.

To pull the latest image and spin up the full container ecosystem:
1. Make sure you have [docker-compose.yml](file:///d:/BridgeLabz/MagicSoftware/fundoo/docker-compose.yml) downloaded.
2. Under the `app` service in `docker-compose.yml`, you can replace the build directive:
   ```yaml
   # Replace "build: ." with image:
   image: mugilanjagadeesan/fundoo-app:latest
   ```
3. Launch everything in one command:
   ```bash
   docker compose up -d
   ```
This pulls the pre-built application image from Docker Hub and launches MySQL, Redis, RabbitMQ, and Kafka alongside it.

---

### Option B: Running with Docker Compose (Local Build)

To build and run the entire ecosystem locally from source code:
1. Ensure Docker Desktop is installed and running.
2. Spin up the full stack:
   ```bash
   docker compose up --build -d
   ```
This automatically builds the Spring Boot image using the [Dockerfile](file:///d:/BridgeLabz/MagicSoftware/fundoo/Dockerfile), pulls the latest MySQL, Redis, RabbitMQ, and Kafka images, and links all services.

#### Verify Services are Running:
```bash
docker compose ps
```
The application will be accessible at **`http://localhost:8080`**.

#### Stop and Clean the Environment:
To stop the containers and delete the volumes (wipes database and cache):
```bash
docker compose down -v
```

---

### Option C: Running Locally (Bare Metal)

Follow these steps to build, test, and run the backend locally:

#### 1. Compile Code
```bash
mvn clean compile
```

#### 2. Run Test Suite
```bash
mvn test
```

#### 3. Start Development Server
```bash
mvn spring-boot:run
```
The server will start on **`http://localhost:8080`**.

---

## ⚙️ Team Configuration Checklist (`application-dev.properties`)

To make it easy for your development team, all sensitive or environment-specific configurations bind to environment variables with local fallbacks in [application-dev.properties](file:///d:/BridgeLabz/MagicSoftware/fundoo/src/main/resources/application-dev.properties):

| Property Name | Env Variable Name | Default Value | Description |
|---|---|---|---|
| `spring.datasource.url` | `SPRING_DATASOURCE_URL` | `jdbc:mysql://localhost:3306/fundoo_db?allowPublicKeyRetrieval=true&useSSL=false` | Database connection URL |
| `spring.datasource.username` | `SPRING_DATASOURCE_USERNAME` | `root` | Database username |
| `spring.datasource.password` | `SPRING_DATASOURCE_PASSWORD` | `Passwd@123` | Database password |
| `spring.data.redis.host` | `SPRING_REDIS_HOST` | `localhost` | Redis server hostname |
| `spring.data.redis.port` | `SPRING_REDIS_PORT` | `6379` | Redis server port |
| `messaging.provider` | `MESSAGING_PROVIDER` | `rabbitmq` | Active message broker (`rabbitmq` or `kafka`) |
| `spring.rabbitmq.host` | `SPRING_RABBITMQ_HOST` | `localhost` | RabbitMQ server hostname |
| `spring.rabbitmq.port` | `SPRING_RABBITMQ_PORT` | `5672` | RabbitMQ server port |
| `spring.kafka.bootstrap-servers` | `SPRING_KAFKA_BOOTSTRAP_SERVERS` | `localhost:9092` | Kafka bootstrap servers |

Your team members can override any value locally by setting environment variables in their IDE or `.env` files without altering code.

---

## 🛠️ CI/CD Pipelines (Automation)

The project supports dual CI/CD automation setups:

### 1. GitHub Actions (Continuous Integration & Delivery)
The GitHub Actions workflow is defined in [.github/workflows/docker-publish.yml](file:///d:/BridgeLabz/MagicSoftware/fundoo/.github/workflows/docker-publish.yml).
- **Trigger:** Pushes or PRs to `dev` or `main`.
- **Workflow Stages:**
  - Checks out repository.
  - Installs JDK 21.
  - Runs all Maven unit tests.
  - Builds the Docker image.
  - Pushes the Docker image tagged with `latest` and `github.sha` to Docker Hub under the `mugilanjagadeesan` account (requires repository secrets `DOCKERHUB_USERNAME` and `DOCKERHUB_TOKEN`).

### 2. Jenkins Pipeline (Continuous Integration & Delivery)
The Jenkins pipeline is defined in the [Jenkinsfile](file:///d:/BridgeLabz/MagicSoftware/fundoo/Jenkinsfile).
- **Workflow Stages:**
  - **Checkout:** Pulls latest branch code.
  - **Build & Test:** Runs `mvn clean test`.
  - **Docker Build:** Builds the application image and tags it with `latest` and Jenkins `${BUILD_NUMBER}`.
  - **Docker Push:** Logs in securely using the Jenkins credentials helper (`dockerhub-credentials`) and pushes the image tags to Docker Hub.

---

## 📬 Frontend Integration & API Documentation

For the frontend team, a detailed API Integration and CORS setup guide is available inside the project at:
* **[docs/frontend-integration.md](file:///d:/BridgeLabz/MagicSoftware/fundoo/docs/frontend-integration.md)**

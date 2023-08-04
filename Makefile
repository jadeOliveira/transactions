PWD = $(shell pwd -L)
GRADLE_CMD=gradle
DOCKERCMD=docker
DOCKERCOMPOSECMD=docker-compose
DOCKER_COMPOSE_FILE_DIR=compose/docker-compose.yml

.PHONY: all test vendor

all: help

help: ## Display help screen
	@echo "Usage:"
	@echo "		make [COMMAND]"
	@echo "		make help \n"
	@echo "Commands: \n"
	@grep -h -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

clean: ## gradle clean files
	$(GRADLE_CMD) clean

build: ## Run the gradle project build
	$(GRADLE_CMD) clean build

buildBootJar: ## Run the gradle project bootJar
	$(GRADLE_CMD) clean bootJar

run: ## Run the project
	$(GRADLE_CMD) clean bootRun

docker-compose-up: ## Run docker-compose services of project
	$(DOCKERCOMPOSECMD) -f $(DOCKER_COMPOSE_FILE_DIR) up -d

docker-compose-down: ## Stop docker-compose services of project
	$(DOCKERCOMPOSECMD) -f $(DOCKER_COMPOSE_FILE_DIR) down --remove-orphans

docker-compose-restart: docker-compose-down docker-compose-up ## Restart docker-compose services of project

docker-compose-logs: ## Logs docker-compose containers of project
	$(DOCKERCOMPOSECMD) -f $(DOCKER_COMPOSE_FILE_DIR) logs -f

docker-compose-ps: ## List docker-compose containers of project
	$(DOCKERCOMPOSECMD) -f $(DOCKER_COMPOSE_FILE_DIR) ps


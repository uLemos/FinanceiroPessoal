# Carregar variáveis do .env automaticamente, se existir

ifneq (,$(wildcard .env))
	include .env
	export
endif

# Targets

up-prod:
	docker-compose -f docker-compose.yml -f docker-compose.prod.yml up --build

down:
	docker-compose -f docker-compose.yml -f docker-compose.prod.yml down -v

logs:
	docker-compose -f docker-compose.yml -f docker-compose.prod.yml logs -f

rebuild:
	docker-compose -f docker-compose.yml -f docker-compose.prod.yml down -v && docker-compose -f docker-compose.yml -f docker-compose.prod.yml up --build

#Targets do Maven

clean:
	mvn clean

test:
	mvn test

build:
	mvn clean package -DskipTests

# SonarQube Analysis

analyze:
ifndef SONAR_TOKEN
	$(error "❌ Variável SONAR_TOKEN não definida. Defina no .env ou exporte no ambiente.")
endif
ifndef SONAR_HOST
	$(error "❌ Variável SONAR_HOST não definida. Defina no .env ou exporte no ambiente. Ex: SONAR_HOST=http://localhost:9000")
endif
	@echo "🔍 Iniciando análise com SonarQube..."
	mvn clean verify sonar:sonar \
	  -Dsonar.token=$(SONAR_TOKEN) \
	  -Dsonar.host.url=$(SONAR_HOST)
	@echo "✅ Análise concluída com sucesso!"
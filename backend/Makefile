# Carregar variáveis do .env automaticamente, se existir

ifneq (,$(wildcard .env))
	include .env
	export
endif

# Targets de Hom

up-dev:
	docker-compose up --build

down-dev:
	docker-compose down -v

# Targets de Prod

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


# Monitoramento (Prometheus + Grafana) 

monitoring-up:
	cd .. && docker-compose -f backend/docker-compose.monitoring.yml up -d

monitoring-down:
	cd .. && docker-compose -f backend/docker-compose.monitoring.yml down -v

monitoring-logs:
	cd .. && docker-compose -f backend/docker-compose.monitoring.yml logs -f

monitoring-rebuild:
	cd .. && docker-compose -f backend/docker-compose.monitoring.yml down -v && docker-compose -f backend/docker-compose.monitoring.yml up --build

# Limpar volume do Grafana (resetar dashboards, configs, etc.)
monitoring-clean:
	docker volume rm financeiro-backend-grafana-storage || true

# Abrir o Grafana no navegador
monitoring-open:
	@echo "🌐 Abrindo Grafana em http://localhost:3001"
	xdg-open http://localhost:3001 > /dev/null 2>&1 || open http://localhost:3001 || echo "Abra manualmente: http://localhost:3001"

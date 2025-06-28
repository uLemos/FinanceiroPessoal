up-prod:
	docker-compose -f docker-compose.yml -f docker-compose.prod.yml up --build

down:
	docker-compose -f docker-compose.yml -f docker-compose.prod.yml down -v

logs:
	docker-compose -f docker-compose.yml -f docker-compose.prod.yml logs -f

rebuild:
	docker-compose -f docker-compose.yml -f docker-compose.prod.yml down -v && docker-compose -f docker-compose.yml -f docker-compose.prod.yml up --build

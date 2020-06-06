zulu-shell:
	docker-compose -f docker/docker-compose.build.yml run --rm --service-ports holiday-zulu bash -l

corretto-shell:
	docker-compose -f docker/docker-compose.build.yml run --rm --service-ports holiday-corretto bash -l

adoptopenjdk-hotspot-shell:
	docker-compose -f docker/docker-compose.build.yml run --rm --service-ports holiday-zulu bash -l

oracle-shell:
	docker-compose -f docker/docker-compose.build.yml run --rm --service-ports holiday-oracle bash -l

build-deployment-images:
	docker-compose -f docker/docker-compose.deploy.yml build

push-deployment-images:
	docker-compose -f docker/docker-compose.deploy.yml push

start-traefik:
	cd ../traefik && make start

start-monitoring:
	cd ../swarm-monitoring && make start-DEV

deploy:
	docker stack deploy -c docker/docker-compose.deploy.yml holidays

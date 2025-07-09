FROM prom/prometheus:latest

# Copia o arquivo de configuração
COPY prometheus.yml /etc/prometheus/prometheus.yml

# Define explicitamente a porta 8080 exigida pelo Railway
CMD [ "--config.file=/etc/prometheus/prometheus.yml", "--web.listen-address=:8080" ]

## Starting Kafka with Docker

### 1. Start the Docker Containers

```sh
docker-compose up -d
```

This command starts both the Zookeeper and Kafka containers in detached mode.

### 2. Verify the Containers are Running

```sh
docker-compose ps
```

## Kafka Operations

When using the Bitnami Kafka Docker image, you'll need to execute Kafka commands inside the container. Use the following pattern:

```sh
docker exec -it kafka /opt/bitnami/kafka/bin/kafka-command-name.sh [options]
```

### 1. Create a Topic

```sh
docker exec -it kafka /opt/bitnami/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --topic NewTopic --partitions 3 --replication-factor 1
```

### 2. List All Topics

```sh
docker exec -it kafka /opt/bitnami/kafka/bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

### 3. Describe a Topic

```sh
docker exec -it kafka /opt/bitnami/kafka/bin/kafka-topics.sh --describe --bootstrap-server localhost:9092 --topic NewTopic
```

### 4. Produce Messages

```sh
docker exec -it kafka /opt/bitnami/kafka/bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic NewTopic
```

After running this command, you can type messages into the console. Each line is sent as a separate message to Kafka. Press Ctrl+D to exit.

### 5. Consume Messages

```sh
docker exec -it kafka /opt/bitnami/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic NewTopic --from-beginning
```

This command will show all messages in the topic, including those sent before the consumer was started. Press Ctrl+C to stop consuming.

### 6. Send CSV File Data to Kafka

To send data from a CSV file to Kafka, first copy the file into the container, then pipe it to the producer:

```sh
# Copy the CSV file to the container
docker cp your-local-file.csv kafka:/tmp/data.csv

# Send the file content to Kafka
docker exec -it kafka bash -c "cat /tmp/data.csv | /opt/bitnami/kafka/bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic NewTopic"
```

## Stopping Kafka

To stop and remove the containers:

```sh
docker-compose down
```

To stop but keep the containers:

```sh
docker-compose stop
```

## Notes for Bitnami Kafka

- The Bitnami Kafka image uses `/opt/bitnami/kafka/bin/` as the directory for Kafka tools
- Commands use `--bootstrap-server` instead of `--broker-list` for newer Kafka versions
- The Bitnami image already has Zookeeper and Kafka configured to work together
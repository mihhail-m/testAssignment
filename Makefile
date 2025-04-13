# Constants
IMAGE_NAME = wiremock-srv
PORT = 8080

# Paths
CUR_DIR := $(abspath .)
RESOURCE_PATH := ${CUR_DIR}/src/test/resources
MAPPINGS_DIR := ${RESOURCE_PATH}/mappings
FILES_DIR := ${RESOURCE_PATH}/__files

# Build Docker image
build:
	@docker build -t $(IMAGE_NAME) .

# Run WireMock container with hot-reload support
run:
	@docker run \
		-v ${MAPPINGS_DIR}:/home/wiremock/mappings \
		-v ${FILES_DIR}:/home/wiremock/__files \
		-p ${PORT}:8080 \
		${IMAGE_NAME}

# Stop running container (if running in detached mode)
stop:
	@docker stop ${CONTAINER_NAME} || true

# Rebuild image and run again
rebuild: stop build run

# Clean up Docker artifacts
clean:
	@docker image rm ${IMAGE_NAME} || true

reload:
	curl -X POST http://localhost:${PORT}/__admin/mappings/reset
# Show help
help:
	@echo "$(CUR_DIR)}"
	@echo "Usage:"
	@echo "  make build     - Build the Docker image"
	@echo "  make run       - Run WireMock with live-reloading"
	@echo "  make stop      - Stop WireMock container"
	@echo "  make reload    - Reload Wiremock mappings"
	@echo "  make rebuild   - Rebuild and run again"
	@echo "  make clean     - Remove the Docker image"
	@echo ""

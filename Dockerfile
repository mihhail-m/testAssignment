FROM wiremock/wiremock:latest

# Add wiremock extension for random data generation`
ADD https://repo1.maven.org/maven2/org/wiremock/extensions/wiremock-faker-extension-standalone/0.1.1/wiremock-faker-extension-standalone-0.1.1.jar /var/wiremock/extensions/

RUN mkdir -p /home/wiremock/mappings /home/wiremock/__files
COPY src/test/resources/mappings /home/wiremock/mappings
COPY src/test/resources/__files /home/wiremock/__files

CMD ["--global-response-templating", "--extensions", "org.wiremock.RandomExtension", "--verbose", "--root-dir", "/home/wiremock"]
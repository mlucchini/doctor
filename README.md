# Doctor
[![Build Status](https://img.shields.io/travis/mlucchini/doctor.svg)](https://travis-ci.org/mlucchini/doctor)
[![SonarQube Test Coverage](https://img.shields.io/sonar/https/sonarqube.com/com.marclucchini:doctor/coverage.svg)](https://sonarqube.com/dashboard?id=com.marclucchini:doctor)
[![SonarQube Tech Debt](https://img.shields.io/sonar/https/sonarqube.com/com.marclucchini:doctor/tech_debt.svg)](https://sonarqube.com/dashboard?id=com.marclucchini:doctor)
[![Heroku Deployment](https://heroku-badge.herokuapp.com/?app=ml-doctor&root=swagger-ui.html&svg=1)](https://ml-doctor.herokuapp.com)
[![Docker Automated Build](https://img.shields.io/docker/automated/marclucchini/doctor.svg)](https://hub.docker.com/r/marclucchini/doctor)

The Doctor API returns NHS Choices pages from a search such as "What are the symptoms of dystonia?".
The API can also be queried through the Swagger UI or a little embedded UI.

## Heroku setup

This will provision the Doctor application and a Bonsai Elasticsearch cluster on Heroku.
This has been done already for the [ml-doctor](https://ml-doctor.herokuapp.com/swagger-ui.html) application.

*Note: due to free dynos and addons limitations, a subset of NHS Choices conditions is indexed on Heroku.
For all the conditions, run Doctor locally with Docker as described below.*

```sh
heroku create ml-doctor
heroku addons:create bonsai:sandbox-10
git push heroku master
open https://ml-doctor.herokuapp.com
open https://ml-doctor.herokuapp.com/swagger-ui.html
```

## Local setup

This will run both the Doctor application and a standalone Elasticsearch instance locally.
You may need to increase the amount of memory allocated to Docker to 4GB+ as Elasticsearch 
is quite demanding and JVMs usually default to ~25% of the total available memory offered.

```sh
gradle build
docker-compose up
open http://localhost:8080
open http://localhost:8080/swagger-ui.html
```

## Code Quality and Continuous Delivery

- Travis CI
- SonarQube with PMD and Checkstyle rules and Jacoco test coverage
- Automated deployment
- DockerHub integration

## Possible improvements

- Distributed crawl and real-time update/index (Spark, actors, queues...)
- Semantic search (knowledge graph, word2vec, deep learning approaches...)
- Boosted search fields (title...)
- Integration tests
- Proper UI
- Many things

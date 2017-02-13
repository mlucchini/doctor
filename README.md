# Doctor
[![Build Status](https://travis-ci.org/mlucchini/doctor.svg?branch=master)](https://travis-ci.org/mlucchini/doctor)
[![Coverage Status](https://coveralls.io/repos/github/mlucchini/doctor/badge.svg?branch=master)](https://coveralls.io/github/mlucchini/doctor?branch=master)
[![Heroku](https://heroku-badge.herokuapp.com/?app=ml-doctor)]

The Doctor API returns NHS Choices pages from a search such as "What are the symptoms of dystonia?".

## Heroku setup

This will provision the Doctor application and a Bonsai Elasticsearch cluster on Heroku.
This has been done already for the [ml-doctor](https://ml-doctor.herokuapp.com/swagger-ui.html) application.

```
heroku create ml-doctor
heroku addons:create bonsai:sandbox-10
heroku addons:create bonsai:sandbox-10
heroku addons:create bonsai:sandbox-10
heroku addons:create bonsai:sandbox-10
git push heroku master
open https://ml-doctor.herokuapp.com/swagger-ui.html
```

## Local setup

This will run both the Doctor application and a standalone Elasticsearch instance locally.
You may need to increase the amount of memory allocated to Docker to 4GB+ as Elasticsearch 
is quite demanding and JVMs usually default to ~25% of the total available memory offered.

```
gradle build
docker-compose up
open http://localhost:8080/swagger-ui.html
```

## Possible improvements

- Distributed crawl and real-time update/index (Spark, actors, queues...)
- Semantic search (knowledge graph, word2vec, deep learning approaches...)
- Integration tests
- UI
- Many things

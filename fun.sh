#!/usr/bin/env bash

function run_app(){
    mvn spring-boot:run
}

function run_angular_build(){
    cd src/main/resources/music-static/
    ng build --target=production --output-path=../static
    rm -rf ../static/*.map
    rm -rf ../static/*.gz
    cd -
    mv src/main/resources/static/styles.*.css src/main/resources/static/styles.css
    mv src/main/resources/static/inline.*.js src/main/resources/static/inline.js
    mv src/main/resources/static/polyfills.*.js src/main/resources/static/polyfills.js
    mv src/main/resources/static/vendor.*.js src/main/resources/static/vendor.js
    mv src/main/resources/static/main.*.js src/main/resources/static/main.js
    cp src/main/resources/static/assets/index.html src/main/resources/static/index.html
    if [ -d "target/classes" ]; then
            if [ -d "target/classes/static" ]; then
                rm -rf target/classes/static
            fi
        cp -r src/main/resources/static target/classes/static
    fi
}

function run_push(){
    mvn clean package -DskipTests=true
    cf push
    mvn clean
}

function run_services(){
    cf delete music
    cf delete-service MySqlDbMusic
    cf delete-service ElasticSearchMusic
    cf delete-service NewRelicMusic
    cf create-service cleardb spark MySqlDbMusic
    cf create-service searchly starter ElasticSearchMusic
    cf create-service newrelic standard NewRelicMusic
}

function run_angular(){
    cd src/main/resources/music-static/
    ng serve
}
function run_custom(){
    mvn spring-boot:run -Dspring.profiles.active=custom
}

echo "1 for angular build"
echo "2 for run app"
echo "3 for push"
echo "4 for services"
echo "5 for run angular"
echo "6 for run custom"

read value
if [ $value -eq 1 ]; then
    run_angular_build
elif [ $value -eq 2 ]; then
    run_app
elif [ $value -eq 3 ]; then
    run_push
elif [ $value -eq 4 ]; then
    run_services
elif [ $value -eq 5 ]; then
    run_angular
elif [ $value -eq 6 ]; then
    run_custom
fi

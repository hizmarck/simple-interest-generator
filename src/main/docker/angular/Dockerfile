# build it from the root context .
# docker build -t hizmarck-microservice/angular:latest -f src/main/docker/angular/Dockerfile .
# docker run --network hizmarck-microservice-net -p 4200:80 hizmarck-microservice/angular:latest
FROM node:16.13.2 as node
WORKDIR /app
COPY [".browserslistrc", ".editorconfig", ".gitignore", "angular.json","karma.conf.js","package.json","package-lock.json","tsconfig.app.json","tsconfig.json","tsconfig.spec.json", "./"]
# we've used "sourceRoot": "src/main/webapp" on angular.json
COPY src/main/webapp ./src/main/webapp
RUN npm install
RUN npm run build --prod
# 2
FROM nginx:alpine
COPY --from=node /app/dist/webapp /usr/share/nginx/html

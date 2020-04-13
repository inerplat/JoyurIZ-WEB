FROM node:12-alpine

WORKDIR "/app"

COPY package.json ./package.json
RUN npm install
COPY . .
EXPOSE 443

CMD node server.js

FROM node:12
COPY package.json /src/package.json
RUN cd /src; npm install
COPY . /src
EXPOSE 10290
WORKDIR /src 

CMD node server.js

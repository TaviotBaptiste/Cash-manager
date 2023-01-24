# syntax=docker/dockerfile:1
FROM node:16

# Create app directory
WORKDIR /src

# Install app dependencies
# A wildcard is used to ensure both package.json AND package-lock.json are copied
# where available (npm@5+)
COPY package*.json ./
COPY tsconfig.json ./


# Bundle app source
COPY . .

RUN ls -a
RUN npm install

EXPOSE 8080

CMD ["npm","run","dev"]
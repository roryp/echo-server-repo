FROM node:alpine
WORKDIR /app
COPY echo-server.js .
CMD ["node", "echo-server.js"]

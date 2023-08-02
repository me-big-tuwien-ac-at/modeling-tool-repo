FROM node:latest as builder

RUN mkdir -p /app

WORKDIR app

COPY . .

RUN npm install
RUN npm run build --prod

CMD ["npm", "start"]

# Stage 2: Serve the angular app with Nginx
FROM nginx:alpine
COPY --from=builder /app/dist/frontend /usr/share/nginx/html
# COPY --from=build-stage /../frontend /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

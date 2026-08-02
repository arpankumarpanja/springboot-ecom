FROM php:8.2-apache

RUN docker-php-ext-install mysqli pdo pdo_mysql

COPY adminer.php /var/www/html/adminer.php
COPY index.php /var/www/html/index.php

EXPOSE 80
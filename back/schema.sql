DROP DATABASE `db_mdd`;
CREATE DATABASE `db_mdd`;
USE `db_mdd`;

CREATE TABLE `USERS` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `email` varchar(255),
  `name` varchar(255),
  `password` varchar(255),
  `created_at` timestamp
);

CREATE TABLE `ARTICLES` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `titre` varchar(255),
  `contenu` varchar(2000),
  `auteur_id` integer NOT NULL,
  `theme_id` integer NOT NULL,
  `created_at` timestamp
);

CREATE TABLE `THEMES` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `theme` varchar(255),
  `description` varchar(2000)
);

CREATE TABLE `COMMENTAIRES` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `contenu` varchar(2000),
  `article_id` integer NOT NULL,
  `user_id` integer NOT NULL,
  `created_at` timestamp
);

CREATE TABLE `ABONNEMENTS` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `user_id` integer NOT NULL,
  `theme_id` integer NOT NULL
);

CREATE UNIQUE INDEX `USERS_index` ON `USERS` (`email`);

ALTER TABLE `ARTICLES` ADD FOREIGN KEY (`auteur_id`) REFERENCES `USERS` (`id`);
ALTER TABLE `ARTICLES` ADD FOREIGN KEY (`theme_id`) REFERENCES `THEMES` (`id`);

ALTER TABLE `COMMENTAIRES` ADD FOREIGN KEY (`article_id`) REFERENCES `ARTICLES` (`id`);
ALTER TABLE `COMMENTAIRES` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);

ALTER TABLE `ABONNEMENTS` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);
ALTER TABLE `ABONNEMENTS` ADD FOREIGN KEY (`theme_id`) REFERENCES `THEMES` (`id`);





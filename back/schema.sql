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

INSERT INTO USERS (email, name, password, created_at) VALUES
  ('ali.hassan@gmail.com', 'Ali Hassan', '$2a$10$W/VUlTXPwmqId36XvGbawuRxZbltohkQDUBVsaElaJYrEaTpp6h9e', now()),
  ('priya.sharma@tech.com', 'Priya Sharma', '$2a$10$W/VUlTXPwmqId36XvGbawuRxZbltohkQDUBVsaElaJYrEaTpp6h9e', now()),
  ('jun.wei@tech.com', 'Jun Wei', '$2a$10$W/VUlTXPwmqId36XvGbawuRxZbltohkQDUBVsaElaJYrEaTpp6h9e', now()),
  ('mohamed.amin@tech.com', 'Mohamed Amin', '$2a$10$W/VUlTXPwmqId36XvGbawuRxZbltohkQDUBVsaElaJYrEaTpp6h9e', now()),
  ('aisha.farouk@web.com', 'Aisha Farouk', '$2a$10$W/VUlTXPwmqId36XvGbawuRxZbltohkQDUBVsaElaJYrEaTpp6h9e', now()),
  ('vijay.kumar@web.com', 'Vijay Kumar', '$2a$10$W/VUlTXPwmqId36XvGbawuRxZbltohkQDUBVsaElaJYrEaTpp6h9e', now()),
  ('chen.li@web.com', 'Chen Li', '$2a$10$W/VUlTXPwmqId36XvGbawuRxZbltohkQDUBVsaElaJYrEaTpp6h9e', now()),
  ('yasmin.nasser@tech.com', 'Yasmin Nasser', '$2a$10$W/VUlTXPwmqId36XvGbawuRxZbltohkQDUBVsaElaJYrEaTpp6h9e', now());


INSERT INTO THEMES (theme, description) VALUES
  ('F#', "Langage fonctionnel faisant partie de l'écosystème .NET, apprécié pour la programmation parallèle et l'analyse de données."),
  ('Golang', "Langage de programmation créé par Google, connu pour sa simplicité, sa rapidité et sa gestion efficace de la concurrence."),
  ('Microsoft Azure', "Plateforme cloud de Microsoft permettant l'hébergement d'applications, de bases de données et de services IA."),
  ('Swagger', "Outil open-source permettant de documenter, concevoir et tester des API REST."),
  ('JavaScript', "Langage de programmation essentiel pour le développement web, utilisé aussi bien côté client que côté serveur (Node.js)."),
  ('SonarCloud', "Plateforme d analyse continue de la qualite du code."),
  ('GitHub', 'Plateforme de gestion de version pour le code source, utilisee pour le controle de version, la collaboration et l hebergement de projets open-source.'),
  ('Spring Boot', 'Framework Java permettant de créer des applications robustes et évolutives avec une configuration minimale.');

  



INSERT INTO ARTICLES (titre, contenu, auteur_id, theme_id, created_at) VALUES
  ('Introduction à F#', 'Découvrez les principes fondamentaux du langage F# et comment il facilite la programmation fonctionnelle.', 1, 1, now()),
  ('Pourquoi Go est-il si rapide ?', 'Golang a été conçu pour offrir une exécution rapide et une gestion efficace de la concurrence. Explorons ses atouts.', 2, 2, now()),
  ('Déployer une application sur Microsoft Azure', 'Tutoriel pas à pas pour héberger une application web sur Microsoft Azure.', 3, 3, now()),
  ('Documentation d API avec Swagger', 'Swagger permet de créer des documentations interactives pour les API REST. Voici comment l’utiliser efficacement.', 4, 4, now()),
  ('Modern JavaScript : Les nouveautés ES6+', 'Découvrez les fonctionnalités modernes de JavaScript depuis ES6 et comment elles améliorent le développement web.', 5, 5, now()),
  ('Introduction a GitHub', 'GitHub est une plateforme essentielle pour le developpement collaboratif. Découvrez ses fonctionnalites de gestion de version et de collaboration entre developpeurs.', 2, 7, now()),
  ('Développement avec Spring Boot', 'Découvrez comment Spring Boot facilite la création d’applications Java avec des configurations simplifiées.', 6, 8, now()),
  ('Améliorer la qualité du code avec SonarCloud', 'SonarCloud est un outil puissant pour analyser la qualité du code en continu. Il aide à détecter les bugs, vulnérabilités et mauvaises pratiques de développement.', 6, 6, now());
INSERT INTO ABONNEMENTS (user_id, theme_id) VALUES
(1,1),
(1,2),
(1,5),
(2,1),
(2,3),
(2,4),
(3,1),
(3,4),
(4,1),
(4,3),
(5,2),
(5,5);


INSERT INTO COMMENTAIRES (contenu, user_id, article_id, created_at) VALUES
  ('Tres bon article sur F# ! Japprecie la simplicite du langage et son approche fonctionnelle.', 1, 1, now()),
  ('Go est vraiment rapide, surtout quand il sagit de gerer la concurrence.', 2, 2, now()),
  ('Deployer une application sur Azure peut sembler complique, mais ce tutoriel le rend beaucoup plus simple.', 3, 3, now()),
  ('Swagger a vraiment facilite la documentation de notre API. Cest un outil essentiel pour les developpeurs dAPI.', 4, 4, now()),
  ('Jadore les nouveautes dES6+, surtout les fonctions flechees et les modules. Cela rend le code plus propre.', 5, 5, now()),
  ('Jai essaye F# dans mon projet et cest vraiment agreable pour le calcul parallele et lanalyse des donnees.', 6, 1, now()),
  ('Golang est ideal pour les applications necessitant une haute performance et une gestion efficace des ressources.', 7, 2, now()),
  ('Le tutoriel sur Azure ma vraiment aide a comprendre comment gerer le deploiement dans le cloud.', 8, 3, now()),
  ('Tres bon article, j apprecie la presentation des fonctionnalites de GitHub pour les projets open-source.', 3, 6, now());
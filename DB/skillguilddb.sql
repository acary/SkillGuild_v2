-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema skillguilddb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `skillguilddb` ;

-- -----------------------------------------------------
-- Schema skillguilddb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `skillguilddb` DEFAULT CHARACTER SET utf8 ;
USE `skillguilddb` ;

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) CHARACTER SET 'utf8' NOT NULL,
  `password` VARCHAR(255) CHARACTER SET 'utf8' NOT NULL,
  `email` VARCHAR(255) CHARACTER SET 'utf8' NULL,
  `enabled` TINYINT NOT NULL DEFAULT 1,
  `role` VARCHAR(20) NULL,
  `created_on` DATETIME NULL,
  `last_updated` DATETIME NULL,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `profile_img_url` VARCHAR(1000) NULL,
  `about_me` TEXT CHARACTER SET 'utf8' NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `guild`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `guild` ;

CREATE TABLE IF NOT EXISTS `guild` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `created_by_user_id` INT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `description` TEXT NULL,
  `is_public` TINYINT NOT NULL DEFAULT 0,
  `cover_img` VARCHAR(2000) NULL,
  `membership_criteria` TEXT NULL,
  `created_on` DATETIME NULL,
  `last_updated` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_guild_user1_idx` (`created_by_user_id` ASC),
  CONSTRAINT `fk_guild_user1`
    FOREIGN KEY (`created_by_user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `category` ;

CREATE TABLE IF NOT EXISTS `category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `description` TEXT NULL,
  `img_url` VARCHAR(1000) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `topic`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `topic` ;

CREATE TABLE IF NOT EXISTS `topic` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL,
  `description` TEXT NULL,
  `is_tech` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `status` ;

CREATE TABLE IF NOT EXISTS `status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(250) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `content`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `content` ;

CREATE TABLE IF NOT EXISTS `content` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `guild_id` INT NOT NULL,
  `created_by_user_id` INT NOT NULL,
  `status_id` INT NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `description` TEXT NULL,
  `publish_date` DATETIME NULL,
  `is_public` TINYINT NOT NULL DEFAULT 0,
  `is_live` TINYINT NOT NULL DEFAULT 0,
  `last_updated` DATETIME NULL,
  `length_minutes` INT NULL,
  `presentation_date` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_content_group1_idx` (`guild_id` ASC),
  INDEX `fk_content_status1_idx` (`status_id` ASC),
  INDEX `fk_content_user1_idx` (`created_by_user_id` ASC),
  CONSTRAINT `fk_content_group1`
    FOREIGN KEY (`guild_id`)
    REFERENCES `guild` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_content_status1`
    FOREIGN KEY (`status_id`)
    REFERENCES `status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_content_user1`
    FOREIGN KEY (`created_by_user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `interaction_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interaction_type` ;

CREATE TABLE IF NOT EXISTS `interaction_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL,
  `img_url` VARCHAR(1000) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `interaction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `interaction` ;

CREATE TABLE IF NOT EXISTS `interaction` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `interaction_type_id` INT NOT NULL,
  `created_on` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_interaction_content1_idx` (`content_id` ASC),
  INDEX `fk_interaction_user1_idx` (`user_id` ASC),
  INDEX `fk_interaction_interaction_type1_idx` (`interaction_type_id` ASC),
  CONSTRAINT `fk_interaction_content1`
    FOREIGN KEY (`content_id`)
    REFERENCES `content` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_interaction_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_interaction_interaction_type1`
    FOREIGN KEY (`interaction_type_id`)
    REFERENCES `interaction_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `comment` ;

CREATE TABLE IF NOT EXISTS `comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `content_id` INT NOT NULL,
  `in_reply_to_id` INT NULL DEFAULT 1,
  `text_content` TEXT NULL,
  `has_been_edited` TINYINT NOT NULL DEFAULT 0,
  `created_on` DATETIME NULL,
  `last_updated` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_comment_user1_idx` (`user_id` ASC),
  INDEX `fk_comment_content1_idx` (`content_id` ASC),
  INDEX `fk_comment_comment1_idx` (`in_reply_to_id` ASC),
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_content1`
    FOREIGN KEY (`content_id`)
    REFERENCES `content` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_comment1`
    FOREIGN KEY (`in_reply_to_id`)
    REFERENCES `comment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `content_topic`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `content_topic` ;

CREATE TABLE IF NOT EXISTS `content_topic` (
  `content_id` INT NOT NULL,
  `topic_id` INT NOT NULL,
  PRIMARY KEY (`content_id`, `topic_id`),
  INDEX `fk_content_has_topic1_topic1_idx` (`topic_id` ASC),
  INDEX `fk_content_has_topic1_content1_idx` (`content_id` ASC),
  CONSTRAINT `fk_content_has_topic1_content1`
    FOREIGN KEY (`content_id`)
    REFERENCES `content` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_content_has_topic1_topic1`
    FOREIGN KEY (`topic_id`)
    REFERENCES `topic` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `member`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `member` ;

CREATE TABLE IF NOT EXISTS `member` (
  `guild_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `approved_by` INT NULL,
  `moderator` TINYINT NULL,
  `created_on` DATETIME NULL,
  PRIMARY KEY (`guild_id`, `user_id`),
  INDEX `fk_group_has_user_user1_idx` (`user_id` ASC),
  INDEX `fk_group_has_user_group1_idx` (`guild_id` ASC),
  CONSTRAINT `fk_group_has_user_group1`
    FOREIGN KEY (`guild_id`)
    REFERENCES `guild` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `group_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `group_category` ;

CREATE TABLE IF NOT EXISTS `group_category` (
  `guild_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`guild_id`, `category_id`),
  INDEX `fk_group_has_category_category1_idx` (`category_id` ASC),
  INDEX `fk_group_has_category_group1_idx` (`guild_id` ASC),
  CONSTRAINT `fk_group_has_category_group1`
    FOREIGN KEY (`guild_id`)
    REFERENCES `guild` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_has_category_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `resource_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `resource_type` ;

CREATE TABLE IF NOT EXISTS `resource_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `resource`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `resource` ;

CREATE TABLE IF NOT EXISTS `resource` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `resource_type_id` INT NOT NULL,
  `title` VARCHAR(500) NULL,
  `description` TEXT NULL,
  `resource_url` VARCHAR(1000) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_resource_resource_type1_idx` (`resource_type_id` ASC),
  CONSTRAINT `fk_resource_resource_type1`
    FOREIGN KEY (`resource_type_id`)
    REFERENCES `resource_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `content_resource`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `content_resource` ;

CREATE TABLE IF NOT EXISTS `content_resource` (
  `content_id` INT NOT NULL,
  `resource_id` INT NOT NULL,
  PRIMARY KEY (`content_id`, `resource_id`),
  INDEX `fk_content_has_resource_resource1_idx` (`resource_id` ASC),
  INDEX `fk_content_has_resource_content1_idx` (`content_id` ASC),
  CONSTRAINT `fk_content_has_resource_content1`
    FOREIGN KEY (`content_id`)
    REFERENCES `content` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_content_has_resource_resource1`
    FOREIGN KEY (`resource_id`)
    REFERENCES `resource` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `question`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `question` ;

CREATE TABLE IF NOT EXISTS `question` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content_id` INT NOT NULL,
  `question` VARCHAR(2000) NULL,
  `correct_answer` VARCHAR(2000) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_question_content1_idx` (`content_id` ASC),
  CONSTRAINT `fk_question_content1`
    FOREIGN KEY (`content_id`)
    REFERENCES `content` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE = '';
DROP USER IF EXISTS skillguilduser@localhost;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'skillguilduser'@'localhost' IDENTIFIED BY 'skillguilduser';

GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE * TO 'skillguilduser'@'localhost';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `user`
-- -----------------------------------------------------
START TRANSACTION;
USE `skillguilddb`;
INSERT INTO `user` (`id`, `username`, `password`, `email`, `enabled`, `role`, `created_on`, `last_updated`, `first_name`, `last_name`, `profile_img_url`, `about_me`) VALUES (1, 'admin1', '$2a$10$IYLeRb3cuw.fH/6Af3y8FOO4JpqUnVFZjmQhTculDnCEmxgwTJukG', 'admin@admin.com', 1, 'data_admin', '2022-03-25 12:30:00', '2022-03-25 12:30:00', 'John', 'Admin', 'https://picsum.photos/200', 'Admininistrator');
INSERT INTO `user` (`id`, `username`, `password`, `email`, `enabled`, `role`, `created_on`, `last_updated`, `first_name`, `last_name`, `profile_img_url`, `about_me`) VALUES (2, 'joie', '$2a$10$OE0ye0s2CmfXsTY82XamA.uJZmkNV2ts6PPE1UlRsfrvkAGQumcby', 'akerman@gmail.com', 1, 'user', '2022-03-25 12:30:00', '2022-03-25 12:30:00', 'Joie', 'Akerman', 'https://picsum.photos/200', 'Web developer who likes frontend development');
INSERT INTO `user` (`id`, `username`, `password`, `email`, `enabled`, `role`, `created_on`, `last_updated`, `first_name`, `last_name`, `profile_img_url`, `about_me`) VALUES (3, 'bates', '$2a$10$OE0ye0s2CmfXsTY82XamA.uJZmkNV2ts6PPE1UlRsfrvkAGQumcby', 'bates@gmail.com', 1, 'user', '2022-03-25 12:30:00', '2022-03-25 12:30:00', 'Kyle', 'Base', 'https://picsum.photos/201', 'Web developer who likes backend development.');
INSERT INTO `user` (`id`, `username`, `password`, `email`, `enabled`, `role`, `created_on`, `last_updated`, `first_name`, `last_name`, `profile_img_url`, `about_me`) VALUES (4, 'jarvis', '$2a$10$OE0ye0s2CmfXsTY82XamA.uJZmkNV2ts6PPE1UlRsfrvkAGQumcby', 'jarvis@gmail.org', 1, 'user', '2022-03-25 12:30:00', '2022-03-25 12:30:00', 'Steve', 'Jarvis', 'https://picsum.photos/202', 'Product manager who wants to learn web development.');
INSERT INTO `user` (`id`, `username`, `password`, `email`, `enabled`, `role`, `created_on`, `last_updated`, `first_name`, `last_name`, `profile_img_url`, `about_me`) VALUES (5, 'milo', '$2a$10$OE0ye0s2CmfXsTY82XamA.uJZmkNV2ts6PPE1UlRsfrvkAGQumcby', 'milo@yahoo.com', 1, 'user', '2022-03-25 12:30:00', '2022-03-25 12:30:00', 'Michelle', 'Milo', 'https://picsum.photos/203', 'Data engineer who likes tech and the outdoors.');

COMMIT;


-- -----------------------------------------------------
-- Data for table `guild`
-- -----------------------------------------------------
START TRANSACTION;
USE `skillguilddb`;
INSERT INTO `guild` (`id`, `created_by_user_id`, `name`, `description`, `is_public`, `cover_img`, `membership_criteria`, `created_on`, `last_updated`) VALUES (1, 2, 'Code Brains', 'Learn by doing. Try a holistic approach to learning in which you study the theory and immediately put it into practice by building working applications.', 0, 'https://images.unsplash.com/photo-1575089976121-8ed7b2a54265', 'Anyone with an interest in learning frontend development can join. We encourage publishing 2 to 3 times per month.', '2022-05-25 12:30:00', '2022-05-26 12:30:00');
INSERT INTO `guild` (`id`, `created_by_user_id`, `name`, `description`, `is_public`, `cover_img`, `membership_criteria`, `created_on`, `last_updated`) VALUES (2, 3, 'Tech in Motion', 'Tech in Motion is a tech event series where thought leaders come together and share ideas. This is a place where enthusiasts can learn from professionals and inspire one another.', 1, 'https://images.unsplash.com/photo-1597534458220-9fb4969f2df5?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1674&q=80', 'Open to all', '2022-04-01 11:00:00', '2022-04-15 12:00:00');
INSERT INTO `guild` (`id`, `created_by_user_id`, `name`, `description`, `is_public`, `cover_img`, `membership_criteria`, `created_on`, `last_updated`) VALUES (3, 3, 'Design Geeks', 'Start building responsive websites with HTML, CSS, and JavaScript — a versatile skill set with powerful applications in a variety of design, marketing, and other tech-adjacent roles', 1, 'https://images.unsplash.com/photo-1533122250115-6bb28e9a48c3?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80', 'No experience required, open to anyone who wants to learn about FE development', '2022-04-10 9:00:00', '2022-04-15 12:00:00');
INSERT INTO `guild` (`id`, `created_by_user_id`, `name`, `description`, `is_public`, `cover_img`, `membership_criteria`, `created_on`, `last_updated`) VALUES (4, 5, 'Photo Phactory', 'With so many cameras available, figuring out how all the specifications and options translate into your everyday use is complicated.', 1, 'https://images.unsplash.com/photo-1452780212940-6f5c0d14d848?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1776&q=80', 'Geared to novices with basic camera knowledge, members are encouraged to keep an active profile and share their work', '2022-05-01 6:30:00', '2022-05-01 6:30:00');
INSERT INTO `guild` (`id`, `created_by_user_id`, `name`, `description`, `is_public`, `cover_img`, `membership_criteria`, `created_on`, `last_updated`) VALUES (5, 2, 'The Mixdown', 'Learn Ableton Live by watching how our globally-successful artists and producers take this incredible Digital Audio Workstation (DAW) utilise it to the full to create their hit tracks.', 0, 'https://images.unsplash.com/photo-1593697820910-a2b68670c1e1', 'Must be a member of the Fader Pro community ', '2022-05-15 10:00:00', '2022-05-25 12:00:00');
INSERT INTO `guild` (`id`, `created_by_user_id`, `name`, `description`, `is_public`, `cover_img`, `membership_criteria`, `created_on`, `last_updated`) VALUES (6, 2, 'Easton Brazilian Jiu Jitsu', 'Above all, we are dedicated to providing martial arts and fitness content of the highest quality. If you are looking for a new way to train or work out check us out!', 0, 'https://pmaabjj.com/wp-content/uploads/2014/03/Professor-Ryan-Cunningham.jpg', 'Must be an active member Of Easton BJJ to join', '2022-04-01 9:00:00', '2022-04-01 9:00:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `category`
-- -----------------------------------------------------
START TRANSACTION;
USE `skillguilddb`;
INSERT INTO `category` (`id`, `name`, `description`, `img_url`) VALUES (1, 'Web Development', 'Web Development', 'https://images.unsplash.com/photo-1549082984-1323b94df9a6');
INSERT INTO `category` (`id`, `name`, `description`, `img_url`) VALUES (2, 'Product Management', 'Product Management', 'https://images.unsplash.com/photo-1517245386807-bb43f82c33c4');
INSERT INTO `category` (`id`, `name`, `description`, `img_url`) VALUES (3, 'Data Engineering', 'Data Engineering', 'https://images.unsplash.com/photo-1518186285589-2f7649de83e0');
INSERT INTO `category` (`id`, `name`, `description`, `img_url`) VALUES (4, 'Photography', 'Photography', 'https://images.unsplash.com/photo-1520390138845-fd2d229dd553?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80');
INSERT INTO `category` (`id`, `name`, `description`, `img_url`) VALUES (5, 'Music Production', 'Music Production', 'https://images.unsplash.com/photo-1629834790224-7c0bd37f5d39?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=735&q=80');
INSERT INTO `category` (`id`, `name`, `description`, `img_url`) VALUES (6, 'Brazilian Jiu Jitsu', 'Brazilian Jiu Jitsu', 'https://images.unsplash.com/photo-1585511543150-dc91145bbc77?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1740&q=80');

COMMIT;


-- -----------------------------------------------------
-- Data for table `topic`
-- -----------------------------------------------------
START TRANSACTION;
USE `skillguilddb`;
INSERT INTO `topic` (`id`, `name`, `description`, `is_tech`) VALUES (1, 'Frontend Web Development', 'Web development frontend', 1);
INSERT INTO `topic` (`id`, `name`, `description`, `is_tech`) VALUES (2, 'Backend Web Development', 'Web development backend', 1);
INSERT INTO `topic` (`id`, `name`, `description`, `is_tech`) VALUES (3, 'Product Development', 'All aspects of product development', 0);
INSERT INTO `topic` (`id`, `name`, `description`, `is_tech`) VALUES (4, 'Data Engineering', 'Building systems that make sense of data', 1);
INSERT INTO `topic` (`id`, `name`, `description`, `is_tech`) VALUES (5, 'Career Development', 'Maximizing developer growth and productivity', 0);
INSERT INTO `topic` (`id`, `name`, `description`, `is_tech`) VALUES (6, 'Photography Composition', 'Getting the most out of our cameras', 0);
INSERT INTO `topic` (`id`, `name`, `description`, `is_tech`) VALUES (7, 'Music Production Workflow', 'Ableton focoused lessons regarding workflow tips and techniques', 0);

COMMIT;


-- -----------------------------------------------------
-- Data for table `status`
-- -----------------------------------------------------
START TRANSACTION;
USE `skillguilddb`;
INSERT INTO `status` (`id`, `name`) VALUES (1, 'draft');
INSERT INTO `status` (`id`, `name`) VALUES (2, 'scheduled');
INSERT INTO `status` (`id`, `name`) VALUES (3, 'published');

COMMIT;


-- -----------------------------------------------------
-- Data for table `content`
-- -----------------------------------------------------
START TRANSACTION;
USE `skillguilddb`;
INSERT INTO `content` (`id`, `guild_id`, `created_by_user_id`, `status_id`, `title`, `description`, `publish_date`, `is_public`, `is_live`, `last_updated`, `length_minutes`, `presentation_date`) VALUES (1, 1, 2, 2, 'Angular Fundamentals', 'Overview of Angular application architecture essentials', '2022-05-25 12:30:00', 0, 0, '2022-05-25 12:30:00', 60, '2022-05-26 12:30:00');
INSERT INTO `content` (`id`, `guild_id`, `created_by_user_id`, `status_id`, `title`, `description`, `publish_date`, `is_public`, `is_live`, `last_updated`, `length_minutes`, `presentation_date`) VALUES (2, 2, 3, 3, 'Advanced Angular', 'Introduces topics related to advanced usage of the Angular framework.', '2022-06-01 12:30:00', 0, 0, '2022-06-01 12:30:00', 30, '2022-06-06 12:30:00');
INSERT INTO `content` (`id`, `guild_id`, `created_by_user_id`, `status_id`, `title`, `description`, `publish_date`, `is_public`, `is_live`, `last_updated`, `length_minutes`, `presentation_date`) VALUES (3, 3, 3, 3, 'HTML and CSS fundamentals', 'Introduction to two cornerstones of the web world- CSS and HTML', '2022-06-01 12:00:00', 1, 1, '2022-06-03 12:00:00', 90, '2022-06-10 12:00:00');
INSERT INTO `content` (`id`, `guild_id`, `created_by_user_id`, `status_id`, `title`, `description`, `publish_date`, `is_public`, `is_live`, `last_updated`, `length_minutes`, `presentation_date`) VALUES (4, 4, 5, 2, 'Photo Composition For Novices', 'A well-composed photograph is really a matter of opinion, but there are a few tricks that tend to result in better pictures. That\'s what we take a look at in this lesson.', '2022-05-17 7:10:00', 1, 1, '2022-05-17 7:10:00', 90, '2022-06-15 9:00:00');
INSERT INTO `content` (`id`, `guild_id`, `created_by_user_id`, `status_id`, `title`, `description`, `publish_date`, `is_public`, `is_live`, `last_updated`, `length_minutes`, `presentation_date`) VALUES (5, 5, 2, 1, 'Ableton Workflow', 'Marc takes you through his entire production process from top to bottom', '2022-05-12 4:46:00', 1, 0, '2022-06-02 10:00:00', 120, '2022-07-01 7:00:00');
INSERT INTO `content` (`id`, `guild_id`, `created_by_user_id`, `status_id`, `title`, `description`, `publish_date`, `is_public`, `is_live`, `last_updated`, `length_minutes`, `presentation_date`) VALUES (6, 6, 2, 1, 'Improving Your Half Guard Game', 'Learn how to improve your half guard from some of our best coaches and half guard players. We cover everything from retention, passes, and sweeps.', '2022-05-15 5:30:00', 0, 1, '2022-06-15 5:30:00', 90, '2022-06-15 5:30:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `interaction_type`
-- -----------------------------------------------------
START TRANSACTION;
USE `skillguilddb`;
INSERT INTO `interaction_type` (`id`, `name`, `img_url`) VALUES (1, 'question', NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `interaction`
-- -----------------------------------------------------
START TRANSACTION;
USE `skillguilddb`;
INSERT INTO `interaction` (`id`, `content_id`, `user_id`, `interaction_type_id`, `created_on`) VALUES (1, 1, 2, 1, '2022-05-25 12:30:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `comment`
-- -----------------------------------------------------
START TRANSACTION;
USE `skillguilddb`;
INSERT INTO `comment` (`id`, `user_id`, `content_id`, `in_reply_to_id`, `text_content`, `has_been_edited`, `created_on`, `last_updated`) VALUES (1, 2, 1, NULL, 'Thank you', 0, '2022-05-25 12:30:00', '2022-05-26 12:30:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `content_topic`
-- -----------------------------------------------------
START TRANSACTION;
USE `skillguilddb`;
INSERT INTO `content_topic` (`content_id`, `topic_id`) VALUES (1, 1);
INSERT INTO `content_topic` (`content_id`, `topic_id`) VALUES (2, 1);
INSERT INTO `content_topic` (`content_id`, `topic_id`) VALUES (3, 1);
INSERT INTO `content_topic` (`content_id`, `topic_id`) VALUES (4, 6);
INSERT INTO `content_topic` (`content_id`, `topic_id`) VALUES (5, 7);

COMMIT;


-- -----------------------------------------------------
-- Data for table `member`
-- -----------------------------------------------------
START TRANSACTION;
USE `skillguilddb`;
INSERT INTO `member` (`guild_id`, `user_id`, `approved_by`, `moderator`, `created_on`) VALUES (1, 2, 1, 2, '2022-05-25 12:30:00');
INSERT INTO `member` (`guild_id`, `user_id`, `approved_by`, `moderator`, `created_on`) VALUES (2, 3, 1, 3, '2022-05-29 12:00:00');
INSERT INTO `member` (`guild_id`, `user_id`, `approved_by`, `moderator`, `created_on`) VALUES (3, 2, 4, 4, '2022-05-20 7:15:00');
INSERT INTO `member` (`guild_id`, `user_id`, `approved_by`, `moderator`, `created_on`) VALUES (4, 5, 4, 4, '2022-04-28 5:15:00');
INSERT INTO `member` (`guild_id`, `user_id`, `approved_by`, `moderator`, `created_on`) VALUES (5, 4, 5, 5, '2022-05-01 8:15:00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `group_category`
-- -----------------------------------------------------
START TRANSACTION;
USE `skillguilddb`;
INSERT INTO `group_category` (`guild_id`, `category_id`) VALUES (1, 1);
INSERT INTO `group_category` (`guild_id`, `category_id`) VALUES (4, 4);
INSERT INTO `group_category` (`guild_id`, `category_id`) VALUES (5, 5);
INSERT INTO `group_category` (`guild_id`, `category_id`) VALUES (6, 6);
INSERT INTO `group_category` (`guild_id`, `category_id`) VALUES (2, 1);
INSERT INTO `group_category` (`guild_id`, `category_id`) VALUES (3, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `resource_type`
-- -----------------------------------------------------
START TRANSACTION;
USE `skillguilddb`;
INSERT INTO `resource_type` (`id`, `name`, `description`) VALUES (1, 'slides', 'Slides for presenting');
INSERT INTO `resource_type` (`id`, `name`, `description`) VALUES (2, 'video', 'Presentation video');
INSERT INTO `resource_type` (`id`, `name`, `description`) VALUES (3, 'blog', 'Presentation blog');
INSERT INTO `resource_type` (`id`, `name`, `description`) VALUES (4, 'code', 'Sample code ');
INSERT INTO `resource_type` (`id`, `name`, `description`) VALUES (5, 'external link', 'URL');

COMMIT;


-- -----------------------------------------------------
-- Data for table `resource`
-- -----------------------------------------------------
START TRANSACTION;
USE `skillguilddb`;
INSERT INTO `resource` (`id`, `resource_type_id`, `title`, `description`, `resource_url`) VALUES (1, 1, 'Angular Fundamentals ', 'Essentials of Angular', 'https://docs.google.com/presentation/d/1XXJrCPqYbSV2cdk8z8rBNjmMe2sBGjIT6Eil0a3l56I/edit?usp=sharing');
INSERT INTO `resource` (`id`, `resource_type_id`, `title`, `description`, `resource_url`) VALUES (2, 4, 'Passing data between components', '@Input and @Output decorators can be used to pass data between parent and child components', 'https://gist.github.com/acary/8ff5ed6f08797023bcad2ec130679e01');
INSERT INTO `resource` (`id`, `resource_type_id`, `title`, `description`, `resource_url`) VALUES (3, 5, 'Ableton Workflow', 'Ableton Live workflow resource', 'https://www.ableton.com/en/live/learn-live/workflows/');
INSERT INTO `resource` (`id`, `resource_type_id`, `title`, `description`, `resource_url`) VALUES (4, 2, 'Angular 13 Crash Course', 'Get up to speed with the changes of Angular 13', 'https://www.youtube.com/watch?v=PUxNiC6Qye4');
INSERT INTO `resource` (`id`, `resource_type_id`, `title`, `description`, `resource_url`) VALUES (5, 2, 'Angular Tutorial for Beginners: Learn Angular & TypeScript', 'Angular tutorial for beginners: Learn Angular & TypeScript from scratch. ', 'https://www.youtube.com/watch?v=k5E2AVpwsko&t');

COMMIT;


-- -----------------------------------------------------
-- Data for table `content_resource`
-- -----------------------------------------------------
START TRANSACTION;
USE `skillguilddb`;
INSERT INTO `content_resource` (`content_id`, `resource_id`) VALUES (1, 1);
INSERT INTO `content_resource` (`content_id`, `resource_id`) VALUES (1, 2);
INSERT INTO `content_resource` (`content_id`, `resource_id`) VALUES (1, 4);
INSERT INTO `content_resource` (`content_id`, `resource_id`) VALUES (2, 5);
INSERT INTO `content_resource` (`content_id`, `resource_id`) VALUES (5, 3);

COMMIT;


-- -----------------------------------------------------
-- Data for table `question`
-- -----------------------------------------------------
START TRANSACTION;
USE `skillguilddb`;
INSERT INTO `question` (`id`, `content_id`, `question`, `correct_answer`) VALUES (1, 1, 'What does TS stand for?', 'TypeScript');
INSERT INTO `question` (`id`, `content_id`, `question`, `correct_answer`) VALUES (2, 1, 'What is a Directive?', 'Class that adds additional behavior to elements');
INSERT INTO `question` (`id`, `content_id`, `question`, `correct_answer`) VALUES (3, 1, 'What is a Module?', 'A place to group components, directives, services, and pipes');
INSERT INTO `question` (`id`, `content_id`, `question`, `correct_answer`) VALUES (4, 2, 'How can data be passed between components?', '@Input and @Output decorators can be used to pass data between child and parent components');
INSERT INTO `question` (`id`, `content_id`, `question`, `correct_answer`) VALUES (5, 4, 'What is the rule of thirds?', 'The rule of thirds is a composition guideline that places your subject in the left or right third of an image, leaving the other two thirds more open. While there are other forms of composition, the rule of thirds generally leads to compelling and well-composed shots.');
INSERT INTO `question` (`id`, `content_id`, `question`, `correct_answer`) VALUES (6, 4, 'What is the best time of day for capturing well-composed photographs?', 'Genrally about an hour right after sunrise and an hour right before sunset is considered to be the best light for taking photos');

COMMIT;


-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema rrs_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Database rrs_db
-- -----------------------------------------------------
create database rrs_db;
USE `rrs_db` ;

-- -----------------------------------------------------
-- Table `rrs_db`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rrs_db`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC))
ENGINE = InnoDB;

drop table user_personal_details;
-- -----------------------------------------------------
-- Table `rrs_db`.`user_personal_details`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rrs_db`.`user_personal_details` (
  `name` VARCHAR(40) NOT NULL,
  `surname` VARCHAR(40) NOT NULL,
  `birthdate` DATE NOT NULL,
  `gender` ENUM("male", "female") NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_user_personal_details_user_credentials`
    FOREIGN KEY (`user_id`)
    REFERENCES `rrs_db`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rrs_db`.`admins`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rrs_db`.`admins` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rrs_db`.`restaurants`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `rrs_db`.`restaurants` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(80) NOT NULL,
  `address` VARCHAR(80) NOT NULL,
  `description` VARCHAR(300) NOT NULL,
  `establishment_year` INT NOT NULL,
  `score` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `rrs_db`.`weights`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rrs_db`.`weights` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `value` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rrs_db`.`restaurant_reviews`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rrs_db`.`restaurant_reviews` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(400) NOT NULL,
  `user_id` INT NOT NULL,
  `restaurant_id` INT NOT NULL,
  `weight_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_restaurant_review_user_credentials1_idx` (`user_id` ASC),
  INDEX `fk_restaurant_review_restaurant_details1_idx` (`restaurant_id` ASC),
  INDEX `fk_restaurant_reviews_weights1_idx` (`weight_id` ASC),
  CONSTRAINT `fk_restaurant_review_user_credentials1`
    FOREIGN KEY (`user_id`)
    REFERENCES `rrs_db`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_restaurant_review_restaurant_details1`
    FOREIGN KEY (`restaurant_id`)
    REFERENCES `rrs_db`.`restaurants` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_restaurant_reviews_weights1`
    FOREIGN KEY (`weight_id`)
    REFERENCES `rrs_db`.`weights` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rrs_db`.`keywords`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rrs_db`.`keywords` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `weight_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  INDEX `fk_keyword_weight_weight_details1_idx` (`weight_id` ASC),
  CONSTRAINT `fk_keyword_weight_weight_details1`
    FOREIGN KEY (`weight_id`)
    REFERENCES `rrs_db`.`weights` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;











select * from admins;




insert into admins(login, password)
values ("root", "1234");

select * from users;

select * from restaurants;

select * from weights;

/*
insert into weights(name, value)
values ("Awful", -5),
("Bad", -2),
("Normal", 0),
("Good", 2),
("Excellent", 5);
*/

select * from keywords;

select * from restaurants;


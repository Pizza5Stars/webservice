SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema pizza5stars_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `pizza5stars_db` DEFAULT CHARACTER SET utf8 ;
USE `pizza5stars_db` ;

-- -----------------------------------------------------
-- Table `pizza5stars_db`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pizza5stars_db`.`customer` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(250) NOT NULL,
  `firstname` VARCHAR(45) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `deleted` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pizza5stars_db`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pizza5stars_db`.`address` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `street` VARCHAR(100) NOT NULL,
  `zipcode` VARCHAR(10) NOT NULL,
  `housenumber` VARCHAR(10) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `firstname` VARCHAR(45) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `user_id` INT(11) NULL DEFAULT NULL,
  `phone` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_address_user_idx` (`user_id` ASC),
  CONSTRAINT `fk_address_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `pizza5stars_db`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pizza5stars_db`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pizza5stars_db`.`order` (
  `nr` INT(11) NOT NULL AUTO_INCREMENT,
  `date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`nr`),
  INDEX `fk_order_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_order_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `pizza5stars_db`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pizza5stars_db`.`receipt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pizza5stars_db`.`receipt` (
  `nr` INT(11) NOT NULL AUTO_INCREMENT,
  `total` DECIMAL(4,2) UNSIGNED NOT NULL,
  `date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `order_nr` INT(11) NOT NULL,
  `address_id` INT(11) NOT NULL,
  PRIMARY KEY (`nr`),
  INDEX `fk_bill_order1_idx` (`order_nr` ASC),
  INDEX `fk_bill_address1_idx` (`address_id` ASC),
  CONSTRAINT `fk_bill_address1`
    FOREIGN KEY (`address_id`)
    REFERENCES `pizza5stars_db`.`address` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bill_order1`
    FOREIGN KEY (`order_nr`)
    REFERENCES `pizza5stars_db`.`order` (`nr`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pizza5stars_db`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pizza5stars_db`.`category` (
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pizza5stars_db`.`ingredient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pizza5stars_db`.`ingredient` (
  `name` VARCHAR(45) NOT NULL,
  `price` DECIMAL(3,2) UNSIGNED NOT NULL,
  `category_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`name`),
  INDEX `fk_ingredient_category1_idx` (`category_name` ASC),
  CONSTRAINT `fk_ingredient_category1`
    FOREIGN KEY (`category_name`)
    REFERENCES `pizza5stars_db`.`category` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pizza5stars_db`.`size`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pizza5stars_db`.`size` (
  `name` VARCHAR(45) NOT NULL,
  `size` INT(11) NOT NULL,
  `price_factor` DECIMAL(3,2) NOT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pizza5stars_db`.`company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pizza5stars_db`.`company` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pizza5stars_db`.`pizza`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pizza5stars_db`.`pizza` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `user_id` INT(11) NULL DEFAULT NULL,
  `size_name` VARCHAR(45) NOT NULL,
  `is_default` TINYINT(1) NOT NULL,
  `company_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_pizza_user1_idx` (`user_id` ASC),
  INDEX `fk_pizza_size1_idx` (`size_name` ASC),
  INDEX `fk_pizza_company_idx` (`company_id` ASC),
  CONSTRAINT `fk_pizza_size`
    FOREIGN KEY (`size_name`)
    REFERENCES `pizza5stars_db`.`size` (`name`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_pizza_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `pizza5stars_db`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_pizza_company`
    FOREIGN KEY (`company_id`)
    REFERENCES `pizza5stars_db`.`company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pizza5stars_db`.`pizza_ingredient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pizza5stars_db`.`pizza_ingredient` (
  `pizza_id` INT(11) NOT NULL,
  `ingredient_name` VARCHAR(45) NOT NULL,
  INDEX `fk_pizza_ingredient_pizza1_idx` (`pizza_id` ASC),
  INDEX `fk_pizza_ingredient_ingredient1_idx` (`ingredient_name` ASC),
  CONSTRAINT `fk_pizza_ingredient_ingredient1`
    FOREIGN KEY (`ingredient_name`)
    REFERENCES `pizza5stars_db`.`ingredient` (`name`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_pizza_ingredient_pizza1`
    FOREIGN KEY (`pizza_id`)
    REFERENCES `pizza5stars_db`.`pizza` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pizza5stars_db`.`pizza_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pizza5stars_db`.`pizza_order` (
  `order_nr` INT(11) NOT NULL,
  `pizza_id` INT(11) NOT NULL,
  INDEX `fk_pizza_order_order1_idx` (`order_nr` ASC),
  INDEX `fk_pizza_order_pizza1_idx` (`pizza_id` ASC),
  CONSTRAINT `fk_pizza_order_order1`
    FOREIGN KEY (`order_nr`)
    REFERENCES `pizza5stars_db`.`order` (`nr`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_pizza_order_pizza1`
    FOREIGN KEY (`pizza_id`)
    REFERENCES `pizza5stars_db`.`pizza` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `pizza5stars_db`.`rating_pizza`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pizza5stars_db`.`rating_pizza` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `pizza_id` INT NULL,
  `rating` FLOAT NULL DEFAULT 0.0,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_rating_pizza`
    FOREIGN KEY (`id`)
    REFERENCES `pizza5stars_db`.`pizza` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rating_customer`
    FOREIGN KEY (`id`)
    REFERENCES `pizza5stars_db`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

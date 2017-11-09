-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema project_tracker
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema project_tracker
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `project_tracker` DEFAULT CHARACTER SET utf8 ;
USE `project_tracker` ;

-- -----------------------------------------------------
-- Table `project_tracker`.`project`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_tracker`.`project` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(50) NOT NULL,
  `Summary` VARCHAR(250) NOT NULL,
  `Topic` VARCHAR(30) NOT NULL,
  `Type` ENUM('capstone', 'thesis') NOT NULL,
  `StartDate` DATE NOT NULL,
  `EndDate` DATE NOT NULL,
  `DueDate` DATE NOT NULL,
  `Completed` TINYINT(4) NOT NULL,
  `ProposalApproved` TINYINT(4) NOT NULL,
  `FinalDefenseDate` DATE NOT NULL,
  `PlagiarismPercentage` DOUBLE NOT NULL,
  `Grade` VARCHAR(4) NOT NULL,
  `CommitteeID` INT(11) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `CommitteID_FK_idx` (`CommitteeID` ASC),
  CONSTRAINT `CommitteID_FK`
    FOREIGN KEY (`CommitteeID`)
    REFERENCES `project_tracker`.`committee` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`committee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_tracker`.`committee` (
  `ID` INT(11) NOT NULL,
  `ProjectID` INT(11) NOT NULL,
  `ProjectGrade` VARCHAR(4) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `ProjectID_FK_idx` (`ProjectID` ASC),
  CONSTRAINT `ProjectID_FK`
    FOREIGN KEY (`ProjectID`)
    REFERENCES `project_tracker`.`project` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_tracker`.`user` (
  `UserName` VARCHAR(7) NOT NULL,
  `FirstName` VARCHAR(20) NOT NULL,
  `LastName` VARCHAR(20) NOT NULL,
  `Password` VARCHAR(20) NOT NULL,
  `Image` VARCHAR(150) NULL DEFAULT NULL,
  `GraduationDate` DATE NULL DEFAULT NULL,
  `Department` VARCHAR(50) NULL DEFAULT NULL,
  `Major` VARCHAR(50) NULL DEFAULT NULL,
  `Role` ENUM('student', 'faculty', 'adjunct') NOT NULL,
  PRIMARY KEY (`UserName`),
  UNIQUE INDEX `UserName_UNIQUE` (`UserName` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`email`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_tracker`.`email` (
  `UserName` VARCHAR(7) NOT NULL,
  `EmailAddress` VARCHAR(30) NOT NULL,
  `EmailType` ENUM('work', 'school', 'personal') NULL DEFAULT NULL,
  PRIMARY KEY (`UserName`, `EmailAddress`),
  CONSTRAINT `UserName_Email_FK`
    FOREIGN KEY (`UserName`)
    REFERENCES `project_tracker`.`user` (`UserName`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`statuses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_tracker`.`statuses` (
  `Code` INT(11) NOT NULL,
  `Description` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`Code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`milestone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_tracker`.`milestone` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `StatusCode` INT(11) NOT NULL,
  `Name` VARCHAR(30) NOT NULL,
  `Number` INT(11) NOT NULL,
  `DueDate` DATE NOT NULL,
  `Approved` TINYINT(4) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `StatusCode_FK_idx` (`StatusCode` ASC),
  CONSTRAINT `StatusCode_FK`
    FOREIGN KEY (`StatusCode`)
    REFERENCES `project_tracker`.`statuses` (`Code`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`office`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_tracker`.`office` (
  `UserName` VARCHAR(7) NOT NULL,
  `OfficeNumber` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`UserName`, `OfficeNumber`),
  CONSTRAINT `UserName_Office_FK`
    FOREIGN KEY (`UserName`)
    REFERENCES `project_tracker`.`user` (`UserName`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`phone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_tracker`.`phone` (
  `UserName` VARCHAR(7) NOT NULL,
  `PhoneNumber` VARCHAR(15) NOT NULL,
  `PhoneType` ENUM('cell', 'home', 'office', 'other') NOT NULL,
  PRIMARY KEY (`UserName`, `PhoneNumber`),
  CONSTRAINT `UserName_FK`
    FOREIGN KEY (`UserName`)
    REFERENCES `project_tracker`.`user` (`UserName`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`project_milestone_link`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_tracker`.`project_milestone_link` (
  `ProjectID` INT(11) NOT NULL,
  `MilestoneID` INT(11) NOT NULL,
  PRIMARY KEY (`ProjectID`, `MilestoneID`),
  INDEX `MilestoneID_idx` (`MilestoneID` ASC),
  CONSTRAINT `MilestoneID_ProjectID_FK`
    FOREIGN KEY (`MilestoneID`)
    REFERENCES `project_tracker`.`milestone` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `ProjectID_Milestone_FK`
    FOREIGN KEY (`ProjectID`)
    REFERENCES `project_tracker`.`project` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`user_committee_link`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_tracker`.`user_committee_link` (
  `UserName` VARCHAR(7) NOT NULL,
  `CommitteID` INT(11) NOT NULL,
  PRIMARY KEY (`UserName`, `CommitteID`),
  INDEX `CommitteeID_FK_idx` (`CommitteID` ASC),
  CONSTRAINT `CommitteeID_User_FK`
    FOREIGN KEY (`CommitteID`)
    REFERENCES `project_tracker`.`committee` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `UserName_Committee_FK`
    FOREIGN KEY (`UserName`)
    REFERENCES `project_tracker`.`user` (`UserName`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`user_project_link`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_tracker`.`user_project_link` (
  `UserName` VARCHAR(7) NOT NULL,
  `ProjectID` INT(11) NOT NULL,
  PRIMARY KEY (`UserName`, `ProjectID`),
  INDEX `ProjectID_FK_idx` (`ProjectID` ASC),
  CONSTRAINT `ProjectID_User_FK`
    FOREIGN KEY (`ProjectID`)
    REFERENCES `project_tracker`.`project` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `UserName_Project_FK`
    FOREIGN KEY (`UserName`)
    REFERENCES `project_tracker`.`user` (`UserName`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

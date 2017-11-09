-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema Project_Tracker
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Project_Tracker
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Project_Tracker` DEFAULT CHARACTER SET utf8 ;
USE `Project_Tracker` ;

-- -----------------------------------------------------
-- Table `Project_Tracker`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Project_Tracker`.`User` (
  `UserName` VARCHAR(7) NOT NULL,
  `FirstName` VARCHAR(20) NOT NULL,
  `LastName` VARCHAR(20) NOT NULL,
  `Password` VARCHAR(20) NOT NULL,
  `Image` VARCHAR(150) NULL,
  `GraduationDate` DATE NULL,
  `Department` VARCHAR(50) NULL,
  `Major` VARCHAR(50) NULL,
  `Role` ENUM('student', 'faculty', 'adjunct') NOT NULL,
  PRIMARY KEY (`UserName`),
  UNIQUE INDEX `UserName_UNIQUE` (`UserName` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project_Tracker`.`Phone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Project_Tracker`.`Phone` (
  `UserName` VARCHAR(7) NOT NULL,
  `Number` VARCHAR(15) NOT NULL,
  `Type` ENUM('cell', 'home', 'office', 'other') NOT NULL,
  PRIMARY KEY (`UserName`, `Number`),
  CONSTRAINT `UserName_Phone_FK`
    FOREIGN KEY (`UserName`)
    REFERENCES `Project_Tracker`.`User` (`UserName`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project_Tracker`.`Office`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Project_Tracker`.`Office` (
  `UserName` VARCHAR(7) NOT NULL,
  `OfficeNumber` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`UserName`, `OfficeNumber`),
  CONSTRAINT `UserName_Office_FK`
    FOREIGN KEY (`UserName`)
    REFERENCES `Project_Tracker`.`User` (`UserName`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project_Tracker`.`Email`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Project_Tracker`.`Email` (
  `UserName` VARCHAR(7) NOT NULL,
  `Address` VARCHAR(30) NOT NULL,
  `Type` ENUM('work', 'school', 'personal') NULL,
  PRIMARY KEY (`UserName`, `Address`),
  CONSTRAINT `UserName_Email_FK`
    FOREIGN KEY (`UserName`)
    REFERENCES `Project_Tracker`.`User` (`UserName`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project_Tracker`.`Committee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Project_Tracker`.`Committee` (
  `ID` INT NOT NULL,
  `ProjectID` INT NOT NULL,
  `ProjectGrade` VARCHAR(4) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `ProjectID_FK_idx` (`ProjectID` ASC),
  CONSTRAINT `ProjectID_Committee_FK`
    FOREIGN KEY (`ProjectID`)
    REFERENCES `Project_Tracker`.`Project` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project_Tracker`.`Project`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Project_Tracker`.`Project` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(50) NOT NULL,
  `Summary` VARCHAR(250) NOT NULL,
  `Topic` VARCHAR(30) NOT NULL,
  `Type` ENUM('capstone', 'thesis') NOT NULL,
  `StartDate` DATE NOT NULL,
  `EndDate` DATE NOT NULL,
  `DueDate` DATE NOT NULL,
  `Completed?` TINYINT NOT NULL,
  `ProposalApproved?` TINYINT NOT NULL,
  `FinalDefenseDate` DATE NOT NULL,
  `PlagiarismPercentage` DOUBLE NOT NULL,
  `ProjectGrade` VARCHAR(4) NOT NULL,
  `CommitteeID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `CommitteID_FK_idx` (`CommitteeID` ASC),
  CONSTRAINT `CommitteID_Project_FK`
    FOREIGN KEY (`CommitteeID`)
    REFERENCES `Project_Tracker`.`Committee` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project_Tracker`.`User_Project_Link`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Project_Tracker`.`User_Project_Link` (
  `UserName` VARCHAR(7) NOT NULL,
  `ProjectID` INT NOT NULL,
  PRIMARY KEY (`UserName`, `ProjectID`),
  INDEX `ProjectID_FK_idx` (`ProjectID` ASC),
  CONSTRAINT `UserName_Project_FK`
    FOREIGN KEY (`UserName`)
    REFERENCES `Project_Tracker`.`User` (`UserName`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `ProjectID_User_FK`
    FOREIGN KEY (`ProjectID`)
    REFERENCES `Project_Tracker`.`Project` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project_Tracker`.`Statuses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Project_Tracker`.`Statuses` (
  `Code` INT NOT NULL,
  `Description` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`Code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project_Tracker`.`Milestone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Project_Tracker`.`Milestone` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `StatusCode` INT NOT NULL,
  `Name` VARCHAR(30) NOT NULL,
  `Number` INT NOT NULL,
  `DueDate` DATE NOT NULL,
  `Approved?` TINYINT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `StatusCode_FK_idx` (`StatusCode` ASC),
  CONSTRAINT `StatusCode_Milestone_FK`
    FOREIGN KEY (`StatusCode`)
    REFERENCES `Project_Tracker`.`Statuses` (`Code`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project_Tracker`.`Project_Milestone_Link`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Project_Tracker`.`Project_Milestone_Link` (
  `ProjectID` INT NOT NULL,
  `MilestoneID` INT NOT NULL,
  PRIMARY KEY (`ProjectID`, `MilestoneID`),
  INDEX `MilestoneID_idx` (`MilestoneID` ASC),
  CONSTRAINT `ProjectID_Milestone_FK`
    FOREIGN KEY (`ProjectID`)
    REFERENCES `Project_Tracker`.`Project` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `MilestoneID_ProjectID_FK`
    FOREIGN KEY (`MilestoneID`)
    REFERENCES `Project_Tracker`.`Milestone` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project_Tracker`.`User_Committee_Link`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Project_Tracker`.`User_Committee_Link` (
  `UserName` VARCHAR(7) NOT NULL,
  `CommitteID` INT NOT NULL,
  PRIMARY KEY (`UserName`, `CommitteID`),
  INDEX `CommitteeID_FK_idx` (`CommitteID` ASC),
  CONSTRAINT `UserName_Committee_FK`
    FOREIGN KEY (`UserName`)
    REFERENCES `Project_Tracker`.`User` (`UserName`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `CommitteeID_User_FK`
    FOREIGN KEY (`CommitteID`)
    REFERENCES `Project_Tracker`.`Committee` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

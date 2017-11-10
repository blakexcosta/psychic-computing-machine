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
  `Name` VARCHAR(50) NULL,
  `Summary` VARCHAR(250) NULL,
  `Topic` VARCHAR(30) NULL,
  `Type` ENUM('capstone', 'thesis') NULL,
  `StartDate` DATE NULL,
  `EndDate` DATE NULL,
  `DueDate` DATE NULL,
  `Completed` TINYINT(4) NULL,
  `ProposalApproved` TINYINT(4) NULL,
  `FinalDefenseDate` DATE NULL,
  `PlagiarismPercentage` DOUBLE NULL,
  `ProjectGrade` VARCHAR(4) NULL,
  `CommitteeID` INT(11) NULL,
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
  `ProjectGrade` VARCHAR(4) NULL,
  PRIMARY KEY (`ID`, `ProjectID`),
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
  `LastName` VARCHAR(20) NULL,
  `Password` VARCHAR(20) NULL,
  `Image` VARCHAR(150) NULL DEFAULT NULL,
  `GraduationDate` DATE NULL DEFAULT NULL,
  `Department` VARCHAR(50) NULL DEFAULT NULL,
  `Major` VARCHAR(50) NULL DEFAULT NULL,
  `Role` ENUM('student', 'faculty', 'adjunct') NULL,
  PRIMARY KEY (`UserName`),
  UNIQUE INDEX `UserName_UNIQUE` (`UserName` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`email`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_tracker`.`email` (
  `UserName` VARCHAR(7) NOT NULL,
  `Address` VARCHAR(30) NOT NULL,
  `Type` ENUM('work', 'school', 'personal') NULL DEFAULT NULL,
  PRIMARY KEY (`UserName`, `Address`),
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
  `Description` VARCHAR(250) NULL,
  PRIMARY KEY (`Code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`milestone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_tracker`.`milestone` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `StatusCode` INT(11) NULL,
  `Name` VARCHAR(30) NULL,
  `Number` INT(11) NULL,
  `DueDate` DATE NULL,
  `Approved` TINYINT(4) NULL,
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
  `Number` VARCHAR(15) NOT NULL,
  `Type` ENUM('cell', 'home', 'office', 'other') NULL,
  PRIMARY KEY (`UserName`, `Number`),
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

-- -----------------------------------------------------
-- Data for table `project_tracker`.`project`
-- -----------------------------------------------------
START TRANSACTION;
USE `project_tracker`;
INSERT INTO `project_tracker`.`project` (`ID`, `Name`, `Summary`, `Topic`, `Type`, `StartDate`, `EndDate`, `DueDate`, `Completed`, `ProposalApproved`, `FinalDefenseDate`, `PlagiarismPercentage`, `ProjectGrade`, `CommitteeID`) VALUES (1, 'Project 01', 'Creating network cables by left or right handed technicians should not be used on the same network. This causes the network to implode if the streams are crossed.', 'Network implosion ', 'Thesis', '2016-08-20', '2017-04-28 ', '2017-05-20', TRUE, TRUE, '2017-05-05', TRUE, 'A', 1);
INSERT INTO `project_tracker`.`project` (`ID`, `Name`, `Summary`, `Topic`, `Type`, `StartDate`, `EndDate`, `DueDate`, `Completed`, `ProposalApproved`, `FinalDefenseDate`, `PlagiarismPercentage`, `ProjectGrade`, `CommitteeID`) VALUES (2, 'Project 02', 'Do men that stroke their chins in contemplation, think smarter when they have a beard?', 'Program better with a beard', 'Capstone', '2016-08-16', '2017-05-01', '2017-05-20', TRUE, TRUE, '2017-05-03', TRUE, 'B', 2);
INSERT INTO `project_tracker`.`project` (`ID`, `Name`, `Summary`, `Topic`, `Type`, `StartDate`, `EndDate`, `DueDate`, `Completed`, `ProposalApproved`, `FinalDefenseDate`, `PlagiarismPercentage`, `ProjectGrade`, `CommitteeID`) VALUES (3, 'Project 03', 'Recursively, this database.', 'Capstone tracking database', 'Capstone', '2015-08-14', '2016-05-06', '2016-05-17', TRUE, TRUE, '2016-05-07', TRUE, 'C', 3);

COMMIT;


-- -----------------------------------------------------
-- Data for table `project_tracker`.`user`
-- -----------------------------------------------------
START TRANSACTION;
USE `project_tracker`;
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('ab1234', 'Al', 'Baker', 'password', '', '5/15/2017', 'Grad', 'NSA', 'Student');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('dn1234', 'Don', 'Novello', 'password', '', '5/15/2017', 'Grad', 'IST', 'Student');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('gs1234', 'Guido', 'Sarducci', 'password', '', '5/13/2016', 'Grad', 'HCI', 'Student');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('txaics', 'Tanweer', 'Alam', 'password', '', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('gpavks', 'Garret', 'Arcoraci', 'password', 'https://ist.rit.edu/assets/img/people/gpavks.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('dlaics', 'Daniel', 'Ashbrook', 'password', 'https://ist.rit.edu/assets/img/people/dlaics.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('ciiics', 'Catherine', 'Beaton', 'password', 'https://ist.rit.edu/assets/img/people/ciiics.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('aobics', 'Alec', 'Berenbaum', 'password', '', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('tsbics', 'Todd', 'Bernhard', 'password', '', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('dsbics', 'Daniel', 'Bogaard', 'password', 'https://ist.rit.edu/assets/img/people/dsbics.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('cbbics', 'Charlie', 'Border', 'password', 'https://ist.rit.edu/assets/img/people/cbbics.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('spbics', 'Sean', 'Boyle', 'password', 'https://www.rit.edu/gccis/sites/rit.edu.gccis/files//Boyle%20Sean%20new%20headshot.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mjcics', 'Michael', 'Carroll', 'password', '', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mjcvks', 'Marco', 'Casale', 'password', '', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mjfics', 'Michael', 'Floeser', 'password', 'https://ist.rit.edu/assets/img/people/mjfics.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('nffbbu', 'Nick', 'Francesco', 'password', '', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('bdfvks', 'Bryan', 'French', 'password', 'https://ist.rit.edu/assets/img/people/bdfvks.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('dmgics', 'Dean', 'Ganskop', 'password', '', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('efgics', 'Erik', 'Golen', 'password', 'https://ist.rit.edu/assets/img/people/efgics.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jrhicsa', 'James', 'Habermas', 'password', '', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('vlhics', 'Vicki', 'Hanson', 'password', 'https://ist.rit.edu/assets/img/people/vlhics.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('bhhics', 'Bruce', 'Hartpence', 'password', 'https://ist.rit.edu/assets/img/people/bhhics.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('amhgss', 'Andrew', 'Herbert', 'password', 'https://ist.rit.edu/assets/img/people/amhgss.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('lwhfac', 'Larry', 'Hill', 'password', 'https://ist.rit.edu/assets/img/people/lwhfac.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('ephics', 'Ed', 'Holden', 'password', 'https://ist.rit.edu/assets/img/people/ephics.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mphics', 'Matt', 'Huenerfauth', 'password', 'https://ist.rit.edu/assets/img/people/mphics.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jcjics', 'Jeffrey', 'Jockel', 'password', 'https://www.rit.edu/gccis/sites/rit.edu.gccis/files//Jeff%20IST%20small.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mrjics', 'Mark', 'Juba', 'password', '', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jwkics', 'Jai', 'Kang', 'password', 'https://ist.rit.edu/assets/img/people/jwkics.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('drkisd', 'Dan', 'Kennedy', 'password', 'https://ist.rit.edu/assets/img/people/drkisd.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('hnkics', 'Heidi', 'Klossner', 'password', '', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mxkics1', 'Mitel', 'Kuliner', 'password', '', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('dmlics', 'Deborah', 'LaBelle', 'password', 'https://ist.rit.edu/assets/img/people/dmlics.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jalics', 'Jeffrey', 'Lasky', 'password', 'https://ist.rit.edu/assets/img/people/jalics.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jalvks', 'Jim', 'Leone', 'password', 'https://ist.rit.edu/assets/img/people/jalvks.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jxlics', 'Joseph', 'Loporcaro', 'password', '', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('phlics', 'Peter', 'Lutz', 'password', 'https://ist.rit.edu/assets/img/people/phlics.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('spmics', 'Sharon', 'Mason', 'password', 'https://ist.rit.edu/assets/img/people/spmics.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mjmics', 'Michael', 'McQuaid', 'password', 'https://ist.rit.edu/assets/img/people/mjmics.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('thoics', 'Tae', 'Oh', 'password', 'https://ist.rit.edu/assets/img/people/thoics.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('sphics', 'Sylvia', 'Perez-Hardy', 'password', 'https://ist.rit.edu/assets/img/people/sphics.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('djpihst', 'Jerry', 'Powell', 'password', 'https://ist.rit.edu/assets/img/people/djpihst.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('sarics', 'Scott', 'Root II', 'password', '', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('ldrics', 'Lawrence', 'Roth', 'password', '', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('kmsics', 'Katie', 'Sabourin', 'password', '', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('cxsics', 'Charles', 'Schneider', 'password', '', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('nxsvks', 'Nirmala', 'Shenoy', 'password', 'https://ist.rit.edu/assets/img/people/nxsvks.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('aesfaa', 'Adam', 'Smith', 'password', 'https://ist.rit.edu/assets/img/people/aesfaa.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jxtadm', 'John-Paul', 'Takats', 'password', '', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('bmtski', 'Brian', 'Tomaszewski', 'password', 'https://ist.rit.edu/assets/img/people/bmtski.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('rpvvks', 'Ronald', 'Vullo', 'password', 'https://ist.rit.edu/assets/img/people/rpvvks.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('axgvks', 'Ann', 'Warren', 'password', 'https://www.rit.edu/gccis/sites/rit.edu.gccis/files//ann.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('emwics', 'Elissa', 'Weeden', 'password', 'https://ist.rit.edu/assets/img/people/emwics.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jswics', 'Jonathan', 'Weissman', 'password', 'https://www.rit.edu/gccis/sites/rit.edu.gccis/files//jweissman%5B1%5D.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mayici', 'Michael', 'Yacci', 'password', 'https://www.rit.edu/gccis//sites/rit.edu.gccis/files//yacci%20new%20headshot.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('qyuvks', 'Qi', 'Yu', 'password', 'https://ist.rit.edu/assets/img/people/qyuvks.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('sjzics', 'Steve', 'Zilora', 'password', 'https://ist.rit.edu/assets/img/people/sjzics.jpg', '', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('rdbcst', 'Rhonda', 'Baker-Smith', 'password', 'https://ist.rit.edu/assets/img/people/rdbcst.jpg', '', 'Staff', '', 'Staff');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('GradCoord', 'Qi', 'Yu', 'password', 'https://ist.rit.edu/assets/img/people/qyuvks.jpg', '', 'Staff', '', 'Staff');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mchics', 'Melissa', 'Hanna', 'password', 'https://ist.rit.edu/assets/img/people/mchics.jpg', '', 'Staff', '', 'Staff');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jmpics', 'Jill', 'Persson', 'password', 'https://ist.rit.edu/assets/img/people/jmpics.jpg', '', 'Staff', '', 'Staff');

COMMIT;


-- -----------------------------------------------------
-- Data for table `project_tracker`.`email`
-- -----------------------------------------------------
START TRANSACTION;
USE `project_tracker`;
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('ab1234', 'ab1234@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('dn1234', 'dn1234@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('gs1234', 'gs1234@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('txaics', 'txaics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('gpavks', 'gpavks@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('dlaics', 'daniel.ashbrook@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('ciiics', 'catherine.beaton@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('aobics', 'aobics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('tsbics', 'tsbics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('dsbics', 'dsbics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('cbbics', 'cbbics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('spbics', 'Sean.Boyle@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('mjcics', 'mjcics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('mjcvks', 'mjcvks@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('mjfics', 'Michael.Floeser@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('nffbbu', 'nick@saunders.rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('bdfvks', 'bdfvks@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('dmgics', 'Dean.Ganskop@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('efgics', 'efgics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('jrhicsa', 'jrhicsa@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('vlhics', 'vlh@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('bhhics', 'Bruce.Hartpence@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('amhgss', 'amhgss@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('lwhfac', 'Lawrence.Hill@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('ephics', 'edward.holden@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('mphics', 'matt.huenerfauth@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('jcjics', 'jcjics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('mrjics', 'mrjics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('jwkics', 'jai.kang@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('drkisd', 'drkisd@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('hnkics', 'hnkics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('mxkics1', 'mxkics1@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('dmlics', 'dmlics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('jalics', 'Jeffrey.Lasky@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('jalvks', 'jalvks@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('jxlics', 'jxlics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('phlics', 'Peter.Lutz@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('spmics', 'Sharon.Mason@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('mjmics', 'mickmcquaid@gmail.com', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('thoics', 'Tom.Oh@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('sphics', 'sylvia.perez-hardy@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('djpihst', 'djpihst@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('sarics', 'sarics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('ldrics', 'ldrics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('kmsics', 'kmsics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('cxsics', 'cxsics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('nxsvks', 'nxsvks@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('aesfaa', 'adam.smith@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('jxtadm', 'jxtadm@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('bmtski', 'bmtski@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('rpvvks', 'rpv@mail.rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('axgvks', 'axgvks@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('emwics', 'emwics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('jswics', 'jswics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('mayici', 'mayici@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('qyuvks', 'qyuvks@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('sjzics', 'sjzics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('rdbcst', 'rdbcst@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `Address`, `Type`) VALUES ('GradCoord', 'qyuvks@rit.edu', 'School');

COMMIT;


-- -----------------------------------------------------
-- Data for table `project_tracker`.`office`
-- -----------------------------------------------------
START TRANSACTION;
USE `project_tracker`;
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('ab1234', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('dn1234', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('gs1234', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('txaics', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('gpavks', 'GOL 2315');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('dlaics', 'GOL 2329');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('ciiics', 'GOL 2621');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('aobics', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('tsbics', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('dsbics', 'GOL 2571');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('cbbics', 'GOL 2615');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('spbics', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('mjcics', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('mjcvks', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('mjfics', 'GOL 2669');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('nffbbu', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('bdfvks', 'GOL 2619');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('dmgics', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('efgics', 'GOL 2617');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('jrhicsa', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('vlhics', 'GOL 2645');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('bhhics', 'GOL 2323');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('amhgss', 'GOL 2323');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('lwhfac', 'GOL 2331');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('ephics', 'GOL 2655');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('mphics', 'GOL 2659');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('jcjics', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('mrjics', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('jwkics', 'GOL 2651');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('drkisd', 'GOL 2285');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('hnkics', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('mxkics1', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('dmlics', 'GOL 2627');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('jalics', 'GOL 26xx');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('jalvks', 'GOL 2657');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('jxlics', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('phlics', 'GOL 2345');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('spmics', 'GOL 2319');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('mjmics', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('thoics', 'GOL 2281');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('sphics', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('djpihst', 'CBT-2161');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('sarics', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('ldrics', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('kmsics', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('cxsics', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('nxsvks', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('aesfaa', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('jxtadm', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('bmtski', 'GOL 2673');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('rpvvks', 'GOL 2519');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('axgvks', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('emwics', 'GOL 2635');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('jswics', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('mayici', '');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('qyuvks', 'GOL 2669');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('sjzics', 'GOL 2109');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('rdbcst', 'GOL 2103');
INSERT INTO `project_tracker`.`office` (`UserName`, `OfficeNumber`) VALUES ('GradCoord', 'GOL 2669');

COMMIT;


-- -----------------------------------------------------
-- Data for table `project_tracker`.`phone`
-- -----------------------------------------------------
START TRANSACTION;
USE `project_tracker`;
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('ab1234', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('dn1234', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('gs1234', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('txaics', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('gpavks', '585-475-7854', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('dlaics', '585-475-4784', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('ciiics', '585-281-6162', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('aobics', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('tsbics', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('dsbics', '585-4755231', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('cbbics', '585-475-7946', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('spbics', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('mjcics', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('mjcvks', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('mjfics', '585-475-7031', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('nffbbu', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('bdfvks', '585-475-6511', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('dmgics', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('efgics', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('jrhicsa', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('vlhics', '585-475-5384', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('bhhics', '585-475-7938', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('amhgss', '585-475-4554', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('lwhfac', '585-475-7064', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('ephics', '585-475-5361', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('mphics', '585-475-2459', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('jcjics', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('mrjics', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('jwkics', '585-475-5362', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('drkisd', '585-475-2811', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('hnkics', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('mxkics1', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('dmlics', '585-475-5001', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('jalics', '585-475-2284', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('jalvks', '585-475-6451', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('jxlics', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('phlics', '585-475-6162', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('spmics', '585-475-6989', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('mjmics', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('thoics', '585-475-7642', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('sphics', '585-475-7941', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('djpihst', '585-475-2487', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('sarics', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('ldrics', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('kmsics', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('cxsics', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('nxsvks', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('aesfaa', '585-475-4552', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('jxtadm', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('bmtski', '585-475-2869', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('rpvvks', '585-475-7281', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('axgvks', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('emwics', '585-475-6733', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('jswics', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('mayici', '', '');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('qyuvks', '585-475-6929', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('sjzics', '585-475-7643', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('rdbcst', '585-475-7924', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `Number`, `Type`) VALUES ('GradCoord', '585-475-6929', 'Office');

COMMIT;


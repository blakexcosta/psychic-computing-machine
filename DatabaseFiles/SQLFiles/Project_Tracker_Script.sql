-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema project_tracker
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `project_tracker` ;

-- -----------------------------------------------------
-- Schema project_tracker
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `project_tracker` DEFAULT CHARACTER SET utf8 ;
USE `project_tracker` ;

-- -----------------------------------------------------
-- Table `project_tracker`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `project_tracker`.`user` ;

CREATE TABLE IF NOT EXISTS `project_tracker`.`user` (
  `UserName` VARCHAR(20) NOT NULL,
  `FirstName` VARCHAR(20) NULL,
  `LastName` VARCHAR(20) NULL,
  `Password` VARCHAR(50) NULL,
  `Image` VARCHAR(150) NULL,
  `GraduationDate` DATE NULL,
  `Department` VARCHAR(50) NULL,
  `Major` VARCHAR(50) NULL,
  `Role` ENUM('student', 'faculty', 'adjunct', 'staff') NULL,
  PRIMARY KEY (`UserName`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`email`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `project_tracker`.`email` ;

CREATE TABLE IF NOT EXISTS `project_tracker`.`email` (
  `UserName` VARCHAR(20) NOT NULL,
  `EmailAddress` VARCHAR(30) NOT NULL,
  `EmailType` ENUM('work', 'school', 'personal', 'none') NULL DEFAULT 'none',
  PRIMARY KEY (`UserName`, `EmailAddress`),
  CONSTRAINT `User_Email_FK`
    FOREIGN KEY (`UserName`)
    REFERENCES `project_tracker`.`user` (`UserName`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`statuses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `project_tracker`.`statuses` ;

CREATE TABLE IF NOT EXISTS `project_tracker`.`statuses` (
  `Code` INT(11) NOT NULL,
  `Description` VARCHAR(250) NULL,
  PRIMARY KEY (`Code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`milestone`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `project_tracker`.`milestone` ;

CREATE TABLE IF NOT EXISTS `project_tracker`.`milestone` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `StatusCode` INT(11) NULL,
  `Name` VARCHAR(30) NULL,
  `Number` INT(11) NULL,
  `DueDate` DATE NULL,
  `Approved` TINYINT(4) NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `Milestone_Status_FK`
    FOREIGN KEY (`StatusCode`)
    REFERENCES `project_tracker`.`statuses` (`Code`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`office`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `project_tracker`.`office` ;

CREATE TABLE IF NOT EXISTS `project_tracker`.`office` (
  `UserName` VARCHAR(20) NOT NULL,
  `OfficeNumber` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`UserName`, `OfficeNumber`),
  CONSTRAINT `User_Office_FK`
    FOREIGN KEY (`UserName`)
    REFERENCES `project_tracker`.`user` (`UserName`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`phone`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `project_tracker`.`phone` ;

CREATE TABLE IF NOT EXISTS `project_tracker`.`phone` (
  `UserName` VARCHAR(20) NOT NULL,
  `PhoneNumber` VARCHAR(20) NOT NULL,
  `PhoneType` ENUM('cell', 'home', 'office', 'other', 'none') NULL DEFAULT 'none',
  PRIMARY KEY (`UserName`, `PhoneNumber`),
  CONSTRAINT `User_Phone_FK`
    FOREIGN KEY (`UserName`)
    REFERENCES `project_tracker`.`user` (`UserName`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`project`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `project_tracker`.`project` ;

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
  `Grade` VARCHAR(4) NULL,
  `CommitteeID` INT(11) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`project_milestone_link`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `project_tracker`.`project_milestone_link` ;

CREATE TABLE IF NOT EXISTS `project_tracker`.`project_milestone_link` (
  `ProjectID` INT(11) NOT NULL,
  `MilestoneID` INT(11) NOT NULL,
  PRIMARY KEY (`ProjectID`, `MilestoneID`),
  CONSTRAINT `ProjectID_Milestone_FK`
    FOREIGN KEY (`ProjectID`)
    REFERENCES `project_tracker`.`project` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Project_MilestoneID_FK`
    FOREIGN KEY (`MilestoneID`)
    REFERENCES `project_tracker`.`milestone` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`committee`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `project_tracker`.`committee` ;

CREATE TABLE IF NOT EXISTS `project_tracker`.`committee` (
  `UserName` VARCHAR(20) NOT NULL,
  `ProjectID` INT(11) NOT NULL,
  `Role` ENUM('chair', 'reader', 'other') NULL,
  PRIMARY KEY (`UserName`, `ProjectID`),
  CONSTRAINT `UserID_Project_FK`
    FOREIGN KEY (`UserName`)
    REFERENCES `project_tracker`.`user` (`UserName`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `User_ProjectID_FK`
    FOREIGN KEY (`ProjectID`)
    REFERENCES `project_tracker`.`project` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `project_tracker`.`user`
-- -----------------------------------------------------
START TRANSACTION;
USE `project_tracker`;
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('ab1234', 'Al', 'Baker', 'password', '', '2018-05-15', 'NSA grad student', 'NSA', 'Student');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('dn1234', 'Don', 'Novello', 'password', '', '2018-05-15', 'IST grad student', 'IST', 'Student');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('gs1234', 'Guido', 'Sarducci', 'password', '', '2017-05-13', 'HCI grad student', 'HCI', 'Student');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('txaics', 'Tanweer', 'Alam', 'password', '', '1900-01-01', 'UNKNOWN', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('gpavks', 'Garret', 'Arcoraci', 'password', 'https://ist.rit.edu/assets/img/people/gpavks.jpg', '1900-01-01', 'Lecturer', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('dlaics', 'Daniel', 'Ashbrook', 'password', 'https://ist.rit.edu/assets/img/people/dlaics.jpg', '1900-01-01', 'Assistant Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('ciiics', 'Catherine', 'Beaton', 'password', 'https://ist.rit.edu/assets/img/people/ciiics.jpg', '1900-01-01', 'Associate Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('aobics', 'Alec', 'Berenbaum', 'password', '', '1900-01-01', 'UNKNOWN', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('tsbics', 'Todd', 'Bernhard', 'password', '', '1900-01-01', 'UNKNOWN', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('dsbics', 'Daniel', 'Bogaard', 'password', 'https://ist.rit.edu/assets/img/people/dsbics.jpg', '1900-01-01', 'Associate Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('cbbics', 'Charlie', 'Border', 'password', 'https://ist.rit.edu/assets/img/people/cbbics.jpg', '1900-01-01', 'Associate Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('spbics', 'Sean', 'Boyle', 'password', 'https://www.rit.edu/gccis/sites/rit.edu.gccis/files//Boyle%20Sean%20new%20headshot.jpg', '1900-01-01', 'UNKNOWN', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mjcics', 'Michael', 'Carroll', 'password', '', '1900-01-01', 'UNKNOWN', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mjcvks', 'Marco', 'Casale', 'password', '', '1900-01-01', 'UNKNOWN', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mjfics', 'Michael', 'Floeser', 'password', 'https://ist.rit.edu/assets/img/people/mjfics.jpg', '1900-01-01', 'Senior Lecturer', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('nffbbu', 'Nick', 'Francesco', 'password', '', '1900-01-01', 'UNKNOWN', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('bdfvks', 'Bryan', 'French', 'password', 'https://ist.rit.edu/assets/img/people/bdfvks.jpg', '1900-01-01', 'Lecturer', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('dmgics', 'Dean', 'Ganskop', 'password', '', '1900-01-01', 'UNKNOWN', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('efgics', 'Erik', 'Golen', 'password', 'https://ist.rit.edu/assets/img/people/efgics.jpg', '1900-01-01', 'Visiting Assistant Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jrhicsa', 'James', 'Habermas', 'password', '', '1900-01-01', 'UNKNOWN', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('vlhics', 'Vicki', 'Hanson', 'password', 'https://ist.rit.edu/assets/img/people/vlhics.jpg', '1900-01-01', 'Distinguished Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('bhhics', 'Bruce', 'Hartpence', 'password', 'https://ist.rit.edu/assets/img/people/bhhics.jpg', '1900-01-01', 'Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('amhgss', 'Andrew', 'Herbert', 'password', 'https://ist.rit.edu/assets/img/people/amhgss.jpg', '1900-01-01', 'Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('lwhfac', 'Larry', 'Hill', 'password', 'https://ist.rit.edu/assets/img/people/lwhfac.jpg', '1900-01-01', 'Associate Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('ephics', 'Ed', 'Holden', 'password', 'https://ist.rit.edu/assets/img/people/ephics.jpg', '1900-01-01', 'Associate Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mphics', 'Matt', 'Huenerfauth', 'password', 'https://ist.rit.edu/assets/img/people/mphics.jpg', '1900-01-01', 'Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jcjics', 'Jeffrey', 'Jockel', 'password', 'https://www.rit.edu/gccis/sites/rit.edu.gccis/files//Jeff%20IST%20small.jpg', '1900-01-01', 'UNKNOWN', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mrjics', 'Mark', 'Juba', 'password', '', '1900-01-01', 'UNKNOWN', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jwkics', 'Jai', 'Kang', 'password', 'https://ist.rit.edu/assets/img/people/jwkics.jpg', '1900-01-01', 'Associate Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('drkisd', 'Dan', 'Kennedy', 'password', 'https://ist.rit.edu/assets/img/people/drkisd.jpg', '1900-01-01', 'Lecturer', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('hnkics', 'Heidi', 'Klossner', 'password', '', '1900-01-01', 'UNKNOWN', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mxkics1', 'Mitel', 'Kuliner', 'password', '', '1900-01-01', 'UNKNOWN', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('dmlics', 'Deborah', 'LaBelle', 'password', 'https://ist.rit.edu/assets/img/people/dmlics.jpg', '1900-01-01', 'Lecturer', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jalics', 'Jeffrey', 'Lasky', 'password', 'https://ist.rit.edu/assets/img/people/jalics.jpg', '1900-01-01', 'Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jalvks', 'Jim', 'Leone', 'password', 'https://ist.rit.edu/assets/img/people/jalvks.jpg', '1900-01-01', 'Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jxlics', 'Joseph', 'Loporcaro', 'password', '', '1900-01-01', 'UNKNOWN', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('phlics', 'Peter', 'Lutz', 'password', 'https://ist.rit.edu/assets/img/people/phlics.jpg', '1900-01-01', 'Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('spmics', 'Sharon', 'Mason', 'password', 'https://ist.rit.edu/assets/img/people/spmics.jpg', '1900-01-01', 'Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mjmics', 'Michael', 'McQuaid', 'password', 'https://ist.rit.edu/assets/img/people/mjmics.jpg', '1900-01-01', 'Lecturer', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('thoics', 'Tae', 'Oh', 'password', 'https://ist.rit.edu/assets/img/people/thoics.jpg', '1900-01-01', 'Associate Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('sphics', 'Sylvia', 'Perez-Hardy', 'password', 'https://ist.rit.edu/assets/img/people/sphics.jpg', '1900-01-01', 'Associate Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('djpihst', 'Jerry', 'Powell', 'password', 'https://ist.rit.edu/assets/img/people/djpihst.jpg', '1900-01-01', 'Visiting Assistant Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('sarics', 'Scott', 'Root II', 'password', '', '1900-01-01', 'UNKNOWN', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('ldrics', 'Lawrence', 'Roth', 'password', '', '1900-01-01', 'UNKNOWN', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('kmsics', 'Katie', 'Sabourin', 'password', '', '1900-01-01', 'UNKNOWN', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('cxsics', 'Charles', 'Schneider', 'password', '', '1900-01-01', 'UNKNOWN', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('nxsvks', 'Nirmala', 'Shenoy', 'password', 'https://ist.rit.edu/assets/img/people/nxsvks.jpg', '1900-01-01', 'Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('aesfaa', 'Adam', 'Smith', 'password', 'https://ist.rit.edu/assets/img/people/aesfaa.jpg', '1900-01-01', 'Associate Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jxtadm', 'John-Paul', 'Takats', 'password', '', '1900-01-01', 'UNKNOWN', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('bmtski', 'Brian', 'Tomaszewski', 'password', 'https://ist.rit.edu/assets/img/people/bmtski.jpg', '1900-01-01', 'Associate Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('rpvvks', 'Ronald', 'Vullo', 'password', 'https://ist.rit.edu/assets/img/people/rpvvks.jpg', '1900-01-01', 'Associate Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('axgvks', 'Ann', 'Warren', 'password', 'https://www.rit.edu/gccis/sites/rit.edu.gccis/files//ann.jpg', '1900-01-01', 'UNKNOWN', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('emwics', 'Elissa', 'Weeden', 'password', 'https://ist.rit.edu/assets/img/people/emwics.jpg', '1900-01-01', 'Associate Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jswics', 'Jonathan', 'Weissman', 'password', 'https://www.rit.edu/gccis/sites/rit.edu.gccis/files//jweissman%5B1%5D.jpg', '1900-01-01', 'UNKNOWN', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mayici', 'Michael', 'Yacci', 'password', 'https://www.rit.edu/gccis//sites/rit.edu.gccis/files//yacci%20new%20headshot.jpg', '1900-01-01', 'UNKNOWN', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('qyuvks', 'Qi', 'Yu', 'password', 'https://ist.rit.edu/assets/img/people/qyuvks.jpg', '1900-01-01', 'Associate Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('sjzics', 'Steve', 'Zilora', 'password', 'https://ist.rit.edu/assets/img/people/sjzics.jpg', '1900-01-01', 'Professor', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('rdbcst', 'Rhonda', 'Baker-Smith', 'password', 'https://ist.rit.edu/assets/img/people/rdbcst.jpg', '1900-01-01', 'IST Office Manager', '', 'Staff');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('GradCoord', 'Qi', 'Yu', 'password', 'https://ist.rit.edu/assets/img/people/qyuvks.jpg', '1900-01-01', 'Associate Professor', '', 'Staff');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mchics', 'Melissa', 'Hanna', 'password', 'https://ist.rit.edu/assets/img/people/mchics.jpg', '1900-01-01', 'Senior Staff Assistant', '', 'Staff');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jmpics', 'Jill', 'Persson', 'password', 'https://ist.rit.edu/assets/img/people/jmpics.jpg', '1900-01-01', 'Graduate Academic Advisor', '', 'Staff');

COMMIT;


-- -----------------------------------------------------
-- Data for table `project_tracker`.`email`
-- -----------------------------------------------------
START TRANSACTION;
USE `project_tracker`;
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('ab1234', 'ab1234@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('dn1234', 'dn1234@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('gs1234', 'gs1234@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('txaics', 'txaics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('gpavks', 'gpavks@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('dlaics', 'daniel.ashbrook@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('ciiics', 'catherine.beaton@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('aobics', 'aobics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('tsbics', 'tsbics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('dsbics', 'dsbics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('cbbics', 'cbbics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('spbics', 'Sean.Boyle@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('mjcics', 'mjcics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('mjcvks', 'mjcvks@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('mjfics', 'Michael.Floeser@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('nffbbu', 'nick@saunders.rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('bdfvks', 'bdfvks@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('dmgics', 'Dean.Ganskop@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('efgics', 'efgics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('jrhicsa', 'jrhicsa@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('vlhics', 'vlh@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('bhhics', 'Bruce.Hartpence@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('amhgss', 'amhgss@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('lwhfac', 'Lawrence.Hill@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('ephics', 'edward.holden@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('mphics', 'matt.huenerfauth@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('jcjics', 'jcjics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('mrjics', 'mrjics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('jwkics', 'jai.kang@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('drkisd', 'drkisd@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('hnkics', 'hnkics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('mxkics1', 'mxkics1@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('dmlics', 'dmlics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('jalics', 'Jeffrey.Lasky@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('jalvks', 'jalvks@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('jxlics', 'jxlics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('phlics', 'Peter.Lutz@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('spmics', 'Sharon.Mason@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('mjmics', 'mickmcquaid@gmail.com', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('thoics', 'Tom.Oh@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('sphics', 'sylvia.perez-hardy@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('djpihst', 'djpihst@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('sarics', 'sarics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('ldrics', 'ldrics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('kmsics', 'kmsics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('cxsics', 'cxsics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('nxsvks', 'nxsvks@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('aesfaa', 'adam.smith@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('jxtadm', 'jxtadm@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('bmtski', 'bmtski@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('rpvvks', 'rpv@mail.rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('axgvks', 'axgvks@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('emwics', 'emwics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('jswics', 'jswics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('mayici', 'mayici@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('qyuvks', 'qyuvks@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('sjzics', 'sjzics@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('rdbcst', 'rdbcst@rit.edu', 'School');
INSERT INTO `project_tracker`.`email` (`UserName`, `EmailAddress`, `EmailType`) VALUES ('GradCoord', 'qyuvks@rit.edu', 'School');

COMMIT;


-- -----------------------------------------------------
-- Data for table `project_tracker`.`statuses`
-- -----------------------------------------------------
START TRANSACTION;
USE `project_tracker`;
INSERT INTO `project_tracker`.`statuses` (`Code`, `Description`) VALUES (100, '1. Pre-proprosal (optional but very useful)');
INSERT INTO `project_tracker`.`statuses` (`Code`, `Description`) VALUES (200, '2. Forming the committee (two for project and three for thesis)');
INSERT INTO `project_tracker`.`statuses` (`Code`, `Description`) VALUES (300, '3. Continue proposal development ');
INSERT INTO `project_tracker`.`statuses` (`Code`, `Description`) VALUES (400, '4. Proposal approval ');
INSERT INTO `project_tracker`.`statuses` (`Code`, `Description`) VALUES (500, '4.1 Download the proposal approval form from the IST website (under forms)');
INSERT INTO `project_tracker`.`statuses` (`Code`, `Description`) VALUES (600, '4.2 Collect signatures from committee members (email approval is fine)');
INSERT INTO `project_tracker`.`statuses` (`Code`, `Description`) VALUES (700, '4.3 Submit the approved proposal form along with the electronic version of the proposal to the department office (Tracy)');
INSERT INTO `project_tracker`.`statuses` (`Code`, `Description`) VALUES (800, '4.4 The IST department office performs plagiarism checking and sends the results and proposal to the Graduate Director for final approval');
INSERT INTO `project_tracker`.`statuses` (`Code`, `Description`) VALUES (900, '4.5 After the approval from the Graduate Director');
INSERT INTO `project_tracker`.`statuses` (`Code`, `Description`) VALUES (1000, '5. Continue the capstone work based on what is proposed in the proposal. ');
INSERT INTO `project_tracker`.`statuses` (`Code`, `Description`) VALUES (1100, '6. Finish the work and complete the final report.');
INSERT INTO `project_tracker`.`statuses` (`Code`, `Description`) VALUES (1200, '7. Get approval from the committee to schedule the final defense.');
INSERT INTO `project_tracker`.`statuses` (`Code`, `Description`) VALUES (1300, '7.1 Conduct the defense');
INSERT INTO `project_tracker`.`statuses` (`Code`, `Description`) VALUES (1400, '8. Revise the final report based on committee feedback. (if committee requires revisions)');
INSERT INTO `project_tracker`.`statuses` (`Code`, `Description`) VALUES (1500, '9. After getting the final approval from the committee');
INSERT INTO `project_tracker`.`statuses` (`Code`, `Description`) VALUES (1600, '10. Committee chair report the capstone grade to the IST department office.');

COMMIT;


-- -----------------------------------------------------
-- Data for table `project_tracker`.`milestone`
-- -----------------------------------------------------
START TRANSACTION;
USE `project_tracker`;
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (1, 100, 'Our first milestone.', 1, '2016-08-05', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (2, 200, 'Our second milestone', 2, '2016-08-08', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (3, 300, 'Our third milestone', 3, '2016-08-12', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (4, 400, 'Our fourth milestone', 4, '2016-08-25', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (5, 500, 'Our fifth milestone', 5, '2016-08-29', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (6, 600, 'Our sixth milestone', 6, '2016-09-02', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (7, 700, 'Our seventh milestone', 7, '2016-09-05', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (8, 800, 'Our eighth milestone', 8, '2016-09-07', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (9, 900, 'Our ninth milestone', 9, '2016-09-12', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (10, 1000, 'Our tenth milestone', 10, '2016-09-14', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (11, 1100, 'Our eleventh milestone', 11, '2016-09-18', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (12, 1200, 'Our twelfth milestone', 12, '2016-10-16', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (13, 1300, 'Our thirteenth milestone', 13, '2016-10-28', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (14, 1400, 'Our fourteenth milestone', 14, '2016-11-08', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (15, 1500, 'Our fifteenth milestone', 15, '2016-11-22', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (16, 1600, 'Our sixteenth milestone', 16, '2016-12-08', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (17, 100, 'Milestone #1', 1, '2017-06-08', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (18, 200, 'Milestone #2', 2, '2017-06-19', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (19, 300, 'Milestone #3', 3, '2017-06-19', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (20, 400, 'Milestone #4', 4, '2017-06-23', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (21, 500, 'Milestone #5', 5, '2017-06-28', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (22, 600, 'Milestone #6', 6, '2017-07-19', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (23, 700, 'Milestone #7', 7, '2017-08-07', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (24, 800, 'Milestone #8', 8, '2017-08-30', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (25, 900, 'Milestone #9', 9, '2017-09-01', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (26, 1000, 'Milestone #10', 10, '2017-09-25', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (27, 1100, 'Milestone #11', 11, '2017-10-02', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (28, 1200, 'Milestone #12', 12, '2017-10-18', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (29, 1300, 'Milestone #13', 13, '2017-11-03', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (30, 1400, 'Milestone #14', 14, '2017-11-20', FALSE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (31, 100, '1st Milestone', 1, '2017-06-28', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (32, 200, '2nd Milestone', 2, '2017-07-14', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (33, 300, '3rd Milestone', 3, '2017-08-02', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (34, 400, '4th Milestone', 4, '2017-08-22', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (35, 500, '5th Milestone', 5, '2017-09-10', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (36, 600, '6th Milestone', 6, '2017-09-22', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (37, 700, '7th Milestone', 7, '2017-10-05', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (38, 800, '8th Milestone', 8, '2017-10-24', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (39, 900, '9th Milestone', 9, '2017-11-10', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (40, 1000, '10th Milestone', 10, '2017-11-17', TRUE);
INSERT INTO `project_tracker`.`milestone` (`ID`, `StatusCode`, `Name`, `Number`, `DueDate`, `Approved`) VALUES (41, 1100, '11th Milestone', 11, '2017-11-27', FALSE);

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
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('ab1234', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('dn1234', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('gs1234', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('txaics', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('gpavks', '585-475-7854', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('dlaics', '585-475-4784', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('ciiics', '585-281-6162', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('aobics', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('tsbics', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('dsbics', '585-4755231', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('cbbics', '585-475-7946', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('spbics', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('mjcics', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('mjcvks', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('mjfics', '585-475-7031', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('nffbbu', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('bdfvks', '585-475-6511', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('dmgics', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('efgics', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('jrhicsa', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('vlhics', '585-475-5384', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('bhhics', '585-475-7938', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('amhgss', '585-475-4554', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('lwhfac', '585-475-7064', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('ephics', '585-475-5361', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('mphics', '585-475-2459', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('jcjics', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('mrjics', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('jwkics', '585-475-5362', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('drkisd', '585-475-2811', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('hnkics', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('mxkics1', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('dmlics', '585-475-5001', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('jalics', '585-475-2284', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('jalvks', '585-475-6451', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('jxlics', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('phlics', '585-475-6162', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('spmics', '585-475-6989', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('mjmics', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('thoics', '585-475-7642', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('sphics', '585-475-7941', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('djpihst', '585-475-2487', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('sarics', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('ldrics', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('kmsics', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('cxsics', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('nxsvks', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('aesfaa', '585-475-4552', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('jxtadm', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('bmtski', '585-475-2869', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('rpvvks', '585-475-7281', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('axgvks', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('emwics', '585-475-6733', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('jswics', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('mayici', '', 'None');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('qyuvks', '585-475-6929', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('sjzics', '585-475-7643', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('rdbcst', '585-475-7924', 'Office');
INSERT INTO `project_tracker`.`phone` (`UserName`, `PhoneNumber`, `PhoneType`) VALUES ('GradCoord', '585-475-6929', 'Office');

COMMIT;


-- -----------------------------------------------------
-- Data for table `project_tracker`.`project`
-- -----------------------------------------------------
START TRANSACTION;
USE `project_tracker`;
INSERT INTO `project_tracker`.`project` (`ID`, `Name`, `Summary`, `Topic`, `Type`, `StartDate`, `EndDate`, `DueDate`, `Completed`, `ProposalApproved`, `FinalDefenseDate`, `PlagiarismPercentage`, `Grade`, `CommitteeID`) VALUES (1, 'Project 01', 'Creating network cables by left or right handed technicians should not be used on the same network. This causes the network to implode if the streams are crossed.', 'Network implosion ', 'Thesis', '2016-07-15', '2016-12-08', '2016-12-12', TRUE, TRUE, '2016-12-16', 25, 'A', 1);
INSERT INTO `project_tracker`.`project` (`ID`, `Name`, `Summary`, `Topic`, `Type`, `StartDate`, `EndDate`, `DueDate`, `Completed`, `ProposalApproved`, `FinalDefenseDate`, `PlagiarismPercentage`, `Grade`, `CommitteeID`) VALUES (2, 'Project 02', 'Do men that stroke their chins in contemplation, think smarter when they have a beard?', 'Program better with a beard', 'Capstone', '2017-06-01', '1900-01-01', '2017-12-10', FALSE, TRUE, '2017-12-15', 25, '', 2);
INSERT INTO `project_tracker`.`project` (`ID`, `Name`, `Summary`, `Topic`, `Type`, `StartDate`, `EndDate`, `DueDate`, `Completed`, `ProposalApproved`, `FinalDefenseDate`, `PlagiarismPercentage`, `Grade`, `CommitteeID`) VALUES (3, 'Project 03', 'Recursively, this database.', 'Capstone tracking database', 'Capstone', '2017-06-20', '1900-01-01', '2017-12-10', FALSE, TRUE, '2017-12-18', 35, '', 3);

COMMIT;


-- -----------------------------------------------------
-- Data for table `project_tracker`.`project_milestone_link`
-- -----------------------------------------------------
START TRANSACTION;
USE `project_tracker`;
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (1, 1);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (1, 2);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (1, 3);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (1, 4);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (1, 5);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (1, 6);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (1, 7);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (1, 8);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (1, 9);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (1, 10);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (1, 11);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (1, 12);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (1, 13);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (1, 14);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (1, 15);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (1, 16);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (2, 17);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (2, 18);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (2, 19);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (2, 20);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (2, 21);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (2, 22);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (2, 23);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (2, 24);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (2, 25);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (2, 26);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (2, 27);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (2, 28);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (2, 29);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (2, 30);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (3, 31);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (3, 32);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (3, 33);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (3, 34);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (3, 35);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (3, 36);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (3, 37);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (3, 38);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (3, 39);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (3, 40);
INSERT INTO `project_tracker`.`project_milestone_link` (`ProjectID`, `MilestoneID`) VALUES (3, 41);

COMMIT;


-- -----------------------------------------------------
-- Data for table `project_tracker`.`committee`
-- -----------------------------------------------------
START TRANSACTION;
USE `project_tracker`;
INSERT INTO `project_tracker`.`committee` (`UserName`, `ProjectID`, `Role`) VALUES ('mjfics', 1, 'Reader');
INSERT INTO `project_tracker`.`committee` (`UserName`, `ProjectID`, `Role`) VALUES ('mjfics', 2, 'Reader');
INSERT INTO `project_tracker`.`committee` (`UserName`, `ProjectID`, `Role`) VALUES ('ephics', 3, 'Reader');
INSERT INTO `project_tracker`.`committee` (`UserName`, `ProjectID`, `Role`) VALUES ('drkisd', 1, 'Chair');
INSERT INTO `project_tracker`.`committee` (`UserName`, `ProjectID`, `Role`) VALUES ('drkisd', 2, 'Chair');
INSERT INTO `project_tracker`.`committee` (`UserName`, `ProjectID`, `Role`) VALUES ('mjfics', 3, 'Chair');
INSERT INTO `project_tracker`.`committee` (`UserName`, `ProjectID`, `Role`) VALUES ('thoics', 1, 'Reader');

COMMIT;


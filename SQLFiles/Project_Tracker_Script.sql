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
-- Table `project_tracker`.`committee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_tracker`.`committee` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `ProjectID` INT(11) NOT NULL,
  `ProjectGrade` VARCHAR(4) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`email`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_tracker`.`email` (
  `UserName` VARCHAR(20) NOT NULL,
  `EmailAddress` VARCHAR(30) NOT NULL,
  `EmailType` ENUM('work', 'school', 'personal') NULL DEFAULT NULL,
  PRIMARY KEY (`UserName`, `EmailAddress`))
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
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`office`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_tracker`.`office` (
  `UserName` VARCHAR(20) NOT NULL,
  `OfficeNumber` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`UserName`, `OfficeNumber`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`phone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_tracker`.`phone` (
  `UserName` VARCHAR(20) NOT NULL,
  `PhoneNumber` VARCHAR(15) NOT NULL,
  `PhoneType` ENUM('cell', 'home', 'office', 'other') NOT NULL,
  PRIMARY KEY (`UserName`, `PhoneNumber`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


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
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`project_milestone_link`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_tracker`.`project_milestone_link` (
  `ProjectID` INT(11) NOT NULL,
  `MilestoneID` INT(11) NOT NULL,
  PRIMARY KEY (`ProjectID`, `MilestoneID`))
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
-- Table `project_tracker`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_tracker`.`user` (
  `UserName` VARCHAR(20) NOT NULL,
  `FirstName` VARCHAR(20) NOT NULL,
  `LastName` VARCHAR(20) NOT NULL,
  `Password` VARCHAR(50) NOT NULL,
  `Image` VARCHAR(150) NULL DEFAULT NULL,
  `GraduationDate` DATE NULL DEFAULT NULL,
  `Department` VARCHAR(50) NULL DEFAULT NULL,
  `Major` VARCHAR(50) NULL DEFAULT NULL,
  `Role` ENUM('student', 'faculty', 'adjunct', 'staff') NOT NULL,
  PRIMARY KEY (`UserName`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`user_committee_link`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_tracker`.`user_committee_link` (
  `UserName` VARCHAR(7) NOT NULL,
  `CommitteID` INT(11) NOT NULL,
  PRIMARY KEY (`UserName`, `CommitteID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `project_tracker`.`user_project_link`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `project_tracker`.`user_project_link` (
  `UserName` VARCHAR(7) NOT NULL,
  `ProjectID` INT(11) NOT NULL,
  PRIMARY KEY (`UserName`, `ProjectID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Data for table `project_tracker`.`email`
-- -----------------------------------------------------
START TRANSACTION;
USE `project_tracker`;
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

COMMIT;


-- -----------------------------------------------------
-- Data for table `project_tracker`.`project`
-- -----------------------------------------------------
START TRANSACTION;
USE `project_tracker`;
INSERT INTO `project_tracker`.`project` (`ID`, `Name`, `Summary`, `Topic`, `Type`, `StartDate`, `EndDate`, `DueDate`, `Completed`, `ProposalApproved`, `FinalDefenseDate`, `PlagiarismPercentage`, `Grade`, `CommitteeID`) VALUES (1, 'Project 01', 'Creating network cables by left or right handed technicians should not be used on the same network. This causes the network to implode if the streams are crossed.', 'Network implosion ', 'Thesis', '2017-08-20', '2017-04-28', '2017-05-20', TRUE, TRUE, '2017-05-10', 25, 'A', 1);
INSERT INTO `project_tracker`.`project` (`ID`, `Name`, `Summary`, `Topic`, `Type`, `StartDate`, `EndDate`, `DueDate`, `Completed`, `ProposalApproved`, `FinalDefenseDate`, `PlagiarismPercentage`, `Grade`, `CommitteeID`) VALUES (2, 'Project 02', 'Do men that stroke their chins in contemplation, think smarter when they have a beard?', 'Program better with a beard', 'Capstone', '2016-08-16', '2017-05-01', '2017-05-20', TRUE, TRUE, '2017-05-12', 25, 'B', 2);
INSERT INTO `project_tracker`.`project` (`ID`, `Name`, `Summary`, `Topic`, `Type`, `StartDate`, `EndDate`, `DueDate`, `Completed`, `ProposalApproved`, `FinalDefenseDate`, `PlagiarismPercentage`, `Grade`, `CommitteeID`) VALUES (3, 'Project 03', 'Recursively, this database.', 'Capstone tracking database', 'Capstone', '2015-08-14', '2016-05-06', '2016-05-17', TRUE, TRUE, '2016-05-08', 35, 'C', 3);

COMMIT;


-- -----------------------------------------------------
-- Data for table `project_tracker`.`user`
-- -----------------------------------------------------
START TRANSACTION;
USE `project_tracker`;
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('ab1234', 'Al', 'Baker', '5f4dcc3b5aa765d61d8327deb882cf99', 'UNKNOWN', '2017-05-15', 'Grad', 'NSA', 'Student');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('dn1234', 'Don', 'Novello', '5f4dcc3b5aa765d61d8327deb882cf99', 'UNKNOWN', '2017-05-15', 'Grad', 'IST', 'Student');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('gs1234', 'Guido', 'Sarducci', '5f4dcc3b5aa765d61d8327deb882cf99', 'UNKNOWN', '2016-05-13', 'Grad', 'HCI', 'Student');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('txaics', 'Tanweer', 'Alam', '5f4dcc3b5aa765d61d8327deb882cf99', '0', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('gpavks', 'Garret', 'Arcoraci', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/gpavks.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('dlaics', 'Daniel', 'Ashbrook', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/dlaics.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('ciiics', 'Catherine', 'Beaton', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/ciiics.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('aobics', 'Alec', 'Berenbaum', '5f4dcc3b5aa765d61d8327deb882cf99', '0', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('tsbics', 'Todd', 'Bernhard', '5f4dcc3b5aa765d61d8327deb882cf99', '0', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('dsbics', 'Daniel', 'Bogaard', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/dsbics.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('cbbics', 'Charlie', 'Border', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/cbbics.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('spbics', 'Sean', 'Boyle', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://www.rit.edu/gccis/sites/rit.edu.gccis/files//Boyle%20Sean%20new%20headshot.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mjcics', 'Michael', 'Carroll', '5f4dcc3b5aa765d61d8327deb882cf99', '0', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mjcvks', 'Marco', 'Casale', '5f4dcc3b5aa765d61d8327deb882cf99', '0', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mjfics', 'Michael', 'Floeser', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/mjfics.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('nffbbu', 'Nick', 'Francesco', '5f4dcc3b5aa765d61d8327deb882cf99', '0', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('bdfvks', 'Bryan', 'French', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/bdfvks.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('dmgics', 'Dean', 'Ganskop', '5f4dcc3b5aa765d61d8327deb882cf99', '0', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('efgics', 'Erik', 'Golen', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/efgics.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jrhicsa', 'James', 'Habermas', '5f4dcc3b5aa765d61d8327deb882cf99', '0', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('vlhics', 'Vicki', 'Hanson', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/vlhics.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('bhhics', 'Bruce', 'Hartpence', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/bhhics.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('amhgss', 'Andrew', 'Herbert', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/amhgss.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('lwhfac', 'Larry', 'Hill', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/lwhfac.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('ephics', 'Ed', 'Holden', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/ephics.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mphics', 'Matt', 'Huenerfauth', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/mphics.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jcjics', 'Jeffrey', 'Jockel', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://www.rit.edu/gccis/sites/rit.edu.gccis/files//Jeff%20IST%20small.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mrjics', 'Mark', 'Juba', '5f4dcc3b5aa765d61d8327deb882cf99', '0', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jwkics', 'Jai', 'Kang', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/jwkics.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('drkisd', 'Dan', 'Kennedy', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/drkisd.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('hnkics', 'Heidi', 'Klossner', '5f4dcc3b5aa765d61d8327deb882cf99', '0', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mxkics1', 'Mitel', 'Kuliner', '5f4dcc3b5aa765d61d8327deb882cf99', '0', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('dmlics', 'Deborah', 'LaBelle', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/dmlics.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jalics', 'Jeffrey', 'Lasky', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/jalics.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jalvks', 'Jim', 'Leone', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/jalvks.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jxlics', 'Joseph', 'Loporcaro', '5f4dcc3b5aa765d61d8327deb882cf99', '0', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('phlics', 'Peter', 'Lutz', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/phlics.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('spmics', 'Sharon', 'Mason', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/spmics.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mjmics', 'Michael', 'McQuaid', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/mjmics.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('thoics', 'Tae', 'Oh', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/thoics.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('sphics', 'Sylvia', 'Perez-Hardy', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/sphics.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('djpihst', 'Jerry', 'Powell', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/djpihst.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('sarics', 'Scott', 'Root II', '5f4dcc3b5aa765d61d8327deb882cf99', '0', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('ldrics', 'Lawrence', 'Roth', '5f4dcc3b5aa765d61d8327deb882cf99', '0', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('kmsics', 'Katie', 'Sabourin', '5f4dcc3b5aa765d61d8327deb882cf99', '0', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('cxsics', 'Charles', 'Schneider', '5f4dcc3b5aa765d61d8327deb882cf99', '0', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('nxsvks', 'Nirmala', 'Shenoy', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/nxsvks.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('aesfaa', 'Adam', 'Smith', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/aesfaa.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jxtadm', 'John-Paul', 'Takats', '5f4dcc3b5aa765d61d8327deb882cf99', '0', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('bmtski', 'Brian', 'Tomaszewski', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/bmtski.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('rpvvks', 'Ronald', 'Vullo', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/rpvvks.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('axgvks', 'Ann', 'Warren', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://www.rit.edu/gccis/sites/rit.edu.gccis/files//ann.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('emwics', 'Elissa', 'Weeden', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/emwics.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jswics', 'Jonathan', 'Weissman', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://www.rit.edu/gccis/sites/rit.edu.gccis/files//jweissman%5B1%5D.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mayici', 'Michael', 'Yacci', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://www.rit.edu/gccis//sites/rit.edu.gccis/files//yacci%20new%20headshot.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('qyuvks', 'Qi', 'Yu', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/qyuvks.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('sjzics', 'Steve', 'Zilora', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/sjzics.jpg', '1970-01-01', 'Faculty', '', 'Faculty');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('rdbcst', 'Rhonda', 'Baker-Smith', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/rdbcst.jpg', '1970-01-01', 'Staff', '', 'Staff');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('mchics', 'Melissa', 'Hanna', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/mchics.jpg', '1970-01-01', 'Staff', '', 'Staff');
INSERT INTO `project_tracker`.`user` (`UserName`, `FirstName`, `LastName`, `Password`, `Image`, `GraduationDate`, `Department`, `Major`, `Role`) VALUES ('jmpics', 'Jill', 'Persson', '5f4dcc3b5aa765d61d8327deb882cf99', 'https://ist.rit.edu/assets/img/people/jmpics.jpg', '1970-01-01', 'Staff', '', 'Staff');

COMMIT;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

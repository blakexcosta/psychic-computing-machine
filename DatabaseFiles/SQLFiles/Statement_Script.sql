----------------------------
-- BASIC SELECTS
----------------------------

SELECT UserName, FirstName, LastName, Password, Image, GraduationDate, Department, Major, Role FROM user;

SELECT UserName, EmailAddress, EmailType FROM email;

SELECT UserName, OfficeNumber FROM office;

SELECT UserName, PhoneNumber, PhoneType FROM phone;

SELECT ID, ProjectID, ProjectGrade FROM committee;

SELECT ID, Name, Summary, Topic, Type, StartDate, EndDate, DueDate, Completed, ProposalApproved, FinalDefenseDate, PlagiarismPercentage, Grade, CommitteeID FROM project;

SELECT ID, StatusCode, Name, Number, DueDate, Approved FROM milestone;

SELECT Code, Description FROM statuses;

----------------------------
-- SPECIFIC SELECTS
----------------------------

SELECT UserName, FirstName, LastName, Password, Image, GraduationDate, Department, Major, Role FROM user WHERE role = 'student';
SELECT UserName, FirstName, LastName, Password, Image, GraduationDate, Department, Major, Role FROM user WHERE role = 'faculty';
SELECT UserName, FirstName, LastName, Password, Image, GraduationDate, Department, Major, Role FROM user WHERE role = 'staff';

SELECT ID, Name, Summary, Topic, Type, StartDate, EndDate, DueDate, Completed, ProposalApproved, FinalDefenseDate, PlagiarismPercentage, Grade, CommitteeID FROM project WHERE completed = 'TRUE';
SELECT ID, Name, Summary, Topic, Type, StartDate, EndDate, DueDate, Completed, ProposalApproved, FinalDefenseDate, PlagiarismPercentage, Grade, CommitteeID FROM project WHERE completed = 'FALSE';

SELECT UserName, FirstName, LastName, EmailAddress, OfficeNumber, PhoneNumber FROM user JOIN email USING (UserName) JOIN office USING (UserName) JOIN phone USING (UserName);

SELECT UserName, FirstName, LastName, EmailAddress, GraduationDate, Major, Name, Summary, Topic, StartDate, EndDate, DueDate, Completed, Grade FROM user JOIN email USING (UserName) JOIN user_project_link USING (UserName) JOIN project ON user_project_link.ProjectID = project.ID WHERE role = 'student';

SELECT UserName, FirstName, LastName, EmailAddress, OfficeNumber, PhoneNumber, user_committee_link.Role, Department, Name, Summary, Topic FROM user JOIN email USING (UserName) JOIN phone USING (UserName) JOIN office USING (UserName) JOIN user_committee_link USING (UserName) JOIN committee ON user_committee_link.CommitteID = committee.ID JOIN project ON committee.ProjectID = project.ID;

SELECT project.Name, Summary, Topic, milestone.Name, statuses.Description, milestone.Number, milestone.DueDate, milestone.Approved FROM project JOIN project_milestone_link ON project.ID = project_milestone_link.ProjectID JOIN milestone ON milestone.ID = project_milestone_link.MilestoneID JOIN statuses ON statuses.Code = milestone.StatusCode;
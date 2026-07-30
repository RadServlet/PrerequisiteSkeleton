select * from courses
where course_id in ('ACCT 622','ACCT 623');
/* Create TABLE course_requirements (
    requirement_id INT AUTO_INCREMENT PRIMARY KEY,
    target_course_id VARCHAR(10) NOT NULL,
    type ENUM('PRE', 'CO') NOT NULL, 
    FOREIGN KEY (target_course_id) REFERENCES courses(course_id)
);
CREATE TABLE requirement_items (
    requirement_id INT NOT NULL,
    required_course_id VARCHAR(10) NOT NULL,
    PRIMARY KEY (requirement_id, required_course_id),
    FOREIGN KEY (requirement_id) REFERENCES course_requirements(requirement_id),
    FOREIGN KEY (required_course_id) REFERENCES courses(course_id)
);
CREATE TABLE finals(
	course VARCHAR(10) NOT NULL,
    semester VARCHAR(3) NOT NULL,
    information VARCHAR(255) NOT NULL,
    PRIMARY KEY (course,semester, information),
    FOREIGN KEY (course) REFERENCES courses(course_id)
);
-- 1. Your existing courses table
CREATE TABLE courses (
    course_id VARCHAR(15) PRIMARY KEY, -- e.g., 'ACCT 351'
    title VARCHAR(150) NOT NULL
);

-- 2. New Grading Schemes Table
CREATE TABLE grading_schemes (
    scheme_id SERIAL PRIMARY KEY,
    course_id VARCHAR(15) REFERENCES courses(course_id) ON DELETE CASCADE,
    semester VARCHAR(20) NOT NULL,    -- e.g., 'Fall 2026'
    scheme_name VARCHAR(50) NOT NULL, -- e.g., 'Regular', 'Exam-Heavy'
    UNIQUE (course_id, semester, scheme_name)
);

-- 3. New Evaluation Weights Table
CREATE TABLE evaluation_weights (
    weight_id SERIAL PRIMARY KEY,
    scheme_id INT REFERENCES grading_schemes(scheme_id) ON DELETE CASCADE,
    eval_type VARCHAR(50) NOT NULL,   -- e.g., 'Quiz', 'Exam'
    weight_percentage DECIMAL(5, 2) NOT NULL,
    UNIQUE (scheme_id, eval_type)
);

-- 4. New Student Grades Table
CREATE TABLE student_grades (
    grade_id SERIAL PRIMARY KEY,
    student_id VARCHAR(50) NOT NULL,
    course_id VARCHAR(15) REFERENCES courses(course_id) ON DELETE CASCADE,
    semester VARCHAR(20) NOT NULL,
    eval_type VARCHAR(50) NOT NULL,   -- e.g., 'Quiz' (Matches weight table)
    eval_name VARCHAR(100) NOT NULL,  -- e.g., 'Quiz 1'
    grade_achieved DECIMAL(5, 2) NOT NULL
);

*/

/*DROP TABLE IF EXISTS requirement_items;
DROP TABLE IF EXISTS course_requirements;*/


select * from course_requirements
where type="PRE" and target_course_id='COMP 302';
/*where target_course_id like'ECSE%';*/
Insert into requirement_items (requirement_id,required_course_id)
values (1394,'ECSE 250');
select * from requirement_items where requirement_id=1394 and required_course_id='ECSE 250';
select * from requirement_items
where requirement_id in (1394,1395);
select * from course_requirements where requirement_id=1393;

select * from requirement_items
where requirement_id in (select requirement_id from requirement_items
where required_course_id="COMP 250");

select count(course_id) as number_of_courses from courses; 
select count(target_course_id) from course_requirements;
select * from courses where course_id="PRIL 2026";
select * from finals where course="MATH 240" and semester="W26";
/*单选*/
DROP TABLE IF EXISTS `singleMostWrong`;
CREATE TABLE `singleMostWrong`(
	`id` INT NOT NULL AUTO_INCREMENT,
	`pian` INT,
	`chapter` INT,
	`no` INT,
	`questionType` VARCHAR(50),
	`questionId` INT,
	`wrongTimes` INT,
	`correctTimes` INT,
	`totalTimes` INT,
	`correctRate` DOUBLE,
	`wrongRate` DOUBLE,
	`mostWrongAnswer` VARCHAR(50),
	`createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
);
INSERT INTO singleMostWrong
(pian,chapter,NO,questionType,questionId,wrongTimes,correctTimes,
totalTimes,correctRate,wrongRate,mostWrongAnswer)
SELECT t1.pian,t1.chapter,t1.no,t1.questionType,t1.questionId,
	t1.wrongTimes,t2.correctTimes,t1.wrongTimes+t2.correctTimes AS totalTimes,
	t2.correctTimes/(t1.wrongTimes+t2.correctTimes) AS correctRate,
	t1.wrongTimes/(t1.wrongTimes+t2.correctTimes) AS wrongRate,
		(SELECT submitAnswer FROM
			(SELECT questionId,submitAnswer,COUNT(*) AS wrongTimes FROM submitLog
				WHERE result='false' AND submitAnswer IS NOT NULL
					AND submitAnswer!=''
					AND questionType='single' AND questionId!=0
				GROUP BY questionId,submitAnswer)AS tmp
		WHERE t1.questionId=tmp.questionId
		ORDER BY tmp.wrongTimes DESC LIMIT 1) AS mostWrongAnswer
	FROM
	(SELECT pian,chapter,NO,questionId,questionType,COUNT(*) AS wrongTimes FROM submitLog
		WHERE result='false' AND questionType='single' AND questionId!=0
		GROUP BY questionId)AS t1
LEFT JOIN
	(SELECT questionId,COUNT(*) AS correctTimes FROM submitLog
		WHERE result='true' AND questionType='single' AND questionId!=0
		GROUP BY questionId)AS t2
ON t1.questionId=t2.questionId
ORDER BY correctRate LIMIT 100

/*多选*/
DROP TABLE IF EXISTS `multipleMostWrong`;
CREATE TABLE `multipleMostWrong`(
	`id` INT NOT NULL AUTO_INCREMENT,
	`pian` INT,
	`chapter` INT,
	`no` INT,
	`questionType` VARCHAR(50),
	`questionId` INT,
	`wrongTimes` INT,
	`correctTimes` INT,
	`totalTimes` INT,
	`correctRate` DOUBLE,
	`wrongRate` DOUBLE,
	`mostWrongAnswer` VARCHAR(50),
	`createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
);
INSERT INTO multipleMostWrong
(pian,chapter,NO,questionType,questionId,wrongTimes,correctTimes,
totalTimes,correctRate,wrongRate,mostWrongAnswer)
SELECT t1.pian,t1.chapter,t1.no,t1.questionType,t1.questionId,
	t1.wrongTimes,t2.correctTimes,t1.wrongTimes+t2.correctTimes AS totalTimes,
	t2.correctTimes/(t1.wrongTimes+t2.correctTimes) AS correctRate,
	t1.wrongTimes/(t1.wrongTimes+t2.correctTimes) AS wrongRate,
		(SELECT submitAnswer FROM
			(SELECT questionId,submitAnswer,COUNT(*) AS wrongTimes FROM submitLog
				WHERE result='false' AND submitAnswer IS NOT NULL
					AND submitAnswer!=''
					AND (questionType IS NULL OR questionType ='multiple') AND questionId!=0
				GROUP BY questionId,submitAnswer)AS tmp
		WHERE t1.questionId=tmp.questionId
		ORDER BY tmp.wrongTimes DESC LIMIT 1) AS mostWrongAnswer
	FROM
	(SELECT pian,chapter,NO,questionId,questionType,COUNT(*) AS wrongTimes FROM submitLog
		WHERE result='false' AND (questionType IS NULL OR questionType ='multiple') AND questionId!=0
		GROUP BY questionId)AS t1
LEFT JOIN
	(SELECT questionId,COUNT(*) AS correctTimes FROM submitLog
		WHERE result='true' AND (questionType IS NULL OR questionType ='multiple') AND questionId!=0
		GROUP BY questionId)AS t2
ON t1.questionId=t2.questionId
ORDER BY correctRate LIMIT 25

/*判断*/
DROP TABLE IF EXISTS `checkMostWrong`;
CREATE TABLE `checkMostWrong`(
	`id` INT NOT NULL AUTO_INCREMENT,
	`pian` INT,
	`chapter` INT,
	`no` INT,
	`questionType` VARCHAR(50),
	`questionId` INT,
	`wrongTimes` INT,
	`correctTimes` INT,
	`totalTimes` INT,
	`correctRate` DOUBLE,
	`wrongRate` DOUBLE,
	`createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
);
INSERT INTO checkMostWrong
(pian,chapter,NO,questionType,questionId,wrongTimes,correctTimes,
totalTimes,correctRate,wrongRate)
SELECT t1.pian,t1.chapter,t1.no,t1.questionType,t1.questionId,
	t1.wrongTimes,t2.correctTimes,t1.wrongTimes+t2.correctTimes AS totalTimes,
	t2.correctTimes/(t1.wrongTimes+t2.correctTimes) AS correctRate,
	t1.wrongTimes/(t1.wrongTimes+t2.correctTimes) AS wrongRate
	FROM
	(SELECT pian,chapter,NO,questionId,questionType,COUNT(*) AS wrongTimes FROM submitLog
		WHERE result='false' AND questionType ='check' AND questionId!=0
		GROUP BY questionId)AS t1
LEFT JOIN
	(SELECT questionId,COUNT(*) AS correctTimes FROM submitLog
		WHERE result='true' AND questionType ='check' AND questionId!=0
		GROUP BY questionId)AS t2
ON t1.questionId=t2.questionId
ORDER BY correctRate LIMIT 80

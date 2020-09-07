/*
 *	user: root
 *  password: 
 */

CREATE DATABASE JCM001;
USE JCM001;

SET GLOBAL time_zone = '-5:00';

CREATE TABLE `JCM001`.`endpoint` (
	`code` 		VARCHAR(25),
	`link` 		VARCHAR(100),
	`times` 	INT,
	`status` 	CHAR(1)
);

INSERT INTO `JCM001`.`endpoint`
(`code`, `link`, `times`, `status`)
VALUES
('google', 'https://www.google.com/', 10, 'A'),
('youtube', 'https://www.youtube.com/', 10, 'A'),
('justclick', 'http://web.justclick.media/', 10, 'A');

-- SELECT * FROM `JCM001`.`endpoint`;
-- DROP TABLE `JCM001`.`endpoints`;

CREATE TABLE `JCM001`.`endpoint_log` (
	`date_request` 	DATETIME,
	`code` 			VARCHAR(25)
);

-- SELECT * FROM `JCM001`.`endpoint_log`;

-- CALL `JCM001`.`get_endpoint`('youtube');
-- CALL `JCM001`.`reset_logs`();


-- =========================================================================
-- =========================================================================

USE `JCM001`;
DROP PROCEDURE IF EXISTS `JCM001`.`get_endpoint`;

DELIMITER $$
USE `JCM001`$$
CREATE PROCEDURE `JCM001`.`get_endpoint` (
	v_code		VARCHAR(25)
)
BEGIN
	
	DECLARE _rollback BOOL DEFAULT 0;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET _rollback = 1;
	DECLARE EXIT HANDLER FOR SQLWARNING SET @exiting = 1;

	START TRANSACTION;

	INSERT INTO `JCM001`.`endpoint_log`
	(`date_request`, `code`)
	VALUES
	(NOW(), v_code);

	IF _rollback THEN
		SELECT
			1 AS `error_code`,
			'an error occurred in the SQL query' AS `message`;
		
		ROLLBACK;
	ELSE
		SELECT
			0 AS `error_code`,
			e.`code` AS `code`,
			e.`link` AS `link`,
			e.`times` AS `times`,
			COUNT(1) AS `quantity`
		FROM `JCM001`.`endpoint` e
		INNER JOIN `JCM001`.`endpoint_log` l
			ON e.`code` = l.`code`
		WHERE e.`status` = 'A'
			AND e.`code` = v_code
		GROUP BY e.`code`, e.`link`, e.`times`;

		COMMIT;
	END IF;
	
END$$

DELIMITER ;



-- =========================================================================
-- =========================================================================

USE `JCM001`;
DROP PROCEDURE IF EXISTS `JCM001`.`reset_logs`;

DELIMITER $$
USE `JCM001`$$
CREATE PROCEDURE `JCM001`.`reset_logs` ()
BEGIN
	
	DECLARE _rollback BOOL DEFAULT 0;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET _rollback = 1;
	DECLARE EXIT HANDLER FOR SQLWARNING SET @exiting = 1;

	START TRANSACTION;

	TRUNCATE TABLE `JCM001`.`endpoint_log`;

	IF _rollback THEN
		SELECT
			1 AS `error_code`,
            'an error occurred in the SQL query' AS `message`;
		ROLLBACK;
	ELSE
		SELECT
			0 AS `error_code`,
            '' AS `message`;
		COMMIT;
	END IF;
	
END$$

DELIMITER ;


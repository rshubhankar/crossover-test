--
-- Dumping data for table `candidate`
--

INSERT INTO `candidate` VALUES (1,'admin','admin123');

--
-- Dumping data for table `exam`
--

INSERT INTO `exam` VALUES (1,'General Quiz with simple and easy questions. Time Limit for this test is 30 Minutes.',30,'MINUTES');

--
-- Dumping data for table `question`
--

INSERT INTO `question` VALUES 
(1,1,'What is the capital of India ?'),
(2,1,'Which is the 4th planet in the solar system ?'),
(3,1,'What is 7 multiplied to 8 ?'),
(4,1,'In television serial Friends, Ross was given a title. Which of the following was it ? Was it:'),
(5,1,'Who was called ''Bapu'' in India ?');

--
-- Dumping data for table `answer`
--

INSERT INTO `answer` VALUES 
(1,'Delhi',1,1),
(2,'Mumbai',NULL,1),
(3,'Chennai',NULL,1),
(4,'Kolkata',NULL,1),
(5,'Jupiter',NULL,2),
(6,'Mars',1,2),
(7,'Earth',NULL,2),
(8,'Venus',NULL,2),
(9,'48',NULL,3),
(10,'40',NULL,3),
(11,'72',NULL,3),
(12,'56',1,3),
(13,'The Enforcer',NULL,4),
(14,'The Guardian',NULL,4),
(15,'The Divorcer',1,4),
(16,'The Machinist',NULL,4),
(17,'Mohandas Karamchand Gandhi',1,5),
(18,'Aamir Khan',NULL,5),
(19,'Sachin Tendulkar',NULL,5),
(20,'Johnny Lever',NULL,5);
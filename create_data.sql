--
-- Dumping data for table `candidate`
--

INSERT INTO `candidate` VALUES (1,'admin','admin123');

--
-- Dumping data for table `exam`
--

INSERT INTO `exam` VALUES (1,'Test Description',30,'MINUTES');

--
-- Dumping data for table `question`
--

INSERT INTO `question` VALUES (1,1,'Question 1'),(2,1,'Question 2'),(3,1,'Question 3'),(4,1,'Question 4'),(5,1,'Question 5');

--
-- Dumping data for table `answer`
--

INSERT INTO `answer` VALUES (1,'Correct Answer 1',1,1),(2,'Answer 2',NULL,1),(3,'Answer 3',NULL,1),(4,'Answer 4',NULL,1),(5,'Answer 1',NULL,2),(6,'Correct Answer 2',1,2),(7,'Answer 3',NULL,2),(8,'Answer 4',NULL,2),(9,'Correct Answer 1',1,3),(10,'Answer 2',NULL,3),(11,'Answer 3',NULL,3),(12,'Answer 4',NULL,3),(13,'Answer 1',NULL,4),(14,'Answer 2',NULL,4),(15,'Correct Answer 3',1,4),(16,'Answer 4',NULL,4),(17,'Correct Answer 1',1,5),(18,'Answer 2',NULL,5),(19,'Answer 3',NULL,5),(20,'Answer 4',NULL,5);
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
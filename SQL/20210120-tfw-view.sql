-- MySQL dump 10.13  Distrib 8.0.22, for Linux (x86_64)
--
-- Host: 10.10.0.203    Database: tongframework_dev
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Temporary view structure for view `act_id_group`
--

DROP TABLE IF EXISTS `act_id_group`;
/*!50001 DROP VIEW IF EXISTS `act_id_group`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `act_id_group` AS SELECT
 1 AS `ID_`,
 1 AS `REV_`,
 1 AS `NAME_`,
 1 AS `TYPE_`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `act_id_membership`
--

DROP TABLE IF EXISTS `act_id_membership`;
/*!50001 DROP VIEW IF EXISTS `act_id_membership`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `act_id_membership` AS SELECT
 1 AS `USER_ID_`,
 1 AS `GROUP_ID_`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `act_id_user`
--

DROP TABLE IF EXISTS `act_id_user`;
/*!50001 DROP VIEW IF EXISTS `act_id_user`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `act_id_user` AS SELECT
 1 AS `ID_`,
 1 AS `REV_`,
 1 AS `FIRST_`,
 1 AS `LAST_`,
 1 AS `EMAIL_`,
 1 AS `PWD_`,
 1 AS `PICTURE_ID_`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `act_id_group`
--

/*!50001 DROP VIEW IF EXISTS `act_id_group`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50001 VIEW `act_id_group` AS select `r`.`position_id` AS `ID_`,NULL AS `REV_`,`r`.`position_code` AS `NAME_`,`r`.`position_type` AS `TYPE_` from `t_sys_job_position` `r` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `act_id_membership`
--

/*!50001 DROP VIEW IF EXISTS `act_id_membership`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50001 VIEW `act_id_membership` AS select `sr`.`user_id` AS `USER_ID_`,`sr`.`position_id` AS `GROUP_ID_` from `t_sys_user_position` `sr` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `act_id_user`
--

/*!50001 DROP VIEW IF EXISTS `act_id_user`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50001 VIEW `act_id_user` AS select `su`.`user_id` AS `ID_`,NULL AS `REV_`,`su`.`user_name` AS `FIRST_`,NULL AS `LAST_`,`su`.`email` AS `EMAIL_`,`su`.`login_pwd` AS `PWD_`,NULL AS `PICTURE_ID_` from `t_sys_user` `su` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-20 13:46:34
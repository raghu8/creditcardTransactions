--Operation type creation--
CREATE TABLE `pismo`.`OperationTypes` (
  `operationTypeId` INT NOT NULL,
  `description` VARCHAR(100) NULL,
  PRIMARY KEY (`operationTypeId`));


CREATE TABLE `pismo`.`Transaction` (
  `transaction_id` INT NOT NULL,
  `account_id` INT NOT NULL,
  `operation_type_id` INT NOT NULL,
  `amount` DECIMAL(10,2) NULL,
  `event_date` DATETIME NULL,
  PRIMARY KEY (`transaction_id`),
  CONSTRAINT `account_id`
    FOREIGN KEY ()
    REFERENCES `pismo`.`Account` ()
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `operation_type_id`
    FOREIGN KEY ()
    REFERENCES `pismo`.`OperationTypes` ()
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
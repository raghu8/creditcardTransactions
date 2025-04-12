CREATE TABLE IF NOT EXISTS `operation_type` (
  `operation_type_id` INT NOT NULL,
  `description` VARCHAR(100) NULL,
  PRIMARY KEY (`operation_type_id`)
);

INSERT IGNORE INTO `operation_type` (`operation_type_id`, `description`) VALUES
(1, 'Normal Purchase'),
(2, 'Purchase with installments'),
(3, 'Withdrawal'),
(4, 'Payment');
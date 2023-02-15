resource "aws_ssm_parameter" "db-connection" {
  name  = "/exam-management-rds/rdsstring"
  type  = "String"
  value = var.rds_connection_path
}

resource "aws_ssm_parameter" "db-password" {
  name  = "/exam-management-rds/rdspassword"
  type  = "SecureString"
  value = var.rds_password
}

resource "aws_ssm_parameter" "db-username" {
  name  = "/exam-management-rds/rdsusername"
  type  = "String"
  value = var.rds_username
}
#resource "aws_db_instance" "rds-standard-instance" {
#  allocated_storage    = 10
#  db_name              = "postgres"
#  engine               = "mysql"
#  engine_version       = "5.7"
#  instance_class       = "db.t3.micro"
#  username             = "foo"
#  password             = "foobarbaz"
#  parameter_group_name = "default.mysql5.7"
#  skip_final_snapshot  = true
#}